package no.atferdssenteret.panda.util;

import java.util.Calendar;

import no.atferdssenteret.panda.DataCollectionGenerator;
import no.atferdssenteret.panda.QuestionnairesForDataCollectionType;
import no.atferdssenteret.panda.model.ParticipationStatuses;
import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.model.entity.Target;

public class TestUtil {

	public static Target createNotParticipatingTarget() {
		Target target = createBasicTarget();
		target.setStatus(ParticipationStatuses.WAITING_FOR_CONSENT);
		return target;	
	}

	public static Target createParticipatingTarget() {
		Target target = createBasicTarget();
		target.setStatus(ParticipationStatuses.PARTICIPATING);
		return target;
	}

	private static Target createBasicTarget() {
		Target target = new Target();
		target.setFirstName("Kalle");
		target.setLastName("Trusenøff");
		return target;
	}

	public static DataCollectionGenerator createDataCollectionGenerator(String type, int noOfMonthsDelay) {
		return new TestUtil().new GenericDataCollectionGenerator(type, noOfMonthsDelay);
	}

	
	private class GenericDataCollectionGenerator implements DataCollectionGenerator {
		private String type;
		private int noOfMonthsDelay;
		
		public GenericDataCollectionGenerator(String type, int noOfMonthsDelay) {
			this.type = type;
			this.noOfMonthsDelay = noOfMonthsDelay;
		}
		
		@Override
		public boolean isApplicable(Target target) {
			return true;
		}

		@Override
		public DataCollection createDataCollection(Target target) {
			DataCollection dataCollection = new DataCollection();
			dataCollection.setType(type);
			dataCollection.setTargetDate(DateUtil.addTime(target.getCreated(), Calendar.MONTH, noOfMonthsDelay));
			dataCollection.setDataCollector(target.getDataCollector());
			dataCollection.setQuestionnaires(QuestionnairesForDataCollectionType.getInstance().getQuestionnairesFor(type));
			return dataCollection;
		}

		@Override
		public String dataCollectionType() {
			return type;
		}	
	}
}
