package no.atferdssenteret.tibirbase;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import no.atferdssenteret.panda.DataCollectionGenerator;
import no.atferdssenteret.panda.DataCollectionManager;
import no.atferdssenteret.panda.QuestionnairesForDataCollectionType;
import no.atferdssenteret.panda.model.ParticipationStatuses;
import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.model.entity.DataCollection.ProgressStatuses;
import no.atferdssenteret.panda.model.entity.Target;
import no.atferdssenteret.panda.util.DateUtil;

public class DCG2 {

	public DCG2() {
		DataCollectionManager dataCollectionManager = DataCollectionManager.getInstance();
		dataCollectionManager.addDataCollectionGenerator(new T1Generator());
		dataCollectionManager.addDataCollectionGenerator(new AfterT1Generator(QDC2.DC_P1, Calendar.WEEK_OF_YEAR, 4));
		dataCollectionManager.addDataCollectionGenerator(new AfterT1Generator(QDC2.DC_P2, Calendar.WEEK_OF_YEAR, 8));
		dataCollectionManager.addDataCollectionGenerator(new AfterT1Generator(QDC2.DC_P3, Calendar.WEEK_OF_YEAR, 12));
		dataCollectionManager.addDataCollectionGenerator(new AfterT1Generator(QDC2.DC_P4, Calendar.WEEK_OF_YEAR, 16));
		dataCollectionManager.addDataCollectionGenerator(new AfterT1Generator(QDC2.DC_P5, Calendar.WEEK_OF_YEAR, 20));
		dataCollectionManager.addDataCollectionGenerator(new AfterT1Generator(QDC2.DC_T2, Calendar.MONTH, 6));
		dataCollectionManager.addDataCollectionGenerator(new AfterT1Generator(QDC2.DC_THERAPIST_I, Calendar.MONTH, 6));
		dataCollectionManager.addDataCollectionGenerator(new AfterT1Generator(QDC2.DC_VIDEO_I, Calendar.MONTH, 6));		
		dataCollectionManager.addDataCollectionGenerator(new AfterT1Generator(QDC2.DC_T3, Calendar.MONTH, 18));
	}
	
	private final class T1Generator implements DataCollectionGenerator {

		@Override
		public boolean isApplicable(Target target) {
			return target.getStatus() == ParticipationStatuses.WAITING_FOR_CONSENT
					|| target.getStatus() == ParticipationStatuses.PARTICIPATING;
		}

		@Override
		public DataCollection createDataCollection(Target target) {
			Date targetDate = DateUtil.addTime(new Date(target.getCreated().getTime()), Calendar.WEEK_OF_MONTH, 1);
			return setupDataCollection(target, QDC2.DC_T1, targetDate);
		}

		@Override
		public String dataCollectionType() {
			return QDC2.DC_T1;
		}

	}
	
	private final class AfterT1Generator implements DataCollectionGenerator {
		private String dataCollection;
		private int timeUnit;
		private int delay;
		
		private AfterT1Generator(String type, int timeUnit, int delay) {
			this.dataCollection = type;
			this.timeUnit = timeUnit;
			this.delay = delay;
		}
		
		@Override
		public boolean isApplicable(Target target) {
			return (target.isParticipating() && isT1Completed(target));
		}

		@Override
		public DataCollection createDataCollection(Target target) {
			Date targetDate = DateUtil.addTime(t1CompletedDate(target), timeUnit, delay);
			return setupDataCollection(target, dataCollection, targetDate);
		}
		
		@Override
		public String dataCollectionType() {
			return dataCollection;
		}
		
		private boolean isT1Completed(Target target) {
			DataCollection t1 = target.getDataCollection(QDC2.DC_T1);
			return t1 != null
					&& t1.getProgressStatus() == ProgressStatuses.COMPLETED
					&& t1.getProgressDate() != null;
		}
		
		private Date t1CompletedDate(Target target) {
			DataCollection t1 = target.getDataCollection(QDC2.DC_T1);
			return t1.getProgressDate();
		}
	}

	private DataCollection setupDataCollection(Target youth, String type, Date targetDate) {
		DataCollection dataCollection = new DataCollection();
		dataCollection.setType(type);
		dataCollection.setTargetDate(targetDate);
		if (dataCollectionsForTelephoneInterview().contains(type)) {
			dataCollection.setDataCollector(((Youth)youth).getDataCollectorTelephone());
		}
		else {
			dataCollection.setDataCollector(youth.getDataCollector());
		}
//		dataCollection.setQuestionnaires(QuestionnairesForDataCollectionType.getInstance().getQuestionnairesFor(type));
		dataCollection.setQuestionnaires(QuestionnairesForDataCollectionType.getInstance().getQuestionnairesFor("P1"));
		return dataCollection;
	}

	private List<String> dataCollectionsForTelephoneInterview() {
		LinkedList<String> dcs = new LinkedList<String>();
		dcs.add(QDC2.DC_P1);
		dcs.add(QDC2.DC_P2);
		dcs.add(QDC2.DC_P3);
		dcs.add(QDC2.DC_P4);
		dcs.add(QDC2.DC_P5);
		return dcs;
	}
}
