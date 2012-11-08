package no.atferdssenteret.panda.filter;

import java.util.List;

public class Filter {
	private final String name;
	private final List<FilterUnit> filterUnits;
	private FilterUnit defaultFilterUnit;

	public Filter(String name, List<FilterUnit> filterUnits) {
		this.name = name;
		this.filterUnits = filterUnits;
//		this.options.add(0, new CustomCondition("Alle", "TRUE")); // Adding this for the "no-filter" option.
	}

	public Filter(String displayName, List<FilterUnit> options, FilterUnit defaultFilterUnit) {
		this(displayName, options);
		this.defaultFilterUnit = defaultFilterUnit;
	}

	public List<FilterUnit> filterUnits() {
		return filterUnits;
	}

	public String name() {
		return name;
	}

//	public void setDefaultCondition(FilterUnit defaultCondition) {	
//		this.defaultFilterUnit = defaultCondition;
//	}


	public FilterUnit defaultFilterUnit() {
		if (defaultFilterUnit != null) {
			return defaultFilterUnit;
		}
		return filterUnits.get(0);
	}
}
