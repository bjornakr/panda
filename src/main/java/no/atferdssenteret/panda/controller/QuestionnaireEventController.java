package no.atferdssenteret.panda.controller;

import java.awt.Window;
import java.awt.event.ActionEvent;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.QuestionnaireEvent;
import no.atferdssenteret.panda.util.DateUtil;
import no.atferdssenteret.panda.util.StringUtil;
import no.atferdssenteret.panda.view.QuestionnaireEventDialog;

public class QuestionnaireEventController extends ApplicationController {
    private QuestionnaireEvent model;
    private QuestionnaireEventDialog view;
    
    public QuestionnaireEventController(Window mainWindow, QuestionnaireEvent model) {
	super(model);
	this.model = model;
	view = new QuestionnaireEventDialog(mainWindow, this);
	
	if (getMode() == Mode.EDIT) {
	    transferModelToView();
	}
	
	view.setVisible(true);
    }

    @Override
    public String title() {
	return "Rediger hendelse";
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
	System.out.println(model.getDate());
	view.setDate(DateUtil.formatDate(model.getDate()));
	view.setType(model.getType());
	view.setComment(model.getComment());	
    }

    @Override
    protected void transferUserInputToModel() {
	if (getMode() == Mode.CREATE) {
	    model = new QuestionnaireEvent();
	}
	model.setDate(StringUtil.parseDate(view.getDate()));
	model.setType((QuestionnaireEvent.Types)view.getType());
	model.setComment(StringUtil.groomString(view.getComment()));
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
