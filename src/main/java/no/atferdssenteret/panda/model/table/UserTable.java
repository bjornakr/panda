package no.atferdssenteret.panda.model.table;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.entity.User;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;

public class UserTable extends DefaultAbstractTableModel {
	private static final long serialVersionUID = 1L;
	private final static int NO_OF_FIELDS = 4;
    private final static int USERNAME = 0;
    private final static int ACCESS_LEVEL = 1;
    private final static int FIRST_NAME = 2;
    private final static int LAST_NAME = 3;

	@Override
	protected String[] headerColumns() {
		String[] headerColumns = new String[NO_OF_FIELDS];
		headerColumns[USERNAME] = "Brukernavn";
		headerColumns[ACCESS_LEVEL] = "Adgangsnivå";
		headerColumns[FIRST_NAME] = "Fornavn";
		headerColumns[LAST_NAME] = "Etternavn";
		return headerColumns;
	}

	@Override
	protected Object[] dataColumns(Model model) {
		User user = (User)model;
		Object[] columns = new Object[headerColumns().length];
		columns[USERNAME] = user.getUsername();
		columns[ACCESS_LEVEL] = user.getAccessLevel();
		columns[FIRST_NAME] = user.getFirstName();
		columns[LAST_NAME] = user.getLastName();
		return columns;
	} 

	@Override
	protected int[] columnSizes() {
		int[] columnSizes = new int[NO_OF_FIELDS];
		columnSizes[USERNAME] = COLUMN_SIZE_SMALL;
		columnSizes[ACCESS_LEVEL] = COLUMN_SIZE_NORMAL;
		columnSizes[FIRST_NAME] = COLUMN_SIZE_WIDE;
		columnSizes[LAST_NAME] = COLUMN_SIZE_WIDE;
		return columnSizes;
	}
}
