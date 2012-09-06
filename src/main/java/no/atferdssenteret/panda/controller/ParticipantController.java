package no.atferdssenteret.panda.controller;

import java.awt.Window;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Participant;
import no.atferdssenteret.panda.util.StringUtil;
import no.atferdssenteret.panda.view.ParticipantDialog;

public class ParticipantController extends ApplicationController {
    private Participant model;
    private ParticipantDialog view;

    public ParticipantController(Window parentWindow, Participant model) {
	super(model);
	this.model = model;
	this.view = new ParticipantDialog(parentWindow, this);
	if (getMode() == Mode.EDIT) {
	    transferModelToView();
	}
	view.setVisible(true);
    }    
    
    @Override
    public String title() {
	return "Opprett/endre deltaker";
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
	view.setFirstName(model.getFirstName());
	view.setLastName(model.getLastName());
	view.setRole(model.getRole());
	view.setTlfNo(model.getTlfNo());
	view.seteMail(model.geteMail());
	view.setContactInfo(model.getContactInfo());
	view.setComment(model.getComment());
    }
    
    @Override
    protected void transferUserInputToModel() {
	if (getMode() == Mode.CREATE) {
	    model = new Participant();
	}
	model.setFirstName(StringUtil.groomString(view.getFirstName()));
	model.setLastName(StringUtil.groomString(view.getLastName()));
	model.setRole((String)view.getRole());
	model.setTlfNo(StringUtil.groomString(view.getTlfNo()));
	model.seteMail(StringUtil.groomString(view.geteMail()));
	model.setContactInfo(StringUtil.groomString(view.getContactInfo()));
	model.setComment(StringUtil.groomString(view.getComment()));
    }
}
