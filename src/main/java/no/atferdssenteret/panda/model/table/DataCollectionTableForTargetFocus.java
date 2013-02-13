package no.atferdssenteret.panda.model.table;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;

public class DataCollectionTableForTargetFocus extends DefaultAbstractTableModel {
	private static final long serialVersionUID = 1L;

	private final static int NO_OF_FIELDS = 6;
	private final static int STATUS = 0;
	private final static int TARGET_DATE = 1;
	private final static int TYPE = 2;
	private final static int PROGRESS_STATUS = 3;
	private final static int PROGRESS_DATE = 4;
	private final static int DATA_COLLECTOR = 5;

	@Override
	protected String[] headerColumns() {
		String[] headerColumns = new String[NO_OF_FIELDS];
		headerColumns[STATUS] = "Status";
		headerColumns[TARGET_DATE] = "Måldato";
		headerColumns[TYPE] = "Type";
		headerColumns[PROGRESS_STATUS] = "Framdrift";
		headerColumns[PROGRESS_DATE] = "Dato";
		headerColumns[DATA_COLLECTOR] = "Datainnsamler";
		return headerColumns;
	}

	@Override
	protected Object[] dataColumns(Model model) {
		Object[] dataColumns = new Object[NO_OF_FIELDS];
		DataCollection dataCollection = (DataCollection)model;
		dataColumns[STATUS] = dataCollection.status();
		dataColumns[TARGET_DATE] = dataCollection.getTargetDate();
		dataColumns[TYPE] = dataCollection.getType();
		dataColumns[PROGRESS_STATUS] = dataCollection.getProgressStatus();
		dataColumns[PROGRESS_DATE] = dataCollection.getProgressDate();
		dataColumns[DATA_COLLECTOR] = dataCollection.getDataCollector();
		return dataColumns;
	}

	@Override
	protected ColumnSizes[] columnSizes() {
		ColumnSizes[] columnSizes = new ColumnSizes[NO_OF_FIELDS];
		columnSizes[STATUS] = ColumnSizes.SMALL;
		columnSizes[TARGET_DATE] = ColumnSizes.SMALL;
		columnSizes[TYPE] = ColumnSizes.SMALL;
		columnSizes[PROGRESS_STATUS] = ColumnSizes.NORMAL;
		columnSizes[PROGRESS_DATE] = ColumnSizes.SMALL;
		columnSizes[DATA_COLLECTOR] = ColumnSizes.WIDE;
		return columnSizes;
	}
	
}