package no.atferdssenteret.panda.model;

public class ParticipantRoles {
	public final static String MOTHER = "Mor eller annen kvinnelig forsørger";
	public final static String FATHER = "Far eller annen mannlig forsørger";
	public final static String INTERVENTIONIST = "Intervensjonist";
	public final static String TEACHER = "Lærer";

	public static String[] allRoles() {
		String[] allRoles = {MOTHER, FATHER, INTERVENTIONIST, TEACHER};
		return allRoles;
	}
}
