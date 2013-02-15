package no.atferdssenteret.panda.config;

import java.util.Calendar;

import no.atferdssenteret.panda.DataCollectionManager;
import no.atferdssenteret.panda.DataCollectionRule;
import no.atferdssenteret.panda.model.DataCollectionTypes;

public class DataCollectionRulesSetup {
	public static void setup() {
		DataCollectionManager.getInstance().addRule(new DataCollectionRule(
				DataCollectionTypes.T1,
				Calendar.MONTH, 0, 
				DataCollectionRule.TargetDates.AFTER_TARGET_CREATION_DATE));
		DataCollectionManager.getInstance().addRule(new DataCollectionRule(
				DataCollectionTypes.P1,
				Calendar.WEEK_OF_YEAR, 4, 
				DataCollectionRule.TargetDates.AFTER_TREATMENT_START));
		DataCollectionManager.getInstance().addRule(new DataCollectionRule(
				DataCollectionTypes.P2,
				Calendar.WEEK_OF_YEAR, 8, 
				DataCollectionRule.TargetDates.AFTER_TREATMENT_START));
		DataCollectionManager.getInstance().addRule(new DataCollectionRule(
				DataCollectionTypes.T2,
				Calendar.WEEK_OF_YEAR, 12, 
				DataCollectionRule.TargetDates.AFTER_TREATMENT_START));
		DataCollectionManager.getInstance().addRule(new DataCollectionRule(
				DataCollectionTypes.T2,
				Calendar.MONTH, 6, 
				DataCollectionRule.TargetDates.AFTER_TREATMENT_START));
	}
}
