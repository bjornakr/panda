package no.atferdssenteret.panda.persistence;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import no.atferdssenteret.panda.model.DataCollection;
import no.atferdssenteret.panda.model.Questionnaire;
import no.atferdssenteret.panda.model.QuestionnaireEvent;
import no.atferdssenteret.panda.model.Target;

import com.sun.corba.se.spi.orb.DataCollector;

public class DatabaseCleaner {
	private static final Class<?>[] ENTITY_TYPES = {
		QuestionnaireEvent.class, Questionnaire.class, DataCollection.class, DataCollector.class, Target.class
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
		System.out.println("DELETE FROM " + entityNameOf(entityType));
	}

	private String entityNameOf(Class<?> entityType) {
		String[] classNameParts = entityType.getName().split("\\."); 
		return classNameParts[classNameParts.length-1];
	}
}
