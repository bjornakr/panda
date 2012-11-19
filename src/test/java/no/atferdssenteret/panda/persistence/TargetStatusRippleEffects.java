package no.atferdssenteret.panda.persistence;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import no.atferdssenteret.panda.DataCollectionManager;
import no.atferdssenteret.panda.model.ParticipationStatuses;
import no.atferdssenteret.panda.model.Target;
import no.atferdssenteret.panda.util.DateUtil;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.util.TestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TargetStatusRippleEffects {
// Positive consent / Participating
// Negative consent
// Waiting for consent
// Datacollection stopped
// Never participated
// Not eligible
// Consent withdrawn
// Dropped by research team
    
//    @Test
//    public void untouchedDataCollectionsAreDeletedIfTargetIsNotParticipating() {

    @Before
    public void cleanDatabase() throws Exception {
	new DatabaseCleaner(JPATransactor.getInstance().entityManager()).clean();
	DataCollectionManager.getInstance().removeAllRules();
    }

    @After
    public void closeEntityManager() {
//	JPATransactor.getInstance().entityManager().close();
    }

    
    @Test
    public void dataCollectionsAreNotCreatedBeforeTargetIsParticipating() throws Exception {
	DataCollectionManager.getInstance().addRule(TestUtil.createDataCollectionRuleT1WhenTargetCreated(3));
	Target target = TestUtil.createNotParticipatingTarget();
	JPATransactor.getInstance().transaction().begin();
	JPATransactor.getInstance().entityManager().persist(target);
	JPATransactor.getInstance().transaction().commit();
	assertEquals("No. of data collections: ", 0, target.getDataCollections().size());
    }
    
    @Test
    public void untouchedDataCollectionsAreDeletedWhenTargetsStatusIsChangedToNotParticipating() throws ParseException {
	DataCollectionManager.getInstance().addRule(TestUtil.createDataCollectionRuleT1WhenTargetCreated(3));
	Target target = TestUtil.createParticipatingTarget();
	target.setTreatmentStart(DateUtil.parseDateFromInternationalDateFormat("2012-01-01"));
	JPATransactor.getInstance().transaction().begin();
	JPATransactor.getInstance().entityManager().persist(target);
	JPATransactor.getInstance().transaction().commit();
	assertEquals("No. of data collections before: ", 1, target.getDataCollections().size());
	JPATransactor.getInstance().transaction().begin();
	target.setStatus(ParticipationStatuses.WAITING_FOR_CONSENT);
	DataCollectionManager.getInstance().notifyTargetUpdated(target);
	JPATransactor.getInstance().transaction().commit();
	assertEquals("No. of data collections after: ", 0, target.getDataCollections().size());
    }
    
    @Test
    public void dataCollectionsInProgressAreNotDeletedWhenTargetStatusIsChangedToNotParticipating() {
	
    }
    
//    private Target createTarget() throws ParseException {
//	Target target = new Target();
//	target.setFirstName("Megaroy");
//	target.setLastName("Baljetr�kk");
//	target.setTreatmentStart(DateUtil.parseDateFromInternationalDateFormat("2012-01-01"));
//	return target;
//    }
}
