package no.atferdssenteret.panda.integration;

import java.util.LinkedList;
import java.util.List;

import no.atferdssenteret.panda.model.DataCollection;
import no.atferdssenteret.panda.model.Questionnaire;
import no.atferdssenteret.panda.model.QuestionnaireEvent;
import no.atferdssenteret.panda.util.JPATransactor;

public class DataCollectionAndQuestionnaireTest {

	public void blargh() {
		QuestionnaireEvent qe = new QuestionnaireEvent();
		Questionnaire q = new Questionnaire();		
		DataCollection dc = new DataCollection();
		q.setQuestionnaireEvents(wrapInList(qe));
		dc.setQuestionnaires(wrapInList(q));

		JPATransactor.getInstance().persist(dc);	
	}

	private <T> List<T> wrapInList(T model) {
		List<T> models = new LinkedList<T>();
		models.add(model);
		return models;
	}
}
