package no.atferdssenteret.panda.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StringUtil {

	/**
	 * Parses a date in String format.
	 * 
	 * @param dateString Date in String format.
	 * @return Date in Date format.
	 */
	public static java.sql.Date parseDate(String dateString) {
		dateString = groomString(dateString);

		if (dateString == null) {
			return null;
		}

		if (dateString.length() != 8) {
			throw new IllegalArgumentException("Ugyldig dato: \"" + dateString + "\". Bruk formen \"dd.mm.åå\".");
		}

		int day = new Integer(dateString.substring(0, 2));
		int month = new Integer(dateString.substring(3, 5));
		int year = new Integer(dateString.substring(6, 7));

		if ((day < 1) || (day > 31) || (month < 1) || (month > 12) || (year < 0) || (year > 99)) {
			throw new IllegalArgumentException("Ugyldig dato: \"" + dateString + "\".");
		}


		SimpleDateFormat df = DateUtil.dateFormat();
		try {
			return new java.sql.Date(df.parse(dateString).getTime());
		}
		catch (ParseException e) {
			throw new IllegalArgumentException(StandardMessages.invalidDateFormat(dateString));
		}
	}


	public static String groomString(String s) {
		if (s != null) {
			s = s.trim();

//			if (s.contains("\\") || s.contains("'")) {
//				throw new IllegalArgumentException("Teksten \"" + s + "\" er ulovlig. Av sikkerhetsmessige grunner kan en tekststreng ikke inneholde tegnene " +
//						"backslash (\\) eller fnutt (').");					
//			}

			if (s.equals("")) {
				s = null;
			}
		}
		return s;
	}

	public static Integer stringToInt(String s, String field) {
		if (s == null) {
			return null;
		}

		try {
			Integer i = Integer.parseInt(s);
			return i; 
		}
		catch (NumberFormatException e) {
			throw new IllegalArgumentException("Feil i feltet \"" + field + "\": Ugyldig verdi: \"" + s + "\".");  
		}
	}

	public static boolean validNumber(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Splits a (long) string over several lines. Doesn't work with strings without spaces.
	 * 
	 * @param s The string to be parsed
	 * @param prefferedLength Preferred length of each line (result may be shorter or may exceed this number)
	 * @param padding_ Number of spaces in front of subsequent lines
	 * @return A new string with linebreaks
	 */
	public static String splitString(String s, int prefferedLength, int padding_) {
		s = s.trim();
		String result = null;
		Integer lastSpace = null;
		int substringStart = 0;
		int currentLength = 0;

		String padding = "";
		for (int i = 0; i < padding_; i++) {
			padding = padding + " ";
		}


		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ' ') {
				lastSpace = i;
			}
			else if (s.charAt(i) == '\n') {
				currentLength = 0;
			}
			if (currentLength >= prefferedLength && lastSpace != null) {
				if (result == null) {
					result = (s.substring(substringStart, lastSpace) + "<br>");
				}
				else {
					result = (result + padding + s.substring(substringStart, lastSpace) + "<br>");
				}
				substringStart = lastSpace + 1;
				currentLength = 0;
				lastSpace = null;
			}
			else {
				currentLength++;
			}
		}

		if (result != null) {
			result = result + padding + s.substring(substringStart);
			result.replace("\n", "<br>");
			return "<html>" + result + "</html>";
		}
		return s;
	}
}
