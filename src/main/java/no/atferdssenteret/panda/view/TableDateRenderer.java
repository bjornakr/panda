package no.atferdssenteret.panda.view;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.table.DefaultTableCellRenderer;

public class TableDateRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat formatter; 

	public TableDateRenderer() {
		super();
	}

	public void setValue(Object value) {
		if (value instanceof Timestamp) {
			formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm", new Locale("Norwegian")); //TODO: Move to DateUtil
		}
		else if (value instanceof java.sql.Date) {
			formatter = new SimpleDateFormat("dd.MM.yyyy", new Locale("Norwegian"));
		}

		setText((value == null) ? "" : formatter.format(value));
	}
}
