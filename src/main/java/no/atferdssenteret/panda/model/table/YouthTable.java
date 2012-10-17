package no.atferdssenteret.panda.model.table;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.fft.Youth;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;

public class YouthTable extends DefaultAbstractTableModel {
    private static final long serialVersionUID = 1L;

    private final static int NO_OF_FIELDS = 9;
    private final static int TARGET_ID = 0;
    private final static int STATUS = 1;
    private final static int REGION = 2;
    private final static int GENDER = 3;
    private final static int FIRST_NAME = 4;
    private final static int LAST_NAME = 5;
    private final static int TREATMENT_GROUP = 6;
    private final static int TREATMENT_START = 7;
    private final static int COMMENT = 8;


    
    @Override
    protected String[] headerColumns() {
	String[] headerColumns = new String[NO_OF_FIELDS];
	headerColumns[TARGET_ID] = "ID-kode";
	headerColumns[STATUS] = "Status";
	headerColumns[REGION] = "Region";
	headerColumns[GENDER] = "Kjønn";
	headerColumns[FIRST_NAME] = "Fornavn";
	headerColumns[LAST_NAME] = "Etternavn";
	headerColumns[TREATMENT_GROUP] = "Gruppe";
	headerColumns[TREATMENT_START] = "Behandlingsstart";
	headerColumns[COMMENT] = "Kommentar";
	return headerColumns;
    }

    @Override
    protected Object[] dataColumns(Model model) {
	Youth target = (Youth)model;
	Object[] columns = new Object[headerColumns().length];
	columns[TARGET_ID] = target.getId();
	columns[STATUS] = target.getStatus();
	columns[REGION] = target.getRegion();
	columns[GENDER] = target.getGender();
	columns[FIRST_NAME] = target.getFirstName();
	columns[LAST_NAME] = target.getLastName();
	columns[TREATMENT_GROUP] = target.getTreatmentGroup();
	columns[TREATMENT_START] = target.getTreatmentStart();
	columns[COMMENT] = target.getComment();
	return columns;
    } 
    
    @Override
    protected ColumnSizes[] columnSizes() {
	ColumnSizes[] columnSizes = new ColumnSizes[NO_OF_FIELDS];
	columnSizes[TARGET_ID] = ColumnSizes.SMALL;
	columnSizes[STATUS] = ColumnSizes.NORMAL;
	columnSizes[REGION] = ColumnSizes.NORMAL;
	columnSizes[GENDER] = ColumnSizes.SMALL;
	columnSizes[FIRST_NAME] = ColumnSizes.NORMAL;
	columnSizes[LAST_NAME] = ColumnSizes.NORMAL;
	columnSizes[TREATMENT_GROUP] = ColumnSizes.NORMAL;
	columnSizes[TREATMENT_START] = ColumnSizes.SMALL;
	columnSizes[COMMENT] = ColumnSizes.WIDE;
	return columnSizes;
    }
}


