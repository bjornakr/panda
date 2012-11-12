package no.atferdssenteret.panda;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import no.atferdssenteret.panda.model.Participant;
import no.atferdssenteret.panda.model.ParticipantRoles;
import no.atferdssenteret.panda.model.fft.Youth;
import no.atferdssenteret.panda.persistence.DatabaseCleaner;
import no.atferdssenteret.panda.util.JPATransactor;

public class DataGenerator {

	public DataGenerator() throws SQLException {
		new DatabaseCleaner(JPATransactor.getInstance().entityManager()).clean();
		JPATransactor.getInstance().transaction().begin();
		for (int i = 0; i < 100; i ++) {
			Youth youth = new Youth();
			youth.setFirstName(createFirstName());
			youth.setLastName(createLastName());
			youth.setStatus(pickRandom(Youth.Statuses.values()));
			youth.setRegion(pickRandom(Youth.Regions.values()));
			youth.setGender(pickRandom(Youth.Genders.values()));
			youth.setTreatmentGroup(pickRandom(Youth.TreatmentGroups.values()));
			
			List<Participant> participants = new LinkedList<Participant>();
			for (int j = 0; j < new Random().nextInt(3); j++) {
				participants.add(createParticipant());
			}
			youth.setParticipants(participants);
			
			JPATransactor.getInstance().entityManager().persist(youth);
		}
		JPATransactor.getInstance().transaction().commit();
	}


	private Participant createParticipant() {
		Participant participant = new Participant();
		participant.setRole(pickRandom(ParticipantRoles.values()));
		participant.setFirstName(createFirstName());
		participant.setLastName(createLastName());
		participant.setStatus(pickRandom(Participant.Statuses.values()));
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
		String[] namepart1 = {"Rug", "Babb", "Odd", "Bob", "Ann", "Frys", "Hild", "Gugg"};
		String[] namepart2 = {"leik", "hild", "ur", "geir", "var", "ar", "oline", "leif", "ine", "e", "ulf"};
		return namepart1[new Random().nextInt(namepart1.length)] + namepart2[new Random().nextInt(namepart2.length)];
	}

	private String createLastName() {
		String[] namepart1 = {"Hølje", "Hyse", "Hurpe", "Brøle", "Flagre", "Kolje", "Klaske", "Knarke", "Røske", "Tråkle", "Hufse", "Bjarte", "Hule"};
		String[] namepart2 = {"gnarf", "bass", "burg", "fist", "targ", "tang", "smell", "burt", "bart", "knurr", "slask", "bubb", "flesk"};
		return namepart1[new Random().nextInt(namepart1.length)] + namepart2[new Random().nextInt(namepart2.length)];
	}

	public static void main(String[] args) throws SQLException {
		new DataGenerator();
	}
}
