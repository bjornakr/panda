package no.atferdssenteret.panda.controller;

import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import no.atferdssenteret.panda.model.entity.User;
import no.atferdssenteret.panda.model.entity.User.AccessLevel;
import no.atferdssenteret.panda.view.util.ButtonUtil;
import no.atferdssenteret.panda.view.util.DefaultTextField;
import no.atferdssenteret.panda.view.util.GridBagLayoutAutomat;
import no.atferdssenteret.panda.view.util.GuiUtil;
import no.atferdssenteret.panda.view.util.LabelFieldPair;

public class UserDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	public static final String CMD_SET_PASSWORD = "SET_PASSWORD";
	private ActionListener actionListener;
	private DefaultTextField txtUsername = new DefaultTextField();
	private JComboBox cboxAccessLevel = new JComboBox(User.AccessLevel.values());
	private DefaultTextField txtFirstName = new DefaultTextField();
	private DefaultTextField txtLastName = new DefaultTextField();
	
	public UserDialog(Window parentWindow, ActionListener actionListener) {
		this.actionListener = actionListener;
		setModalityType(ModalityType.APPLICATION_MODAL);
		layoutContent();
		pack();
		setLocationRelativeTo(parentWindow);
	}

	private void layoutContent() {
		setLayout(new GridBagLayout());
		
		List<LabelFieldPair> labelsAndFields = new LinkedList<LabelFieldPair>();
		JLabel labUserName = new JLabel("Brukernavn");
		GuiUtil.setNotNullFlag(labUserName);
		labelsAndFields.add(new LabelFieldPair(labUserName, txtUsername));
		labelsAndFields.add(new LabelFieldPair(new JLabel("Adgangsnivå"), cboxAccessLevel));
		JLabel labFirstName = new JLabel("Fornavn");
		GuiUtil.setNotNullFlag(labFirstName);
		labelsAndFields.add(new LabelFieldPair(labFirstName, txtFirstName));
		JLabel labLastName = new JLabel("Etternavn");
		GuiUtil.setNotNullFlag(labLastName);
		labelsAndFields.add(new LabelFieldPair(labLastName, txtLastName));
		JButton butPassword = new JButton("Passord");
		butPassword.setActionCommand(CMD_SET_PASSWORD);
		JPasswordField txtPassword = new JPasswordField(10);
		txtPassword.setText("abcde");
		txtPassword.setEditable(false);
		labelsAndFields.add(new LabelFieldPair(butPassword, txtPassword));
		add(GridBagLayoutAutomat.createPanelFor(labelsAndFields, true), GridBagLayoutAutomat.typicalConstraintsForPanel(0, 0));
		add(ButtonUtil.createSaveCancelButtonPanel(actionListener, this), GridBagLayoutAutomat.constraintsForButtonPanel(1));
	}
	
	public void restrictAccess() {
		cboxAccessLevel.removeItem(AccessLevel.SUPER_USER);
		cboxAccessLevel.removeItem(AccessLevel.ADMINISTRATOR);
	}

	public String getUserName() {
		return txtUsername.getText();
	}

	public void setUsername(String username) {
		txtUsername.setText(username);
	}

	public Object getAccessLevel() {
		return cboxAccessLevel.getSelectedItem();
	}

	public void setAccessLevel(Object accessLevel) {
		this.cboxAccessLevel.setSelectedItem(accessLevel);
	}

	public String getFirstName() {
		return txtFirstName.getText();
	}

	public void setFirstName(String firstName) {
		txtFirstName.setText(firstName);
	}

	public String getLastName() {
		return txtLastName.getText();
	}

	public void setTxtLastName(String lastName) {
		txtLastName.setText(lastName);
	}

	
	
	
//	public char[] getPassword() {
//		return butPassword;
//	}
//
//	public void setButPassword(JButton butPassword) {
//		this.butPassword = butPassword;
//	}
}
