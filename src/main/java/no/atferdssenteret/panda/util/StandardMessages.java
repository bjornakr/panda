package no.atferdssenteret.panda.util;

public class StandardMessages {
	public static String missingField(String field) {
		return "Følgende felt må være utfylt: " + field;
	}
	
	public static String invalidDateFormat(String input) {
		return "Ugyldig dato: \"" + input + "\". Bruk formen \"dd.mm.åå\".";
	}

	public static String textTooLong(String input) {
		return "For lang tekst i felt: " + input; 
	}
}