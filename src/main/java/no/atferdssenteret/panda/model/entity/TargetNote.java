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

import no.atferdssenteret.panda.InvalidUserInputException;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Session;
import no.atferdssenteret.panda.util.StandardMessages;

@Entity
public class TargetNote implements Model {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String note;
	@JoinColumn(nullable = false)
	private Target target;	
	@Column(nullable = false)
	private Date created;
	@Column(nullable = false)
	private Date updated;
	@Column(nullable = false)
	private String createdBy;
	@Column(nullable = false)
	private String updatedBy;
	
	@PrePersist
	protected void onCreate() {
		created = new Date(System.currentTimeMillis());
		createdBy = Session.currentSession.user().getUsername();
		onUpdate();
	}

	@PreUpdate
	protected void onUpdate() {
		updated = new Date(System.currentTimeMillis());
		updatedBy = Session.currentSession.user().getUsername();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
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
	public void validate() {
		if (note == null) {
			throw new InvalidUserInputException(StandardMessages.missingField("Notat"));
		}
	}

	@Override
	public String toString() {
		return note;
	}
	
	@Override
	public String referenceName() {
		return "Notat: " + note;
	}
}
