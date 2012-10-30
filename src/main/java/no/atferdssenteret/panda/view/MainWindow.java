package no.atferdssenteret.panda.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import no.atferdssenteret.panda.controller.MainController;
import no.atferdssenteret.panda.view.util.GuiUtil;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
//	private JTabbedPane tabbedPane; 
	private JTextField txtTargetLink;
	private MainController controller;
	private TabsAndTablesPanel tabsAndTablesPanel;
//	private List<AbstractTableController> tableControllers;

	public MainWindow(MainController controller, TabsAndTablesPanel tabsAndTablesPanel) { 
		this.controller = controller;
//		this.tableControllers = tableControllers;
		this.tabsAndTablesPanel = tabsAndTablesPanel;
	}

	public void display() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		add(createTargetLinkPanel(), BorderLayout.PAGE_START);
//		add(createTabbedPane(), BorderLayout.CENTER);
		add(tabsAndTablesPanel);
		
		pack();
		setLocationRelativeTo(null);
		addWindowListener(controller);
		setTitle(controller.title());
		//	setIconImage(new ImageIcon("graphics/icons/globe_icon.png").getImage());
		setVisible(true);
	}

	private JPanel createTargetLinkPanel() {
		txtTargetLink = new JTextField(4);
		txtTargetLink.addActionListener(controller);
		txtTargetLink.setActionCommand("GOTO_CHILD");
		JButton butChildLink = new JButton("OK");
		butChildLink.setActionCommand("GOTO_CHILD");
		butChildLink.addActionListener(controller);

		JPanel targetLinkPanel = new JPanel();
		targetLinkPanel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(0, 10, 0, 10);
		targetLinkPanel.add(new JLabel("Gå til barn: "), c);

		txtTargetLink.setBorder(BorderFactory.createEtchedBorder());
		txtTargetLink.setFont(GuiUtil.valueFont());
		c = new GridBagConstraints();
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(0, 0, 0, 10);
		targetLinkPanel.add(txtTargetLink, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(0, 0, 0, 300);
		targetLinkPanel.add(butChildLink, c);

		c = new GridBagConstraints();
		c.gridx = 3;
		c.anchor = GridBagConstraints.LINE_START;
		targetLinkPanel.add(new JLabel(new ImageIcon("resources/gfx/Panda-icon.png")), c);

		c = new GridBagConstraints();
		c.gridx = 4;
		c.anchor = GridBagConstraints.LINE_START;
		targetLinkPanel.add(new JLabel(new ImageIcon("resources/gfx/logo-beta.png")), c);

		targetLinkPanel.setBackground(getRootPane().getBackground());

		JPanel wrapperPanel = new JPanel();
		wrapperPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0)); // pos, hgap, vgap
		wrapperPanel.add(targetLinkPanel);

		wrapperPanel.setBackground(getRootPane().getBackground());

		wrapperPanel.setBorder(BorderFactory.createEtchedBorder());

		return wrapperPanel;
	}

//	private JTabbedPane createTabbedPane() {
//		tabbedPane = new JTabbedPane();
//
//		for (AbstractTableController tableController : tableControllers) {    
//			tabbedPane.addTab(tableController.title(), tableController.view());
//		}	
//
//		tabbedPane.addChangeListener(controller);
//		tabbedPane.setBackground(new Color(0xFFBF00));
//
//		return tabbedPane;
//	}


//	public JTabbedPane getTabbedPane() {
//		return tabbedPane;
//	}

	public void setSelectedChildID(Integer childID) {
		txtTargetLink.setText(childID.toString());
	}
	public String getTypedChildID() {
		return txtTargetLink.getText();
	}

	public void addClosableTab(String title, Component contentPanel) {
		JTabbedPane tabbedPane = tabsAndTablesPanel.tabbedPane();
		tabbedPane.addTab(title, contentPanel);
		int newTabIndex = tabbedPane.getTabCount()-1;
		tabbedPane.setTabComponentAt(newTabIndex, new ButtonTabComponent(tabbedPane, controller));
		tabbedPane.setBackgroundAt(newTabIndex, new Color(0x74FF00));
		tabbedPane.setSelectedIndex(newTabIndex);
	}
}
