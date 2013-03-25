package no.atferdssenteret.panda.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import no.atferdssenteret.panda.util.StringUtil;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public class ErrorMessageDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	public ErrorMessageController controller = new ErrorMessageController();
	private Exception exception;
	private JButton butClose = new JButton("Lukk");
	private JButton butDetails = new JButton("Detaljer");

	public ErrorMessageDialog(String message, Exception exception, Window parentWindow) {
		this.exception = exception;
		setModalityType(ModalityType.APPLICATION_MODAL);
		JPanel mainPanel = new JPanel(new GridBagLayout());
		JLabel labMessage = new JLabel(StringUtil.splitString(message, 80, 0));
		labMessage.setIcon(new ImageIcon(getClass().getResource("/gfx/cross.png")));
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(40, 40, 20, 40);
		mainPanel.add(labMessage, c);
		List<JButton> buttonList = new LinkedList<JButton>();
		buttonList.add(butClose);
		butClose.addActionListener(controller);
		if (exception != null) {
			buttonList.add(butDetails);
			butDetails.addActionListener(controller);
		}
		JPanel buttonPanel = ButtonUtil.createButtonPanel(buttonList);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(20, 20, 20, 20);
		mainPanel.add(buttonPanel, c);
		add(mainPanel);
		getRootPane().setDefaultButton(butClose);
		setTitle("Feilmelding");
		pack();
		setLocationRelativeTo(parentWindow);
		setVisible(true);
	}


	private class ErrorMessageController implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource().equals(butClose)) {
				dispose();
			}
			else if (event.getSource().equals(butDetails)) {
				new ExceptionDisplayConsole(exception, ErrorMessageDialog.this);
			}
		}
	}
}

