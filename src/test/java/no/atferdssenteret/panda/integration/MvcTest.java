package no.atferdssenteret.panda.integration;

import java.awt.Window;

import no.atferdssenteret.panda.QuestionnairesForDataCollectionType;
import no.atferdssenteret.panda.controller.DataCollectionController;
import no.atferdssenteret.panda.controller.QuestionnaireController;
import no.atferdssenteret.panda.controller.QuestionnaireEventController;
import no.atferdssenteret.panda.controller.YouthController;
import no.atferdssenteret.panda.model.DataCollection;
import no.atferdssenteret.panda.model.Participant;
import no.atferdssenteret.panda.model.ParticipationStatuses;
import no.atferdssenteret.panda.model.Questionnaire;
import no.atferdssenteret.panda.model.QuestionnaireEvent;
import no.atferdssenteret.panda.model.fft.Youth;
import no.atferdssenteret.panda.util.DateUtil;
import no.atferdssenteret.panda.util.EmptyMainWindow;


@SuppressWarnings("unused")
public class MvcTest {


	public static void main(String[] args) {
		Window mainWindow = EmptyMainWindow.create();
		//	testYouth(mainWindow);
		//	testParticipant(mainWindow);
		testDataCollection(mainWindow);
		//	testQuestionnaire(mainWindow);
		//	testQuestionnaireEvent(mainWindow);
		//	JPATransactor.getInstance().persist(model);
	}

	private static void testQuestionnaireEvent(Window mainWindow) {
		QuestionnaireEvent model = createQuestionnaireEvent();
		new QuestionnaireEventController(mainWindow, model);

	}

	private static QuestionnaireEvent createQuestionnaireEvent() {
		QuestionnaireEvent model = new QuestionnaireEvent();
		model.setDate(DateUtil.parseDateFromInternationalDateFormat("2012-01-30"));
		model.setType(QuestionnaireEvent.Types.RECIEVED);
		model.setComment("I saw you in the morning light, your skin was softly glowing.");
		return model;
	}

	private static void testQuestionnaire(Window mainWindow) {
		createQuestionnairesForDataCollectionsTypes();
		Questionnaire model = createQuestionnaire();
		new QuestionnaireController(mainWindow, model);
	}

	private static Questionnaire createQuestionnaire() {
		return null;
	}

	private static void testDataCollection(Window mainWindow) {
		createQuestionnairesForDataCollectionsTypes();
		DataCollection model = createDataCollection();
		new DataCollectionController(mainWindow, null, null);
	}

//	private static void testParticipant(Window mainWindow) {
//		Participant model = createParticipant();
//		new ParticipantController(mainWindow, null);	
//	}

	private static void testYouth(Window mainWindow) {
		Youth model = createYouthModel();
		new YouthController(mainWindow, model);	
	}

	private static DataCollection createDataCollection() {
		return null;
	}

	private static Youth createYouthModel() {
		Youth youth = new Youth();
		youth.setFirstName("Doodlestick");
		youth.setLastName("Jones");
		youth.setComment("Hey Dr. Ukulele man");
		youth.setRegion(Youth.Regions[0]);
		youth.setStatus(ParticipationStatuses.PARTICIPATING);
		youth.setTreatmentGroup(Youth.TreatmentGroups.INTERVENTION);
		youth.setTreatmentStart(DateUtil.parseDateFromInternationalDateFormat("2012-01-02"));
		return youth;
	}

	private static Participant createParticipant() {
		Participant participant = new Participant();
		participant.setFirstName("Targulf");
		participant.setLastName("Trugeslesk");
		participant.setRole("Mor eller annen kvinnelig forsørger");
		participant.setPhoneNo("95 16 61 35");
		participant.setEMail("targul@gmail.com");
		participant.setContactInfo("Knarkesløyfa 3F, 0192 Oslo");
		participant.setComment("Testing - Ein to tre");
		return participant;
	}

	private static void createQuestionnairesForDataCollectionsTypes() {	
		String questionnaireCBCL = "CBCL";
		String questionnaireTRF = "TRF";
		String questionnaireTeacher = "Teacher";
		String questionnaireInt = "Interventionist";
		String questionnaireAll = "Alliance";
		QuestionnairesForDataCollectionType dcqMap = QuestionnairesForDataCollectionType.getInstance();
		dcqMap.addQuestionnaireNameForDataCollection("T1", questionnaireCBCL);
		dcqMap.addQuestionnaireNameForDataCollection("T1", questionnaireTRF);
		dcqMap.addQuestionnaireNameForDataCollection("T1", questionnaireTeacher);
		dcqMap.addQuestionnaireNameForDataCollection("T2", questionnaireCBCL);
		dcqMap.addQuestionnaireNameForDataCollection("T2", questionnaireTRF);
		dcqMap.addQuestionnaireNameForDataCollection("T2", questionnaireTeacher);
		dcqMap.addQuestionnaireNameForDataCollection("T2", questionnaireInt);
		dcqMap.addQuestionnaireNameForDataCollection("T3", questionnaireCBCL);
		dcqMap.addQuestionnaireNameForDataCollection("T3", questionnaireTRF);
		dcqMap.addQuestionnaireNameForDataCollection("T3", questionnaireTeacher);
		dcqMap.addQuestionnaireNameForDataCollection("T3", questionnaireAll);
	}
}
