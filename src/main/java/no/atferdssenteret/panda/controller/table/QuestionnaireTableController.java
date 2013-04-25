package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.JButton;

import no.atferdssenteret.panda.controller.QuestionnaireController;
import no.atferdssenteret.panda.filter.QuestionnaireFilterCreator;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.model.entity.DataCollection_;
import no.atferdssenteret.panda.model.entity.Questionnaire;
import no.atferdssenteret.panda.model.entity.QuestionnaireEvent;
import no.atferdssenteret.panda.model.entity.Questionnaire_;
import no.atferdssenteret.panda.model.entity.Target;
import no.atferdssenteret.panda.model.entity.Target_;
import no.atferdssenteret.panda.model.table.QuestionnaireTable;
import no.atferdssenteret.panda.model.table.QuestionnaireTableForDataCollectionView;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;
import no.atferdssenteret.panda.view.DefaultTablePanel;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public class QuestionnaireTableController extends AbstractTableController {
	private final static String COMMAND_REGISTER_QUESTIONNAIRE = "REGISTER_QUESTIONNAIRE";
	private DefaultTablePanel view;
	private DefaultAbstractTableModel tableModel;
	private DataCollection dataCollection;
	private List<JButton> buttons = new LinkedList<JButton>();

	public QuestionnaireTableController(DataCollection dataCollection) {
		super("Spørreskjemaer");
		this.dataCollection = dataCollection;

		if (dataCollection == null) {
			tableModel = new QuestionnaireTable();
			buttons = createButtons();
			view = new DefaultTablePanel(this, new QuestionnaireFilterCreator());
		}
		else {
			tableModel = new QuestionnaireTableForDataCollectionView();
			view = new DefaultTablePanel(this, null);
		}
		setButtonEnabledStates();
	}

	@Override
	public DefaultTablePanel view() {
		return view;
	}

	@Override
	public DefaultAbstractTableModel tableModel() {
		return tableModel;
	}

	public List<Questionnaire> currentModels() {
		List<Questionnaire> models = new LinkedList<Questionnaire>();
		for (Model model : tableModel.allModels()) {
			models.add((Questionnaire)model);
		}
		return models;		
	}

	private List<JButton> createButtons() {
		List<JButton> buttons = new LinkedList<JButton>();
		buttons.add(ButtonUtil.editButton(this));
		JButton butRegisterRecievedQuestionnaire = new JButton("Registrer mottatt skjema");
		butRegisterRecievedQuestionnaire.setActionCommand(COMMAND_REGISTER_QUESTIONNAIRE);
		butRegisterRecievedQuestionnaire.addActionListener(this);
		buttons.add(butRegisterRecievedQuestionnaire);
		return buttons;
	}

	@Override
	public List<JButton> buttons() {
		if (dataCollection == null) {
			return buttons;
		}
		else {
			return super.buttons();
		}
	}

	@Override
	protected void setButtonEnabledStates() {
		super.setButtonEnabledStates();
		boolean hasSelection = view().selectedTableRow() >= 0;
		for (JButton button : buttons()) {
			if (button.getActionCommand().equals(COMMAND_REGISTER_QUESTIONNAIRE)) {
				button.setEnabled(hasSelection);
			}
		}
	}

	@Override
	public void evaluateActionEvent(ActionEvent event) {
		if (event.getActionCommand().equals(ButtonUtil.COMMAND_CREATE)) {
			QuestionnaireController questionnaireController = new QuestionnaireController(view.getWindow(), null);
			if (questionnaireController.model() != null) {
//				dataCollection.addQuestionnaire((Questionnaire)questionnaireController.model());
//				((Questionnaire)questionnaireController.model()).setDataCollection(dataCollection);
				tableModel.addRow(questionnaireController.model());
//				tableModel.setModels(dataCollection.getQuestionnaires());
			}
		}
		else if (event.getActionCommand().equals(ButtonUtil.COMMAND_EDIT)
				|| event.getActionCommand().equals(ButtonUtil.COMMAND_DOUBLE_CLICK)) {
			Questionnaire model = (Questionnaire)modelForSelectedTableRow();
			new QuestionnaireController(view.getWindow(), model);
//			tableModel.setModels(dataCollection.getQuestionnaires());
			tableModel.update(model);
//			tableModel.addRow(controller.model());
//			updateTableModel(); // funker ikke
		}
		else if (event.getActionCommand().equals(COMMAND_REGISTER_QUESTIONNAIRE)) {
			registerRecievedQuestionnaire();
		}
		setButtonEnabledStates();
	}

	private void registerRecievedQuestionnaire() {
		Questionnaire questionnaire = (Questionnaire)modelForSelectedTableRow();
		JPATransactor.getInstance().transaction().begin();
		questionnaire.createEvent(QuestionnaireEvent.Types.RECIEVED);
		JPATransactor.getInstance().transaction().commit();
		updateTableModel();
	}

	@Override
	protected List<? extends Model> retrieve(List<Object> filterValues) {
		CriteriaBuilder criteriaBuilder = JPATransactor.getInstance().criteriaBuilder();
		CriteriaQuery<Questionnaire> criteriaQuery = criteriaBuilder.createQuery(Questionnaire.class);
		Root<Questionnaire> root = criteriaQuery.from(Questionnaire.class);
		Join<Questionnaire, DataCollection> joinQuestionnaireDataColleciton = root.join(Questionnaire_.dataCollection);
		Join<DataCollection, Target> joinQuestionnaireTarget = joinQuestionnaireDataColleciton.join(DataCollection_.target);
		criteriaQuery.where(extractPredicatesFromFilterValues(filterValues, root, joinQuestionnaireDataColleciton));
		criteriaQuery.orderBy(criteriaBuilder.asc(joinQuestionnaireTarget.get(Target_.id)),
				criteriaBuilder.asc(joinQuestionnaireDataColleciton.get(DataCollection_.type)),
				criteriaBuilder.asc(root.get(Questionnaire_.name)));
		return JPATransactor.getInstance().entityManager().createQuery(criteriaQuery).getResultList();
	}

	private Predicate[] extractPredicatesFromFilterValues(List<Object> filterValues,
			Root<Questionnaire> root, Join<Questionnaire, DataCollection> joinQuestionnaireDataColleciton) {
		Predicate[] predicates = new Predicate[filterValues.size()+1];
		for (int i = 0; i < filterValues.size(); i++) {
			predicates[i] = QuestionnaireFilterCreator.createPredicate(filterValues.get(i),
					root, joinQuestionnaireDataColleciton);
		}
		predicates[predicates.length-1] = completedDataCollections(joinQuestionnaireDataColleciton);
		return predicates;
	}

	private Predicate completedDataCollections(Join<Questionnaire, DataCollection> joinQuestionnaireDataColleciton) {
		CriteriaBuilder criteriaBuilder = JPATransactor.getInstance().criteriaBuilder();
		Predicate completedDataCollecitons = criteriaBuilder.
				equal(joinQuestionnaireDataColleciton.get(DataCollection_.progressStatus), DataCollection.ProgressStatuses.COMPLETED);
		Predicate initiatedDataCollections = criteriaBuilder.
				equal(joinQuestionnaireDataColleciton.get(DataCollection_.progressStatus), DataCollection.ProgressStatuses.INITIATED);
		return criteriaBuilder.or(completedDataCollecitons, initiatedDataCollections);
	}
}
