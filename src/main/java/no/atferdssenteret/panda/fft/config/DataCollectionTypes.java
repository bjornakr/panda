package no.atferdssenteret.panda.fft.config;

import java.util.LinkedList;

public class DataCollectionTypes extends LinkedList<String> {
	private static final long serialVersionUID = 1L;
	private static DataCollectionTypes self = null;
	
	private DataCollectionTypes() {
	}
	
	public static DataCollectionTypes getInstance() {
		if (self == null) {
			self = new DataCollectionTypes();
		}
		return self;
	}
}
