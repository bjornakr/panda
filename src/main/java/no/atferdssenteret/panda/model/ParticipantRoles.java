package no.atferdssenteret.panda.model;

public class ParticipantRoles {
	public final static String MOTHER = "Mor";
	public final static String STEP_MOTHER = "Stemor";
	public final static String FOSTER_MOTHER = "Fostermor";
	public final static String MOTHERS_PARTNER_FEMALE = "Partner til mor, kvinne";
	public final static String FATHERS_PARTNER_FEMALE = "Partner til far, kvinne";	
	public final static String FATHER = "Far";
	public final static String STEP_FATHER = "Stefar";
	public final static String FOSTER_FATHER = "Fosterfar";
	public final static String PARTNER_MALE = "Mannlig partner";
	public final static String MOTHERS_PARTNER_MALE = "Partner til mor, mann";
	public final static String FATHERS_PARTNER_MALE = "Partner til far, mann";	
	public final static String INTERVENTIONIST = "Intervensjonist";
	public final static String TEACHER = "Lærer";

	public static String[] values() {
		String[] allRoles = {
				MOTHER,
				STEP_MOTHER,
				FOSTER_MOTHER,
				FATHERS_PARTNER_FEMALE,
				MOTHERS_PARTNER_MALE,
				FATHER,
				STEP_FATHER,
				FOSTER_FATHER,
				MOTHERS_PARTNER_MALE,
				FATHERS_PARTNER_MALE,
				INTERVENTIONIST,
				TEACHER
		};
		return allRoles;
	}
}
