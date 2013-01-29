package no.atferdssenteret.panda.filter;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import no.atferdssenteret.panda.model.DataCollection;
import no.atferdssenteret.panda.model.DataCollectionTypes;
import no.atferdssenteret.panda.model.DataCollection_;
import no.atferdssenteret.panda.model.Target;
import no.atferdssenteret.panda.util.DateUtil;

public class DataCollectionFilterCreator implements FilterCreator {
	private final CriteriaQuery<DataCollection> criteriaQuery = criteriaBuilder.createQuery(DataCollection.class);
	private final Root<DataCollection> root = criteriaQuery.from(DataCollection.class);
	private Target target;
	
	public DataCollectionFilterCreator() {
	}

	/**
	 * This constructor will filter out all cases except the specified target.
	 * 
	 * @param target The target
	 */
	public DataCollectionFilterCreator(Target target) {
		this.target = target;
	}

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
		filterUnits.add(new FilterUnit("Aktuelle og forsinkede", createForthcomingAndDelayedPredicate()));
		filterUnits.add(new FilterUnit("Avklarte", createConcludedPredicate()));
		return new Filter("Status", filterUnits);
	}

	private Filter createTypeFiter() {
		List<FilterUnit> filterUnits = new LinkedList<FilterUnit>();
		filterUnits.add(defaultFilterUnit());
		for (String type : DataCollectionTypes.values()) {
			filterUnits.add(new FilterUnit(type, criteriaBuilder.equal(root.get(DataCollection_.type), type)));
		}
		return new Filter("Type", filterUnits);
	}
	
	private Filter createProgressStatusFilter() {
		List<FilterUnit> filterUnits = new LinkedList<FilterUnit>();
		filterUnits.add(defaultFilterUnit());
		for (DataCollection.ProgressStatuses progressStatus : DataCollection.ProgressStatuses.values()) {
			filterUnits.add(new FilterUnit(progressStatus.toString(), criteriaBuilder.equal(root.get(DataCollection_.progressStatus), progressStatus)));
		}
		return new Filter("Framdrift", filterUnits);
	}
	
	private Predicate createConcludedPredicate() {
		Predicate givenUpPredicate = criteriaBuilder.equal(root.get(DataCollection_.progressStatus), DataCollection.ProgressStatuses.GIVEN_UP);
		Predicate concludedPredicate = criteriaBuilder.equal(root.get(DataCollection_.progressStatus), DataCollection.ProgressStatuses.PERFORMED);
		return criteriaBuilder.or(givenUpPredicate, concludedPredicate);
	}
	
	private Predicate createForthcomingAndDelayedPredicate() {
		Predicate isWithinTimeFrame = criteriaBuilder.lessThanOrEqualTo(root.get(DataCollection_.targetDate),
				DateUtil.addTime(DateUtil.today(), Calendar.MONTH, 1));
		Predicate notConcluded = criteriaBuilder.not(createConcludedPredicate());
		return criteriaBuilder.and(isWithinTimeFrame, notConcluded);
	}
	
	private FilterUnit defaultFilterUnit() {
		if (target == null) {
			return includeAllFilterUnit;
		}
		else {
			return new FilterUnit("Alle", criteriaBuilder.equal(root.get(DataCollection_.target), target));
		}
	}
}
