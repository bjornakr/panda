package no.atferdssenteret.panda.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;

import no.atferdssenteret.panda.controller.table.AbstractTableController;

public class TabsAndTablesPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public TabsAndTablesPanel(ChangeListener changeListener, List<AbstractTableController> controllers) {
	JTabbedPane tabbedPane = new JTabbedPane();

	for (AbstractTableController controller : controllers) {    
	    tabbedPane.addTab(controller.title(), controller.view());
	}	
	setLayout(new BorderLayout());
	tabbedPane.setBackground(new Color(0xE5C0F3));
	add(tabbedPane, BorderLayout.CENTER);
	tabbedPane.addChangeListener(changeListener);
    }    
}
