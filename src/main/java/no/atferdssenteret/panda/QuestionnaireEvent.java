package no.atferdssenteret.panda;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class QuestionnaireEvent {    
    public enum Types {PERSONAL_INTERVIEW, RECIEVED_FOR_PROCESSING, GIVEN_UP, LOST, PROCESSED}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Types type;
    private Questionnaire questionnaire;

    public QuestionnaireEvent() {	
	// Dummy constructor for JPA
    }
    
    public QuestionnaireEvent(Types type) {
	this.type = type;
    }
    
    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }
    
    public Questionnaire getQuestionnaire() {
	return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
	this.questionnaire = questionnaire;
    }

    public Types getType() {
	return type;
    }

    public void setType(Types type) {
	this.type = type;
    }

    public boolean isConclusive() {
	return (type == Types.PROCESSED || type == Types.GIVEN_UP || type == Types.LOST);
    }
}
