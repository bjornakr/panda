package no.atferdssenteret.panda.model;

public class CustomCondition implements Condition {
    private String displayValue;
    private String SQLcondition;
    
    public CustomCondition(String displayValue, String SQLcondition) {
	this.displayValue = displayValue;
	this.SQLcondition =SQLcondition; 
    }
    
    @Override
    public String toString() {
	return displayValue;
    }
    
    @Override
    public String getSQLcondition() {
	return SQLcondition;
    }

}
