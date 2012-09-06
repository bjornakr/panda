package no.atferdssenteret.panda.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Participant implements Model {
    public enum Statuses {PARTICIPATING, CONSENT_WITHDRAWN, SOMETHING}    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Target target;
    private String role;
    private String firstName;
    private String lastName;
    private String contactInfo;
    private String tlfNo;
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
    
    public String getTlfNo() {
        return tlfNo;
    }
    
    public void setTlfNo(String tlfNo) {
        this.tlfNo = tlfNo;
    }
    
    public String geteMail() {
        return eMail;
    }
    
    public void seteMail(String eMail) {
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
}