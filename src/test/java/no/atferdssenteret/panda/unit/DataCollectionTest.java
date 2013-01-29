package no.atferdssenteret.panda.unit;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import no.atferdssenteret.panda.model.DataCollection;
import no.atferdssenteret.panda.model.DataCollection.ProgressStatuses;
import no.atferdssenteret.panda.util.DateUtil;

import org.junit.Test;

public class DataCollectionTest {


	@Test
	public void dataCollectionsShouldAlwaysBeConcludedWhenSpecificProgressStatusesAreSet() {
		DataCollection dc = new DataCollection();
		dc.setTargetDate(DateUtil.today());
		Calendar cal = Calendar.getInstance();


		ProgressStatuses[] progressStatusesThatShouldResultInConcludedState =
			{DataCollection.ProgressStatuses.GIVEN_UP, DataCollection.ProgressStatuses.PERFORMED};
		for (DataCollection.ProgressStatuses status : progressStatusesThatShouldResultInConcludedState) {
			dc.setProgressStatus(status);
			cal.setTime(DateUtil.today());
			dc.setTargetDate(DateUtil.sqlDate(cal.getTime()));
			assertEquals(DataCollection.Statuses.CONCLUDED, dc.status());			
			cal.add(Calendar.YEAR, -1);
			dc.setTargetDate(DateUtil.sqlDate(cal.getTime()));
			assertEquals(DataCollection.Statuses.CONCLUDED, dc.status());			
			cal.add(Calendar.YEAR, +2);
			dc.setTargetDate(DateUtil.sqlDate(cal.getTime()));
			assertEquals(DataCollection.Statuses.CONCLUDED, dc.status());			
		}

	}

	@Test
	public void dataCollectionsShouldBeForthcomingWhenOneMonthOrLessInTheFuture() {
		DataCollection dc = new DataCollection();
		dc.setProgressStatus(DataCollection.ProgressStatuses.NOT_INITIATED);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.today());
		calendar.add(Calendar.MONTH, 1);
		dc.setTargetDate(DateUtil.sqlDate(calendar.getTime()));
		assertEquals(DataCollection.Statuses.FORTHCOMING, dc.status());
		
	}

	@Test
	public void dataCollectionsAreReportedDelayedOneWeekAfterTargetDate() {
		DataCollection dc = new DataCollection();
		dc.setProgressStatus(DataCollection.ProgressStatuses.NOT_INITIATED);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.today());
		calendar.add(Calendar.DAY_OF_YEAR, -8);
		dc.setTargetDate(DateUtil.sqlDate(calendar.getTime()));
		assertEquals(DataCollection.Statuses.DELAYED, dc.status());
	}
	
	@Test
	public void dataCollectionsMoreThanOneMonthAheadInTimeAreReportedPlanned() {
		DataCollection dc = new DataCollection();
		dc.setProgressStatus(DataCollection.ProgressStatuses.NOT_INITIATED);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.today());
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		dc.setTargetDate(DateUtil.sqlDate(calendar.getTime()));
		assertEquals(DataCollection.Statuses.PLANNED, dc.status());		
	}
}
