package no.atferdssenteret.panda.model;

import java.sql.Date;
import java.util.Collections;
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

import no.atferdssenteret.panda.controller.MainController;

@Entity
public class Questionnaire implements Model, TargetBelonging {

	public enum Statuses {RECIEVED("Mottatt"),
		NOT_RECIEVED("Ikke motatt"),
		GIVEN_UP("Gitt opp"),
		LOST("Mistet");
	
		private String name;

		Statuses(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

//	@OrderBy("dataCollection, name")
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
	@OneToMany(mappedBy = "questionnaire", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@OrderBy("date")
	private List<QuestionnaireEvent> questionnaireEvents = new LinkedList<QuestionnaireEvent>();
	private Date created;
	private Date updated;
	private String createdBy;
	private String updatedBy;
	
	@PrePersist
	protected void onCreate() {
		created = new Date(System.currentTimeMillis());
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
		for (QuestionnaireEvent event : questionnaireEvents) {
			switch (event.getType()) {
			case RECIEVED: return Statuses.RECIEVED;
			case GIVEN_UP: return Statuses.GIVEN_UP;
			case LOST: return Statuses.LOST;
			default: break;
			}
		}
		return Statuses.NOT_RECIEVED;
	}

	public void setStatus(Statuses status) {
		this.status = status;
	}
	
	public Statuses getStatus() {
		return status;
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
		if (event.isConclusive() && hasConclusiveEvents(questionnaireEvents)) {
			throw new IllegalArgumentException();
		}
		event.setQuestionnaire(this);
		questionnaireEvents.add(event);
	}

	private boolean hasConclusiveEvents(List<QuestionnaireEvent> events) {
		for (QuestionnaireEvent event : events) {
			if (event.isConclusive()) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		return this.getClass() + " " + name;
	}

	public QuestionnaireEvent lastEvent() {
		if (questionnaireEvents != null && questionnaireEvents.size() > 0) {
			Collections.sort(questionnaireEvents);
			return questionnaireEvents.get(0);
		}
		else {
			return null;
		}
	}
	
	public Target target() {
		return dataCollection.getTarget();
	}
	
	
	@Override
	public void validate() {
		
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
		return getDataCollection().getTarget().getId();
	}

}
