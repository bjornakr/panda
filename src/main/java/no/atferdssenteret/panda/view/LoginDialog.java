package no.atferdssenteret.panda.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import no.atferdssenteret.panda.view.util.ButtonUtil;
import no.atferdssenteret.panda.view.util.DefaultTextField;
import no.atferdssenteret.panda.view.util.GridBagLayoutAutomat;
import no.atferdssenteret.panda.view.util.LabelFieldPair;

public class LoginDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private final DefaultTextField txtUsername = new DefaultTextField(8);
	private final JPasswordField txtPassword = new JPasswordField(8);
	
	public LoginDialog(Window parentWindow, ActionListener actionListener) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setLayout(new GridBagLayout());
		
		List<LabelFieldPair> labelsAndFields = new LinkedList<LabelFieldPair>();
		labelsAndFields.add(new LabelFieldPair(new JLabel("Brukernavn"), txtUsername));
		labelsAndFields.add(new LabelFieldPair(new JLabel("Passord"), txtPassword));
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		add(GridBagLayoutAutomat.createPanelFor(labelsAndFields, true), c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_END;
		add(ButtonUtil.createOkCancelButtonPanel(actionListener, this), c);
		
		pack();
		setLocationRelativeTo(parentWindow);
	}
	
	public String getUsername() {
		return txtUsername.getText();
	}
	
	public char[] getPassword() {
		return txtPassword.getPassword();
	}
}
