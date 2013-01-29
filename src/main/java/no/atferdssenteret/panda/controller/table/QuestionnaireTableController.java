package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;

import no.atferdssenteret.panda.controller.QuestionnaireController;
import no.atferdssenteret.panda.filter.QuestionnaireFilter;
import no.atferdssenteret.panda.model.DataCollection;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Questionnaire;
import no.atferdssenteret.panda.model.table.QuestionnaireTable;
import no.atferdssenteret.panda.model.table.QuestionnaireTableForDataCollectionView;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;
import no.atferdssenteret.panda.view.DefaultTablePanel;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public class QuestionnaireTableController extends AbstractTableController {
	private DefaultTablePanel view;
	private DefaultAbstractTableModel tableModel;
	private DataCollection dataCollection;

	public QuestionnaireTableController(DataCollection dataCollection) {
		super("Spørreskjemaer");
		this.dataCollection = dataCollection;
		
		if (dataCollection == null) {
			tableModel = new QuestionnaireTable();
			view = new DefaultTablePanel(this, new QuestionnaireFilter());
		}
		else {
			tableModel = new QuestionnaireTableForDataCollectionView();
			view = new DefaultTablePanel(this, null);
		}
	}

	@Override
	public DefaultTablePanel view() {
		return view;
	}

	@Override
	public DefaultAbstractTableModel tableModel() {
		return tableModel;
	}

	@Override
	protected String getWarningBeforeDelete() {
		return null;
	}
//
//	public List<Questionnaire> retrieveAllModels() {
//		TypedQuery<Questionnaire> query = JPATransactor.getInstance().entityManager().createQuery(
//				"SELECT q FROM Questionnaire q", Questionnaire.class);
//		return query.getResultList();
//	}
//
//	@Override
//	protected List<? extends Model> retrieve(Predicate[] predicates) {
//		return retrieveAllModels();
//	}
//	@Override
//	protected List<? extends Model> retrieve(Predicate[] predicates) {
//		if (dataCollection != null) {
//			return dataCollection.getQuestionnaires();
//		}
//		else {
//			return super.retrieve(predicates);
//		}
//	}    
	
//	@Override
//	protected List<? extends Model> retrieve(Predicate[] predicates) {
//		CriteriaBuilder criteriaBuilder = JPATransactor.getInstance().entityManager().getCriteriaBuilder();
//		CriteriaQuery<? extends Model> criteriaQuery = criteriaBuilder.createQuery(getModelClass());
//		Root<Questionnaire> qRoot = criteriaQuery.from(Questionnaire.class);
////		Root<QuestionnaireEvent> qeRoot = criteriaQuery.from(QuestionnaireEvent.class);
////		criteriaQuery.multiselect(qRoot, qeRoot);
////		criteriaQuery.where(criteriaBuilder.equal(qRoot.get(Questionnaire_.id), qeRoot.get(QuestionnaireEvent_.questionnaire)));
////		criteriaQuery.where(predicates);
//		Join<Questionnaire, QuestionnaireEvent> join = qRoot.join(Questionnaire_.questionnaireEvents);
//		criteriaQuery.multiselect(join);
//	}
	
	
	public List<Questionnaire> currentModels() {
		List<Questionnaire> models = new LinkedList<Questionnaire>();
		for (Model model : tableModel.allModels()) {
			models.add((Questionnaire)model);
		}
		return models;		
	}

	@Override
	public List<JButton> buttons() {
		if (dataCollection == null) {
			return new LinkedList<JButton>();
		}
		else {
			return super.buttons();
		}
	}
	
	@Override
	public void evaluateActionEvent(ActionEvent event) {
		if (event.getActionCommand().equals(ButtonUtil.COMMAND_CREATE)) {
			QuestionnaireController questionnaireController = new QuestionnaireController(view.getWindow(), null);
			if (questionnaireController.model() != null) {
				tableModel.addRow(questionnaireController.model());
			}
//			updateTableModel();
		}
		else if (event.getActionCommand().equals(ButtonUtil.COMMAND_EDIT)
				|| event.getActionCommand().equals(ButtonUtil.COMMAND_DOUBLE_CLICK)) {
			Questionnaire model = (Questionnaire)modelForSelectedTableRow();
			new QuestionnaireController(view.getWindow(), model);
			tableModel.update(model);
//			updateTableModel();
		}
	}

	@Override
	protected Class<? extends Model> getModelClass() {
		return Questionnaire.class;
	}
}
