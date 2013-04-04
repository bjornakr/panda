package no.atferdssenteret.panda.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateUtil {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", new Locale("Norwegian"));
	private static final SimpleDateFormat timestampFormat = new SimpleDateFormat("dd.MM.yy, HH:mm", new Locale("Norwegian"));

	public static Date parseDateFromInternationalDateFormat(String dateString) {
		try {
			return new Date(new SimpleDateFormat("yy-MM-dd").parse(dateString).getTime());
		}
		catch (ParseException e) {
			throw new RuntimeException(e);
		}

	}

	public static SimpleDateFormat dateFormat() {
		return dateFormat;
	}
	
	public static SimpleDateFormat timestampFormat() {
		return timestampFormat;
	}

	public static String getFormattedTodaysDate() {
		return dateFormat.format(today());
	}

	public static String formatDate(Date d) {
		if (d == null) {
			return null;
		}

		return dateFormat.format(d);
	}

	public static java.sql.Date formatYear(String year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));

		return new java.sql.Date(cal.getTimeInMillis());
	}

	public static boolean IsDateInInterval(Date d, Date start, Date end) {
		return (!d.before(start) && !d.after(end));		
	}

	public static Date today() {
		return new Date(System.currentTimeMillis());
	}
	
	public static Date sqlDate(java.util.Date utilDate) {
		return new Date(utilDate.getTime());
	}
	
	public static Date addTime(Date date, int field, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return sqlDate(calendar.getTime());
	}
	
	public static Date addTime(Timestamp timestamp, int field, int amount) {
		return addTime(new Date(timestamp.getTime()), field, amount);
	}
}
