package no.atferdssenteret.panda.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import no.atferdssenteret.panda.DataCollectionManager;
import no.atferdssenteret.panda.model.Session;
import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.model.entity.Target;
import no.atferdssenteret.panda.model.entity.User;
import no.atferdssenteret.panda.util.DatabaseCleaner;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.util.TestUtil;

import org.junit.Before;
import org.junit.Test;

public class DataCollectionAutomaticCreationTest {
	public final static String DC_T1 = "T1";
	
	@Before
	public void setup() throws Exception {
		new JPATransactor(Persistence.createEntityManagerFactory("targets"));
//		JPATransactor.getInstance().entityManager().close();
		Session.createTestSession();
		new DatabaseCleaner(JPATransactor.getInstance().entityManager()).clean();
		DataCollectionManager.getInstance().removeAllDataCollectionGenerators();
	}

	@Test
	public void dataCollectionShouldBeCreatedAfterTargetIsCreated() {
		DataCollectionManager.getInstance().addDataCollectionGenerator(TestUtil.createDataCollectionGenerator(DC_T1, 0));
		Target target = TestUtil.createParticipatingTarget();
		DataCollectionManager.getInstance().generateDataCollections(target);
		persist(target);
		assertTrue("Target has data collection", target.hasDataCollection(DC_T1));
		assertTrue("Data collection created in database", dataCollectionExistsInDatabase(target, DC_T1));
	}
	
	@Test
	public void dataCollectionHasCorrectTargetDate() {
		final int noOfMonthsDelay = 3;
		DataCollectionManager.getInstance().addDataCollectionGenerator(TestUtil.createDataCollectionGenerator(DC_T1, noOfMonthsDelay));
		Target target = TestUtil.createParticipatingTarget();
		DataCollectionManager.getInstance().generateDataCollections(target);
		DataCollection dataCollection = target.getDataCollection(DC_T1);
		assertNotNull(dataCollection);
		assertEquals("Target date: ", calculateExpectedTargetDate(target.getCreated(), noOfMonthsDelay), dataCollection.getTargetDate());
	}

	private boolean dataCollectionExistsInDatabase(Target target, String type) {
		TypedQuery<DataCollection> query = JPATransactor.getInstance().entityManager().
				createQuery("SELECT dc FROM DataCollection dc WHERE dc.target.id = " + target.getId() +
						" AND dc.type = '" + type + "'", DataCollection.class);
		return query.getResultList().size() > 0;
	}

	private List<DataCollection> getDataCollections(Target target) {
		TypedQuery<DataCollection> query = JPATransactor.getInstance().entityManager().createQuery(
				"SELECT dc FROM DataCollection dc WHERE dc.target.id = " + target.getId(), DataCollection.class);
		return query.getResultList();
	}

	@Test
	public void noDuplcateDataCollectionsShouldBeCreated() throws ParseException {
		DataCollectionManager.getInstance().addDataCollectionGenerator(TestUtil.createDataCollectionGenerator(DC_T1, 0));
		Target target = TestUtil.createParticipatingTarget();
		DataCollectionManager.getInstance().generateDataCollections(target);
		DataCollectionManager.getInstance().generateDataCollections(target);
		DataCollectionManager.getInstance().generateDataCollections(target);
		persist(target);
		assertFalse("Duplicate data collections", hasDuplicateDataCollections(target));	
	}

	@Test
	public void GeneratedDataCollectionsAreAssignedToSameDataCollectorAsTarget() {
		User dataCollector = new User();
		dataCollector.setUsername("robrock");
		dataCollector.setFirstName("Robby");
		dataCollector.setLastName("Rocksleigh");
		dataCollector.setAccessLevel(User.AccessLevel.DATA_COLLECTOR);
		DataCollectionManager.getInstance().addDataCollectionGenerator(TestUtil.createDataCollectionGenerator(DC_T1, 0));
		Target target = TestUtil.createParticipatingTarget();
		target.setDataCollector(dataCollector);
		DataCollectionManager.getInstance().generateDataCollections(target);
		for (DataCollection dataCollection : target.getDataCollections()) {
			assertEquals("Target's data collector == Data collection's data collector: ",
					target.getDataCollector(), dataCollection.getDataCollector());
		}
	}

	private boolean hasDuplicateDataCollections(Target target) {
		List<String> existingDataCollectionTypes = new ArrayList<String>(); 
		for (DataCollection dataCollection : getDataCollections(target)) {
			System.err.println(dataCollection.getType());
			if (existingDataCollectionTypes.contains(dataCollection.getType())) {
				return true;
			}
			else {
				existingDataCollectionTypes.add(dataCollection.getType());
			}
		}
		return false;
	}

	private Date calculateExpectedTargetDate(Date initialDate, int timeSpan) {
		Calendar expectedTargetDate = Calendar.getInstance();
		expectedTargetDate.setTime(initialDate);
		expectedTargetDate.add(Calendar.MONTH, timeSpan);
		return new Date(expectedTargetDate.getTimeInMillis());
	}

	private void persist(Object object) {
		JPATransactor.getInstance().transaction().begin();
		JPATransactor.getInstance().entityManager().persist(object);
		JPATransactor.getInstance().transaction().commit();
	}
}
