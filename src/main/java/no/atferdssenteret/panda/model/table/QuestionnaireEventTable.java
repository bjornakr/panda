package no.atferdssenteret.panda.model.table;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.QuestionnaireEvent;
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
		dataColumns[SIGNATURE] = null;
		return dataColumns;
	}

	@Override
	protected ColumnSizes[] columnSizes() {
		ColumnSizes[] columnSizes = new ColumnSizes[NO_OF_FIELDS];
		columnSizes[DATE] = ColumnSizes.SMALL;
		columnSizes[TYPE] = ColumnSizes.WIDE;
		columnSizes[COMMENT] = ColumnSizes.WIDE;
		columnSizes[SIGNATURE] = ColumnSizes.NORMAL;  
		return columnSizes;
	}
}
