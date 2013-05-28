package no.atferdssenteret.tibirbase;

import no.atferdssenteret.panda.QuestionnairesForDataCollectionType;
import no.atferdssenteret.panda.model.DataCollectionTypes;
import no.atferdssenteret.panda.model.QuestionnaireTypes;

public class QDC2 {
	public static final String DC_T1 = "T1";
	public static final String DC_P1 = "P1";
	public static final String DC_P2 = "P2";
	public static final String DC_P3 = "P3";
	public static final String DC_P4 = "P4";
	public static final String DC_P5 = "P5";
	public static final String DC_T2 = "T2";
	public static final String DC_THERAPIST_I = "Terapeutskjema";
	public static final String DC_VIDEO_I = "Videopptak";
	public static final String DC_T3 = "T3";	

	private static final String Q_PARENT_M = "Foreldreskjema mor";
	private static final String Q_PARENT_F = "Foreldreskjema far";
	private static final String Q_YOUTH = "Ungdomsskjema";
	private static final String Q_TEACHER = "Lærerskjema ";
	private static final String Q_HI = "Skjema til henvisende instans";
	private static final String Q_PHONE_PARENT = "Telefonintervju forelder";
	private static final String Q_PHONE_YOUTH = "Telefonintervju ungdom";
	private static final String Q_THERAPIST = "Terapeutskjema";
	private static final String Q_FAMSATIS = "Vurdering av tilaket";
	private static final String Q_VIDEO_EM1 = "Film EM1";
	private static final String Q_VIDEO_EM2 = "Film EM2";
	private static final String Q_VIDEO_BC1 = "Film BC1";
	private static final String Q_VIDEO_BC2 = "Film BC2";
	private static final String Q_VIDEO_GEN1 = "Film GEN1";
	private static final String Q_VIDEO_GEN2 = "Film GEN2";
	private static final String Q_THER_FSR_EM1 = "FSR-EM1";
	private static final String Q_THER_FSR_EM2 = "FSR-EM2";
	private static final String Q_THER_FSR_BC1 = "FSR_BC1";
	private static final String Q_THER_FSR_BC2 = "FSR_EBC2";
	private static final String Q_THER_FSR_GEN1 = "FSR_GEN1";
	private static final String Q_THER_FSR_GEN2 = "FSR-GEN2";
	private static final String Q_THER_FFT_CSS = "FFT-CSS";
	private static final String Q_THER_COM_A = "COM-A";
	private static final String Q_THER_COM_P = "COM-P";





