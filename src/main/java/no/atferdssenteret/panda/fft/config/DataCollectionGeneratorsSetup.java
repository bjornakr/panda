package no.atferdssenteret.panda.fft.config;

import java.sql.Date;
import java.util.Calendar;

import no.atferdssenteret.panda.DataCollectionGenerator;
import no.atferdssenteret.panda.DataCollectionManager;
import no.atferdssenteret.panda.QuestionnairesForDataCollectionType;
import no.atferdssenteret.panda.fft.Youth;
import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.model.entity.Target;
import no.atferdssenteret.panda.util.DateUtil;

public class DataCollectionGeneratorsSetup {

	public DataCollectionGeneratorsSetup() {
		DataCollectionManager dataCollectionManager = DataCollectionManager.getInstance();
		dataCollectionManager.addDataCollectionGenerator(new T1Generator());
		dataCollectionManager.addDataCollectionGenerator(new P1Generator());
		dataCollectionManager.addDataCollectionGenerator(new P2Generator());
		dataCollectionManager.addDataCollectionGenerator(new T2Generator());
		dataCollectionManager.addDataCollectionGenerator(new T3Generator());
	}
	
	private final class T1Generator implements DataCollectionGenerator {

		@Override
		public boolean isApplicable(Target target) {
			return target.isParticipating();
		}

		@Override
		public DataCollection createDataCollection(Target target) {
			Date targetDate = DateUtil.addTime(target.getCreated(), Calendar.DAY_OF_WEEK, 0);
			return setupDataCollection(target, QuestionnairesAndDataCollectionsSetup.DC_T1, targetDate);
		}
	}
	
	private final class P1Generator implements DataCollectionGenerator {
		@Override
		public boolean isApplicable(Target target) {
			return (target.isParticipating() && ((Youth)target).getTreatmentStart() != null);
		}

		@Override
		public DataCollection createDataCollection(Target target) {
			Date targetDate = DateUtil.addTime(target.getCreated(), Calendar.WEEK_OF_YEAR, 4);
			return setupDataCollection(target, QuestionnairesAndDataCollectionsSetup.DC_P1, targetDate);
		}
	}
	
	private final class P2Generator implements DataCollectionGenerator {

		@Override
		public boolean isApplicable(Target target) {
			return (target.isParticipating() && ((Youth)target).getTreatmentStart() != null);
		}

		@Override
		public DataCollection createDataCollection(Target target) {
			Date targetDate = DateUtil.addTime(target.getCreated(), Calendar.WEEK_OF_YEAR, 8);
			return setupDataCollection(target, QuestionnairesAndDataCollectionsSetup.DC_P2, targetDate);
		}
	}
	
	private final class T2Generator implements DataCollectionGenerator {

		@Override
		public boolean isApplicable(Target target) {
			return (target.isParticipating() && ((Youth)target).getTreatmentStart() != null);
		}

		@Override
		public DataCollection createDataCollection(Target target) {
			Date targetDate = DateUtil.addTime(target.getCreated(), Calendar.WEEK_OF_YEAR, 12);
			return setupDataCollection(target, QuestionnairesAndDataCollectionsSetup.DC_T2, targetDate);
		}
	}

	private final class T3Generator implements DataCollectionGenerator {

		@Override
		public boolean isApplicable(Target target) {
			return (target.isParticipating() && ((Youth)target).getTreatmentStart() != null
					&& ((Youth)target).getTreatmentGroup() != Youth.TreatmentGroups.NOT_YET_RANDOMIZED);
		}

		@Override
		public DataCollection createDataCollection(Target target) {
			Date targetDate = DateUtil.addTime(target.getCreated(), Calendar.MONTH, 6);
			String type = null;
			if (((Youth)target).getTreatmentGroup() == Youth.TreatmentGroups.INTERVENTION) {
				type = QuestionnairesAndDataCollectionsSetup.DC_T3_I;
			}
			else if (((Youth)target).getTreatmentGroup() == Youth.TreatmentGroups.CONTROL) {
				type = QuestionnairesAndDataCollectionsSetup.DC_T3_C;
			}

			return setupDataCollection(target, type, targetDate);
		}
	}

	private static DataCollection setupDataCollection(Target target, String type, Date targetDate) {
		DataCollection dataCollection = new DataCollection();
		dataCollection.setType(type);
		dataCollection.setTargetDate(targetDate);
		dataCollection.setDataCollector(target.getDataCollector());
		dataCollection.setQuestionnaires(QuestionnairesForDataCollectionType.getInstance().getQuestionnairesFor(type));
		return dataCollection;
	}

}
