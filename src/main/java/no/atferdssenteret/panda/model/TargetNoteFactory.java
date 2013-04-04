package no.atferdssenteret.panda.model;

import no.atferdssenteret.panda.model.entity.TargetNote;

public class TargetNoteFactory {

	public static TargetNote createChangeNote(String field, String preValue, String postValue) {
		TargetNote note = new TargetNote();
		note.setNote("AUTOMATISK BESKJED: " + field + " endret (" + preValue + " -> " + postValue + ")");
		return note;
	}
}
