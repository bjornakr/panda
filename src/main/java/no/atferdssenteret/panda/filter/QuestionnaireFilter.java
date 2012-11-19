package no.atferdssenteret.panda.filter;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import no.atferdssenteret.panda.model.Questionnaire;
import no.atferdssenteret.panda.model.QuestionnaireEvent;
import no.atferdssenteret.panda.model.QuestionnaireTypes;
import no.atferdssenteret.panda.model.Questionnaire_;

public class QuestionnaireFilter implements FilterCreator {
	private final CriteriaQuery<Questionnaire> criteriaQuery = criteriaBuilder.createQuery(Questionnaire.class);
	private final Root<Questionnaire> root = criteriaQuery.from(Questionnaire.class);
	private final Join<Questionnaire, QuestionnaireEvent> qqe = root.join(Questionnaire_.questionnaireEvents);
	
	@Override
	public List<Filter> createFilters() {
		List<Filter> filters = new LinkedList<Filter>();
		filters.add(createTypeFilter());
//		filters.add(createTypeFiter());
//		filters.add(createProgressStatusFilter());
		return filters;
	}

	private Filter createTypeFilter() {
		List<FilterUnit> filterUnits = new LinkedList<FilterUnit>();
		filterUnits.add(includeAllFilterUnit);
		for (String value : QuestionnaireTypes.values()) {
			filterUnits.add(new FilterUnit(value, criteriaBuilder.equal(root.get(Questionnaire_.name), value)));
		}
		return new Filter("Spørreskjema", filterUnits);
	}

//	private Filter createLastEventFilter() {
//		List<FilterUnit> filterUnits = new LinkedList<FilterUnit>();
//		filterUnits.add(includeAllFilterUnit);
//		for (String value : QuestionnaireTypes.values()) {
//			filterUnits.add(new FilterUnit(value, criteriaBuilder.equal(root.get(Questionnaire_.questionnaireEvents.), value)));
//		}
//		return new Filter("Spørreskjema", filterUnits);
//		
//	}
}
