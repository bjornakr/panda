package no.atferdssenteret.panda;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import no.atferdssenteret.panda.DataCollectionRule.ApplicationTimes;
import no.atferdssenteret.panda.DataCollectionRule.TargetDates;
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

    public void notifyTargetCreated(Target target) {
	if (!target.isParticipating()) {
	    return;
	}
	
	dataCollectionManager.currentTarget = target;
	for (DataCollectionRule dataCollectionRule : dataCollectionRules) {
	    if (dataCollectionRule.applicationTime() == DataCollectionRule.ApplicationTimes.WHEN_TARGET_CREATED) {
		DataCollection dataCollection = new DataCollection();
		//		dataCollection.setTarget(target);
		dataCollection.setType(dataCollectionRule.dataCollectionType());
		java.sql.Date targetDate = calculateTargetDate(target.getCreated(), dataCollectionRule);
		dataCollection.setTargetDate(new java.sql.Date(targetDate.getTime()));
		dataCollection.setDataCollector(target.getDataCollector());
		target.addDataCollection(dataCollection);
	    }
	}
    }

    public void notifyTargetUpdated(Target target) {
	if (!target.isParticipating()) {
	    deleteUntouchedDataCollections(target);
	    return;
	}

	for (DataCollectionRule dataCollectionRule : dataCollectionRules) {
	    if (dataCollectionRule.applicationTime() == DataCollectionRule.ApplicationTimes.WHEN_TARGET_UPDATED) {
		if (dataCollectionRule.targetDate() == DataCollectionRule.TargetDates.AFTER_TREATMENT_START
			&& target.getTreatmentStart() != null) {
		    DataCollection dataCollection = target.getDataCollection(dataCollectionRule.dataCollectionType()); 
		    if (dataCollection != null) {
			dataCollection.setTargetDate(calculateTargetDate(target.getTreatmentStart(), dataCollectionRule));
		    }
		    else {
			dataCollection = new DataCollection();
			dataCollection.setType(dataCollectionRule.dataCollectionType());		
			dataCollection.setTargetDate(calculateTargetDate(target.getTreatmentStart(), dataCollectionRule));
			dataCollection.setDataCollector(target.getDataCollector());
			target.addDataCollection(dataCollection);
			System.out.println("Data collection: " + dataCollection);
		    }
		}
	    }
	}
	System.err.println("TARGET UPDATED END: " + target);
    }


    private void deleteUntouchedDataCollections(Target target) {
	List<DataCollection> untouchedDataCollections = new LinkedList<DataCollection>();
	for (DataCollection dataCollection : target.getDataCollections()) {
	    if (dataCollection.getProgressStatus() == DataCollection.ProgressStatus.NOT_INITIATED) {
		untouchedDataCollections.add(dataCollection);
	    }
	}
	target.getDataCollections().removeAll(untouchedDataCollections);
    }

    public void addRule(DataCollectionRule dataCollectionRule) {
	dataCollectionRules.add(dataCollectionRule);
    }

    public java.sql.Date calculateTargetDate(java.sql.Date initialDate, DataCollectionRule dataCollectionRule) {
	Calendar targetDateCalendar = Calendar.getInstance();
	targetDateCalendar.setTime(initialDate);
	targetDateCalendar.add(dataCollectionRule.timeUnit(), dataCollectionRule.timeSpan());
	return new java.sql.Date(targetDateCalendar.getTimeInMillis());
    }

    public void removeAllRules() {
	dataCollectionRules.clear();
    }
}
