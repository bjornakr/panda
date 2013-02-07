package no.atferdssenteret.panda.filter;

import javax.persistence.criteria.CriteriaBuilder;

import no.atferdssenteret.panda.util.JPATransactor;


public interface FilterCreator {
	static final CriteriaBuilder criteriaBuilder = JPATransactor.getInstance().criteriaBuilder();
    public Filter[] createFilters();
}
