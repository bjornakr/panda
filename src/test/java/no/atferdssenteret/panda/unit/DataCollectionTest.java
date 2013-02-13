package no.atferdssenteret.panda.unit;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.model.entity.DataCollection.ProgressStatuses;
import no.atferdssenteret.panda.util.DateUtil;

import org.junit.Test;

public class DataCollectionTest {


	@Test
	public void dataCollectionsShouldAlwaysBeConcludedWhenSpecificProgressStatusesAreSet() {
		DataCollection dc = new DataCollection();
		dc.setTargetDate(DateUtil.today());
		Calendar cal = Calendar.getInstance();


		ProgressStatuses[] progressStatusesThatShouldResultInConcludedState =
			{DataCollection.ProgressStatuses.GIVEN_UP, DataCollection.ProgressStatuses.COMPLETED};
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
	public void dataCollectionsShouldBeForthcomingWhenLessThanThirtyDaysInTheFuture() {
		DataCollection dc = new DataCollection();
		dc.setProgressStatus(DataCollection.ProgressStatuses.NOT_INITIATED);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.today());
		calendar.add(Calendar.DAY_OF_YEAR, 29);
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
	public void dataCollectionsMoreThanThirtyDaysAheadInTimeAreReportedPlanned() {
		DataCollection dc = new DataCollection();
		dc.setProgressStatus(DataCollection.ProgressStatuses.NOT_INITIATED);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.today());
		calendar.add(Calendar.DAY_OF_YEAR, 31);
//		calendar.add(Calendar.DAY_OF_YEAR, 1);
		dc.setTargetDate(DateUtil.sqlDate(calendar.getTime()));
		assertEquals(DataCollection.Statuses.PLANNED, dc.status());		
	}
}
