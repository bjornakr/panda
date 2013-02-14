package no.atferdssenteret.panda.model.table;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;

public class DataCollectionTable extends DefaultAbstractTableModel {
	private static final long serialVersionUID = 1L;

	private final static int NO_OF_FIELDS = 7;
	private final static int TARGET_ID = 0;
	private final static int STATUS = 1;
	private final static int TARGET_DATE = 2;
	private final static int TYPE = 3;
	private final static int PROGRESS_STATUS = 4;
	private final static int PROGRESS_DATE = 5;
	private final static int DATA_COLLECTOR = 6;

	@Override
	protected String[] headerColumns() {
		String[] headerColumns = new String[NO_OF_FIELDS];
		headerColumns[TARGET_ID] = "ID";
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
		dataColumns[TARGET_ID] = dataCollection.getTarget().formattedIdWithLetterAppendix();
		dataColumns[STATUS] = dataCollection.status();
		dataColumns[TARGET_DATE] = dataCollection.getTargetDate();
		dataColumns[TYPE] = dataCollection.getType();
		dataColumns[PROGRESS_STATUS] = dataCollection.getProgressStatus();
		dataColumns[PROGRESS_DATE] = dataCollection.getProgressDate();
		dataColumns[DATA_COLLECTOR] = dataCollection.getDataCollector();
		return dataColumns;
	}

	@Override
	protected int[] columnSizes() {
		int[] columnSizes = new int[NO_OF_FIELDS];
		columnSizes[TARGET_ID] = COLUMN_SIZE_SMALL;
		columnSizes[STATUS] = COLUMN_SIZE_SMALL;
		columnSizes[TARGET_DATE] = COLUMN_SIZE_SMALL;
		columnSizes[TYPE] = COLUMN_SIZE_SMALL;
		columnSizes[PROGRESS_STATUS] = COLUMN_SIZE_NORMAL;
		columnSizes[PROGRESS_DATE] = COLUMN_SIZE_SMALL;
		columnSizes[DATA_COLLECTOR] = COLUMN_SIZE_WIDE;
		return columnSizes;
	}
}
