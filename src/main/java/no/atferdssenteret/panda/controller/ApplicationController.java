package no.atferdssenteret.panda.controller;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.util.JPATransactor;

public abstract class ApplicationController implements ActionListener {
    public enum Mode {CREATE, EDIT};
    public static final String COMMAND_SAVE = "SAVE";
    public static final String COMMAND_CANCEL = "CANCEL";
    private Mode mode;
//    private Model model;
//    private boolean databaseHasChanged = false;

    public ApplicationController(Model model) {
	if (model == null) {
	    mode = Mode.CREATE;
	}
	else {
	    mode = Mode.EDIT;
	}
    }

    public Mode getMode() {
	return mode;
    }

//    public boolean databaseHasChanged() {
//	return databaseHasChanged;
//    }
//
//    public void setDatabaseHasChanged(boolean databaseHasChanged) {
//	this.databaseHasChanged = databaseHasChanged;
//    }

    public abstract String title();

    public abstract Model model();

    public abstract Window view();

    public abstract void transferModelToView();
    
    protected abstract void transferUserInputToModel();

    @Override
    public void actionPerformed(ActionEvent event) {
	if (event.getActionCommand().equals(COMMAND_SAVE)) {
	    transferUserInputToModel();
	    if (mode == Mode.CREATE) {
		JPATransactor.getInstance().persist(model());
	    }
	    else if (mode == Mode.EDIT) {
		JPATransactor.getInstance().transaction().begin();
		JPATransactor.getInstance().transaction().commit();
	    }	    
	    view().dispose();
	}
	else if (event.getActionCommand().equals(COMMAND_CANCEL)) {
	    view().dispose();
	}
    }
}

