package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

import no.atferdssenteret.panda.controller.QuestionnaireController;
import no.atferdssenteret.panda.model.DataCollection;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Questionnaire;
import no.atferdssenteret.panda.model.table.QuestionnaireTable;
import no.atferdssenteret.panda.model.table.QuestionnaireTableUnderDataCollection;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;
import no.atferdssenteret.panda.view.DefaultTablePanel;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public class QuestionnaireTableController extends AbstractTableController {
	private DefaultTablePanel view;
	private DefaultAbstractTableModel tableModel;
//	private DataCollection dataCollection;

	public QuestionnaireTableController(DataCollection dataCollection) {
		super("Spørreskjemaer");
		if (dataCollection == null) {
			tableModel = new QuestionnaireTable();
		}
		else {
			tableModel = new QuestionnaireTableUnderDataCollection();
		}
		view = new DefaultTablePanel(this, null);	
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
	
	@Override
	protected List<? extends Model> retrieve(Predicate[] predicates) {
		CriteriaBuilder criteriaBuilder = JPATransactor.getInstance().entityManager().getCriteriaBuilder();
		CriteriaQuery<? extends Model> criteriaQuery = criteriaBuilder.createQuery(getModelClass());
		criteriaQuery.where(predicates);
		return JPATransactor.getInstance().entityManager().createQuery(criteriaQuery).getResultList();
	}
	
	
	public List<Questionnaire> currentModels() {
		List<Questionnaire> models = new LinkedList<Questionnaire>();
		for (Model model : tableModel.allModels()) {
			models.add((Questionnaire)model);
		}
		return models;		
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
