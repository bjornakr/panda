package no.atferdssenteret.panda.model.table;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.entity.Participant;
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
	protected int[] columnSizes() {
		int[] columnSizes = new int[NO_OF_FIELDS];
		columnSizes[TARGET_ID] = COLUMN_SIZE_SMALL;
		columnSizes[ROLE] = COLUMN_SIZE_WIDE;
		columnSizes[STATUS] = COLUMN_SIZE_NORMAL;
		columnSizes[FIRST_NAME] = COLUMN_SIZE_NORMAL;
		columnSizes[LAST_NAME] = COLUMN_SIZE_NORMAL;
		columnSizes[TLF_NO] = COLUMN_SIZE_NORMAL;
		columnSizes[E_MAIL] = COLUMN_SIZE_NORMAL;
		columnSizes[COMMENT] = COLUMN_SIZE_WIDE;
		return columnSizes;
	}
}
