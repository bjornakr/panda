package no.atferdssenteret.panda.unit;

import static org.junit.Assert.assertEquals;
import no.atferdssenteret.panda.Questionnaire;
import no.atferdssenteret.panda.QuestionnaireEvent;

import org.junit.Before;
import org.junit.Test;

public class QuestionnairesAndQuestionnaireEvents {
    private Questionnaire questionnaire;
    
    @Before
    public void setup() {
	questionnaire = new Questionnaire();
    }
    
    @Test
    public void statusShouldBeNotRecievedWhenQuestionnaireHasNoEvents() {	
	assertEquals(Questionnaire.Statuses.NOT_RECIEVED, questionnaire.getStatus()); 
    }
    
    @Test
    public void statusShouldBeRecievedWhenQuestionnaireHasRecieveEvent() {
	questionnaire.addEvent(new QuestionnaireEvent(QuestionnaireEvent.Types.RECIEVED_FOR_PROCESSING));
	assertEquals(Questionnaire.Statuses.RECIEVED_FOR_PROCESSING, questionnaire.getStatus());
    }
    
    @Test
    public void statusShouldBeGivenUpWhenQuestionnaireHasGivenUpEvent() {
	questionnaire.addEvent(new QuestionnaireEvent(QuestionnaireEvent.Types.GIVEN_UP));
	assertEquals(Questionnaire.Statuses.GIVEN_UP, questionnaire.getStatus());
    }
    
    @Test
    public void statusShouldBeLostWhenQuestionnaireHasLostEvent() {
	questionnaire.addEvent(new QuestionnaireEvent(QuestionnaireEvent.Types.LOST));
	assertEquals(Questionnaire.Statuses.LOST, questionnaire.getStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotHaveMultipleConclusiveEventStatuses() {
	questionnaire.addEvent(new QuestionnaireEvent(QuestionnaireEvent.Types.PROCESSED));
	questionnaire.addEvent(new QuestionnaireEvent(QuestionnaireEvent.Types.LOST));
    }
    
}
