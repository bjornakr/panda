package no.atferdssenteret.panda.controller;

import java.util.List;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import no.atferdssenteret.panda.controller.table.AbstractTableController;

public class TabChangeListener implements ChangeListener {
	private final List<AbstractTableController> tableControllers;
	
	public TabChangeListener(List<AbstractTableController> tableControllers) {
		this.tableControllers = tableControllers;
	}

	@Override
	public void stateChanged(ChangeEvent event) {
		if (event.getSource() instanceof JTabbedPane) {
			/* Listens for tab switches */
			JTabbedPane tabbedPane = (JTabbedPane)(event.getSource());
			if (tabbedPane.getSelectedIndex()+1 <= tableControllers.size()) {
				AbstractTableController selectedController = tableControllers.get(tabbedPane.getSelectedIndex());
				if (!selectedController.isModelInitialized()) {
					selectedController.updateTableModel();
				}
			}
		}
	}

//	@Override
//	public void actionPerformed(ActionEvent event) {
//		if (event.getSource() instanceof TabButton) {
////			JTabbedPane tabbedPane = tabsAndTablesPanel.tabbedPane();
//			TabButton tabButton = (TabButton)event.getSource();
//			int i = tabbedPane.indexOfTabComponent(tabButton.getButtonTabComponent());
//
//			if (i == currentSelectedTab) {
//				if (previousSelectedTab > tabbedPane.getTabCount() -1) {
//					previousSelectedTab = tabbedPane.getTabCount() -1;
//				}
//				tabbedPane.setSelectedIndex(previousSelectedTab);
//			}
//
//			if (i != -1) {
//				tabbedPane.remove(i);
//			}
//		}
//	}
}
