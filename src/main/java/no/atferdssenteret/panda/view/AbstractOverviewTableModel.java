package no.atferdssenteret.panda.view;

import java.util.LinkedList;
import java.util.List;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Observable;

public abstract class AbstractOverviewTableModel extends AbstractTableModel implements Observable {
    private static final long serialVersionUID = 1L;
    protected enum ColumnSizes {
	SMALL(75), NORMAL(100), WIDE(170);
	
	private int value;
	
	private ColumnSizes(int value) {
	    this.value = value;
	}
	
	public int value() {
	    return value;
	}
    }

    //    protected DisplayMode displayMode;

    //    protected String[] headerColumns;
    //    protected int[] columnSizes;
    private TableRow[] rows;

    private List<Observer> observers = new LinkedList<Observer>();

    protected abstract String[] headerColumns();
    protected abstract Object[] dataColumns(Model model);
    protected abstract ColumnSizes[] columnSizes();

    //    public AbstractOverviewTableModel() {
    //	createHeaderColumns();
    //    }

    //    public AbstractOverviewTableModel(DisplayMode displayMode) {
    //    public AbstractOverviewTableModel(DisplayMode displayMode) {
    //	this.displayMode = displayMode;
    //	headerColumns = createHeaderColumns();
    //    }



    public void insertNewData(List<? extends Model> dataList) {
	TableRow[] rows_local = new TableRow[dataList.size()];
	for (int i = 0; i < dataList.size(); i++) {
	    rows_local[i] = new TableRow(dataColumns(dataList.get(i)), dataList.get(i));
	}
	setRows(rows_local);
    }


    @Override
    public String getColumnName(int i) {
	return headerColumns()[i];
    }

    @Override
    public int getColumnCount() {
	return headerColumns().length;
    }

    @Override
    public int getRowCount() {
	if (rows == null) {
	    return 0;
	}
	return rows.length;
    }

    @Override
    public Class<?> getColumnClass(int c) {
	Object o;
	for (int i = 0; i < getRowCount(); i++) {
	    o = getValueAt(i, c);

	    if (o != null) {
		return o.getClass();
	    }
	}
	return new String().getClass(); // Entire column was empty
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
	TableRow row = rows[rowIndex];
	Object[] columns = row.getColumns();
	return columns[columnIndex];
    }

    public Object getMetadataAt(int rowIndex) {
	if (rowIndex < 0 | rowIndex > rows.length) {
	    return null;
	}
	return rows[rowIndex].getMetaData();
    }


    public int getPreferredColumnWidth(int index) {
	return columnSizes()[index].value;
    }

    protected void setRows(TableRow[] rows) {
	this.rows = rows;
	fireTableDataChanged();
	notifyObservers();
    }

    @Override
    public void addObserver(Observer o) {
	observers.add(o);
    }

    @Override
    public void notifyObservers() {
	for (Observer observer : observers) {
	    observer.update(null, null);
	}
    }
}
