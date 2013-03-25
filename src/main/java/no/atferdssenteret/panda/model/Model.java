package no.atferdssenteret.panda.model;

import java.sql.Timestamp;


public interface Model {
	public void validateUserInput();
	public String referenceName();
	
	public void setCreated(Timestamp timestamp);
	public Timestamp getCreated();
	
	public void setCreatedBy(String username);
	public String getCreatedBy();
	
	public void setUpdated(Timestamp updated);
	public Timestamp getUpdated();
	
	public void setUpdatedBy(String updatedBy);
	public String getUpdatedBy();
	
	
}
