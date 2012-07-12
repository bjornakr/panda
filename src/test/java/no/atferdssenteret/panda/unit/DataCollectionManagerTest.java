package no.atferdssenteret.panda.unit;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.text.ParseException;
import java.util.Calendar;

import no.atferdssenteret.panda.DataCollectionManager;
import no.atferdssenteret.panda.DataCollectionRule;
import no.atferdssenteret.panda.DataCollectionRule.ApplicationTimes;
import no.atferdssenteret.panda.DataCollectionRule.TargetDates;
import no.atferdssenteret.panda.util.DateUtil;

import org.junit.Test;

public class DataCollectionManagerTest {

    @Test
    public void testCalculateTargetDate() throws ParseException  {
	DataCollectionManager dcManager = DataCollectionManager.getInstance();
	Date initialDate = DateUtil.parseDateFromInternationalDateFormat("2012-05-21");
	Date expectedDate = DateUtil.parseDateFromInternationalDateFormat("2012-08-21");
	DataCollectionRule dcRule = new DataCollectionRule("T1", ApplicationTimes.WHEN_TARGET_CREATED, 
		Calendar.MONTH, 3, TargetDates.AFTER_TARGET_CREATION_DATE);	
	assertEquals(expectedDate, dcManager.calculateTargetDate(initialDate, dcRule));	
    }
}
