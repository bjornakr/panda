package no.atferdssenteret.panda.filter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Filter {
	private final String name;
	private final Object[] filterValues;
	private final Object defaultValue;
	
	public Filter(String name, Object[] filterValues, Object defaultValue) {
		this.name = name;
		this.filterValues = valuesIncludingNoFilter(filterValues);
		this.defaultValue = defaultValue;
	}
	
	public Filter(String name, Object[] values) {
		this(name, values, null);
	}
	
	public String name() {
		return name;
	}
	
	public Object[] values() {
		return filterValues;
	}
	
	public Object defaultValue() {
		if (defaultValue == null) {
			return filterValues[0];
		}
		return defaultValue;
	}
	
	private Object[] valuesIncludingNoFilter(Object[] filterValues) {
		List<Object> valuesIncludingNoFilter = new LinkedList<Object>();
		valuesIncludingNoFilter.add("Alle");
		valuesIncludingNoFilter.addAll(Arrays.asList(filterValues));
		return valuesIncludingNoFilter.toArray();
	}
}
