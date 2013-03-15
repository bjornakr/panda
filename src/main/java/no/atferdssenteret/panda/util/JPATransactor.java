package no.atferdssenteret.panda.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import no.atferdssenteret.panda.model.Model;

public class JPATransactor {
	private static JPATransactor self; 
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;

	public JPATransactor(EntityManagerFactory entityManagerFactory) {
		self = this;
		this.entityManagerFactory = entityManagerFactory;
	}

	public static JPATransactor getInstance() {
		return self;
	}

	public EntityTransaction transaction() {
		return entityManager().getTransaction();
	}

	public EntityManager entityManager() {
		if (entityManager == null || !entityManager.isOpen()) {
			System.out.println("Creating new entitymanager");
			entityManager = entityManagerFactory.createEntityManager();
		}
		return entityManager;
	}

	public void quickPersist(Model model) {
		transaction().begin();
		entityManager().persist(model);
		transaction().commit();
	}
	
	public CriteriaBuilder criteriaBuilder() {
		return entityManager().getCriteriaBuilder();
	}
	
	public CriteriaQuery<? extends Model> criteriaQuery(Class<? extends Model> c) {
		return entityManager().getCriteriaBuilder().createQuery(c);
	}

	public Model mergeIfDetached(Model model) {
		if (model != null) {
			if (!JPATransactor.getInstance().entityManager().contains(model)) {
				System.out.println("MERGE!");
				return JPATransactor.getInstance().entityManager().merge(model);
			}
		}
		return model;
	}
}
