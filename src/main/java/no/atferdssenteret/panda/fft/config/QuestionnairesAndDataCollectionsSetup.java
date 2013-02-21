package no.atferdssenteret.panda.fft.config;

import no.atferdssenteret.panda.QuestionnairesForDataCollectionType;
import no.atferdssenteret.panda.model.QuestionnaireTypes;

public class QuestionnairesAndDataCollectionsSetup {
	public static final String DC_T1 = "T1";
	public static final String DC_P1 = "P1";
	public static final String DC_P2 = "P2";
	public static final String DC_T2 = "T2";
	public static final String DC_T3_I = "T3 Intervensjon";	
	public static final String DC_T3_C = "T3 Kontroll";	
	
	private static final String Q_PARENT = "Foreldreskjema";
	private static final String Q_TEACHER = "Lærerskjema";
	private static final String Q_INTERVENTIONIST_PROCESS = "Prosess, intervensjonist";
	private static final String Q_TEACHER_PROCESS = "Prosess, lærer";

	
	
	public static void setup() {
		DataCollectionTypes dataCollectionTypes = DataCollectionTypes.getInstance();
		dataCollectionTypes.add(DC_T1);
		dataCollectionTypes.add(DC_T2);
		dataCollectionTypes.add(DC_P1);
		dataCollectionTypes.add(DC_P2);
		dataCollectionTypes.add(DC_T3_I);
		dataCollectionTypes.add(DC_T3_C);

		QuestionnaireTypes questionnaireTypes = QuestionnaireTypes.getInstance();
		questionnaireTypes.add(Q_PARENT);
		questionnaireTypes.add(Q_TEACHER);
		questionnaireTypes.add(Q_INTERVENTIONIST_PROCESS);
		questionnaireTypes.add(Q_TEACHER_PROCESS);
		
		
		QuestionnairesForDataCollectionType dcqMap = QuestionnairesForDataCollectionType.getInstance();
		dcqMap.addQuestionnaireNameForDataCollection(DC_T1, Q_PARENT);
		dcqMap.addQuestionnaireNameForDataCollection(DC_T1, Q_TEACHER);
		dcqMap.addQuestionnaireNameForDataCollection(DC_T2, Q_PARENT);
		dcqMap.addQuestionnaireNameForDataCollection(DC_T2, Q_TEACHER);
		dcqMap.addQuestionnaireNameForDataCollection(DC_T3_I, Q_PARENT);		
		dcqMap.addQuestionnaireNameForDataCollection(DC_T3_I, Q_TEACHER);
		dcqMap.addQuestionnaireNameForDataCollection(DC_T3_I, Q_INTERVENTIONIST_PROCESS);
		dcqMap.addQuestionnaireNameForDataCollection(DC_T3_C, Q_PARENT);		
		dcqMap.addQuestionnaireNameForDataCollection(DC_T3_C, Q_TEACHER);
	}
}
