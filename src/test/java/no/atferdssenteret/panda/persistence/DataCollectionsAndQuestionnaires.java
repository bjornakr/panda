package no.atferdssenteret.panda.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.Query;

import no.atferdssenteret.panda.DataCollectionManager;
import no.atferdssenteret.panda.QuestionnairesForDataCollectionType;
import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.util.DatabaseCleaner;
import no.atferdssenteret.panda.util.DateUtil;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.util.TestUtil;

import org.junit.Before;
import org.junit.Test;

public class DataCollectionsAndQuestionnaires {
	private final String questionnaireCBCL = "CBCL";
	private final String questionnaireTRF = "TRF";

	@Before
	public void cleanDatabase() throws Exception {
		new DatabaseCleaner(JPATransactor.getInstance().entityManager()).clean();
		DataCollectionManager.getInstance().removeAllRules();
		QuestionnairesForDataCollectionType dcqMap = QuestionnairesForDataCollectionType.getInstance();
		dcqMap.clear();
		dcqMap.addQuestionnaireNameForDataCollection("T1", questionnaireCBCL);
		dcqMap.addQuestionnaireNameForDataCollection("T1", questionnaireTRF);
	}

	@Test
	public void creatingDataCollectionWillCreateQuestionnairesForThatDataCollectionType() {
		DataCollection dataCollection = createTestDataCollection();
		dataCollection.setDefaultQuestionnaires();
		JPATransactor.getInstance().entityManager().persist(dataCollection.getTarget());
		assertNotNull("DataCollection has " + questionnaireCBCL + ": ", dataCollection.getQuestionnaire(questionnaireCBCL));
		assertNotNull("DataCollection has " + questionnaireTRF + ": ", dataCollection.getQuestionnaire(questionnaireTRF));
	}

	@Test
	public void updatingDataCollectionWillSaveQuestionnaires() {
		DataCollection dataCollection = createTestDataCollection();
		dataCollection.setDefaultQuestionnaires();
		JPATransactor.getInstance().quickPersist(dataCollection.getTarget());
//		JPATransactor.getInstance().update();

		Query query = JPATransactor.getInstance().entityManager().createQuery(
				"SELECT COUNT(q) FROM Questionnaire q WHERE q.dataCollection.id = " +
				dataCollection.getId());
		assertEquals(new Long(2), query.getSingleResult());
	}

	private DataCollection createTestDataCollection() {
		DataCollection dataCollection = new DataCollection();
		TestUtil.createParticipatingTarget().addDataCollection(dataCollection);
		dataCollection.setType("T1");
		dataCollection.setTargetDate(DateUtil.parseDateFromInternationalDateFormat("2012-01-01"));
		dataCollection.setProgressStatus(DataCollection.ProgressStatuses.NOT_INITIATED);
		return dataCollection;
	}

}
