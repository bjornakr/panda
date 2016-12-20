package no.atferdssenteret.panda.model;


public enum ParticipationStatuses {
	WAITING_FOR_CONSENT("Venter på samtykke"),
	NEVER_PARTICIPATED("Har aldri deltatt"),
	PARTICIPATING("Deltar"),
	DATA_COLLECTION_TERMINATED("Datainnsamling avsluttet"),
	CONSENT_WITHDRAWN("Trukket seg");

	private String name;

	private ParticipationStatuses(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
