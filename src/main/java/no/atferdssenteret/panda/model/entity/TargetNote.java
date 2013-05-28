package no.atferdssenteret.panda.model.entity;

import java.sql.Timestamp;

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
import no.atferdssenteret.panda.model.TimeStamper;
import no.atferdssenteret.panda.util.StandardMessages;

@Entity
public class TargetNote implements Model {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false, length = 1024)
	private String note;
	@JoinColumn(nullable = false)
	private Target target;	
	@Column(nullable = false)
	private Timestamp created;
	@Column(nullable = false)
	private Timestamp updated;
	@Column(nullable = false)
	private String createdBy;
	@Column(nullable = false)
	private String updatedBy;
	
	@PrePersist
	protected void onCreate() {
		TimeStamper.onCreate(this);
	}

	@PreUpdate
	protected void onUpdate() {
		TimeStamper.onUpdate(this);
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

	@Override
	public Timestamp getCreated() {
		return created;
	}

	@Override
	public void setCreated(Timestamp created) {
		this.created = created;
	}

	@Override
	public Timestamp getUpdated() {
		return updated;
	}

	@Override
	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}

	@Override
	public String getCreatedBy() {
		return createdBy;
	}

	@Override
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String getUpdatedBy() {
		return updatedBy;
	}

	@Override
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public void validateUserInput() {
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
