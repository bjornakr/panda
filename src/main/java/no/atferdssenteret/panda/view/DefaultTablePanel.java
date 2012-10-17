package no.atferdssenteret.panda.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Window;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import no.atferdssenteret.panda.controller.table.AbstractTableController;
import no.atferdssenteret.panda.model.FilterCreator;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public class DefaultTablePanel extends JPanel implements Observer {
    private static final long serialVersionUID = 1L;

    private AbstractTableController controller;
    private List<JButton> buttons = new LinkedList<JButton>();
//    private List<Filter> filterList;
//    private List<JComboBox> filterBoxList;
//    private AbstractOverviewTableModel tableModel;
    private JTable table;
    private TableRowSorter<AbstractTableModel> sorter;
    private JScrollPane tableScroller;
    
    private JLabel labNoOfRows;
    private JLabel labNoOfSelectedRows;

    public DefaultTablePanel(AbstractTableController controller, FilterCreator filterCreator) {
	this.controller = controller;
//	this.tableModel = tableModel;
//	if (filterCreator != null) {
//	    this.filterList = filterCreator.getFilters();
//	}
	initialize();
    }

    private void initialize() {
	setLayout(new BorderLayout());
//	add(createFilterPanel(), BorderLayout.PAGE_START);
	add(createTable(), BorderLayout.CENTER);
	add(createButtonPanel(), BorderLayout.PAGE_END);
    }

//    public List<Condition> getSelectedFilterConditions() {
//	List<Condition> conditionList = new LinkedList<Condition>();
//
//	for (JComboBox filterBox : filterBoxList) {
//	    Condition condition = (Condition)(filterBox.getSelectedItem());	    
//	    if (!condition.getSQLcondition().equals("TRUE")) {
//		conditionList.add((Condition)(filterBox.getSelectedItem()));
//	    }
//	}
//
//	return conditionList;
//    }


//    private JPanel createFilterPanel() {	
//	JPanel filterPanel = new JPanel(new GridBagLayout());
//	GridBagConstraints gbc = new GridBagConstraints();
//
//	filterBoxList = new LinkedList<JComboBox>();
//
//	if (filterList == null) {
//	    return filterPanel;
//	}
//	
//	
//
//	gbc.gridx = 0;
//	gbc.gridy = 0;	
//
//	for (Filter filter : filterList) {
//	    if (gbc.gridx > 5) {
//		gbc.gridy++;
//		gbc.gridx = 0;
//	    }
//
//	    /* Adding filter name to panel */
//	    gbc.anchor = GridBagConstraints.LINE_END;
//	    gbc.fill = GridBagConstraints.NONE;
//	    gbc.insets = new Insets(5, 5, 5, 2); // ver_y, ver_x, hor_y, hor_x
//	    filterPanel.add(new JLabel(filter.getDisplayName() + ": "), gbc);
//	    gbc.gridx++;
//
//
//	    /* Adding filter combobox to panel */
//	    gbc.anchor = GridBagConstraints.LINE_START;
//	    gbc.fill = GridBagConstraints.HORIZONTAL;
//	    gbc.insets = new Insets(5, 0, 5, 15); // ver_y, ver_x, hor_y, hor_x 
//
//	    JComboBox filterBox = new JComboBox(filter.getOptions());
//	    filterBox.setSelectedItem(filter.getDefaultCondition());
//
//	    filterBoxList.add(filterBox); // Storing combobox for later retrieval
//	    filterBox.addActionListener(controller);
//	    filterPanel.add(filterBox, gbc);
//	    gbc.gridx++;
//	}
//
//	return filterPanel;
//    }


    private JScrollPane createTable() {
	System.out.println(controller.tableModel());
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
	buttons = ButtonUtil.createCRUDButtons(controller);
	buttonPanel.add(ButtonUtil.createButtonPanel(buttons));
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

    public List<JButton> buttons() {
	return buttons;
    }
}
