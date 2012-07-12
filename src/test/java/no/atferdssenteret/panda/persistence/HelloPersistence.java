package no.atferdssenteret.panda.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import no.atferdssenteret.panda.hellopersistence.Cat;
import no.atferdssenteret.panda.hellopersistence.Monster;

import org.junit.Test;

public class HelloPersistence {
    private final EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("hello");
    private final EntityManager em = emFactory.createEntityManager();
    
//    @Test
//    public void eatenCatsShouldBeRemovedFromDatabase() {
//	Monster monster = new Monster();
//	monster.setName("Harald Huleknurr");
//	
//	Cat cat = new Cat();
//	cat.setName("Bobby");
//	cat.setWeight(7.4);
//	cat.setColor(Cat.Colors.BROWN);
//	monster.stealCat(cat);
//	
//	cat = new Cat();
//	cat.setName("Buckingham");
//	cat.setWeight(5.3);
//	cat.setColor(Cat.Colors.WHITE);
//	monster.stealCat(cat);
//			
//	em.getTransaction().begin();
//	em.persist(monster);
//	em.getTransaction().commit();
//	
//	cat = new Cat();
//	cat.setName("Squarebuckle");
//	cat.setWeight(5.3);
//	cat.setColor(Cat.Colors.BLACK);
//	cat.setCaptor(monster);
//	em.getTransaction().begin();
//	em.persist(cat);
//	em.getTransaction().commit();
//
//
//	TypedQuery<Cat> q = em.createQuery("SELECT x FROM Cat x", Cat.class);
//	List<Cat> cats = q.getResultList();
//	Cat todaysDinner = cats.get(new Random().nextInt(cats.size())); 
//	assertTrue(monster.getCats().contains(todaysDinner));
//	int noOfCats = monster.getCats().size();
//	monster.eat(todaysDinner);
//	assertEquals(noOfCats-1, monster.getCats().size());
//	
//	em.getTransaction().begin();
//	em.merge(monster);
//	em.getTransaction().commit();
//	em.close();
//    }
}
