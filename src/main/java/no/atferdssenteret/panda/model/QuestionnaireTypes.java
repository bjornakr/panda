package no.atferdssenteret.panda.model;

public class QuestionnaireTypes {
	public final static String PARENT = "Foreldreskjema";
	public final static String YOUTH = "Ungdomsskjema";
	public final static String TEACHER = "Lærerskjema";
	public final static String INTERVENTIONIST = "Konsulentskjema";
	
	public static String[] values() {
		String[] values = {PARENT, YOUTH, TEACHER, INTERVENTIONIST};
		return values;
	}
}
