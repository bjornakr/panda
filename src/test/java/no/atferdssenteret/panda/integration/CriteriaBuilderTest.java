package no.atferdssenteret.panda.integration;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import no.atferdssenteret.panda.DataCollectionManager;
import no.atferdssenteret.panda.model.DataCollectionTypes;
import no.atferdssenteret.panda.model.ParticipationStatuses;
import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.model.entity.DataCollection_;
import no.atferdssenteret.panda.model.entity.Target;
import no.atferdssenteret.panda.model.entity.Target_;
import no.atferdssenteret.panda.util.DatabaseCleaner;
import no.atferdssenteret.panda.util.DateUtil;
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
		criteriaQuery.where(criteriaBuilder.equal(targetRoot.get(Target_.status), ParticipationStatuses.PARTICIPATING));
	
		List<Target> targets = JPATransactor.getInstance().entityManager().createQuery(criteriaQuery).getResultList();
		assertEquals(1, targets.size());
	}
	
	@Test
	public void testQueringForDataCollectionStatus() {
		Target t = TestUtil.createNotParticipatingTarget();
//		List<DataCollection> dcs = new LinkedList<DataCollection>();
		DataCollection dc = new DataCollection();
		dc.setType(DataCollectionTypes.T1);
		dc.setTargetDate(DateUtil.today());
		t.addDataCollection(dc);
		JPATransactor.getInstance().transaction().begin();
		JPATransactor.getInstance().entityManager().persist(t);
		JPATransactor.getInstance().transaction().commit();
		
		CriteriaBuilder criteriaBuilder = JPATransactor.getInstance().entityManager().getCriteriaBuilder();
		CriteriaQuery<DataCollection> criteriaQuery = criteriaBuilder.createQuery(DataCollection.class);
		Root<DataCollection> root = criteriaQuery.from(DataCollection.class);
//		ParameterExpression<Date> d = criteriaBuilder.parameter(Date.class);
		Predicate p = criteriaBuilder.between(root.get(DataCollection_.targetDate),
				DateUtil.addTime(DateUtil.today(), Calendar.MONTH, -1),
				DateUtil.addTime(DateUtil.today(), Calendar.MONTH, 1));
		criteriaQuery.select(root);
		criteriaQuery.where(p);
		
		List<DataCollection> targets = JPATransactor.getInstance().entityManager().createQuery(criteriaQuery).getResultList();
		assertEquals(1, targets.size());
	
//				ParameterExpression<Date> d = builder.parameter(Date.class);
//		builder.between(d, root.<Date>get("from"), root.<Date>get("to")); 
//		return entityManager.createQuery(query)
//		    .setParameter(d, currentDate, TemporalType.DATE).getResultList(); 
	}
}
