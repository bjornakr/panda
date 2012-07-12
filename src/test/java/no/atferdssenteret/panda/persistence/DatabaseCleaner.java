package no.atferdssenteret.panda.persistence;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.sun.corba.se.spi.orb.DataCollector;

import no.atferdssenteret.panda.DataCollection;
import no.atferdssenteret.panda.Questionnaire;
import no.atferdssenteret.panda.QuestionnaireEvent;
import no.atferdssenteret.panda.Target;

public class DatabaseCleaner {
    private static final Class<?>[] ENTITY_TYPES = {
	Target.class, DataCollection.class, Questionnaire.class, QuestionnaireEvent.class, DataCollector.class
//	Monster.class, Cat.class
    };
    private final EntityManager entityManager;
    
    public DatabaseCleaner(EntityManager entityManager) {
	this.entityManager = entityManager;
    }
    
    public void clean() throws SQLException {
	EntityTransaction transaction = entityManager.getTransaction();
	transaction.begin();
	
	for (Class<?> entityType : ENTITY_TYPES) {
	    deleteEntries(entityType);
	}	
	transaction.commit();
    }

    private void deleteEntries(Class<?> entityType) {
	entityManager.createQuery("DELETE FROM " + entityNameOf(entityType));
    }

    private String entityNameOf(Class<?> entityType) {
	String[] classNameParts = entityType.getName().split("\\."); 
	return classNameParts[classNameParts.length-1];
    }
}
