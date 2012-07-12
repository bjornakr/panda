package no.atferdssenteret.panda.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {

    public static Date parseDateFromInternationalDateFormat(String dateString) throws ParseException {
	return new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateString).getTime());
	
    }
}
