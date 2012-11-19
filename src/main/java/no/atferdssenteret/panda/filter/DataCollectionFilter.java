package no.atferdssenteret.panda.filter;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import no.atferdssenteret.panda.model.DataCollection;
import no.atferdssenteret.panda.model.DataCollectionTypes;
import no.atferdssenteret.panda.model.DataCollection_;

public class DataCollectionFilter implements FilterCreator {
	private final CriteriaQuery<DataCollection> criteriaQuery = criteriaBuilder.createQuery(DataCollection.class);
	private final Root<DataCollection> root = criteriaQuery.from(DataCollection.class);
	
	@Override
	public List<Filter> createFilters() {
		List<Filter> filters = new LinkedList<Filter>();
		filters.add(createStatusFilter());
		filters.add(createTypeFiter());
		filters.add(createProgressStatusFilter());
		return filters;
	}

	private Filter createStatusFilter() {
		List<FilterUnit> filterUnits = new LinkedList<FilterUnit>();
		filterUnits.add(includeAllFilterUnit);
//		for (String role : ParticipantRoles.values()) {
//			filterUnits.add(new FilterUnit(role, criteriaBuilder.equal(root.get(Participant_.role), role)));
//		}
		return new Filter("Status", filterUnits);
	}

	private Filter createTypeFiter() {
		List<FilterUnit> filterUnits = new LinkedList<FilterUnit>();
		filterUnits.add(includeAllFilterUnit);
		for (String type : DataCollectionTypes.values()) {
			filterUnits.add(new FilterUnit(type, criteriaBuilder.equal(root.get(DataCollection_.type), type)));
		}
		return new Filter("Type", filterUnits);
	}
	
	private Filter createProgressStatusFilter() {
		List<FilterUnit> filterUnits = new LinkedList<FilterUnit>();
		filterUnits.add(includeAllFilterUnit);
		for (DataCollection.ProgressStatuses progressStatus : DataCollection.ProgressStatuses.values()) {
			filterUnits.add(new FilterUnit(progressStatus.toString(), criteriaBuilder.equal(root.get(DataCollection_.progressStatus), progressStatus)));
		}
		return new Filter("Framdrift", filterUnits);
	}
	
}
