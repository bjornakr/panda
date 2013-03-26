package no.atferdssenteret.panda.view;

import no.atferdssenteret.panda.model.entity.User;

public interface TargetDialog {
	public String getFirstName();
	public String getLastName();
	public Object getStatus();
	public Object getDataCollector();
	public String getTreatmentStart();
	public String getComment();
	public void setId(String id);
	public void setFirstName(String firstName);
	public void setLastName(String lastName);
	public void setStatus(Object status);
	public void setDataCollector(User dataCollector);
	public void setTreatmentStart(String treatmentStart);
	public void setComment(String comment);
}
