package no.atferdssenteret.panda.controller;

import java.awt.Window;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.User;
import no.atferdssenteret.panda.util.StringUtil;

public class UserController extends ApplicationController {
	private User model;
	private UserDialog view;
	
	public UserController(Window parentWindow, User model) {
		super(model);
		this.model = model;
		view = new UserDialog(parentWindow, this);
		if (getMode() == Mode.EDIT) {
			transferModelToView();
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

}
