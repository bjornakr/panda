package no.atferdssenteret.panda.util;

import java.util.Calendar;

import no.atferdssenteret.panda.DataCollectionRule;
import no.atferdssenteret.panda.Target;

public class TestUtil {

    public static Target createNotParticipatingTarget() {
	Target target = createBasicTarget();
	target.setStatus(Target.Statuses.WAITING_FOR_CONSENT);
	return target;	
    }
    
    public static Target createParticipatingTarget() {
	Target target = createBasicTarget();
	target.setStatus(Target.Statuses.PARTICIPATING);
	return target;
    }
    
    private static Target createBasicTarget() {
	Target target = new Target();
	target.setFirstName("Kalle");
	target.setLastName("Trusenøff");
	return target;
    }
    
    public static DataCollectionRule createDataCollectionRuleT1WhenTargetCreated(int noOfMonthsDelay) {
	return new DataCollectionRule(
		"T1",
		DataCollectionRule.ApplicationTimes.WHEN_TARGET_CREATED,
		Calendar.MONTH, noOfMonthsDelay, 
		DataCollectionRule.TargetDates.AFTER_TARGET_CREATION_DATE);
    }

    public static DataCollectionRule createDataCollectionRuleT2WhenTargetUpdated(int noOfMonthsDelay) {
	return new DataCollectionRule(
		"T2",
		DataCollectionRule.ApplicationTimes.WHEN_TARGET_UPDATED,
		Calendar.MONTH, noOfMonthsDelay,
		DataCollectionRule.TargetDates.AFTER_TREATMENT_START);
    }

    public static DataCollectionRule createDataCollectionRuleT3WhenTargetUpdated(int noOfMonthsDelay) {
	return new DataCollectionRule(
		"T3",
		DataCollectionRule.ApplicationTimes.WHEN_TARGET_UPDATED,
		Calendar.MONTH, noOfMonthsDelay,
		DataCollectionRule.TargetDates.AFTER_TREATMENT_START);
    }
}
