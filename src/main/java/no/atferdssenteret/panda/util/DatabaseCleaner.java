package no.atferdssenteret.panda.util;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.model.entity.Participant;
import no.atferdssenteret.panda.model.entity.Questionnaire;
import no.atferdssenteret.panda.model.entity.QuestionnaireEvent;
import no.atferdssenteret.panda.model.entity.Target;
import no.atferdssenteret.panda.model.entity.TargetNote;
import no.atferdssenteret.panda.model.entity.User;
import no.atferdssenteret.panda.model.entity.Version;

public class DatabaseCleaner {
	private static final Class<?>[] ENTITY_TYPES = {
		Version.class, TargetNote.class, QuestionnaireEvent.class, Questionnaire.class, DataCollection.class, Participant.class, Target.class, User.class
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
		entityManager.createQuery("DELETE FROM " + entityNameOf(entityType)).executeUpdate();
	}

	private String entityNameOf(Class<?> entityType) {
		String[] classNameParts = entityType.getName().split("\\."); 
		return classNameParts[classNameParts.length-1];
	}
}
