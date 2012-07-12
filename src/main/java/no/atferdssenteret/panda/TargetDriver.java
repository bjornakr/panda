package no.atferdssenteret.panda;

import static org.hamcrest.Matchers.equalTo;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

public class TargetDriver extends JFrameDriver {

    @SuppressWarnings("unchecked")
    public TargetDriver(int timeoutMillis) {
	super(new GesturePerformer(),
		JFrameDriver.topLevelFrame(named(MainWindow.NAME), showingOnScreen()),
		new AWTEventQueueProber(timeoutMillis, 100));
    }

    @SuppressWarnings("unchecked")
    public void showFirstName(String firstNameText) {
	new JLabelDriver(this, named(MainWindow.FIRST_NAME_LABEL_NAME)).hasText(equalTo(firstNameText));
    }
    
    @SuppressWarnings("unchecked")
    public void showLastName(String lastNameText) {
	new JLabelDriver(this, named(MainWindow.LAST_NAME_LABEL_NAME)).hasText(equalTo(lastNameText));
    }
}
