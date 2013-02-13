package no.atferdssenteret.panda.model.table;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.entity.Questionnaire;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;

public class QuestionnaireTable extends DefaultAbstractTableModel {
	private static final long serialVersionUID = 1L;

	private final static int NO_OF_FIELDS = 7;
	private final static int TARGET_ID = 0;
	private final static int DATA_COLLECTION = 1;
	private final static int NAME = 2;
	private final static int STATUS = 3;
	private final static int LAST_EVENT = 4;
	private final static int LAST_EVENT_DATE = 5;
	private final static int LAST_EVENT_SIGN = 6;

	@Override
	protected String[] headerColumns() {
		String[] headerColumns = new String[NO_OF_FIELDS];
		headerColumns[TARGET_ID] = "ID";
		headerColumns[DATA_COLLECTION] = "Datainnsamling";
		headerColumns[NAME] = "Spørreskjema";
		headerColumns[STATUS] = "Status";
		headerColumns[LAST_EVENT] = "Siste hendelse";	
		headerColumns[LAST_EVENT_DATE] = "Date";	
		headerColumns[LAST_EVENT_SIGN] = "Signatur";	
		return headerColumns;
	}

	@Override
	protected Object[] dataColumns(Model model) {
		Questionnaire questionnaire = (Questionnaire)model;
		Object[] dataColumns = new Object[NO_OF_FIELDS];
		if (questionnaire.getDataCollection() != null) {
			dataColumns[TARGET_ID] = questionnaire.target().formattedIdWithLetterAppendix();
		}
		dataColumns[DATA_COLLECTION] = questionnaire.getDataCollection().getType();
		dataColumns[NAME] = questionnaire.getName();
		dataColumns[STATUS] = questionnaire.calculateStatus();
		if (questionnaire.lastEvent() != null) {
			dataColumns[LAST_EVENT] = questionnaire.lastEvent().getType();
			dataColumns[LAST_EVENT_DATE] = questionnaire.lastEvent().getDate();
			dataColumns[LAST_EVENT_SIGN] = questionnaire.lastEvent().getUpdatedBy();
		}
		return dataColumns;
	}

	@Override
	protected ColumnSizes[] columnSizes() {
		ColumnSizes[] columnSizes = new ColumnSizes[NO_OF_FIELDS];
		columnSizes[TARGET_ID] = ColumnSizes.SMALL;
		columnSizes[DATA_COLLECTION] = ColumnSizes.NORMAL;
		columnSizes[NAME] = ColumnSizes.WIDE;
		columnSizes[STATUS] = ColumnSizes.WIDE;
		columnSizes[LAST_EVENT] = ColumnSizes.WIDE;
		columnSizes[LAST_EVENT_DATE] = ColumnSizes.SMALL;
		columnSizes[LAST_EVENT_SIGN] = ColumnSizes.SMALL;
		return columnSizes;
	}
}
