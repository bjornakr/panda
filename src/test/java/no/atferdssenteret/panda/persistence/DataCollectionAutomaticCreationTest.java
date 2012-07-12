package no.atferdssenteret.panda.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import no.atferdssenteret.panda.DataCollection;
import no.atferdssenteret.panda.DataCollectionManager;
import no.atferdssenteret.panda.DataCollector;
import no.atferdssenteret.panda.Target;
import no.atferdssenteret.panda.util.DateUtil;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.util.TestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DataCollectionAutomaticCreationTest {

    @Before
    public void setup() throws Exception {
	System.out.println("setup()");
	new DatabaseCleaner(JPATransactor.getInstance().entityManager()).clean();
	DataCollectionManager.getInstance().removeAllRules();
    }

    @Test
    public void dataCollectionManagerGetsNotifiedWhenTargetIsCreated() throws Exception {
	Target target = TestUtil.createParticipatingTarget();
	persist(target);
	assertEquals(target, DataCollectionManager.getInstance().currentTarget());
    }


    @Test
    public void dataCollectionIsCreatedAfterTargetIsCreatedAccordingToRule() {
	final int noOfMonths = 3;
	DataCollectionManager.getInstance().addRule(TestUtil.createDataCollectionRuleT1WhenTargetCreated(noOfMonths));
	Target target = TestUtil.createParticipatingTarget();
	persist(target);
	assertTrue("Data collection created", existsDataCollectionCreatedFor(target));
	DataCollection dc = getDataCollection(target, "T1");
	assertEquals("Data collection type: ", "T1", dc.getType());
	assertEquals("Target date: ", calculateExpectedTargetDate(target.getCreated(), noOfMonths), dc.getTargetDate());
    }

    private boolean existsDataCollectionCreatedFor(Target target) {
	TypedQuery<DataCollection> query = JPATransactor.getInstance().entityManager().createQuery("SELECT dc FROM DataCollection dc WHERE dc.target.id = " + target.getId(), DataCollection.class);
	System.err.println(query.toString());
	return query.getResultList().size() > 0;
    }
    
    private DataCollection getDataCollection(Target target, String type) {
	TypedQuery<DataCollection> query = JPATransactor.getInstance().entityManager().createQuery(
		"SELECT dc FROM DataCollection dc WHERE dc.target.id = " + target.getId() + " AND dc.type = '" + type + "'", DataCollection.class);
	System.err.println(query.toString());
	if (query.getResultList().size() > 0) {
	    return query.getResultList().get(0);
	}
	else {
	    return null;
	}
    }

    private List<DataCollection> getDataCollections(Target target) {
	TypedQuery<DataCollection> query = JPATransactor.getInstance().entityManager().createQuery(
		"SELECT dc FROM DataCollection dc WHERE dc.target.id = " + target.getId(), DataCollection.class);
	return query.getResultList();
    }

    @Test
    public void dataCollectionsAreCreatedAfterTargetIsUpdatedAccordingToRules() throws ParseException {
	final int noOfMonthsForT2 = 6;
	final int noOfMonthsForT3 = 18;
	DataCollectionManager dcManager = DataCollectionManager.getInstance();
	dcManager.addRule(TestUtil.createDataCollectionRuleT2WhenTargetUpdated(noOfMonthsForT2));
	dcManager.addRule(TestUtil.createDataCollectionRuleT3WhenTargetUpdated(noOfMonthsForT3));
	Target target = TestUtil.createParticipatingTarget();
	persist(target);
	JPATransactor.getInstance().entityManager().getTransaction().begin();
	target.setTreatmentStart(DateUtil.parseDateFromInternationalDateFormat("2012-03-19"));
	JPATransactor.getInstance().entityManager().getTransaction().commit();
	
	DataCollection dataCollection = getDataCollection(target, "T2");
	assertNotNull("Data collection exists in database: ", dataCollection);
	assertEquals("Target date: ", calculateExpectedTargetDate(target.getTreatmentStart(), noOfMonthsForT2),
		dataCollection.getTargetDate());
	dataCollection = getDataCollection(target, "T3");
	assertNotNull("Data collection exists in database: ", dataCollection);
	assertEquals("Target date: ", calculateExpectedTargetDate(target.getTreatmentStart(), noOfMonthsForT3),
		dataCollection.getTargetDate());
    }

    @Test
    public void updatesExistingInsteadOfCreatingNewDataCollection() throws ParseException {
	DataCollectionManager dcManager = DataCollectionManager.getInstance();
	dcManager.addRule(TestUtil.createDataCollectionRuleT2WhenTargetUpdated(6));
	Target target = TestUtil.createParticipatingTarget();
	persist(target);
	JPATransactor.getInstance().transaction().begin();
	System.out.println("JPATransactor.getInstance().transaction().begin();");
	target.setTreatmentStart(DateUtil.parseDateFromInternationalDateFormat("2012-01-01"));
	JPATransactor.getInstance().transaction().commit();
	System.out.println("JPATransactor.getInstance().transaction().commit();");
	JPATransactor.getInstance().transaction().begin();
	target.setTreatmentStart(DateUtil.parseDateFromInternationalDateFormat("2012-02-02"));
	JPATransactor.getInstance().transaction().commit();
	assertFalse("Duplicate data collections", hasDuplicateDataCollections(target));
	DataCollection dataCollection = getDataCollection(target, "T2");
	assertEquals(DateUtil.parseDateFromInternationalDateFormat("2012-08-02"), dataCollection.getTargetDate());
    }
    
    @Test
    public void ruleIsIgnoredWhenPrerequisiteFieldsAreNotSet() {
	DataCollectionManager dcManager = DataCollectionManager.getInstance();
	dcManager.addRule(TestUtil.createDataCollectionRuleT2WhenTargetUpdated(6));
	Target target = TestUtil.createParticipatingTarget();
	persist(target);
	JPATransactor.getInstance().transaction().begin();
	target.setTreatmentStart(null);
	JPATransactor.getInstance().transaction().commit();
	assertNull(target.getDataCollection("T2"));
    }

    @Test
    public void GeneratedDataCollectionsAreAssignedToSameDataCollectorAsTarget() {
	DataCollector dataCollector = new DataCollector();
	dataCollector.setFirstName("Robby");
	persist(dataCollector);	
	DataCollectionManager.getInstance().addRule(TestUtil.createDataCollectionRuleT1WhenTargetCreated(1));
	Target target = TestUtil.createParticipatingTarget();
	target.setDataCollector(dataCollector);
	persist(target);	
	assertTrue("Target has data collections: ", getDataCollections(target).size() > 0);
	for (DataCollection dataCollection : getDataCollections(target)) {
	    assertEquals("Target's data collector = Data collection's data collector: ",
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