package no.atferdssenteret.panda.filter;

import java.util.Arrays;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import no.atferdssenteret.panda.QuestionnairesForDataCollectionType;
import no.atferdssenteret.panda.model.DataCollection;
import no.atferdssenteret.panda.model.DataCollectionTypes;
import no.atferdssenteret.panda.model.DataCollection_;
import no.atferdssenteret.panda.model.Questionnaire;
import no.atferdssenteret.panda.model.Questionnaire_;

public class QuestionnaireFilterCreator implements FilterCreator {
	@Override
	public Filter[] createFilters() {
		Filter[] filters = new Filter[3];
		filters[0] = new Filter("Datainnsamling", DataCollectionTypes.values());
		filters[1] = new Filter("Spørreskjema", QuestionnairesForDataCollectionType.getInstance().allQuestionnaireNames().toArray());
		filters[2] = new Filter("Status", Questionnaire.Statuses.values());
		return filters;
	}
	
	public static Predicate createPredicate(Object value, Root<Questionnaire> root,
			Join<Questionnaire, DataCollection> joinQuestionnaireDataCollection) {
		if (Arrays.asList(DataCollectionTypes.values()).contains(value)) {
			Path<String> dataCollectionTypePath = joinQuestionnaireDataCollection.get(DataCollection_.type);
			return criteriaBuilder.equal(dataCollectionTypePath, value);
		}
		else if (QuestionnairesForDataCollectionType.getInstance().allQuestionnaireNames().contains(value)) {
			return criteriaBuilder.equal(root.get(Questionnaire_.name), value);
		}
		else if (value instanceof Questionnaire.Statuses) {
			return criteriaBuilder.equal(root.get(Questionnaire_.status), value);
		}
		else {
			return criteriaBuilder.conjunction();
		}
	}
}
