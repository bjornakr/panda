package no.atferdssenteret.panda.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

public class JPATransactor {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("targets");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private static JPATransactor transactor; 
    
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
	return entityManager.getTransaction();
    }
    
    public EntityManager entityManager() {
	return entityManager;
    }
    
    public void perform(UnitOfWork unitOfWork) throws Exception {
	EntityTransaction transaction = entityManager.getTransaction();
	transaction.begin();
	try {
	    unitOfWork.work();
	    transaction.commit();
	}
	catch (PersistenceException e) {
	    throw e;
	}
	catch (Exception e) {
	    transaction.rollback();
	    throw e;
	}
    }
}
