package no.atferdssenteret.panda.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.swing.JButton;
import javax.swing.JDialog;

import no.atferdssenteret.panda.view.util.DefaultTextArea;

public class ExceptionDisplayConsole extends JDialog {
	private static final long serialVersionUID = 1L;

	private ExceptionDisplayConsoleController controller = new ExceptionDisplayConsoleController();

	private DefaultTextArea txtDetails = new DefaultTextArea(20, 60);
	private JButton butClose = new JButton("Lukk");

	public ExceptionDisplayConsole(Exception exception, Window parentWindow) {
		setModalityType(ModalityType.APPLICATION_MODAL);

		txtDetails.setForeground(Color.RED);
		txtDetails.setText(getStackTrace(exception));
		txtDetails.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
		txtDetails.setEditable(false);
		txtDetails.setCaretPosition(0);

		butClose.addActionListener(controller);

		setLayout(new BorderLayout());
		add(txtDetails.scrollPane(), BorderLayout.CENTER);
		add(butClose, BorderLayout.PAGE_END);

		pack();
		setLocationRelativeTo(parentWindow);
		setVisible(true);
	}

	public static String getStackTrace(Throwable aThrowable) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}

	private class ExceptionDisplayConsoleController implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource().equals(butClose)) {
				dispose();
			}
		}
	}
}