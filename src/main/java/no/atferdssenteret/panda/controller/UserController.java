package no.atferdssenteret.panda.controller;

import java.awt.Window;
import java.awt.event.ActionEvent;

import org.jasypt.util.password.BasicPasswordEncryptor;

import no.atferdssenteret.panda.InvalidUserInputException;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Session;
import no.atferdssenteret.panda.model.entity.User;
import no.atferdssenteret.panda.util.StringUtil;
import no.atferdssenteret.panda.view.PasswordDialog;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public class UserController extends ApplicationController {
	private User model;
	private UserDialog view;
	private PasswordDialog passwordDialog; 

	public UserController(Window parentWindow, User model) {
		super(model);


		this.model = model;
		view = new UserDialog(parentWindow, this);
		if (getMode() == Mode.EDIT) {
			if (Session.currentSession.user().getAccessLevel().value() <= model.getAccessLevel().value()) {
				throw new RuntimeException("Du har ikke adgang til å endre brukere med likt eller høyere adgangsnivå.");
			}
			transferModelToView();
			view.showPasswordIsSet(true);
		}
		if (Session.currentSession.user().getAccessLevel().value() < User.AccessLevel.ADMINISTRATOR.value()) {
			throw new IllegalStateException("Access violation. User cannot manage other users.");
		}
		if (Session.currentSession.user().getAccessLevel().value() < User.AccessLevel.SUPER_USER.value()) {
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
		view.setUsername(model.getUsername());
		view.setAccessLevel(model.getAccessLevel());
		view.setFirstName(model.getFirstName());
		view.setTxtLastName(model.getLastName());
	}

	@Override
	protected void transferUserInputToModel() {
		if (getMode() == Mode.CREATE) {
			model = new User();
		}

		model.setUsername(StringUtil.groomString(view.getUsername()));
		model.setAccessLevel((User.AccessLevel)view.getAccessLevel());
		model.setFirstName(StringUtil.groomString(view.getFirstName()));
		model.setLastName(StringUtil.groomString(view.getLastName()));
		System.out.println("Svapp?");
		if (passwordDialog != null && passwordDialog.isPasswordCreated()) {
			String password = new String(passwordDialog.getPassword());
			System.out.println(password);
			validatePassword(password);
			BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
			System.out.println(encryptor.encryptPassword(password));
			model.setEncryptedPassword(encryptor.encryptPassword(password));
		}
	}

	private void validatePassword(String password) {
		if (password.length() < 6) {
			throw new InvalidUserInputException("Passordet må være minst seks tegn.");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		if (getMode() == Mode.CREATE
				&& e.getActionCommand().equals(ButtonUtil.COMMAND_CREATE)
				&& User.userNameExists(model.getUsername())) {
			throw new IllegalArgumentException("Brukernavnet \"" + model.getUsername() + "\" er allerede registrert.");
		}
		else if (e.getActionCommand().equals(UserDialog.CMD_SET_PASSWORD)) {
			passwordDialog = new PasswordDialog(view);
			passwordDialog.setVisible(true);
			view.showPasswordIsSet(passwordDialog.isPasswordCreated());
		}
		super.actionPerformed(e);
	}
	
	@Override
	protected void setModel(Model model) {
		this.model = (User)model;
	}
}
