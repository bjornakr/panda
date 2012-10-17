package no.atferdssenteret.panda.controller;

import java.awt.Window;
import java.awt.event.ActionEvent;

import no.atferdssenteret.panda.controller.table.QuestionnaireEventTableController;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Questionnaire;
import no.atferdssenteret.panda.view.QuestionnaireDialog;

public class QuestionnaireController extends ApplicationController {
    private Questionnaire model;
    private QuestionnaireDialog view;
    private QuestionnaireEventTableController questionnaireEventTableController = new QuestionnaireEventTableController();
    
    public QuestionnaireController(Window parentWindow, Questionnaire model) {
	super(model);
	this.model = model;
	view = new QuestionnaireDialog(parentWindow, this, questionnaireEventTableController.view());
	
	if (getMode() == Mode.EDIT) {
	    transferModelToView();
	}
	
	view.setVisible(true);
    }

    @Override
    public String title() {
	return "Rediger spørreskjema";
    }

    @Override
    public Model model() {
	return model;
    }

    @Override
    public Window view() {
	return view;
    }

    @Override
    public void transferModelToView() {
	view.setQuestionnaireName(model.getName());
	questionnaireEventTableController.updateTableModel();
    }

    @Override
    protected void transferUserInputToModel() {
	if (getMode() == Mode.CREATE) {
	    model = new Questionnaire();
	}
	model.setName((String)view.getQuestionnaireName());
	model.setEvents(questionnaireEventTableController.allModels());
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
	if (event.getActionCommand().equals(COMMAND_SAVE)) {
	    transferUserInputToModel();
	    view.dispose();
	}
	else if (event.getActionCommand().equals(COMMAND_CANCEL)) {
	    view().dispose();
	}
    }
}
