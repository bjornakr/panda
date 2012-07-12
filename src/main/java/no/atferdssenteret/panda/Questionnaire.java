package no.atferdssenteret.panda;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Questionnaire {
    
    public enum Statuses {RECIEVED_FOR_PROCESSING, NOT_RECIEVED, GIVEN_UP, LOST}
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private DataCollection dataCollection;
    @OneToMany(mappedBy = "questionnaire")
    private List<QuestionnaireEvent> events = new LinkedList<QuestionnaireEvent>();
    
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
	for (QuestionnaireEvent event : events) {
	    switch (event.getType()) {
	    case RECIEVED_FOR_PROCESSING: return Statuses.RECIEVED_FOR_PROCESSING;
	    case GIVEN_UP: return Statuses.GIVEN_UP;
	    case LOST: return Statuses.LOST;
	    }
	}
	return Statuses.NOT_RECIEVED;
    }

    public List<QuestionnaireEvent> getEvents() {
	return events;
    }

    public void setEvents(List<QuestionnaireEvent> events) {
	this.events = events;
    }
    
    public void addEvent(QuestionnaireEvent event) {
	if (event.isConclusive() && hasConclusiveEvents(events)) {
	    throw new IllegalArgumentException();
	}
	event.setQuestionnaire(this);
	events.add(event);
    }

    private boolean hasConclusiveEvents(List<QuestionnaireEvent> events) {
	for (QuestionnaireEvent event : events) {
	    if (event.isConclusive()) {
		return true;
	    }
	}
	return false;
    }

}
