package no.atferdssenteret.panda.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import no.atferdssenteret.panda.controller.table.AbstractTableController;
import no.atferdssenteret.panda.filter.Filter;
import no.atferdssenteret.panda.filter.FilterCreator;
import no.atferdssenteret.panda.view.util.ButtonUtil;

/**
 * This is the main view for the overview panels. It implements tables for
 * any kind of model in the system, and drop-down menus with options for
 * filtering the table.
 * 
 */
public class DefaultTablePanel extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;

	private AbstractTableController controller;
	private Filter[] filters;
	private List<JComboBox> filterComboBoxes;
	private HashMap<String, JComboBox> filterComboBoxLookupByName = new HashMap<String, JComboBox>();
	private JTable table;
	private TableRowSorter<AbstractTableModel> sorter;
	private JScrollPane tableScroller;

	private JLabel labNoOfRows;
	private JLabel labNoOfSelectedRows;

	public DefaultTablePanel(AbstractTableController controller, FilterCreator filterCreator) {
		this.controller = controller;
		if (filterCreator != null) {
			this.filters = filterCreator.createFilters();
		}
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout());
		add(createFilterPanel(), BorderLayout.PAGE_START);
		add(createTable(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.PAGE_END);
	}

	public List<Object> selectedFilterValues() {
		List<Object> filterUnits = new LinkedList<Object>();

		for (JComboBox filterBox : filterComboBoxes) {
			filterUnits.add(filterBox.getSelectedItem());
		}
		return filterUnits;		
	}

	private JPanel createFilterPanel() {	
		JPanel filterPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		filterComboBoxes = new LinkedList<JComboBox>();

		if (filters == null) {
			return filterPanel;
		}

		gbc.gridx = 0;
		gbc.gridy = 0;	

		for (Filter filter : filters) {
			if (gbc.gridx > 5) {
				gbc.gridy++;
				gbc.gridx = 0;
			}

			gbc.anchor = GridBagConstraints.LINE_END;
			gbc.fill = GridBagConstraints.NONE;
			gbc.insets = new Insets(5, 5, 5, 2);
			filterPanel.add(new JLabel(filter.name() + ": "), gbc);
			gbc.gridx++;

			gbc.anchor = GridBagConstraints.LINE_START;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(5, 0, 5, 15);

			JComboBox filterBox = new JComboBox(filter.values());
			filterBox.setSelectedItem(filter.defaultValue());

			filterComboBoxes.add(filterBox);
			filterBox.addActionListener(controller);
			filterPanel.add(filterBox, gbc);
			gbc.gridx++;
			
			filterComboBoxLookupByName.put(filter.name(), filterBox);
		}

		return filterPanel;
	}


	private JScrollPane createTable() {
		sorter = new TableRowSorter<AbstractTableModel>(controller.tableModel());
		table = new JTable(controller.tableModel());
		table.setRowSorter(sorter);
		table.setDefaultRenderer(Timestamp.class, new TableDateRenderer());
		table.setDefaultRenderer(Date.class, new TableDateRenderer());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scaleColumns();
		table.getSelectionModel().addListSelectionListener(controller);
		table.addMouseListener(controller);
		tableScroller = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		return tableScroller;
	}

	private void scaleColumns() {
		TableColumnModel cm = table.getColumnModel();
		for (int i = 0; i < table.getColumnCount(); i++) {
			cm.getColumn(i).setPreferredWidth(controller.tableModel().getPreferredColumnWidth(i));
		}
	}

	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));	
		labNoOfRows = new JLabel("Antall rader: " + table.getRowCount());
		labNoOfSelectedRows = new JLabel("<html><font color=\"#4682B4\">Markerte rader: " + table.getSelectedRowCount() + "</font></html>");
		buttonPanel.add(labNoOfRows);
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPanel.add(labNoOfSelectedRows);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(ButtonUtil.createButtonPanel(controller.buttons()));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		return buttonPanel;
	}

	public void showCounters(boolean showCounters) {
		labNoOfRows.setVisible(showCounters);
		labNoOfSelectedRows.setVisible(showCounters);
	}

	public void updateTableCounters() {
		labNoOfRows.setText("Antall rader: " + table.getRowCount());
		labNoOfSelectedRows.setText("<html><font color=\"#4682B4\">Markerte rader: " + table.getSelectedRowCount() + "</font></html>");
	}


	public int selectedTableRow() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			return -1;	
		}
		return sorter.convertRowIndexToModel(table.getSelectedRow());
	}

	public void setWaitingState() {
		/*
		 * I perform this null check, because I am having trouble with
		 * the startup of the program. The controller sets this waiting
		 * state before the GUI thread has finished its display code,
		 * hence the root pane doesn't exist. I could probably prevent
		 * it with some monitors or something, but I really don't want to
		 * go there.
		 *  
		 */
		if (getRootPane() != null) {
			getRootPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
		}
	}
	public void endWaitingState() {
		if (getRootPane() != null) {
			getRootPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	public void setTableHeight(int height) {
		tableScroller.setPreferredSize(new Dimension(tableScroller.getPreferredSize().width, height));
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		updateTableCounters();
	}

	public Window getWindow() {
		return (Window)SwingUtilities.getRoot(this);
	}
	
	public void setSelectedFilter(String name, Object value) {
		JComboBox filterBox = filterComboBoxLookupByName.get(name);
		if (filterBox == null) {
			return;
		}
		else {
			filterBox.setSelectedItem(value);
		}
	}
}
