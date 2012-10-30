package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.TypedQuery;

import no.atferdssenteret.panda.controller.QuestionnaireController;
import no.atferdssenteret.panda.model.DataCollection;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Questionnaire;
import no.atferdssenteret.panda.model.table.QuestionnaireTable;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;
import no.atferdssenteret.panda.view.DefaultTablePanel;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public class QuestionnaireTableController extends AbstractTableController {
	private DefaultTablePanel view;
	private QuestionnaireTable tableModel = new QuestionnaireTable();

	public QuestionnaireTableController() {
		super("Spørreskjemaer");
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

	public List<Questionnaire> retrieveAllModels() {
		TypedQuery<Questionnaire> query = JPATransactor.getInstance().entityManager().createQuery(
				"SELECT q FROM Questionnaire q", Questionnaire.class);
		return query.getResultList();
	}

	@Override
	protected List<? extends Model> retrieveModelsForCurrentConditions() {
		return retrieveAllModels();
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
		}
		else if (event.getActionCommand().equals(ButtonUtil.COMMAND_EDIT)
				|| event.getActionCommand().equals(ButtonUtil.COMMAND_DOUBLE_CLICK)) {
			new QuestionnaireController(view.getWindow(), (Questionnaire)modelForSelectedTableRow());
			updateTableModel();
		}
	}
}
