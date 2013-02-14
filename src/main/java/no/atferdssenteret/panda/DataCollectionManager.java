package no.atferdssenteret.panda;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.model.entity.Target;

public class DataCollectionManager {
	private static DataCollectionManager dataCollectionManager;
	private final List<DataCollectionRule> dataCollectionRules = new LinkedList<DataCollectionRule>();

	private DataCollectionManager() {
	}

	public static DataCollectionManager getInstance() {
		if (dataCollectionManager == null) {
			dataCollectionManager = new DataCollectionManager();
		}
		return dataCollectionManager;
	}

	public void generateDataCollections(Target target) {
		deleteUntouchedDataCollections(target);
		if (target.isParticipating()) {
			for (DataCollectionRule dataCollectionRule : dataCollectionRules) {
				processDataCollectionRuleForTarget(target, dataCollectionRule);
			}
		}
	}

	private void processDataCollectionRuleForTarget(Target target, DataCollectionRule dataCollectionRule) {
		if (!target.hasDataCollection(dataCollectionRule.dataCollectionType())) {
			if (dataCollectionRule.targetDate() == DataCollectionRule.TargetDates.AFTER_TARGET_CREATION_DATE) {
				createDataCollectionForTarget(dataCollectionRule, target);
			}
			if (dataCollectionRule.targetDate() == DataCollectionRule.TargetDates.AFTER_TREATMENT_START
					&& target.getTreatmentStart() != null) {
				createDataCollectionForTarget(dataCollectionRule, target);
			}
		}
	}

	private void createDataCollectionForTarget(DataCollectionRule dataCollectionRule, Target target) {
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
			if (dataCollection.isUntouched()) {
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
