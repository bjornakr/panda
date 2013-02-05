package no.atferdssenteret.panda.filter;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import no.atferdssenteret.panda.model.DataCollectionTypes;
import no.atferdssenteret.panda.model.DataCollection_;
import no.atferdssenteret.panda.model.ModelRootFactory;
import no.atferdssenteret.panda.model.Questionnaire;
import no.atferdssenteret.panda.model.QuestionnaireTypes;
import no.atferdssenteret.panda.model.Questionnaire_;

public class QuestionnaireFilter implements FilterCreator {
	private final Root<Questionnaire> root = new ModelRootFactory().root(Questionnaire.class);
	private final Path<String> dcPath = root.join(Questionnaire_.dataCollection).get(DataCollection_.type);
	
	@Override
	public List<Filter> createFilters() {
		List<Filter> filters = new LinkedList<Filter>();
		filters.add(createDataCollectionFilter());
		filters.add(createTypeFilter());
		filters.add(createStatusFilter());
		return filters;
	}
	

	private Filter createDataCollectionFilter() {
		List<FilterUnit> filterUnits = new LinkedList<FilterUnit>();
		filterUnits.add(includeAllFilterUnit);
		for (String value : DataCollectionTypes.values()) {
			filterUnits.add(new FilterUnit(value, criteriaBuilder.equal(dcPath, value)));
		}
		return new Filter("Datainnsamling", filterUnits);
	}

	private Filter createTypeFilter() {
		List<FilterUnit> filterUnits = new LinkedList<FilterUnit>();
		filterUnits.add(includeAllFilterUnit);
		for (String value : QuestionnaireTypes.values()) {
			filterUnits.add(new FilterUnit(value, criteriaBuilder.equal(root.get(Questionnaire_.name), value)));
		}
		return new Filter("Spørreskjema", filterUnits);
	}

	private Filter createStatusFilter() {
		List<FilterUnit> filterUnits = new LinkedList<FilterUnit>();
		filterUnits.add(includeAllFilterUnit);
		for (Questionnaire.Statuses status : Questionnaire.Statuses.values()) {
			filterUnits.add(new FilterUnit(status.toString(), criteriaBuilder.equal(root.get(Questionnaire_.status), status)));
		}
		return new Filter("Status", filterUnits);
	}
}
