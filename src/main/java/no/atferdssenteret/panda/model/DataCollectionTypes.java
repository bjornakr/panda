package no.atferdssenteret.panda.model;

public enum DataCollectionTypes {
	T1("T1"), T2("T2"), T3("T3"), P1("P1"), P2("P2");

	private String name;

	private DataCollectionTypes(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public static DataCollectionTypes find(String type) {
		for (DataCollectionTypes enumType : values()) {
			if (enumType.toString().equals(type)) {
				return enumType;
			}
		}
		return null;
	}
}
