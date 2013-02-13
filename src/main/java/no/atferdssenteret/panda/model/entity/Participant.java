package no.atferdssenteret.panda.model.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import no.atferdssenteret.panda.controller.MainController;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.ParticipationStatuses;
import no.atferdssenteret.panda.model.TargetBelonging;
import no.atferdssenteret.panda.util.StandardMessages;

@Entity
public class Participant implements Model, TargetBelonging {
//	public enum Statuses {PARTICIPATING, CONSENT_WITHDRAWN, SOMETHING}    

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@JoinColumn(nullable = false)
	private Target target;
	@Column(nullable = false)
	private String role;
	@Column(nullable = false)
	private ParticipationStatuses status;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	private String phoneNo;
	private String eMail;
	private String address;
	private String contactInfo;
	private String comment;
	private Date created;
	private Date updated;
	private String createdBy;
	private String updatedBy;

	@PrePersist
	protected void onCreate() {
		created = new Date(System.currentTimeMillis());
		createdBy = MainController.session.user().getUserName();
		updatedBy = MainController.session.user().getUserName();
	}

	@PreUpdate
	protected void onUpdate() {
		updated = new Date(System.currentTimeMillis());
		updatedBy = MainController.session.user().getUserName();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEMail() {
		return eMail;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public ParticipationStatuses getStatus() {
		return status;
	}

	public void setStatus(ParticipationStatuses status) {
		this.status = status;
	}
	
	public void validate() {
		if (target == null) {
			throw new IllegalStateException(StandardMessages.missingField("Target"));
		}
		else if (role == null) {
			throw new IllegalStateException(StandardMessages.missingField("Deltakerrolle"));
		}
		else if (status == null) {
			throw new IllegalStateException(StandardMessages.missingField("Status"));
		}
		else if (firstName == null) {
			throw new IllegalStateException(StandardMessages.missingField("Fornavn"));
		}
		else if (lastName == null) {
			throw new IllegalStateException(StandardMessages.missingField("Etternavn"));
		}
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

	@Override
	public long getTargetId() {
		return getTarget().getId();
	}
	
	@Override
	public String toString() {
		return "Deltaker: " + firstName + " " + lastName + " (" + role + ")";
	}

	@Override
	public String referenceName() {
		return toString();
	}
	
}
