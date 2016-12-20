package no.atferdssenteret.panda.filter;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import no.atferdssenteret.panda.QuestionnairesForDataCollectionType;
import no.atferdssenteret.panda.model.DataCollectionTypes;
import no.atferdssenteret.panda.model.QuestionnaireTypes;
import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.model.entity.DataCollection_;
import no.atferdssenteret.panda.model.entity.Questionnaire;
import no.atferdssenteret.panda.model.entity.Questionnaire_;
import no.atferdssenteret.panda.model.entity.User;

public class QuestionnaireFilterCreator implements FilterCreator {
	public final static String FILTER_NAME_DATA_COLLECTOR = "Datainnsamler";
	public final static String FILTER_NAME_STATUS = "Status";
	
	
	@Override
	public Filter[] createFilters() {
		Filter[] filters = new Filter[5];
		filters[0] = new Filter("Datainnsamling", DataCollectionTypes.getInstance().toArray());
		filters[1] = new Filter(FILTER_NAME_DATA_COLLECTOR, User.dataCollectors().toArray());
		filters[2] = new Filter("Spørreskjema", QuestionnaireTypes.getInstance().toArray());
		filters[3] = new Filter(FILTER_NAME_STATUS, Questionnaire.Statuses.values());
		filters[4] = new Filter("Format", Questionnaire.Formats.values());
		return filters;
	}
	
	public static Predicate createPredicate(Object value, Root<Questionnaire> root,
			Join<Questionnaire, DataCollection> joinQuestionnaireDataCollection) {
		if (DataCollectionTypes.getInstance().contains(value)) {
			Path<String> dataCollectionTypePath = joinQuestionnaireDataCollection.get(DataCollection_.type);
			return criteriaBuilder.equal(dataCollectionTypePath, value);
		}
		else if (QuestionnairesForDataCollectionType.getInstance().allQuestionnaireNames().contains(value)) {
			return criteriaBuilder.equal(root.get(Questionnaire_.name), value);
		}
		else if (value instanceof Questionnaire.Statuses) {
			return criteriaBuilder.equal(root.get(Questionnaire_.status), value);
		}
		else if (value instanceof Questionnaire.Formats) {
			return criteriaBuilder.equal(root.get(Questionnaire_.format), value);
		}
		else if (value instanceof User) {
			Path<User> dataCollectorPath = joinQuestionnaireDataCollection.get(DataCollection_.dataCollector);
			return criteriaBuilder.equal(dataCollectorPath, value);
		}
		else {
			return criteriaBuilder.conjunction();
		}
	}
}
