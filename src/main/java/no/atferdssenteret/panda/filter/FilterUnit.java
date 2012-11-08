package no.atferdssenteret.panda.filter;

import javax.persistence.criteria.Predicate;

public class FilterUnit {
	private final String name;
	private final Predicate predicate;

	public FilterUnit(String name, Predicate predicate) {
		this.name = name;
		this.predicate = predicate;
	}

	public Predicate predicate() {
		return predicate;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
