package no.atferdssenteret.panda.model.table;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.entity.Questionnaire;
import no.atferdssenteret.panda.model.entity.QuestionnaireEvent;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;

public class QuestionnaireTableForDataCollectionView extends DefaultAbstractTableModel {
	private static final long serialVersionUID = 1L;

	private final static int NO_OF_FIELDS = 4;
	private final static int NAME = 0;
	private final static int STATUS = 1;
	private final static int LAST_EVENT = 2;
	private final static int EVENT_DATE = 3;

	@Override
	protected String[] headerColumns() {
		String[] headerColumns = new String[NO_OF_FIELDS];
		headerColumns[NAME] = "Spørreskjema";
		headerColumns[STATUS] = "Status";
		headerColumns[LAST_EVENT] = "Siste hendelse";
		headerColumns[EVENT_DATE] = "Dato";
		return headerColumns;
	}

	@Override
	protected Object[] dataColumns(Model model) {
		Questionnaire questionnaire = (Questionnaire)model;
		Object[] dataColumns = new Object[NO_OF_FIELDS];
		dataColumns[NAME] = questionnaire.getName();
		dataColumns[STATUS] = questionnaire.calculateStatus();
		QuestionnaireEvent lastEvent = questionnaire.lastEvent();
		if (lastEvent != null) {
			dataColumns[LAST_EVENT] = questionnaire.lastEvent().getType();
			dataColumns[EVENT_DATE] = questionnaire.lastEvent().getDate();
		}
		return dataColumns;
	}

	@Override
	protected int[] columnSizes() {
		int[] columnSizes = new int[NO_OF_FIELDS];
		columnSizes[NAME] = COLUMN_SIZE_WIDE;
		columnSizes[STATUS] = COLUMN_SIZE_WIDE;
		columnSizes[LAST_EVENT] = COLUMN_SIZE_WIDE;
		columnSizes[EVENT_DATE] = COLUMN_SIZE_SMALL;
		return columnSizes;
	}
}
