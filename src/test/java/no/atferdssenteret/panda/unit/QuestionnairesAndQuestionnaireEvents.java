package no.atferdssenteret.panda.unit;

import static org.junit.Assert.assertEquals;
import no.atferdssenteret.panda.model.entity.Questionnaire;
import no.atferdssenteret.panda.model.entity.QuestionnaireEvent;

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
		assertEquals(Questionnaire.Statuses.NOT_RECIEVED, questionnaire.calculateStatus()); 
	}

	@Test
	public void statusShouldBeRecievedWhenQuestionnaireHasRecieveEvent() {
		questionnaire.addQuestionnaireEvent(new QuestionnaireEvent(QuestionnaireEvent.Types.RECIEVED));
		assertEquals(Questionnaire.Statuses.RECIEVED, questionnaire.calculateStatus());
	}

	@Test
	public void statusShouldBeGivenUpWhenQuestionnaireHasGivenUpEvent() {
		questionnaire.addQuestionnaireEvent(new QuestionnaireEvent(QuestionnaireEvent.Types.GIVEN_UP));
		assertEquals(Questionnaire.Statuses.GIVEN_UP, questionnaire.calculateStatus());
	}

	@Test
	public void statusShouldBeLostWhenQuestionnaireHasLostEvent() {
		questionnaire.addQuestionnaireEvent(new QuestionnaireEvent(QuestionnaireEvent.Types.LOST));
		assertEquals(Questionnaire.Statuses.LOST, questionnaire.calculateStatus());
	}

	@Test(expected = IllegalArgumentException.class)
	public void cannotHaveMultipleConclusiveEventStatuses() {
		questionnaire.addQuestionnaireEvent(new QuestionnaireEvent(QuestionnaireEvent.Types.RECIEVED));
		questionnaire.addQuestionnaireEvent(new QuestionnaireEvent(QuestionnaireEvent.Types.LOST));
	}

}
