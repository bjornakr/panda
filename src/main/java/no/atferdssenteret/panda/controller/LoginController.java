package no.atferdssenteret.panda.controller;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import no.atferdssenteret.panda.InvalidUserInputException;
import no.atferdssenteret.panda.model.Session;
import no.atferdssenteret.panda.model.entity.User;
import no.atferdssenteret.panda.model.entity.Version;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.view.ErrorMessageDialog;
import no.atferdssenteret.panda.view.LoginDialog;
import no.atferdssenteret.panda.view.util.ButtonUtil;

import org.jasypt.util.password.BasicPasswordEncryptor;

public class LoginController implements ActionListener, WindowListener {
	private final static String INVALID_LOGIN = "Ugyldig brukernavn/passord.";
	private LoginDialog view;
	private final double appVersion;

	public LoginController(Window parentWindow, double appVersion) {
		this.appVersion = appVersion;
		view = new LoginDialog(parentWindow, this);
		view.addWindowListener(this);
		view.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals(ButtonUtil.COMMAND_CANCEL)) {
			System.exit(0);
		}
		else if (event.getActionCommand().equals(ButtonUtil.COMMAND_OK)) {
			try {
				login(view.getUsername(), view.getPassword());
				view.dispose();
			} catch (InvalidUserInputException e) {
				new ErrorMessageDialog(e.getMessage(), null, view);
			}
		}
	}

	public void login(String username, char[] password) throws InvalidUserInputException {
		if (username == null || password == null
				|| username.length() == 0 || password.length == 0) {
			throw new InvalidUserInputException(INVALID_LOGIN);
		}
		EntityManager em = JPATransactor.getInstance().entityManager();
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = '" + username + "'", User.class);
		if (query.getResultList().size() == 0) {
			throw new InvalidUserInputException(INVALID_LOGIN);						
		}
		User user = query.getSingleResult();
		BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
		if (!encryptor.checkPassword(new String(password), user.getEncryptedPassword())) {
			throw new InvalidUserInputException(INVALID_LOGIN);
		}
		if (user.getAccessLevel() == User.AccessLevel.NO_ACCESS) {
			throw new InvalidUserInputException(INVALID_LOGIN);			
		}

		checkVersion();

		new Session(user);
	}

	private void checkVersion() {
		String queryString = "SELECT v FROM Version v";
		TypedQuery<Version> query = JPATransactor.getInstance().entityManager().createQuery(queryString, Version.class);
		List<Version> versions = query.getResultList();
		Version version = null;
		if (versions.size() > 0) {
			version = versions.get(0);
		}

		if (version != null && appVersion < version.getRequiredVersion()) {
			new ErrorMessageDialog("Denne versjonen av systemet er foreldet. Du kjører versjon " + appVersion + ". " +
					"Siste versjon er " + version.getRequiredVersion() + ".", null, view);
			System.exit(0);
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}
}
