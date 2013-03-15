package no.atferdssenteret.panda.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import no.atferdssenteret.panda.view.util.ButtonUtil;
import no.atferdssenteret.panda.view.util.GridBagLayoutAutomat;
import no.atferdssenteret.panda.view.util.LabelFieldPair;

public class PasswordDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPasswordField txtPassword = new JPasswordField(8);
	private boolean isPasswordCreated = false;
	
	public PasswordDialog(Window parentWindow) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setLayout(new GridBagLayout());
		layoutContent();
		pack();
		setLocationRelativeTo(parentWindow);
	}
	
	private void layoutContent() {
		List<LabelFieldPair> labelsAndFields = new LinkedList<LabelFieldPair>();
		labelsAndFields.add(new LabelFieldPair(new JLabel("Passord"), txtPassword));
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		add(GridBagLayoutAutomat.createPanelFor(labelsAndFields, true), c);		

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		add(ButtonUtil.createOkCancelButtonPanel(new ButtonListener(), this), c);
	}
	
	public boolean isPasswordCreated() {
		return isPasswordCreated;
	}
	
	public char[] getPassword() {
		return txtPassword.getPassword();
	}
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getActionCommand().equals(ButtonUtil.COMMAND_OK)) {
				isPasswordCreated = true;
			}
			dispose();
		}
	}
}
