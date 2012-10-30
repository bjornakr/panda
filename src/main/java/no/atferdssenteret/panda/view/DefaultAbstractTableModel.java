package no.atferdssenteret.panda.view;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Observable;

public abstract class DefaultAbstractTableModel extends AbstractTableModel implements Observable {
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
	private List<TableRow> rows = new ArrayList<TableRow>();

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



	public void setModels(List<? extends Model> models) {
		List<TableRow> rows_local = new ArrayList<TableRow>();
		for (int i = 0; i < models.size(); i++) {
			rows_local.add(new TableRow(dataColumns(models.get(i)), models.get(i)));
		}
		setRows(rows_local);
		fireTableDataChanged();
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
		return rows.size();
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
		TableRow row = rows.get(rowIndex);
		Object[] columns = row.getColumns();
		return columns[columnIndex];
	}

	public Object[] rowAt(int rowIndex) {
		return rows.get(rowIndex).getColumns();
	}

	public Object metadataAt(int rowIndex) {
		if (rowIndex < 0 | rowIndex > rows.size()) {
			return null;
		}
		return rows.get(rowIndex).getMetaData();
	}


	public int getPreferredColumnWidth(int index) {
		return columnSizes()[index].value;
	}

	protected void setRows(List<TableRow> rows) {
		this.rows = rows;
		fireTableDataChanged();
		notifyObservers();
	}

	public void addRow(Model model) {
		rows.add(new TableRow(dataColumns(model), model));
		fireTableDataChanged();
	}

	public void deleteRow(Model model) {
		rows.remove(new TableRow(dataColumns(model), model));
		fireTableDataChanged();
	}

	public List<Model> allModels() {	
		List<Model> models = new LinkedList<Model>();
		for (TableRow tableRow : rows) {
			models.add(tableRow.getMetaData());
		}
		return models;
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
