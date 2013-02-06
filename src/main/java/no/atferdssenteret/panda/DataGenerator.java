package no.atferdssenteret.panda;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import no.atferdssenteret.panda.model.Participant;
import no.atferdssenteret.panda.model.ParticipantRoles;
import no.atferdssenteret.panda.model.ParticipationStatuses;
import no.atferdssenteret.panda.model.QuestionnaireTypes;
import no.atferdssenteret.panda.model.fft.Youth;
import no.atferdssenteret.panda.util.DatabaseCleaner;
import no.atferdssenteret.panda.util.JPATransactor;

public class DataGenerator {

	public DataGenerator() throws SQLException {
		new DatabaseCleaner(JPATransactor.getInstance().entityManager()).clean();
		createDataCollectionRules();
		
		for (int i = 0; i < 100; i ++) {
			Youth youth = createYouth();
			List<Participant> participants = new LinkedList<Participant>();
			for (int j = 0; j <= new Random().nextInt(3); j++) {
				participants.add(createParticipant());
			}
			youth.setParticipants(participants);
			JPATransactor.getInstance().transaction().begin();
			JPATransactor.getInstance().entityManager().persist(youth);
			JPATransactor.getInstance().transaction().commit();
			DataCollectionManager.getInstance().notifyTargetUpdated(youth);
		}
	}

	private void createDataCollectionRules() {
		DataCollectionManager.getInstance().addRule(new DataCollectionRule(
				"T1",
				Calendar.MONTH, 0, 
				DataCollectionRule.TargetDates.AFTER_TARGET_CREATION_DATE));
	}
	
	private Youth createYouth() {
		Youth youth = new Youth();
		youth.setFirstName(createFirstName());
		youth.setLastName(createLastName());
//		youth.setStatus(pickRandom(ParticipationStatuses.values()));
		youth.setStatus(ParticipationStatuses.PARTICIPATING);
		youth.setRegion(pickRandom(Youth.Regions));
		youth.setGender(pickRandom(Youth.Genders.values()));
		youth.setTreatmentGroup(pickRandom(Youth.TreatmentGroups.values()));
		return youth;
	}
	
	private Participant createParticipant() {
		Participant participant = new Participant();
		participant.setRole(pickRandom(ParticipantRoles.values()));
		participant.setFirstName(createFirstName());
		participant.setLastName(createLastName());
		participant.setStatus(pickRandom(ParticipationStatuses.values()));
		participant.setPhoneNo(generatePhoneNumber());
		String[] eMailProviders = {"gmail.com", "slugmail.org","hotmail.com", "nyresopp.net", "online.no", "klikkadur.is"}; 
		participant.setEMail(participant.getFirstName().substring(0, 4).toLowerCase() +
				participant.getLastName().substring(0, 4).toLowerCase() + "@" + pickRandom(eMailProviders));
		return participant;
	}


	private String generatePhoneNumber() {
		String phoneNo = "";
		for (int i = 0; i < 8; i++) {
			phoneNo += new Random().nextInt(10);
			if (i % 2 == 1 && i != 7) {
				phoneNo += " ";
			}
		}
		return phoneNo;
	}


	private <T> T pickRandom(T[] values) {
		return values[new Random().nextInt(values.length)];
	}

	private String createFirstName() {
		String[] namepart1 = {"Rug", "Babb", "Odd", "Bob", "Ann", "Frys", "Hild", "Gugg", "Fjøs", "Tut", "Dorg", "Svupp"};
		String[] namepart2 = {"leik", "hild", "ur", "geir", "var", "oline", "leif", "ine", "ulf"};
		return namepart1[new Random().nextInt(namepart1.length)] + namepart2[new Random().nextInt(namepart2.length)];
	}

	private String createLastName() {
		String[] namepart1 = {"Hølje", "Hyse", "Hurpe", "Brøle", "Flagre", "Kolje", "Klaske", "Knarke", "Røske", "Tråkle", "Hufse", "Bjarte", "Hule"};
		String[] namepart2 = {"gnarf", "bass", "burg", "fist", "targ", "tang", "smell", "burt", "bart", "knurr", "slask", "bubb", "flesk"};
		return namepart1[new Random().nextInt(namepart1.length)] + namepart2[new Random().nextInt(namepart2.length)];
	}

	private static void setupQuestionnaires() {
		QuestionnairesForDataCollectionType dcqMap = QuestionnairesForDataCollectionType.getInstance();
		dcqMap.addQuestionnaireNameForDataCollection("T1", QuestionnaireTypes.PARENT);
		dcqMap.addQuestionnaireNameForDataCollection("T1", QuestionnaireTypes.YOUTH);
		dcqMap.addQuestionnaireNameForDataCollection("T1", QuestionnaireTypes.TEACHER);
		dcqMap.addQuestionnaireNameForDataCollection("T2", QuestionnaireTypes.PARENT);
		dcqMap.addQuestionnaireNameForDataCollection("T2", QuestionnaireTypes.YOUTH);
		dcqMap.addQuestionnaireNameForDataCollection("T2", QuestionnaireTypes.TEACHER);
		dcqMap.addQuestionnaireNameForDataCollection("T3", QuestionnaireTypes.PARENT);
		dcqMap.addQuestionnaireNameForDataCollection("T3", QuestionnaireTypes.YOUTH);
		dcqMap.addQuestionnaireNameForDataCollection("T3", QuestionnaireTypes.TEACHER);
	}
	
	public static void main(String[] args) throws SQLException {
		setupQuestionnaires();
		new DataGenerator();
	}
}
