package no.atferdssenteret.panda.config;

import no.atferdssenteret.panda.QuestionnairesForDataCollectionType;
import no.atferdssenteret.panda.model.DataCollectionTypes;
import no.atferdssenteret.panda.model.QuestionnaireTypes;

public class QuestionnairesForDataCollectionSetup {
	public static void setup() {
		QuestionnairesForDataCollectionType dcqMap = QuestionnairesForDataCollectionType.getInstance();
		dcqMap.addQuestionnaireNameForDataCollection(DataCollectionTypes.T1, QuestionnaireTypes.PARENT);
		dcqMap.addQuestionnaireNameForDataCollection(DataCollectionTypes.T1, QuestionnaireTypes.TEACHER);
		dcqMap.addQuestionnaireNameForDataCollection(DataCollectionTypes.T2, QuestionnaireTypes.PARENT);
		dcqMap.addQuestionnaireNameForDataCollection(DataCollectionTypes.T2, QuestionnaireTypes.TEACHER);
		dcqMap.addQuestionnaireNameForDataCollection(DataCollectionTypes.T2, QuestionnaireTypes.INTERVENTIONIST);		
		dcqMap.addQuestionnaireNameForDataCollection(DataCollectionTypes.T3, QuestionnaireTypes.PARENT);
		dcqMap.addQuestionnaireNameForDataCollection(DataCollectionTypes.T3, QuestionnaireTypes.TEACHER);
		dcqMap.addQuestionnaireNameForDataCollection(DataCollectionTypes.P1, QuestionnaireTypes.INTERVENTIONIST);		
		dcqMap.addQuestionnaireNameForDataCollection(DataCollectionTypes.P2, QuestionnaireTypes.INTERVENTIONIST);	
	}
}
