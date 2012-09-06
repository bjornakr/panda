package no.atferdssenteret.panda.view.util;


import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DefaultTextArea extends JTextArea {
    private static final long serialVersionUID = 1L;

    public DefaultTextArea() {
	this(5, 30);
    }

    public DefaultTextArea(int rows, int columns) {
	super(rows, columns);
	setMinimumSize(getPreferredSize());
	setWrap();
    }
    
    public DefaultTextArea(String text, int rows, int columns) {
	super(text, rows, columns);
	setWrap();
    }
    
    private void setWrap() {
	setLineWrap(true);
	setWrapStyleWord(true);
    }
    
    
    public JScrollPane scrollPane() {
	JScrollPane scrollPane = new JScrollPane(this);
	scrollPane.setMinimumSize(scrollPane.getPreferredSize());
	return scrollPane;	
    }
}
