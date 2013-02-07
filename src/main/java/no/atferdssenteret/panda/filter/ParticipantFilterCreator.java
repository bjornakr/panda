package no.atferdssenteret.panda.filter;

import java.util.Arrays;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import no.atferdssenteret.panda.model.Participant;
import no.atferdssenteret.panda.model.ParticipantRoles;
import no.atferdssenteret.panda.model.Participant_;
import no.atferdssenteret.panda.model.ParticipationStatuses;

public class ParticipantFilterCreator implements FilterCreator {

	public Filter[] createFilters() {
		Filter[] filters = new Filter[2];
		filters[0] = new Filter("Rolle", ParticipantRoles.values());
		filters[1] = new Filter("Status", ParticipationStatuses.values());
		return filters;
	}


	public static Predicate createPredicate(Object value, Root<Participant> root) {
		if (Arrays.asList(ParticipantRoles.values()).contains(value)) {
			return criteriaBuilder.equal(root.get(Participant_.role), value);
		}
		else if (value instanceof ParticipationStatuses) {
			return criteriaBuilder.equal(root.get(Participant_.status), value);
		}
		else {
			return criteriaBuilder.conjunction();
		}
	}
}
