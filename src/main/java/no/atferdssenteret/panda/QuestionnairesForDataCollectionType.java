package no.atferdssenteret.panda;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
}
