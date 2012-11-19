package no.atferdssenteret.panda.model.table;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Participant;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;

public class ParticipantTable extends DefaultAbstractTableModel {
	private static final long serialVersionUID = 1L;

	private final static int NO_OF_FIELDS = 8;
	private final static int TARGET_ID = 0;
	private final static int ROLE = 1;
	private final static int STATUS = 2;
	private final static int FIRST_NAME = 3;
	private final static int LAST_NAME = 4;
	private final static int TLF_NO = 5;    
	private final static int E_MAIL = 6;
	private final static int COMMENT = 7;

	@Override
	protected String[] headerColumns() {
		String[] headerColumns = new String[NO_OF_FIELDS];
		headerColumns[TARGET_ID] = "ID";
		headerColumns[ROLE] = "Rolle";
		headerColumns[STATUS] = "Status";
		headerColumns[FIRST_NAME] = "Fornavn";
		headerColumns[LAST_NAME] = "Etternavn";
		headerColumns[TLF_NO] = "Tlf nr.";
		headerColumns[E_MAIL] = "E-post";
		headerColumns[COMMENT] = "Kommentar";
		return headerColumns;
	}

	@Override
	protected Object[] dataColumns(Model model) {
		Participant participant = (Participant)model;
		Object[] dataColumns = new Object[NO_OF_FIELDS];
		dataColumns[TARGET_ID] = participant.getTarget().formattedIdWithLetterAppendix();
		dataColumns[ROLE] = participant.getRole();
		dataColumns[STATUS] = participant.getStatus();
		dataColumns[FIRST_NAME] = participant.getFirstName();
		dataColumns[LAST_NAME] = participant.getLastName();
		dataColumns[TLF_NO] = participant.getPhoneNo();
		dataColumns[E_MAIL] = participant.getEMail();
		dataColumns[COMMENT] = participant.getComment();	
		return dataColumns;
	}

	@Override
	protected ColumnSizes[] columnSizes() {
		ColumnSizes[] columnSizes = new ColumnSizes[NO_OF_FIELDS];
		columnSizes[TARGET_ID] = ColumnSizes.SMALL;
		columnSizes[ROLE] = ColumnSizes.SMALL;
		columnSizes[STATUS] = ColumnSizes.NORMAL;
		columnSizes[FIRST_NAME] = ColumnSizes.NORMAL;
		columnSizes[LAST_NAME] = ColumnSizes.NORMAL;
		columnSizes[TLF_NO] = ColumnSizes.NORMAL;
		columnSizes[E_MAIL] = ColumnSizes.NORMAL;
		columnSizes[COMMENT] = ColumnSizes.WIDE;
		return columnSizes;
	}
}
