package no.atferdssenteret.panda.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateUtil {
    private static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy", new Locale("Norwegian"));

    public static Date parseDateFromInternationalDateFormat(String dateString) {
	try {
	    return new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateString).getTime());
	}
	catch (ParseException e) {
	    throw new RuntimeException(e);
	}
	
    }
    
    public static SimpleDateFormat getDefaultDateFormat() {
	return df;
    }
    
    public static String getFormattedTodaysDate() {
	return df.format(new Date(System.currentTimeMillis()));
    }
    
    public static String formatDate(Date d) {
	if (d == null) {
	    return null;
	}
	
	return df.format(d);
    }

    public static java.sql.Date formatYear(String year) {
	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.YEAR, Integer.parseInt(year));

	return new java.sql.Date(cal.getTimeInMillis());
    }
    
    public static boolean IsDateInInterval(Date d, Date start, Date end) {
	return (!d.before(start) && !d.after(end));		
    }
}
