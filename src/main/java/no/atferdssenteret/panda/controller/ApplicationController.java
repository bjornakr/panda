package no.atferdssenteret.panda.controller;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.view.ErrorMessageDialog;

public abstract class ApplicationController implements ActionListener {
	public enum Mode {CREATE, EDIT};
	public static final String COMMAND_SAVE = "SAVE";
	public static final String COMMAND_CANCEL = "CANCEL";
	private Mode mode;

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

	public abstract String title();

	public abstract Model model();

	public abstract Window view();

	public abstract void transferModelToView();

	protected abstract void transferUserInputToModel();

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals(COMMAND_SAVE)) {
			save();
		}
		else if (event.getActionCommand().equals(COMMAND_CANCEL)) {
			view().dispose();
		}
	}

	private void save() {
		try {
			mergeModelIfDetached();
			performTransaction();
			view().dispose();
		}
		catch (Exception e) {
			e.printStackTrace();
			JPATransactor.getInstance().transaction().rollback();
			new ErrorMessageDialog(e.getMessage(), null, view());
		}
	}

	protected void performTransaction() {
		JPATransactor.getInstance().transaction().begin();
		transferUserInputToModel();
		model().validate();
		if (mode == Mode.CREATE) {
			JPATransactor.getInstance().entityManager().persist(model());
		}
		JPATransactor.getInstance().transaction().commit();
	}

	private void mergeModelIfDetached() {
		if (model() != null) {
			if (!JPATransactor.getInstance().entityManager().contains(model())) {
				setModel(JPATransactor.getInstance().entityManager().merge(model()));
			}
		}
	}

	protected abstract void setModel(Model model);
}

