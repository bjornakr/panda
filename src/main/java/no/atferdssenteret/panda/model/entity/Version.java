package no.atferdssenteret.panda.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Version {
	public final static double PANDA_VERSION = 1.2;
	
	@Id
	private double requiredVersion;
	
	public double getRequiredVersion() {
		return requiredVersion;
	}
	
	public double pv() {
		return PANDA_VERSION;
	}
}
