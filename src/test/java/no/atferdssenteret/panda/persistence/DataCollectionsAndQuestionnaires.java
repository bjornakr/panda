package no.atferdssenteret.panda.persistence;

import static org.junit.Assert.assertNotNull;
import no.atferdssenteret.panda.DataCollection;
import no.atferdssenteret.panda.DataCollectionManager;
import no.atferdssenteret.panda.QuestionnairesForDataCollectionType;
import no.atferdssenteret.panda.util.JPATransactor;

import org.junit.Before;
import org.junit.Test;

public class DataCollectionsAndQuestionnaires {
    
    @Before
    public void cleanDatabase() throws Exception {
	new DatabaseCleaner(JPATransactor.getInstance().entityManager()).clean();
	DataCollectionManager.getInstance().removeAllRules();
    }

    @Test
    public void creatingDataCollectionWillCreateQuestionnairesForThatDataCollectionType() {
	final String questionnaireCBCL = "CBCL";
	final String questionnaireTRF = "TRF";

	QuestionnairesForDataCollectionType dcqMap = QuestionnairesForDataCollectionType.getInstance();
	dcqMap.addQuestionnaireForDataCollection("T1", questionnaireCBCL);
	dcqMap.addQuestionnaireForDataCollection("T1", questionnaireTRF);

	DataCollection dataCollection = new DataCollection();
	dataCollection.setType("T1");
	JPATransactor.getInstance().entityManager().getTransaction().begin();
	JPATransactor.getInstance().entityManager().persist(dataCollection);
	JPATransactor.getInstance().entityManager().getTransaction().commit();
	
	assertNotNull("DataCollection has " + questionnaireCBCL + ": ", dataCollection.getQuestionnaire(questionnaireCBCL));
	assertNotNull("DataCollection has " + questionnaireTRF + ": ", dataCollection.getQuestionnaire(questionnaireTRF));
    }
    

    
}
