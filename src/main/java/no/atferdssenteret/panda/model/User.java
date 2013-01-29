package no.atferdssenteret.panda.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import no.atferdssenteret.panda.util.JPATransactor;

@Entity
public class User implements Model {
	
	public enum AccessLevel {
		NO_ACCESS("Ingen tilgang", 0),
		DATA_COLLECTOR("Datainnsamler", 1),
		ADMINISTRATOR("Administrator", 2),
		SUPER_USER("Superbruker", 3);
		
		private String name;
		private int level;
		
		private AccessLevel(String name, int level) {
			this.name = name;
			this.level = level;
		}
		
		public String toString() {
			return name;
		}
		
		public int level() {
			return level;
		}
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private AccessLevel accessLevel;
	private String userName;
	private String firstName;
	private String lastName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public AccessLevel getAccessLevel() {
		return accessLevel;
	}
	
	public void setAccessLevel(AccessLevel accessLevel) {
		this.accessLevel = accessLevel;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public static Collection<User> dataCollectors() {
		CriteriaBuilder criteriaBuilder = JPATransactor.getInstance().entityManager().getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> userRoot = criteriaQuery.from(User.class);
		criteriaQuery.where(criteriaBuilder.equal(userRoot.get(User_.accessLevel), User.AccessLevel.DATA_COLLECTOR));
		return JPATransactor.getInstance().entityManager().createQuery(criteriaQuery).getResultList();
	}
	
	public String toString() {
		return firstName + " " + lastName;
	}
}
