package no.atferdssenteret.tibirbase;

import java.util.Arrays;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import no.atferdssenteret.panda.filter.Filter;
import no.atferdssenteret.panda.filter.FilterCreator;
import no.atferdssenteret.panda.model.ParticipationStatuses;
import no.atferdssenteret.panda.model.entity.Target_;
//import no.atferdssenteret.tibirbase.Youth_;

public class YouthFilterCreator implements FilterCreator {

	@Override
	public Filter[] createFilters() {
		Filter[] filters = new Filter[4];
		filters[0] = new Filter("Status", ParticipationStatuses.values());
		filters[1] = new Filter("Gruppe", Youth.TreatmentGroups.values());
		filters[2] = new Filter("Region", Youth.regions);
		filters[3] = new Filter("Kjønn", Youth.Genders.values());
		return filters;
	}
	
	public static Predicate createPredicate(Object value, Root<Youth> root) {
		if (value instanceof ParticipationStatuses) {
			return criteriaBuilder.equal(root.get(Target_.status), value);
		}
		else if (value instanceof Youth.TreatmentGroups) {
			return criteriaBuilder.equal(root.get(Youth_.treatmentGroup), value);
		}
		else if (Arrays.asList(Youth.regions).contains(value)) {
			return criteriaBuilder.equal(root.get(Youth_.region), value);
		}
		else if (value instanceof Youth.Genders) {
			return criteriaBuilder.equal(root.get(Youth_.gender), value);
		}
		else {
			return criteriaBuilder.conjunction();
		}
	}
}
