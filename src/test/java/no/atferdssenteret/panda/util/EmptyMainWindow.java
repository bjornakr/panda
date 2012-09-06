package no.atferdssenteret.panda.util;

import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class EmptyMainWindow {
    
    public static Window create() {
	final JFrame mainFrame = new JFrame();
	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	mainFrame.setSize(640, 480);
	mainFrame.setLocationRelativeTo(null);
	
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		mainFrame.setVisible(true);
	    }	   
	}); 
	return mainFrame;	
    }
}
