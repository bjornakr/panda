package no.atferdssenteret.panda.controller.table;

import java.awt.Component;
import java.awt.event.ActionListener;


// TODO: Do we really need this?
public interface StandardController extends ActionListener {
	public abstract Component view();
	public abstract String title();
//	public abstract List<AbstractTableController> tableControllers();

//	@Override
//	public void stateChanged(ChangeEvent event) {
//		if (event.getSource() instanceof JTabbedPane) {
//			/* Listens for tab switches */
//			JTabbedPane tabbedPane = (JTabbedPane)(event.getSource());
//			if (tabbedPane.getSelectedIndex()+1 <= tableControllers().size()) {
//				AbstractTableController selectedController = tableControllers().get(tabbedPane.getSelectedIndex());
//				if (!selectedController.isModelInitialized()) {
//					selectedController.updateTableModel();
//				}
//			}
//		}
//	}
}
