package no.atferdssenteret.panda.model;

import java.util.LinkedList;

public class QuestionnaireTypes extends LinkedList<String> {
	private static final long serialVersionUID = 1L;
	private static QuestionnaireTypes self;
	
	private QuestionnaireTypes() {
	}
	
	public static QuestionnaireTypes getInstance() {
		if (self == null) {
			self = new QuestionnaireTypes();
		}
		return self;
	}
}
