package no.atferdssenteret.panda.model.table;

import no.atferdssenteret.panda.model.DataCollection;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;

public class DataCollectionTable extends DefaultAbstractTableModel {
    private static final long serialVersionUID = 1L;

    private final static int NO_OF_FIELDS = 5;
    private final static int TARGET_ID = 0;
    private final static int TARGET_DATE = 1;
    private final static int TYPE = 2;
    private final static int PROGRESS_STATUS = 3;
    private final static int PROGRESS_DATE = 4;
    
    @Override
    protected String[] headerColumns() {
	String[] headerColumns = new String[NO_OF_FIELDS];
	headerColumns[TARGET_ID] = "ID-kode";
	headerColumns[TARGET_DATE] = "Måldato";
	headerColumns[TYPE] = "Type";
	headerColumns[PROGRESS_STATUS] = "Status";
	headerColumns[PROGRESS_DATE] = "Dato";
	return headerColumns;
    }

    @Override
    protected Object[] dataColumns(Model model) {
	Object[] dataColumns = new Object[NO_OF_FIELDS];
	DataCollection dataCollection = (DataCollection)model;
	dataColumns[TARGET_ID] = dataCollection.getTarget().getId();
	dataColumns[TARGET_DATE] = dataCollection.getTargetDate();
	dataColumns[TYPE] = dataCollection.getType();
	dataColumns[PROGRESS_STATUS] = dataCollection.getProgressStatus();
	dataColumns[PROGRESS_DATE] = dataCollection.getProgressDate();
	return dataColumns;
    }

    @Override
    protected ColumnSizes[] columnSizes() {
	ColumnSizes[] columnSizes = new ColumnSizes[NO_OF_FIELDS];
	columnSizes[TARGET_ID] = ColumnSizes.SMALL;
	columnSizes[TARGET_DATE] = ColumnSizes.SMALL;
	columnSizes[TYPE] = ColumnSizes.NORMAL;
	columnSizes[PROGRESS_STATUS] = ColumnSizes.SMALL;
	columnSizes[PROGRESS_DATE] = ColumnSizes.SMALL;
	return columnSizes;
    }
}
