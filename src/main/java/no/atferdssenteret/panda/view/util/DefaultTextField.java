package no.atferdssenteret.panda.view.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JTextField;

import no.atferdssenteret.panda.util.StringUtil;

public class DefaultTextField extends JTextField {
    private static final long serialVersionUID = 1L;
    
    public static final int DATE_WIDTH = 6;
    public static final int STANDARD_TEXT_WIDTH = 20;
    

    {
	setMinimumSize(getPreferredSize());
    }

    public DefaultTextField() {
	super(STANDARD_TEXT_WIDTH);
    }
    
    public DefaultTextField(int columns) {
	super(columns);
    }
    
    public void setText(Date d) {
	if (d != null) {
	    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy", new Locale("Norwegian"));
	    setText(df.format(d));
	}
    }
    
    @Override
    public String getText() {
	return StringUtil.groomString(super.getText());
    }
}

