package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;

import no.atferdssenteret.panda.controller.AbstractOverviewController;
import no.atferdssenteret.panda.model.table.QuestionnaireEventTable;
import no.atferdssenteret.panda.view.AbstractOverviewTableModel;
import no.atferdssenteret.panda.view.DefaultOverviewPanel;

public class QuestionnaireEventTableController extends AbstractOverviewController {
    private QuestionnaireEventTable tableModel = new QuestionnaireEventTable();
    private DefaultOverviewPanel view = new DefaultOverviewPanel(this, null);
    
    public QuestionnaireEventTableController() {
	super("Hendelser");
    }

    @Override
    protected String getWarningBeforeDelete() {
	return null;
    }

    @Override
    public void evaluateActionEvent(ActionEvent event) {
	// TODO Auto-generated method stub
    }

    @Override
    public DefaultOverviewPanel view() {
	return view;
    }

    @Override
    public AbstractOverviewTableModel tableModel() {
	return tableModel;
    }
}
