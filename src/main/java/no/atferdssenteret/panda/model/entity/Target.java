package no.atferdssenteret.panda.model.entity;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import no.atferdssenteret.panda.InvalidUserInputException;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.ParticipationStatuses;
import no.atferdssenteret.panda.model.Session;
import no.atferdssenteret.panda.model.TargetBelonging;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.util.StandardMessages;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Target implements Model, TargetBelonging {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private ParticipationStatuses status;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	private String comment;
	private Date created;
	private Date updated;
	private String createdBy;
	private String updatedBy;

	@OneToMany(mappedBy = "target", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	@OrderBy("targetDate")
	private final List<DataCollection> dataCollections = new LinkedList<DataCollection>();
	
	@OneToMany(mappedBy = "target", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	private List<Participant> participants = new LinkedList<Participant>();
	
	@OneToMany(mappedBy = "target", cascade = {CascadeType.ALL}, orphanRemoval = true)
	@OrderBy("created DESC, id DESC")
	private List<TargetNote> targetNotes = new LinkedList<TargetNote>();
	
	@ManyToOne(cascade = {CascadeType.DETACH})
	private User dataCollector;

	public Target() {
		// Cannot wait to set the "created" field until persisting,
		// because it is required by the DataCollectionManager before
		// it is persisted.
		created = new Date(System.currentTimeMillis());
	}
	
	@PrePersist
	protected void onCreate() {
		createdBy = Session.currentSession.user().getUsername();
		updatedBy = Session.currentSession.user().getUsername();
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

	public String formattedIdWithLetterAppendix() {
		return formattedNumericId() + " " + letterId();
	}

	public Object formattedNumericId() {
		DecimalFormat idFormat = new DecimalFormat("000");
		return idFormat.format(id);
	}

	public String letterId() {
		final int MAX_LETTERS = 2;
		char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Z'};
		char[] letterId = new char[MAX_LETTERS];
		for (int i = 0; i < MAX_LETTERS; i++) {
			letterId[MAX_LETTERS-1-i] = letters[(int)(id / (Math.pow(letters.length, i)) % letters.length)];
		}
		return new String(letterId);
	}


	public ParticipationStatuses getStatus() {
		return status;
	}

	public void setStatus(ParticipationStatuses status) {
		this.status = status;	
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

	public void addDataCollection(final DataCollection dataCollection) {
		dataCollection.setTarget(this);
		dataCollections.add(dataCollection);
	}

	public Collection<DataCollection> getDataCollections() {
		return dataCollections;
	}

	public DataCollection getDataCollection(String dataCollectionType) {
		for (DataCollection dataCollection : dataCollections) {
			if (dataCollectionType.equals(dataCollection.getType())) {
				return dataCollection;
			}
		}
		return null;
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

	public boolean isParticipating() {
		return (status == ParticipationStatuses.PARTICIPATING);
	}

	public User getDataCollector() {
		return dataCollector;
	}

	public void setDataCollector(User dataCollector) {
		this.dataCollector = dataCollector;
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Participant> participants) {
		for (Participant participant : participants) {
			participant.setTarget(this);
		}
		this.participants = participants;
	}

	public void addParticipant(Participant participant) {
		participant.setTarget(this);
		participants.add(participant);
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public static boolean targetIdExists(long id) {
		return (JPATransactor.getInstance().entityManager().find(Target.class, id) != null);
	}

	public void validate() {
		if (status == null) {
			throw new InvalidUserInputException(StandardMessages.missingField("Status"));
		}
		else if (firstName == null) {
			throw new InvalidUserInputException(StandardMessages.missingField("Fornavn"));
		}
		else if (lastName == null) {
			throw new InvalidUserInputException(StandardMessages.missingField("Etternavn"));
		}
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
	
	public long getTargetId() {
		return id;
	}
	
	public boolean hasDataCollection(String dataCollectionType) {
		for (DataCollection dataCollection : dataCollections) {
			if (dataCollection.getType().equals(dataCollectionType)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Target: " + id;
	}

	@Override
	public String referenceName() {
		return toString();
	}

	public List<TargetNote> getTargetNotes() {
		return targetNotes;
	}

	public void setTargetNotes(List<TargetNote> targetNotes) {
		for (TargetNote targetNote : targetNotes) {
			targetNote.setTarget(this);
		}
		this.targetNotes = targetNotes;
	}
	
	public void addTargetNote(TargetNote targetNote) {
		targetNote.setTarget(this);
		targetNotes.add(targetNote);
	}

	public void removeTargetNote(TargetNote targetNote) {
		targetNotes.remove(targetNote);
	}
}

