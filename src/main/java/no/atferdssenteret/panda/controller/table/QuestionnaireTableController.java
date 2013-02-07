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
import no.atferdssenteret.panda.model.DataCollection;
import no.atferdssenteret.panda.model.DataCollection_;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Questionnaire;
import no.atferdssenteret.panda.model.Questionnaire_;
import no.atferdssenteret.panda.model.Target;
import no.atferdssenteret.panda.model.Target_;
import no.atferdssenteret.panda.model.table.QuestionnaireTable;
import no.atferdssenteret.panda.model.table.QuestionnaireTableForDataCollectionView;
import no.atferdssenteret.panda.util.JPATransactor;
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
			view = new DefaultTablePanel(this, new QuestionnaireFilterCreator());
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
		}
		else if (event.getActionCommand().equals(ButtonUtil.COMMAND_EDIT)
				|| event.getActionCommand().equals(ButtonUtil.COMMAND_DOUBLE_CLICK)) {
			Questionnaire model = (Questionnaire)modelForSelectedTableRow();
			new QuestionnaireController(view.getWindow(), model);
			if (dataCollection == null) {
				updateTableModel();
			}
			else {
				tableModel.update(model);
			}
		}
	}

//	@Override
//	protected Class<? extends Model> getModelClass() {
//		return Questionnaire.class;
//	}
//	
//	@Override
//	protected String orderAttribute() {
//		return "dataCollection.type";
//	}
	
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
		Predicate[] predicates = new Predicate[filterValues.size()];
		for (int i = 0; i < filterValues.size(); i++) {
			predicates[i] = QuestionnaireFilterCreator.createPredicate(filterValues.get(i),
					root, joinQuestionnaireDataColleciton);
		}
		return predicates;
	}
}
