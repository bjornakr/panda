package no.atferdssenteret.panda;


public class DataCollectionRule {
	public enum ApplicationTimes {WHEN_TARGET_CREATED, WHEN_TARGET_UPDATED};
	public enum TargetDates {AFTER_TARGET_CREATION_DATE, AFTER_TREATMENT_START};
	private final String dataCollectionType;
	private final ApplicationTimes applicationTime;
	private final int timeSpan;
	private final int timeUnit;
	private final TargetDates targetDate;

	public DataCollectionRule(String dataCollectionType, ApplicationTimes applicationTime,
			int timeUnit, int timeSpan, TargetDates targetDate) {
		this.dataCollectionType = dataCollectionType;
		this.applicationTime = applicationTime;
		this.timeSpan = timeSpan;
		this.timeUnit = timeUnit;
		this.targetDate = targetDate;
	}

	public ApplicationTimes applicationTime() {
		return applicationTime;
	}

	public int timeSpan() {
		return timeSpan;
	}

	public int timeUnit() {
		return timeUnit;
	}

	public String dataCollectionType() {
		return dataCollectionType;
	}

	public TargetDates targetDate() {
		return targetDate;
	}
}
