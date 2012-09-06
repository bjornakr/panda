package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;

import no.atferdssenteret.panda.controller.AbstractOverviewController;
import no.atferdssenteret.panda.controller.QuestionnaireController;
import no.atferdssenteret.panda.model.table.QuestionnaireTable;
import no.atferdssenteret.panda.view.AbstractOverviewTableModel;
import no.atferdssenteret.panda.view.DefaultOverviewPanel;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public class QuestionnaireTableController extends AbstractOverviewController {
    private DefaultOverviewPanel view;
    private QuestionnaireTable tableModel = new QuestionnaireTable();
    
    public QuestionnaireTableController() {
	super("Spørreskjemaer");
	view = new DefaultOverviewPanel(this, null);	
    }
    
    @Override
    public DefaultOverviewPanel view() {
	return view;
    }

    @Override
    public AbstractOverviewTableModel tableModel() {
	return tableModel;
    }
    
    @Override
    protected String getWarningBeforeDelete() {
	return null;
    }

    @Override
    public void evaluateActionEvent(ActionEvent event) {
	if (event.getActionCommand().equals(ButtonUtil.COMMAND_CREATE)) {
	    view.getWindow();
	    new QuestionnaireController(view.getWindow(), null); 
	}
    }
}