	public static void setup() {
		DataCollectionTypes dataCollectionTypes = DataCollectionTypes.getInstance();
		dataCollectionTypes.add(DC_T1);
		dataCollectionTypes.add(DC_P1);
		dataCollectionTypes.add(DC_P2);
		dataCollectionTypes.add(DC_P3);
		dataCollectionTypes.add(DC_P4);
		dataCollectionTypes.add(DC_P5);
		dataCollectionTypes.add(DC_T2);
		dataCollectionTypes.add(DC_THERAPIST_I);
		dataCollectionTypes.add(DC_VIDEO_I);
		dataCollectionTypes.add(DC_T3);

		QuestionnaireTypes questionnaireTypes = QuestionnaireTypes.getInstance();
		questionnaireTypes.add(Q_PARENT_M);
		questionnaireTypes.add(Q_PARENT_F);
		questionnaireTypes.add(Q_YOUTH);
		questionnaireTypes.add(Q_TEACHER);
		questionnaireTypes.add(Q_HI);
		questionnaireTypes.add(Q_PHONE_PARENT);
		questionnaireTypes.add(Q_PHONE_YOUTH);
		questionnaireTypes.add(Q_THERAPIST);
		questionnaireTypes.add(Q_FAMSATIS);
		questionnaireTypes.add(Q_VIDEO_EM1);
		questionnaireTypes.add(Q_VIDEO_EM2);
		questionnaireTypes.add(Q_VIDEO_BC1);
		questionnaireTypes.add(Q_VIDEO_BC2);
		questionnaireTypes.add(Q_VIDEO_GEN1);
		questionnaireTypes.add(Q_VIDEO_GEN2);
		questionnaireTypes.add(Q_THER_FSR_EM1);
		questionnaireTypes.add(Q_THER_FSR_EM2);
		questionnaireTypes.add(Q_THER_FSR_BC1);
		questionnaireTypes.add(Q_THER_FSR_BC2);
		questionnaireTypes.add(Q_THER_FSR_GEN1);
		questionnaireTypes.add(Q_THER_FSR_GEN2);
		questionnaireTypes.add(Q_THER_FFT_CSS);
		questionnaireTypes.add(Q_THER_COM_A);
		questionnaireTypes.add(Q_THER_COM_P);

		
		

		QuestionnairesForDataCollectionType dcqMap = QuestionnairesForDataCollectionType.getInstance();
		dcqMap.addQuestionnaireNameForDataCollection(DC_T1, Q_PARENT_M);
		dcqMap.addQuestionnaireNameForDataCollection(DC_T1, Q_PARENT_F);
		dcqMap.addQuestionnaireNameForDataCollection(DC_T1, Q_YOUTH);
		dcqMap.addQuestionnaireNameForDataCollection(DC_T1, Q_TEACHER);
		dcqMap.addQuestionnaireNameForDataCollection(DC_T1, Q_HI);

		dcqMap.addQuestionnaireNameForDataCollection(DC_P1, Q_PHONE_PARENT);
		dcqMap.addQuestionnaireNameForDataCollection(DC_P1, Q_PHONE_YOUTH);
		dcqMap.addQuestionnaireNameForDataCollection(DC_P2, Q_PHONE_PARENT);
		dcqMap.addQuestionnaireNameForDataCollection(DC_P2, Q_PHONE_YOUTH);
		dcqMap.addQuestionnaireNameForDataCollection(DC_P3, Q_PHONE_PARENT);
		dcqMap.addQuestionnaireNameForDataCollection(DC_P3, Q_PHONE_YOUTH);
		dcqMap.addQuestionnaireNameForDataCollection(DC_P4, Q_PHONE_PARENT);
		dcqMap.addQuestionnaireNameForDataCollection(DC_P4, Q_PHONE_YOUTH);
		dcqMap.addQuestionnaireNameForDataCollection(DC_P5, Q_PHONE_PARENT);
		dcqMap.addQuestionnaireNameForDataCollection(DC_P5, Q_PHONE_YOUTH);
		
		dcqMap.addQuestionnaireNameForDataCollection(DC_T2, Q_PARENT_M);
		dcqMap.addQuestionnaireNameForDataCollection(DC_T2, Q_PARENT_F);
		dcqMap.addQuestionnaireNameForDataCollection(DC_T2, Q_YOUTH);
		dcqMap.addQuestionnaireNameForDataCollection(DC_T2, Q_TEACHER);
		dcqMap.addQuestionnaireNameForDataCollection(DC_T2, Q_THERAPIST);
		dcqMap.addQuestionnaireNameForDataCollection(DC_T2, Q_FAMSATIS);
		
		dcqMap.addQuestionnaireNameForDataCollection(DC_VIDEO_I, Q_VIDEO_EM1);
		dcqMap.addQuestionnaireNameForDataCollection(DC_VIDEO_I, Q_VIDEO_EM2);
		dcqMap.addQuestionnaireNameForDataCollection(DC_VIDEO_I, Q_VIDEO_BC1);
		dcqMap.addQuestionnaireNameForDataCollection(DC_VIDEO_I, Q_VIDEO_BC2);
		dcqMap.addQuestionnaireNameForDataCollection(DC_VIDEO_I, Q_VIDEO_GEN1);
		dcqMap.addQuestionnaireNameForDataCollection(DC_VIDEO_I, Q_VIDEO_GEN2);

		dcqMap.addQuestionnaireNameForDataCollection(DC_THERAPIST_I, Q_THER_FSR_EM1);
		dcqMap.addQuestionnaireNameForDataCollection(DC_THERAPIST_I, Q_THER_FSR_EM2);
		dcqMap.addQuestionnaireNameForDataCollection(DC_THERAPIST_I, Q_THER_FSR_BC1);
		dcqMap.addQuestionnaireNameForDataCollection(DC_THERAPIST_I, Q_THER_FSR_BC2);
		dcqMap.addQuestionnaireNameForDataCollection(DC_THERAPIST_I, Q_THER_FSR_GEN1);
		dcqMap.addQuestionnaireNameForDataCollection(DC_THERAPIST_I, Q_THER_FSR_GEN2);
		dcqMap.addQuestionnaireNameForDataCollection(DC_THERAPIST_I, Q_THER_COM_A);
		dcqMap.addQuestionnaireNameForDataCollection(DC_THERAPIST_I, Q_THER_COM_P);
		dcqMap.addQuestionnaireNameForDataCollection(DC_THERAPIST_I, Q_THER_FFT_CSS);
		
		dcqMap.addQuestionnaireNameForDataCollection(DC_T3, Q_PARENT_M);
		dcqMap.addQuestionnaireNameForDataCollection(DC_T3, Q_PARENT_F);
		dcqMap.addQuestionnaireNameForDataCollection(DC_T3, Q_YOUTH);
		dcqMap.addQuestionnaireNameForDataCollection(DC_T3, Q_TEACHER);		
	}
}
