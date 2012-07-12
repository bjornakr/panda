package no.atferdssenteret.panda;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JTabbedPaneDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;
import com.objogate.wl.swing.probe.ComponentIdentity;

public class ApplicationRunner {
    private MainWindow mainWindow;
    
    public ApplicationRunner() {
	Thread thread = new Thread("Test Application"){
	
	    @Override
	    public void run() {
		try {
		    mainWindow = new MainWindow(null);		    
		}
		catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	};
	thread.setDaemon(true);
	thread.start();

//	TargetDriver targetDriver = new TargetDriver(1000);
//	
//	targetDriver.showFirstName(target.getFirstName());
//	targetDriver.showLastName(target.getLastName());
    }

//    @SuppressWarnings("unchecked")
    public void findTargetInOverviewTable(Target target) {
	JTabbedPaneDriver tabbedPaneDriver = new JTabbedPaneDriver(new GesturePerformer(),
		ComponentIdentity.selectorFor(mainWindow.tabbedPane()), new AWTEventQueueProber()); 

	
//	
//	JTableDriver tableDriver = new JTableDriver(new GesturePerformer(),
//		ComponentIdentity.selectorFor(mainWindow.overviewTable(MainWindow.TARGET)), new AWTEventQueueProber());
//	tableDriver.hasRow(IterableComponentsMatcher.matching(withLabelText(target.getFirstName()), withLabelText(target.getLastName())));
	
	
    }
    
    public void displayDataCollection() {
	
	
    }
    
}
