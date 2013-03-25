package no.atferdssenteret.panda.model;

import java.sql.Timestamp;

public class TimeStamper {
	
	public static void onCreate(Model model) {
		model.setCreated(new Timestamp(System.currentTimeMillis()));
		model.setCreatedBy(Session.currentSession.user().getUsername());
		model.setUpdated(model.getCreated());
		model.setUpdatedBy(model.getCreatedBy());
	}
	
	public static void onUpdate(Model model) {
		model.setUpdated(new Timestamp(System.currentTimeMillis()));
		model.setUpdatedBy(Session.currentSession.user().getUsername());
	}
}
