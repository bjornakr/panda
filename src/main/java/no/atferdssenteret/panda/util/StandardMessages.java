package no.atferdssenteret.panda.util;

public class StandardMessages {
	public static String missingField(String s) {
		return "Følgende felt må være utfylt: " + s;
	}
	
	public static String invalidDateFormat(String s) {
		return "Ugyldig dato: \"" + s + "\". Bruk formen \"dd.mm.åå\".";
	}
}