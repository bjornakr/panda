package no.atferdssenteret.panda;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import no.atferdssenteret.panda.model.Questionnaire;

public class QuestionnairesForDataCollectionType {
	private static QuestionnairesForDataCollectionType self;
	private HashMap<String, List<String>> questionnaireNamesForDataCollectionType = new HashMap<String, List<String>>();  

	private QuestionnairesForDataCollectionType() {	    
	}

	public static QuestionnairesForDataCollectionType getInstance() {
		if (self == null) {
			self = new QuestionnairesForDataCollectionType();
		}
		return self; 
	}

	public List<Questionnaire> getQuestionnairesFor(String dataCollectionType) {
		List<Questionnaire> questionnaires = new LinkedList<Questionnaire>();
		if (questionnaireNamesForDataCollectionType.get(dataCollectionType) == null) {
			return questionnaires;
		}
		for (String questionnaireName : questionnaireNamesForDataCollectionType.get(dataCollectionType)) {
			Questionnaire questionnaire = new Questionnaire();
			questionnaire.setName(questionnaireName);
			questionnaires.add(questionnaire);
		}
		return questionnaires;
	}

	public List<String> getQuestionnaireNamesFor(String type) {
		List<String> questionnaireNames = questionnaireNamesForDataCollectionType.get(type);
		if (questionnaireNames == null) {
			questionnaireNames = new LinkedList<String>();
		}
		return questionnaireNames;
	}

	public void addQuestionnaireNameForDataCollection(String name, String questionnaireName) {
		List<String> questionnaireNames = questionnaireNamesForDataCollectionType.get(name);
		if (questionnaireNames == null) {
			questionnaireNames = new LinkedList<String>();
			questionnaireNamesForDataCollectionType.put(name, questionnaireNames);
		}
		questionnaireNames.add(questionnaireName);
	}

	public Set<String> allQuestionnaireNames() {
		Set<String> allQuestionnaireNames = new HashSet<String>();
		for (List<String> questionnaireNamesForType : questionnaireNamesForDataCollectionType.values()) {
			allQuestionnaireNames.addAll(questionnaireNamesForType);
		}
		return allQuestionnaireNames;
	}

	public void clear() {
		questionnaireNamesForDataCollectionType.clear();
	}    
}
