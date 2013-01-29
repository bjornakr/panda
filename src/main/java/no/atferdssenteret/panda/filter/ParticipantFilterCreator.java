package no.atferdssenteret.panda.filter;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import no.atferdssenteret.panda.model.Participant;
import no.atferdssenteret.panda.model.ParticipantRoles;
import no.atferdssenteret.panda.model.Participant_;
import no.atferdssenteret.panda.model.ParticipationStatuses;
import no.atferdssenteret.panda.model.Target;

public class ParticipantFilterCreator implements FilterCreator {
	private final CriteriaQuery<Participant> criteriaQuery = criteriaBuilder.createQuery(Participant.class);
	private final Root<Participant> root = criteriaQuery.from(Participant.class);
	private Target target;

	public ParticipantFilterCreator() {
	}

	/**
	 * This constructor will filter out all cases except the specified target.
	 * 
	 * @param target The target
	 */
	public ParticipantFilterCreator(Target target) {
		this.target = target;
	}

	@Override
	public List<Filter> createFilters() {
		List<Filter> filters = new LinkedList<Filter>();
		filters.add(createRoleFilter());
		filters.add(createStatusFilter());
		return filters;
	}

	private Filter createRoleFilter() {
		List<FilterUnit> filterUnits = new LinkedList<FilterUnit>();
		filterUnits.add(defaultFilterUnit());
		for (String role : ParticipantRoles.values()) {
			filterUnits.add(new FilterUnit(role, criteriaBuilder.equal(root.get(Participant_.role), role)));
		}
		return new Filter("Rolle", filterUnits);
	}

	private Filter createStatusFilter() {
		List<FilterUnit> filterUnits = new LinkedList<FilterUnit>();
		filterUnits.add(defaultFilterUnit());
		for (ParticipationStatuses status : ParticipationStatuses.values()) {
			filterUnits.add(new FilterUnit(status.toString(), criteriaBuilder.equal(root.get(Participant_.status), status)));
		}
		return new Filter("Status", filterUnits);
	}

	private FilterUnit defaultFilterUnit() {
		if (target == null) {
			return includeAllFilterUnit;
		}
		else {
			return new FilterUnit("Alle", criteriaBuilder.equal(root.get(Participant_.target), target));
		}

	}

}
