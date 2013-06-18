package no.atferdssenteret.panda.view;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import no.atferdssenteret.panda.view.util.DefaultTextArea;

public class TextDisplayDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private DefaultTextArea textArea = new DefaultTextArea(30, 80);
	private JButton butClose = new JButton("Lukk");
	
	public TextDisplayDialog(String text, Window parentWindow) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		textArea.setText(text);
		textArea.setEditable(false);
		textArea.setCaretPosition(0);
		
		butClose.addActionListener(new DialogController());
		
		setLayout(new BorderLayout());
		add(textArea.scrollPane(), BorderLayout.CENTER);
		add(butClose, BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(parentWindow);
		setVisible(true);
	}
	
	private class DialogController implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource().equals(butClose)) {
				dispose();
			}
		}
	}
}
