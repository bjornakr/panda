package no.atferdssenteret.panda.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Participant implements Model {
//	public enum Statuses {PARTICIPATING, CONSENT_WITHDRAWN, SOMETHING}    

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Target target;
	@Column(nullable = false)
	private String role;
	@Column(nullable = false)
	private ParticipationStatuses status;
	private String firstName;
	private String lastName;
	private String contactInfo;
	private String phoneNo;
	private String eMail;
	private String comment;

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
}
