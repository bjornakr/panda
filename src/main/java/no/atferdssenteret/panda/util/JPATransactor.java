package no.atferdssenteret.panda.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import no.atferdssenteret.panda.model.Model;

public class JPATransactor {
	private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("targets");
	private EntityManager entityManager = entityManagerFactory.createEntityManager();
	private static JPATransactor transactor; 
	private static CriteriaBuilder criteriaBuilder = JPATransactor.getInstance().entityManager().getCriteriaBuilder();


	//    public JPATransactor(EntityManager entityManager) {
	//	this.entityManager = entityManager;
	//    }

	private JPATransactor() {
	}

	public static JPATransactor getInstance() {
		if (transactor == null) {
			transactor = new JPATransactor();
		}
		return transactor;
	}

	public EntityTransaction transaction() {
		return entityManager().getTransaction();
	}

	public EntityManager entityManager() {
		if (!entityManager.isOpen()) {
			entityManager = entityManagerFactory.createEntityManager();
		}
		return entityManager;
	}

//	public void perform(UnitOfWork unitOfWork) throws Exception {
//		EntityTransaction transaction = entityManager.getTransaction();
//		transaction.begin();
//		try {
//			unitOfWork.work();
//			transaction.commit();
//		}
//		catch (PersistenceException e) {
//			throw e;
//		}
//		catch (Exception e) {
//			transaction.rollback();
//			throw e;
//		}
//	}

	public void quickPersist(Model model) {
		transaction().begin();
		entityManager().persist(model);
		transaction().commit();
	}
//	
//	public void update() {
//		entityManager.getTransaction().begin();
//		entityManager.getTransaction().commit();
////		entityManager.close();
////		entityManager = entityManagerFactory.createEntityManager();
//	}
	
	public CriteriaBuilder criteriaBuilder() {
		return criteriaBuilder;
	}
	
	public CriteriaQuery<? extends Model> criteriaQuery(Class<? extends Model> c) {
		return criteriaBuilder.createQuery(c);
	}
}
