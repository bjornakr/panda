package no.atferdssenteret.panda;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import no.atferdssenteret.panda.model.DataCollection;
import no.atferdssenteret.panda.model.Target;
import no.atferdssenteret.panda.util.JPATransactor;

public class DataCollectionManager {
	private static DataCollectionManager dataCollectionManager;
	private Target currentTarget;
	private final List<DataCollectionRule> dataCollectionRules = new LinkedList<DataCollectionRule>();

	private DataCollectionManager() {
	}

	public static DataCollectionManager getInstance() {
		if (dataCollectionManager == null) {
			dataCollectionManager = new DataCollectionManager();
		}
		return dataCollectionManager;
	}

	public Target currentTarget() {
		return dataCollectionManager.currentTarget;
	}

	//	public void notifyTargetCreated(Target target) {
	//		if (!target.isParticipating()) {
	//			return;
	//		}
	//
	//		dataCollectionManager.currentTarget = target;
	//		for (DataCollectionRule dataCollectionRule : dataCollectionRules) {
	//			if (dataCollectionRule.applicationTime() == DataCollectionRule.ApplicationTimes.WHEN_TARGET_CREATED) {
	//				DataCollection dataCollection = new DataCollection();
	//				//		dataCollection.setTarget(target);
	//				dataCollection.setType(dataCollectionRule.dataCollectionType());
	//				dataCollection.setTargetDate(calculateTargetDate(target, dataCollectionRule));
	//				dataCollection.setDataCollector(target.getDataCollector());
	//				target.addDataCollection(dataCollection);
	//			}
	//		}
	//	}

	public void notifyTargetUpdated(Target target) {
		System.err.println("HUP!");
		currentTarget = target;
		deleteUntouchedDataCollections(target);

		if (target.isParticipating()) {
			for (DataCollectionRule dataCollectionRule : dataCollectionRules) {
				if (!target.hasDataCollection(dataCollectionRule.dataCollectionType())) {
					if (dataCollectionRule.targetDate() == DataCollectionRule.TargetDates.AFTER_TARGET_CREATION_DATE) {
						addDataCollectionToTarget(dataCollectionRule, target);
					}
					if (dataCollectionRule.targetDate() == DataCollectionRule.TargetDates.AFTER_TREATMENT_START
							&& target.getTreatmentStart() != null) {
						addDataCollectionToTarget(dataCollectionRule, target);
					}
				}
			}
		}
		JPATransactor.getInstance().transaction().begin();
		JPATransactor.getInstance().transaction().commit();
	}

	private void addDataCollectionToTarget(DataCollectionRule dataCollectionRule, Target target) {
		DataCollection dataCollection = new DataCollection();
		dataCollection.setType(dataCollectionRule.dataCollectionType());
		dataCollection.setTargetDate(calculateTargetDate(target, dataCollectionRule));
		dataCollection.setDataCollector(target.getDataCollector());
		dataCollection.setQuestionnaires(QuestionnairesForDataCollectionType.getInstance().getQuestionnairesFor(dataCollectionRule.dataCollectionType()));
		target.addDataCollection(dataCollection);
	}


	private void deleteUntouchedDataCollections(Target target) {
		List<DataCollection> untouchedDataCollections = new LinkedList<DataCollection>();
		for (DataCollection dataCollection : target.getDataCollections()) {
			if (dataCollection.getProgressStatus() == DataCollection.ProgressStatuses.NOT_INITIATED) {
				untouchedDataCollections.add(dataCollection);
			}
		}
		target.getDataCollections().removeAll(untouchedDataCollections);
	}

	public void addRule(DataCollectionRule dataCollectionRule) {
		dataCollectionRules.add(dataCollectionRule);
	}

	public java.sql.Date calculateTargetDate(Target target, DataCollectionRule dataCollectionRule) {
		Calendar targetDateCalendar = Calendar.getInstance();
		if (dataCollectionRule.targetDate() == DataCollectionRule.TargetDates.AFTER_TARGET_CREATION_DATE) {
			targetDateCalendar.setTime(target.getCreated());
		}
		else if (dataCollectionRule.targetDate() == DataCollectionRule.TargetDates.AFTER_TREATMENT_START) {
			targetDateCalendar.setTime(target.getTreatmentStart());
		}
		targetDateCalendar.add(dataCollectionRule.timeUnit(), dataCollectionRule.timeSpan());
		return new java.sql.Date(targetDateCalendar.getTimeInMillis());
	}

	public void removeAllRules() {
		dataCollectionRules.clear();
	}
}
