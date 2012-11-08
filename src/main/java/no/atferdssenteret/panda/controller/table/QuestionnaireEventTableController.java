package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import no.atferdssenteret.panda.controller.QuestionnaireEventController;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.QuestionnaireEvent;
import no.atferdssenteret.panda.model.table.QuestionnaireEventTable;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;
import no.atferdssenteret.panda.view.DefaultTablePanel;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public class QuestionnaireEventTableController extends AbstractTableController {
	private QuestionnaireEventTable tableModel = new QuestionnaireEventTable();
	private DefaultTablePanel view;

	public QuestionnaireEventTableController() {
		super("Hendelser");
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

	public List<QuestionnaireEvent> allModels() {
		List<QuestionnaireEvent> models = new LinkedList<QuestionnaireEvent>();
		for (Model model : tableModel.allModels()) {
			models.add((QuestionnaireEvent)model);
		}
		return models;
	}

	@Override
	protected List<? extends Model> retrieve(Predicate[] predicates) {
		return allModels();
	}

	public List<QuestionnaireEvent> currentModels() {
		List<QuestionnaireEvent> models = new LinkedList<QuestionnaireEvent>();
		for (Model model : tableModel.allModels()) {
			models.add((QuestionnaireEvent)model);
		}
		return models;
	}

	@Override
	public void evaluateActionEvent(ActionEvent event) {
		if (event.getActionCommand().equals(ButtonUtil.COMMAND_CREATE)) {
			QuestionnaireEventController questionnaireEventController = new QuestionnaireEventController(view.getWindow(), null);
			if (questionnaireEventController.model() != null) {
				tableModel.addRow(questionnaireEventController.model());
			}
		}
		else if (event.getActionCommand().equals(ButtonUtil.COMMAND_EDIT)
				|| event.getActionCommand().equals(ButtonUtil.COMMAND_DOUBLE_CLICK)) {
			QuestionnaireEvent model = (QuestionnaireEvent)modelForSelectedTableRow();
			new QuestionnaireEventController(view.getWindow(), model);
			tableModel.update(model);
		}
	}
}
