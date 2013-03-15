package no.atferdssenteret.panda.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import no.atferdssenteret.panda.controller.table.AbstractTabsAndTablesController;
import no.atferdssenteret.panda.view.util.ButtonUtil;
import no.atferdssenteret.panda.view.util.GuiUtil;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField txtTargetLink;
	private AbstractTabsAndTablesController controller;
	private TabsAndTablesPanel tabsAndTablesPanel;

	public MainWindow(AbstractTabsAndTablesController controller, TabsAndTablesPanel tabsAndTablesPanel) { 
		this.controller = controller;
		this.tabsAndTablesPanel = tabsAndTablesPanel;
	}

	public void display() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		add(createTargetLinkPanel(), BorderLayout.PAGE_START);
//		add(tabsAndTablesPanel, BorderLayout.CENTER);
		
//		pack();
		setSize(1024, 768);
		setLocationRelativeTo(null);
		addWindowListener(new MainWindowListener());
		setTitle(controller.title());
		setVisible(true);
	}

	public void addTabsAndTablesPanel(TabsAndTablesPanel tabsAndTablesPanel) {
		add(tabsAndTablesPanel, BorderLayout.CENTER);
		this.tabsAndTablesPanel = tabsAndTablesPanel;
	}
	
	private JPanel createTargetLinkPanel() {
		txtTargetLink = new JTextField(4);
		txtTargetLink.addActionListener(controller);
		txtTargetLink.setActionCommand(ButtonUtil.COMMAND_GOTO);
		JButton butChildLink = new JButton(new ImageIcon("resources/gfx/arrow_right.png"));
		butChildLink.setActionCommand(ButtonUtil.COMMAND_GOTO);
		butChildLink.addActionListener(controller);

		JPanel targetLinkPanel = new JPanel();
		targetLinkPanel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(0, 10, 0, 10);
		targetLinkPanel.add(new JLabel("Gå til: "), c);

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

	public String targetIdFromGotoField() {
		return txtTargetLink.getText();
	}
	
	public void setTargetGotoField(String id) {
		txtTargetLink.setText(id);
	}
	
	private class MainWindowListener implements WindowListener {
		@Override
		public void windowActivated(WindowEvent e) {
		}

		@Override
		public void windowClosed(WindowEvent e) {
		}

		@Override
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
		}

		@Override
		public void windowIconified(WindowEvent e) {
		}

		@Override
		public void windowOpened(WindowEvent e) {
		}
		
	}
}
