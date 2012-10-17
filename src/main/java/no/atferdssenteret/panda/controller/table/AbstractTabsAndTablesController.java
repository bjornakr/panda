package no.atferdssenteret.panda.controller.table;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


// TODO: Do we really need this?
public abstract class AbstractTabsAndTablesController implements ChangeListener, ActionListener {
//    protected List<AbstractTableController> tableControllers = new LinkedList<AbstractTableController>();  
    
    
//    public AbstractTabsAndTablesController() {
//	createChildControllers();
//    }
    
    public abstract Component view();
    public abstract String title();
    public abstract List<AbstractTableController> tableControllers();
//    protected abstract List<AbstractTableController> createChildControllers();
    
    
    @Override
    public void stateChanged(ChangeEvent event) {
	if (event.getSource() instanceof JTabbedPane) {
	    /* Listens for tab switches */
	    JTabbedPane tabbedPane = (JTabbedPane)(event.getSource());
	    if (tabbedPane.getSelectedIndex()+1 <= tableControllers().size()) {
		AbstractTableController selectedController = tableControllers().get(tabbedPane.getSelectedIndex());
		if (!selectedController.isModelInitialized()) {
		    selectedController.updateTableModel();
		}
	    }
	}
    }
}
