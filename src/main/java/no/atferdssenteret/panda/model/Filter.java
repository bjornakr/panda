package no.atferdssenteret.panda.model;

import java.util.List;

public class Filter {
    private final String displayName;
    private List<Condition> options;
    private Condition defaultCondition;
    
    public Filter(String displayName, List<Condition> options) {
	this.displayName = displayName;
	this.options = options;
	this.options.add(0, new CustomCondition("Alle", "TRUE")); // Adding this for the "no-filter" option.
    }

    public Filter(String displayName, List<Condition> options, Condition defaultCondition) {
	this(displayName, options);
	this.defaultCondition = defaultCondition;
    }
    

    public Condition[] getOptions() {
	Condition[] conditions = new Condition[options.size()];
	
	for (int i = 0; i < options.size(); i++) {
	    conditions[i] = options.get(i);
	}
		
	return conditions;
    }
    
    
    
    public String getDisplayName() {
        return displayName;
    }



    public void setDefaultCondition(Condition defaultCondition) {	
	this.defaultCondition = defaultCondition;
    }


    public Condition getDefaultCondition() {
	if (defaultCondition != null) {
	    return defaultCondition;
	}
	return options.get(0);
    }
}