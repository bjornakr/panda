package no.atferdssenteret.panda.controller;

import java.awt.Window;

import no.atferdssenteret.panda.controller.table.QuestionnaireEventTableController;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Questionnaire;
import no.atferdssenteret.panda.view.QuestionnaireDialog;

public class QuestionnaireController extends ApplicationController {
    private Questionnaire model;
    private QuestionnaireDialog view;
    
    public QuestionnaireController(Window parentWindow, Questionnaire model) {
	super(model);
	this.model = model;
	view = new QuestionnaireDialog(parentWindow, this, new QuestionnaireEventTableController().view());

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
	// TODO Auto-generated method stub
    }

    @Override
    protected void transferUserInputToModel() {
	// TODO Auto-generated method stub
    }
}
