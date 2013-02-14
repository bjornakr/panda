package no.atferdssenteret.panda.view;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.swing.table.DefaultTableCellRenderer;

import no.atferdssenteret.panda.util.DateUtil;

public class TableDateRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat formatter; 

	public TableDateRenderer() {
		super();
	}

	public void setValue(Object value) {
		if (value instanceof Timestamp) {
			formatter = DateUtil.timestampFormat();
		}
		else if (value instanceof java.sql.Date) {
			formatter = DateUtil.dateFormat();
		}

		setText((value == null) ? "" : formatter.format(value));
	}
}
