package no.atferdssenteret.panda.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.Query;

import no.atferdssenteret.panda.DataCollectionManager;
import no.atferdssenteret.panda.QuestionnairesForDataCollectionType;
import no.atferdssenteret.panda.model.DataCollection;
import no.atferdssenteret.panda.util.DateUtil;
import no.atferdssenteret.panda.util.JPATransactor;

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
		JPATransactor.getInstance().entityManager().getTransaction().begin();
		JPATransactor.getInstance().entityManager().persist(dataCollection);
		JPATransactor.getInstance().entityManager().getTransaction().commit();

		assertNotNull("DataCollection has " + questionnaireCBCL + ": ", dataCollection.getQuestionnaire(questionnaireCBCL));
		assertNotNull("DataCollection has " + questionnaireTRF + ": ", dataCollection.getQuestionnaire(questionnaireTRF));
	}

	@Test
	public void updatingDataCollectionWillSaveQuestionnaires() {
		DataCollection dataCollection = createTestDataCollection();
		JPATransactor.getInstance().entityManager().getTransaction().begin();
		JPATransactor.getInstance().entityManager().persist(dataCollection);
		JPATransactor.getInstance().entityManager().getTransaction().commit();
		dataCollection.setDefaultQuestionnaires();
		JPATransactor.getInstance().entityManager().getTransaction().begin();
		JPATransactor.getInstance().entityManager().getTransaction().commit();

		Query query = JPATransactor.getInstance().entityManager().createQuery(
				"SELECT COUNT(q) FROM Questionnaire q WHERE q.dataCollection.id = " +
				dataCollection.getId());
		assertEquals(new Long(2), query.getSingleResult());
	}

	private DataCollection createTestDataCollection() {
		DataCollection dataCollection = new DataCollection();
		dataCollection.setType("T1");
		dataCollection.setTargetDate(DateUtil.parseDateFromInternationalDateFormat("2012-01-01"));
		dataCollection.setProgressStatus(DataCollection.ProgressStatuses.NOT_INITIATED);
		return dataCollection;
	}

}
