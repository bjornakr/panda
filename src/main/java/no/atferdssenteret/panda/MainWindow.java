package no.atferdssenteret.panda;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

public class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    public static final String NAME = "Target";
    public static final String FIRST_NAME_LABEL_NAME = "first name";
    public static final String LAST_NAME_LABEL_NAME = "last name";
    private final JLabel labFirstName = new JLabel();
    private final JLabel labLastName = new JLabel();
    
    public MainWindow(Target target) {
	setName(NAME);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	labFirstName.setName(FIRST_NAME_LABEL_NAME);
	labFirstName.setText(target.getFirstName());
	labLastName.setName(LAST_NAME_LABEL_NAME);
	labLastName.setText(target.getLastName());
	getContentPane().add(labFirstName);
	getContentPane().add(labLastName);
	setVisible(true);
    }

    public JTabbedPane tabbedPane() {
	// TODO Auto-generated method stub
	return null;
    }
}
