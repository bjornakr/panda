package no.atferdssenteret.panda.integration;

import java.awt.Window;

import no.atferdssenteret.panda.QuestionnairesForDataCollectionType;
import no.atferdssenteret.panda.controller.DataCollectionController;
import no.atferdssenteret.panda.controller.QuestionnaireController;
import no.atferdssenteret.panda.controller.QuestionnaireEventController;
import no.atferdssenteret.panda.controller.TargetController;
import no.atferdssenteret.panda.model.DataCollectionTypes;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.ParticipationStatuses;
import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.model.entity.Participant;
import no.atferdssenteret.panda.model.entity.Questionnaire;
import no.atferdssenteret.panda.model.entity.QuestionnaireEvent;
import no.atferdssenteret.panda.model.entity.Target;
import no.atferdssenteret.panda.util.DateUtil;
import no.atferdssenteret.panda.util.EmptyMainWindow;


@SuppressWarnings("unused")
public class MvcTest {


	public static void main(String[] args) {
		Window mainWindow = EmptyMainWindow.create();
			testTarget(mainWindow);
		//	testParticipant(mainWindow);
//		testDataCollection(mainWindow);
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

	private static void testTarget(final Window mainWindow) {
		final Target model = createTarget();
		new TargetController(model) {
			private Model model;

//			public TargetController(Model model) {
//				super(model);
//			}
			
			@Override
			public String title() {
				return "Test";
			}

			@Override
			public Model model() {
				return model;
			}

			@Override
			public Window view() {
				return mainWindow;
			}

			@Override
			protected void setModel(Model model) {
				this.model = model;
			}
			
		};	
	}

	private static DataCollection createDataCollection() {
		return null;
	}

	private static Target createTarget() {
		Target target = new Target();
		target.setFirstName("Doodlestick");
		target.setLastName("Jones");
		target.setComment("Hey Dr. Ukulele man");
//		target.setRegion(Youth.regions[0]);
		target.setStatus(ParticipationStatuses.PARTICIPATING);
//		target.setTreatmentGroup(Youth.TreatmentGroups.INTERVENTION);
		target.setTreatmentStart(DateUtil.parseDateFromInternationalDateFormat("2012-01-02"));
		return target;
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
		dcqMap.addQuestionnaireNameForDataCollection(DataCollectionTypes.T1, questionnaireCBCL);
		dcqMap.addQuestionnaireNameForDataCollection(DataCollectionTypes.T1, questionnaireTRF);
		dcqMap.addQuestionnaireNameForDataCollection(DataCollectionTypes.T1, questionnaireTeacher);
		dcqMap.addQuestionnaireNameForDataCollection(DataCollectionTypes.T2, questionnaireCBCL);
		dcqMap.addQuestionnaireNameForDataCollection(DataCollectionTypes.T2, questionnaireTRF);
		dcqMap.addQuestionnaireNameForDataCollection(DataCollectionTypes.T2, questionnaireTeacher);
		dcqMap.addQuestionnaireNameForDataCollection(DataCollectionTypes.T2, questionnaireInt);
		dcqMap.addQuestionnaireNameForDataCollection(DataCollectionTypes.T3, questionnaireCBCL);
		dcqMap.addQuestionnaireNameForDataCollection(DataCollectionTypes.T3, questionnaireTRF);
		dcqMap.addQuestionnaireNameForDataCollection(DataCollectionTypes.T3, questionnaireTeacher);
		dcqMap.addQuestionnaireNameForDataCollection(DataCollectionTypes.T3, questionnaireAll);
	}
}
