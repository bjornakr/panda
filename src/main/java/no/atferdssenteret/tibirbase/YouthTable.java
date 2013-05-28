package no.atferdssenteret.tibirbase;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;

public class YouthTable extends DefaultAbstractTableModel {
	private static final long serialVersionUID = 1L;

	private final static int NO_OF_FIELDS = 13;
	private final static int TARGET_ID_NUM = 0;
	private final static int TARGET_ID_STR = 1;
	private final static int STATUS = 2;
	private final static int REGION = 3;
	private final static int DATA_COLLECTOR = 4;
	private final static int DATA_COLLECTOR_TELEPHONE = 5;
	private final static int GENDER = 6;
	private final static int FIRST_NAME = 7;
	private final static int LAST_NAME = 8;
	private final static int TREATMENT_GROUP = 9;
//	private final static int TREATMENT_START = 10;
	private final static int COMMENT = 10;
	private final static int CREATED = 11;
	private final static int UPDATED = 12;

	@Override
	protected String[] headerColumns() {
		String[] headerColumns = new String[NO_OF_FIELDS];
		headerColumns[TARGET_ID_NUM] = "ID (1)";
		headerColumns[TARGET_ID_STR] = "ID (2)";
		headerColumns[STATUS] = "Status";
		headerColumns[REGION] = "Region";
		headerColumns[DATA_COLLECTOR] = "Datainnsamler";
		headerColumns[DATA_COLLECTOR_TELEPHONE] = "Telefonintervjuer";		
		headerColumns[GENDER] = "Kjønn";
		headerColumns[FIRST_NAME] = "Fornavn";
		headerColumns[LAST_NAME] = "Etternavn";
		headerColumns[TREATMENT_GROUP] = "Gruppe";
//		headerColumns[TREATMENT_START] = "Behandlingsstart";
		headerColumns[COMMENT] = "Kommentar";
		headerColumns[CREATED] = "Opprettet";
		headerColumns[UPDATED] = "Oppdatert";
		return headerColumns;
	}

	@Override
	protected Object[] dataColumns(Model model) {
		Youth target = (Youth)model;
		Object[] columns = new Object[headerColumns().length];
		columns[TARGET_ID_NUM] = target.formattedNumericId();
		columns[TARGET_ID_STR] = target.letterId();
		columns[STATUS] = target.getStatus();
		columns[REGION] = target.getRegion();
		columns[DATA_COLLECTOR] = target.getDataCollector();
		columns[DATA_COLLECTOR_TELEPHONE] = target.getDataCollectorTelephone();
		columns[GENDER] = target.getGender();
		columns[FIRST_NAME] = target.getFirstName();
		columns[LAST_NAME] = target.getLastName();
		columns[TREATMENT_GROUP] = target.getTreatmentGroup();
//		columns[TREATMENT_START] = target.getTreatmentStart();
		columns[COMMENT] = target.getComment();
		columns[CREATED] = target.getCreated();
		columns[UPDATED] = target.getUpdated();		
		return columns;
	} 

	@Override
	protected int[] columnSizes() {
		int[] columnSizes = new int[NO_OF_FIELDS];
		columnSizes[TARGET_ID_NUM] = COLUMN_SIZE_SMALL;
		columnSizes[TARGET_ID_STR] = COLUMN_SIZE_SMALL;
		columnSizes[STATUS] = COLUMN_SIZE_WIDE;
		columnSizes[REGION] = COLUMN_SIZE_NORMAL;
		columnSizes[DATA_COLLECTOR] = COLUMN_SIZE_WIDE;
		columnSizes[DATA_COLLECTOR_TELEPHONE] = COLUMN_SIZE_WIDE;
		columnSizes[GENDER] = COLUMN_SIZE_SMALL;
		columnSizes[FIRST_NAME] = COLUMN_SIZE_NORMAL;
		columnSizes[LAST_NAME] = COLUMN_SIZE_NORMAL;
		columnSizes[TREATMENT_GROUP] = COLUMN_SIZE_WIDE;
//		columnSizes[TREATMENT_START] = COLUMN_SIZE_NORMAL;
		columnSizes[COMMENT] = COLUMN_SIZE_WIDE;
		columnSizes[CREATED] = 120;
		columnSizes[UPDATED] = 120;
		return columnSizes;
	}
}


