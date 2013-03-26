package no.atferdssenteret.panda.controller;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import no.atferdssenteret.panda.InvalidUserInputException;
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
			setModel(JPATransactor.getInstance().mergeIfDetached(model));
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
		System.out.println("We are here: " + title() + ", " + event.getActionCommand());
		if (event.getActionCommand().equals(COMMAND_SAVE)) {
			save();
		}
		else if (event.getActionCommand().equals(COMMAND_CANCEL)) {
			view().dispose();
		}
	}

	private void save() {
		try {
			if (mode == Mode.EDIT) {
				setModel(JPATransactor.getInstance().mergeIfDetached(model()));
			}
			performTransaction();
			view().dispose();
		}
		catch (InvalidUserInputException e) {
			if (JPATransactor.getInstance().transaction().isActive()) {
				JPATransactor.getInstance().transaction().rollback();
			}
			new ErrorMessageDialog(e.getMessage(), null, view());
		}
		catch (Exception e) {
			if (JPATransactor.getInstance().transaction().isActive()) {
				JPATransactor.getInstance().transaction().rollback();
			}
			e.printStackTrace();
		}
		finally {
			System.out.println("Closing entity manager.");
			JPATransactor.getInstance().entityManager().close();
		}
	}

	protected void performTransaction() {
		JPATransactor.getInstance().transaction().begin();
		transferUserInputToModel();
		model().validateUserInput();
		System.out.println(model());
		if (mode == Mode.CREATE) {
			JPATransactor.getInstance().entityManager().persist(model());
		}
		JPATransactor.getInstance().transaction().commit();
	}

	protected abstract void setModel(Model model);
}

