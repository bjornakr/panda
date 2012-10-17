package no.atferdssenteret.panda.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import no.atferdssenteret.panda.DataCollectionManager;
import no.atferdssenteret.panda.DataCollectionRule;
import no.atferdssenteret.panda.controller.table.AbstractTableController;
import no.atferdssenteret.panda.controller.table.AbstractTabsAndTablesController;
import no.atferdssenteret.panda.controller.table.DataCollectionTableController;
import no.atferdssenteret.panda.controller.table.ParticipantTableController;
import no.atferdssenteret.panda.controller.table.QuestionnaireEventTableController;
import no.atferdssenteret.panda.controller.table.QuestionnaireTableController;
import no.atferdssenteret.panda.controller.table.YouthTableController;
import no.atferdssenteret.panda.model.fft.Youth;
import no.atferdssenteret.panda.view.MainWindow;
import no.atferdssenteret.panda.view.util.TabButton;

public class MainController extends AbstractTabsAndTablesController implements ActionListener, WindowListener {
    public static final String APP_NAME = "PANDA";
    public static final String VERSION = "0.314";
    private List<AbstractTableController> tableControllers;
    private MainWindow view;
    
    public MainController() {

	view = new MainWindow(this, tableControllers());
	
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		view.display();
	    }
	});
    }
    
    @Override
    public Component view() {
	return view;
    }
    
    public List<AbstractTableController> tableControllers() {
	if (tableControllers == null) {
	    tableControllers = new LinkedList<AbstractTableController>();
	    tableControllers.add(new YouthTableController(this));
	    tableControllers.add(new ParticipantTableController());
	    tableControllers.add(new DataCollectionTableController(null));
	    tableControllers.add(new QuestionnaireTableController());
	    tableControllers.add(new QuestionnaireEventTableController());
	}
	return tableControllers;
    }

    public void addYouthTab(Youth youth) {
	YouthOverviewController youthOverviewController = new YouthOverviewController(view, youth);
	view.addClosableTab(youthOverviewController.title(), youthOverviewController.view());
    }

    public String title() {
	return APP_NAME + " " + VERSION;
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
	if (event.getSource() instanceof TabButton) {
	    JTabbedPane tabbedPane = view.getTabbedPane(); 
	    TabButton tabButton = (TabButton)event.getSource();
	    int i = tabbedPane.indexOfTabComponent(tabButton.getButtonTabComponent());

//	    if (i == currentSelectedTab) {
//		if (previousSelectedTab > tabbedPane.getTabCount() -1) {
//		    previousSelectedTab = tabbedPane.getTabCount() -1;
//		}
//		tabbedPane.setSelectedIndex(previousSelectedTab);
//	    }

	    if (i != -1) {
		tabbedPane.remove(i);
	    }
	}
	
    }

    @Override
    public void windowActivated(WindowEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void windowClosed(WindowEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void windowClosing(WindowEvent arg0) {
	exit();
	
    }


    @Override
    public void windowDeactivated(WindowEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void windowDeiconified(WindowEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void windowIconified(WindowEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void windowOpened(WindowEvent arg0) {
	// TODO Auto-generated method stub
	
    }

//    @Override
//    public void stateChanged(ChangeEvent arg0) {
//	// TODO Auto-generated method stub
//	
//    }
    
    public static void main(String[] args) {
	DataCollectionRule dcRule = new DataCollectionRule(
		"T1",
		DataCollectionRule.ApplicationTimes.WHEN_TARGET_UPDATED,
		Calendar.MONTH,
		3,
		DataCollectionRule.TargetDates.AFTER_TREATMENT_START);
	DataCollectionManager.getInstance().addRule(dcRule);
	new MainController();
    }

    private void exit() {
	System.exit(0);
    }



}
