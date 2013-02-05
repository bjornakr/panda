package no.atferdssenteret.panda.controller;

import java.awt.Window;
import java.awt.event.ActionEvent;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.User;
import no.atferdssenteret.panda.util.StringUtil;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public class UserController extends ApplicationController {
	private User model;
	private UserDialog view;

	public UserController(Window parentWindow, User model) {
		super(model);


		this.model = model;
		view = new UserDialog(parentWindow, this);
		if (getMode() == Mode.EDIT) {
			if (MainController.session.user().getAccessLevel().value() <= model.getAccessLevel().value()) {
				throw new RuntimeException("Du har ikke adgang til å endre brukere med likt eller høyere adgangsnivå.");
			}
			transferModelToView();
		}
		if (MainController.session.user().getAccessLevel().value() < User.AccessLevel.ADMINISTRATOR.value()) {
			throw new IllegalStateException("Access violation. User cannot manage other users.");
		}
		if (MainController.session.user().getAccessLevel().value() < User.AccessLevel.SUPER_USER.value()) {
			view.restrictAccess();
		}
		view.setVisible(true);
	}

	@Override
	public String title() {
		return "Rediger bruker";
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
		view.setUsername(model.getUserName());
		view.setAccessLevel(model.getAccessLevel());
		view.setFirstName(model.getFirstName());
		view.setTxtLastName(model.getLastName());
	}

	@Override
	protected void transferUserInputToModel() {
		if (getMode() == Mode.CREATE) {
			model = new User();
		}

		model.setUserName(StringUtil.groomString(view.getUserName()));
		model.setAccessLevel((User.AccessLevel)view.getAccessLevel());
		model.setFirstName(StringUtil.groomString(view.getFirstName()));
		model.setLastName(StringUtil.groomString(view.getLastName()));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (getMode() == Mode.CREATE
				&& e.getActionCommand().equals(ButtonUtil.COMMAND_CREATE)
				&& User.userNameExists(model.getUserName())) {
			throw new IllegalArgumentException("Brukernavnet \"" + model.getUserName() + "\" er allerede registrert.");
		}
		super.actionPerformed(e);
	}
}
