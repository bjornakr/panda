package no.atferdssenteret.panda.model.table;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.entity.TargetNote;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;

public class TargetNoteTable extends DefaultAbstractTableModel {
	private static final long serialVersionUID = 1L;

	private final static int NO_OF_FIELDS = 3;
	private final static int DATE = 0;
	private final static int NOTE = 1;
	private final static int SIGNATURE = 2;

	@Override
	protected String[] headerColumns() {
		String[] headerColumns = new String[NO_OF_FIELDS];
		headerColumns[DATE] = "Dato";
		headerColumns[NOTE] = "Notat";
		headerColumns[SIGNATURE] = "Signatur";
		return headerColumns;
	}

	@Override
	protected Object[] dataColumns(Model model) {
		TargetNote targetNote = (TargetNote)model;
		Object[] dataColumns = new Object[NO_OF_FIELDS];
		dataColumns[DATE] = targetNote.getCreated();
		dataColumns[NOTE] = targetNote.getNote();
		dataColumns[SIGNATURE] = targetNote.getUpdatedBy();
		return dataColumns;
	}

	@Override
	protected int[] columnSizes() {
		int[] columnSizes = new int[NO_OF_FIELDS];
		columnSizes[DATE] = 120;
		columnSizes[NOTE] = 500;
		columnSizes[SIGNATURE] = COLUMN_SIZE_SMALL;
		return columnSizes;
	}

}
