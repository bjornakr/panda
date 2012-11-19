package no.atferdssenteret.panda.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Questionnaire implements Model {

	public enum Statuses {RECIEVED_FOR_PROCESSING, NOT_RECIEVED, GIVEN_UP, LOST}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String name;
	private DataCollection dataCollection;

	@OneToMany(mappedBy = "questionnaire", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<QuestionnaireEvent> questionnaireEvents = new LinkedList<QuestionnaireEvent>();

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

	public Statuses getStatus() {
		for (QuestionnaireEvent event : questionnaireEvents) {
			switch (event.getType()) {
			case RECIEVED_FOR_PROCESSING: return Statuses.RECIEVED_FOR_PROCESSING;
			case PROCESSED: return Statuses.RECIEVED_FOR_PROCESSING;
			case GIVEN_UP: return Statuses.GIVEN_UP;
			case LOST: return Statuses.LOST;
			default: break;
			}
		}
		return Statuses.NOT_RECIEVED;
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
		return "Q: " + name;
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
}
