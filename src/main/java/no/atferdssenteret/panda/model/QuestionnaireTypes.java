package no.atferdssenteret.panda.model;

public class QuestionnaireTypes {
	public final static String PARENT = "Skjemapakke foreldre";
	public final static String YOUTH = "Skjemapakke ungdom";
	public final static String TEACHER = "Skjemapakke lærer";
//	public final static String 
	
	public static String[] values() {
		String[] values = {PARENT, YOUTH, TEACHER};
		return values;
	}
}
