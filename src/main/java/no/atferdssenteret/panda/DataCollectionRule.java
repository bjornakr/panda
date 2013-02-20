package no.atferdssenteret.panda;

import no.atferdssenteret.panda.model.DataCollectionTypes;


public class DataCollectionRule {
	public enum TargetDates {AFTER_TARGET_CREATION_DATE, AFTER_TREATMENT_START};
	private final DataCollectionTypes dataCollectionType;
	private final int timeSpan;
	private final int timeUnit;
	private final TargetDates targetDate;

	public DataCollectionRule(DataCollectionTypes dataCollectionType,
			int timeUnit, int timeSpan, TargetDates targetDate) {
		this.dataCollectionType = dataCollectionType;
		this.timeSpan = timeSpan;
		this.timeUnit = timeUnit;
		this.targetDate = targetDate;
	}

	public int timeSpan() {
		return timeSpan;
	}

	public int timeUnit() {
		return timeUnit;
	}

	public DataCollectionTypes dataCollectionType() {
		return dataCollectionType;
	}

	public TargetDates targetDate() {
		return targetDate;
	}
}
