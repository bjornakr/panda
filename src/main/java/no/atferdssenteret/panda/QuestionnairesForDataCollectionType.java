package no.atferdssenteret.panda;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class QuestionnairesForDataCollectionType {
    private static QuestionnairesForDataCollectionType self;
    private HashMap<String, List<String>> questionnairesForDataCollectionType = new HashMap<String, List<String>>();  

    private QuestionnairesForDataCollectionType() {	    
    }
    
    public static QuestionnairesForDataCollectionType getInstance() {
	if (self == null) {
	    self = new QuestionnairesForDataCollectionType();
	}
	return self; 
    }
    
    public List<String> getQuestionnairesFor(String type) {
	List<String> questionnaireNames = questionnairesForDataCollectionType.get(type);
	if (questionnaireNames == null) {
	    questionnaireNames = new LinkedList<String>();
	}
	return questionnaireNames;
    }
    
    public void addQuestionnaireForDataCollection(String type, String questionnaireName) {
	List<String> questionnaireNames = questionnairesForDataCollectionType.get(type);
	if (questionnaireNames == null) {
	    questionnaireNames = new LinkedList<String>();
	    questionnairesForDataCollectionType.put(type, questionnaireNames);
	}
	questionnaireNames.add(questionnaireName);
    }    
}
