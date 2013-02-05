package no.atferdssenteret.panda.model;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import no.atferdssenteret.panda.controller.MainController;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.util.StandardMessages;

@Entity
public class User implements Model {

	public enum AccessLevel {
		NO_ACCESS("Ingen tilgang", 0),
		DATA_COLLECTOR("Datainnsamler", 1),
		ADMINISTRATOR("Administrator", 2),
		SUPER_USER("Superbruker", 3);

		private String name;
		private int value;

		private AccessLevel(String name, int value) {
			this.name = name;
			this.value = value;
		}

		public String toString() {
			return name;
		}

		public int value() {
			return value;
		}
	}

	@Id
	private String userName;
	@Column(nullable = false)
	private AccessLevel accessLevel;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	private Date created;
	private Date updated;
	private String createdBy;
	private String updatedBy;

	@PrePersist
	protected void onCreate() {
		setCreated(new Date(System.currentTimeMillis()));
		setUpdatedBy(MainController.session.user().getUserName());
	}

	@PreUpdate
	protected void onUpdate() {
		setUpdated(new Date(System.currentTimeMillis()));
		setUpdatedBy(MainController.session.user().getUserName());
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


	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public boolean hasAccessToRestrictedFields() {
		return (accessLevel == AccessLevel.ADMINISTRATOR || accessLevel == AccessLevel.SUPER_USER);
	}
	
	@Override
	public void validate() {
		if (userName == null) {
			throw new IllegalStateException(StandardMessages.missingField("Brukernavn"));
		}
		else if (firstName == null) {
			throw new IllegalStateException(StandardMessages.missingField("Fornavn"));
		}
		else if (lastName == null) {
			throw new IllegalStateException(StandardMessages.missingField("Etternavn"));
		}
	}

	public static boolean userNameExists(String userName) {
		return (JPATransactor.getInstance().entityManager().find(User.class, userName) != null);
	}
}
