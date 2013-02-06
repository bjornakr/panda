package no.atferdssenteret.panda.filter;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.util.JPATransactor;

public class FilterUnit<T extends Model> {
	private final String name;
	

	public FilterUnit(String name, SingularAttribute<T, ?> singularAttribute, Object value) {
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
	
	public Predicate predicate(Root<T> root, SingularAttribute<T, ? extends Object> sa) {
		return JPATransactor.getInstance().criteriaBuilder().equal(root.get(sa), 7);
	}
	
}
