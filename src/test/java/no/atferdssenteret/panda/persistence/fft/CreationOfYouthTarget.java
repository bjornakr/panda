package no.atferdssenteret.panda.persistence.fft;

import javax.persistence.TypedQuery;

import no.atferdssenteret.panda.DataCollectionManager;
import no.atferdssenteret.panda.model.fft.Youth;
import no.atferdssenteret.panda.persistence.DatabaseCleaner;
import no.atferdssenteret.panda.util.JPATransactor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreationOfYouthTarget {
    
    @Before
    public void setup() throws Exception {
	new DatabaseCleaner(JPATransactor.getInstance().entityManager()).clean();
	DataCollectionManager.getInstance().removeAllRules();
    }
    
    @Test
    public void persistExtensionOfTarget() {
	Youth youth = new Youth();
	youth.setFirstName("Glufsur");
	youth.setLastName("Slufsidottir");
	youth.setRegion(Youth.Regions.TRONDHEIM);
	youth.setTreatmentGroup(Youth.TreatmentGroups.INTERVENTION);
	JPATransactor.getInstance().transaction().begin();
	JPATransactor.getInstance().entityManager().persist(youth);
	JPATransactor.getInstance().transaction().commit();
	TypedQuery<Youth> query = JPATransactor.getInstance().entityManager().createQuery("SELECT y FROM Youth y", Youth.class);
	Youth youthFromDatabase = query.getResultList().get(0);
	assertEquals(youth.getFirstName(), youthFromDatabase.getFirstName());
	assertEquals(youth.getLastName(), youthFromDatabase.getLastName());
	assertEquals(youth.getRegion(), youthFromDatabase.getRegion());
    }
}
