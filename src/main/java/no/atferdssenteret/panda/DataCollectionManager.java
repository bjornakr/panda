package no.atferdssenteret.panda;

import java.util.LinkedList;
import java.util.List;

import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.model.entity.Target;

public class DataCollectionManager {
	private static DataCollectionManager dataCollectionManager;
	private final List<DataCollectionGenerator> dataCollectionGenerators = new LinkedList<DataCollectionGenerator>();

	private DataCollectionManager() {
	}

	public static DataCollectionManager getInstance() {
		if (dataCollectionManager == null) {
			dataCollectionManager = new DataCollectionManager();
		}
		return dataCollectionManager;
	}

	public void generateDataCollections(Target target) {
		deleteUntouchedSystemGeneratedDataCollections(target);
		for (DataCollectionGenerator dataCollectionGenerator : dataCollectionGenerators) {
			if (dataCollectionGenerator.isApplicable(target) && !target.hasDataCollection(dataCollectionGenerator.dataCollectionType())) {
				target.addDataCollection(dataCollectionGenerator.createDataCollection(target));
			}
		}
	}

	private void deleteUntouchedSystemGeneratedDataCollections(Target target) {
		List<DataCollection> untouchedDataCollections = new LinkedList<DataCollection>();
		for (DataCollection dataCollection : target.getDataCollections()) {
			if (dataCollection.isUntouched() && dataCollection.getSystemGenerated()) {
				untouchedDataCollections.add(dataCollection);
			}
		}
		target.getDataCollections().removeAll(untouchedDataCollections);
	}

	public void addDataCollectionGenerator(DataCollectionGenerator dataCollectionGenerator) {
		dataCollectionGenerators.add(dataCollectionGenerator);
	}

//	public java.sql.Date calculateTargetDate(Target target, DataCollectionRuleNo2 dataCollectionRule) {
//		Calendar targetDateCalendar = Calendar.getInstance();
//		if (dataCollectionRule.targetDate() == DataCollectionRuleNo2.TargetDates.AFTER_TARGET_CREATION_DATE) {
//			targetDateCalendar.setTime(target.getCreated());
//		}
//		else if (dataCollectionRule.targetDate() == DataCollectionRuleNo2.TargetDates.AFTER_TREATMENT_START) {
//			targetDateCalendar.setTime(target.getTreatmentStart());
//		}
//		targetDateCalendar.add(dataCollectionRule.timeUnit(), dataCollectionRule.timeSpan());
//		return new java.sql.Date(targetDateCalendar.getTimeInMillis());
//	}

	public void removeAllDataCollectionGenerators() {
		dataCollectionGenerators.clear();
	}
}
