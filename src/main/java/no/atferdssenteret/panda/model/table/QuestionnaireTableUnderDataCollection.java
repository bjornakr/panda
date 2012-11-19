package no.atferdssenteret.panda.model.table;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Questionnaire;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;

public class QuestionnaireTableUnderDataCollection extends DefaultAbstractTableModel {
	private static final long serialVersionUID = 1L;

	private final static int NO_OF_FIELDS = 3;
	private final static int NAME = 0;
	private final static int STATUS = 1;
	private final static int LAST_EVENT = 2;

	@Override
	protected String[] headerColumns() {
		String[] headerColumns = new String[NO_OF_FIELDS];
		headerColumns[NAME] = "Spørreskjema";
		headerColumns[STATUS] = "Status";
		headerColumns[LAST_EVENT] = "Siste hendelse";	
		return headerColumns;
	}

	@Override
	protected Object[] dataColumns(Model model) {
		Questionnaire questionnaire = (Questionnaire)model;
		Object[] dataColumns = new Object[NO_OF_FIELDS];
		dataColumns[NAME] = questionnaire.getName();
		dataColumns[STATUS] = questionnaire.getStatus();
		dataColumns[LAST_EVENT] = questionnaire.lastEvent();
		return dataColumns;
	}

	@Override
	protected ColumnSizes[] columnSizes() {
		ColumnSizes[] columnSizes = new ColumnSizes[NO_OF_FIELDS];
		columnSizes[NAME] = ColumnSizes.WIDE;
		columnSizes[STATUS] = ColumnSizes.WIDE;
		columnSizes[LAST_EVENT] = ColumnSizes.WIDE;
		return columnSizes;
	}
}
