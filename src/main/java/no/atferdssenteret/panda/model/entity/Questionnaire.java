package no.atferdssenteret.panda.model.entity;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.TargetBelonging;
import no.atferdssenteret.panda.model.TimeStamper;
import no.atferdssenteret.panda.model.entity.QuestionnaireEvent.Types;
import no.atferdssenteret.panda.util.DateUtil;

@Entity
public class Questionnaire implements Model, TargetBelonging {

	public enum Statuses {
		RECIEVED("Mottatt"),
		NOT_RECIEVED("Ikke motatt"),
		GIVEN_UP("Gitt opp"),
		LOST("Mistet"),
		NOT_RECIEVED_FOLLOW_UP("Ikke mottatt: Til oppfølging"),
		NOT_APPLICABLE("Ikke aktuell");
	
		private String name;

		Statuses(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}
	
	public enum Formats {
		ELECTRONIC("Elektronisk"),
		PAPER("Papir");
		
		private String name;

		Formats(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	@OrderBy("name")
	private String name;
	@JoinColumn(nullable = false)
	private DataCollection dataCollection;
	@Column(nullable = false)
	private Statuses status = Statuses.NOT_RECIEVED;
	private Formats format = Formats.ELECTRONIC;
	@OneToMany(mappedBy = "questionnaire", cascade = {CascadeType.ALL}, orphanRemoval = true)
	@OrderBy("date ASC, id ASC")
	private List<QuestionnaireEvent> questionnaireEvents = new LinkedList<QuestionnaireEvent>();
	private Timestamp created;
	private Timestamp updated;
	private String createdBy;
	private String updatedBy;
	
	@PrePersist
	protected void onCreate() {
		TimeStamper.onCreate(this);
	}

	@PreUpdate
	protected void onUpdate() {
		TimeStamper.onUpdate(this);
		status = calculateStatus();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DataCollection getDataCollection() {
		return dataCollection;
	}

	public void setDataCollection(DataCollection dataCollection) {
		this.dataCollection = dataCollection;
	}

	public Statuses calculateStatus() {
		boolean isFollowUp = false;
		for (QuestionnaireEvent event : questionnaireEvents) {
			switch (event.getType()) {
			case RECIEVED: return Statuses.RECIEVED;
			case GIVEN_UP: return Statuses.GIVEN_UP;
			case LOST: return Statuses.LOST;
			case NOT_APPLICABLE: return Statuses.NOT_APPLICABLE;
			default: break;
			}
			isFollowUp = isFollowUp
					|| event.getType() == QuestionnaireEvent.Types.E_MAILED_TO_RESPONDENT
					|| event.getType() == QuestionnaireEvent.Types.MAILED_TO_RESPONDENT;
		}
		if (isFollowUp) {
			return Statuses.NOT_RECIEVED_FOLLOW_UP;
		}
		else {
			return Statuses.NOT_RECIEVED;
		}
	}

	public void setStatus(Statuses status) {
		this.status = status;
	}
	
	public Statuses getStatus() {
		return status;
	}
	
	public void setFormat(Formats format) {
		this.format = format;
	}
	
	public Formats getFormat() {
		return format;
	}

	public List<QuestionnaireEvent> getQuestionnaireEvents() {
		return questionnaireEvents;
	}

	public void setQuestionnaireEvents(List<QuestionnaireEvent> events) {
		for (QuestionnaireEvent event : events) {
			event.setQuestionnaire(this);
		}
		this.questionnaireEvents = events;
	}

	public void addQuestionnaireEvent(QuestionnaireEvent event) {
//		if (event.isConclusive() && hasConclusiveEvents(questionnaireEvents)) {
//			throw new IllegalArgumentException();
//		}
		event.setQuestionnaire(this);
		questionnaireEvents.add(event);
	}

//	private boolean hasConclusiveEvents(List<QuestionnaireEvent> events) {
//		for (QuestionnaireEvent event : events) {
//			if (event.isConclusive()) {
//				return true;
//			}
//		}
//		return false;
//	}
	
	public boolean hasEvents() {
		return questionnaireEvents.size() > 0;
	}

	public QuestionnaireEvent lastEvent() {
		if (questionnaireEvents != null && questionnaireEvents.size() > 0) {
			return questionnaireEvents.get(questionnaireEvents.size()-1);
		}
		else {
			return null;
		}
	}
	
	public Target target() {
		return dataCollection.getTarget();
	}
	
	
	@Override
	public void validateUserInput() {
		
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
	public long getTargetId() {
		return getDataCollection().getTarget() != null ? getDataCollection().getTarget().getId() : -1;
	}
	
	@Override
	public String toString() {
		return "Spørreskjema: " + name;
	}

	@Override
	public String referenceName() {
		return toString();
	}

	public void createEvent(Types type) {
		QuestionnaireEvent event = new QuestionnaireEvent();
		event.setDate(DateUtil.today());
		event.setType(type);
		addQuestionnaireEvent(event);
	}
}
