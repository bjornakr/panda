package no.atferdssenteret.panda.model.table;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.entity.QuestionnaireEvent;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;

public class QuestionnaireEventTable extends DefaultAbstractTableModel {
	private static final long serialVersionUID = 1L;

	private final static int NO_OF_FIELDS = 4;
	private final static int DATE = 0;
	private final static int TYPE = 1;
	private final static int COMMENT = 2;
	private final static int SIGNATURE = 3;


	@Override
	protected String[] headerColumns() {
		String[] headerColumns = new String[NO_OF_FIELDS];
		headerColumns[DATE] = "Dato";
		headerColumns[TYPE] = "Hendelse";
		headerColumns[COMMENT] = "Kommentar";
		headerColumns[SIGNATURE] = "Signatur";
		return headerColumns;
	}

	@Override
	protected Object[] dataColumns(Model model) {
		QuestionnaireEvent questionnaireEvent = (QuestionnaireEvent)model;
		Object[] dataColumns = new Object[NO_OF_FIELDS];
		dataColumns[DATE] = questionnaireEvent.getDate();
		dataColumns[TYPE] = questionnaireEvent.getType();
		dataColumns[COMMENT] = questionnaireEvent.getComment();
		dataColumns[SIGNATURE] = questionnaireEvent.getUpdatedBy();
		return dataColumns;
	}

	@Override
	protected int[] columnSizes() {
		int[] columnSizes = new int[NO_OF_FIELDS];
		columnSizes[DATE] = COLUMN_SIZE_SMALL;
		columnSizes[TYPE] = COLUMN_SIZE_WIDE;
		columnSizes[COMMENT] = COLUMN_SIZE_WIDE;
		columnSizes[SIGNATURE] = COLUMN_SIZE_NORMAL;  
		return columnSizes;
	}
}
