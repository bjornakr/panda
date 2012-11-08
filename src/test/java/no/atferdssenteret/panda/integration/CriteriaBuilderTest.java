package no.atferdssenteret.panda.integration;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import no.atferdssenteret.panda.DataCollectionManager;
import no.atferdssenteret.panda.model.Target;
import no.atferdssenteret.panda.persistence.DatabaseCleaner;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.util.TestUtil;

import org.junit.Before;
import org.junit.Test;

public class CriteriaBuilderTest {

	@Before
	public void setup() throws SQLException {
		new DatabaseCleaner(JPATransactor.getInstance().entityManager()).clean();
		DataCollectionManager.getInstance().removeAllRules();
	}
	
	@Test
	public void testCriteriaQuery() {
		JPATransactor.getInstance().transaction().begin();
		JPATransactor.getInstance().entityManager().persist(TestUtil.createNotParticipatingTarget());
		JPATransactor.getInstance().entityManager().persist(TestUtil.createParticipatingTarget());
		JPATransactor.getInstance().transaction().commit();
		
		CriteriaBuilder criteriaBuilder = JPATransactor.getInstance().entityManager().getCriteriaBuilder();
		CriteriaQuery<Target> criteriaQuery = criteriaBuilder.createQuery(Target.class);
		
		Root<Target> targetRoot = criteriaQuery.from(Target.class);
		criteriaQuery.select(targetRoot);
		criteriaQuery.where(criteriaBuilder.equal(targetRoot.get("status"), Target.Statuses.PARTICIPATING));
	
		List<Target> targets = JPATransactor.getInstance().entityManager().createQuery(criteriaQuery).getResultList();
		assertEquals(1, targets.size());
	}
}
