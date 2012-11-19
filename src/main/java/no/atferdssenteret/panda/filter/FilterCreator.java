package no.atferdssenteret.panda.filter;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;

import no.atferdssenteret.panda.util.JPATransactor;


public interface FilterCreator {
	static final CriteriaBuilder criteriaBuilder = JPATransactor.getInstance().entityManager().getCriteriaBuilder();
	static final FilterUnit includeAllFilterUnit = new FilterUnit("Alle", criteriaBuilder.conjunction());
	
    public List<Filter> createFilters();
}
