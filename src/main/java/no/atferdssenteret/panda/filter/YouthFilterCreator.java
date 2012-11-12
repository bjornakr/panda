package no.atferdssenteret.panda.filter;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import no.atferdssenteret.panda.model.Target;
import no.atferdssenteret.panda.model.fft.Youth;
import no.atferdssenteret.panda.model.fft.Youth_;
import no.atferdssenteret.panda.util.JPATransactor;

public class YouthFilterCreator implements FilterCreator {
	private final CriteriaBuilder criteriaBuilder = JPATransactor.getInstance().entityManager().getCriteriaBuilder();
	private final CriteriaQuery<Youth> criteriaQuery = criteriaBuilder.createQuery(Youth.class);
	private final Root<Youth> root = criteriaQuery.from(Youth.class);
	
	@Override
	public List<Filter> createFilters() {
		List<Filter> filters = new LinkedList<Filter>();
		filters.add(createStatusFilter());
		filters.add(createTreatmentGroupFilter());
		filters.add(createRegionFilter());
		filters.add(createGenderFilter());
		return filters;
	}

	private Filter createStatusFilter() {
		List<FilterUnit> filterUnits = new LinkedList<FilterUnit>();
		filterUnits.add(includeAllFilterUnit());
		for (Target.Statuses status : Target.Statuses.values()) {
			filterUnits.add(new FilterUnit(status.toString(), criteriaBuilder.equal(root.get(Youth_.status), status)));
		}
		return new Filter("Status", filterUnits);
	}
	
	private Filter createTreatmentGroupFilter() {
		List<FilterUnit> filterUnits = new LinkedList<FilterUnit>();
		filterUnits.add(includeAllFilterUnit());
		for (Youth.TreatmentGroups treatmentsGroup : Youth.TreatmentGroups.values()) {
			filterUnits.add(new FilterUnit(treatmentsGroup.toString(), criteriaBuilder.equal(root.get(Youth_.treatmentGroup), treatmentsGroup)));
		}
		return new Filter("Gruppe", filterUnits);
	}

	private Filter createRegionFilter() {
		List<FilterUnit> filterUnits = new LinkedList<FilterUnit>();
		filterUnits.add(includeAllFilterUnit());
		for (Youth.Regions item : Youth.Regions.values()) {
			filterUnits.add(new FilterUnit(item.toString(), criteriaBuilder.equal(root.get(Youth_.region), item)));
		}
		return new Filter("Region", filterUnits);
	}
	
	
	private Filter createGenderFilter() {
		List<FilterUnit> filterUnits = new LinkedList<FilterUnit>();
		filterUnits.add(includeAllFilterUnit());
		for (Youth.Genders item : Youth.Genders.values()) {
			filterUnits.add(new FilterUnit(item.toString(), criteriaBuilder.equal(root.get(Youth_.gender), item)));
		}
		return new Filter("Kjønn", filterUnits);
	}
	
	private FilterUnit includeAllFilterUnit() {
		return new FilterUnit("Alle", criteriaBuilder.conjunction());
	}
}
