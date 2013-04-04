package no.atferdssenteret.panda.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Version {
	@Id
	private double requiredVersion;
	
	public double getRequiredVersion() {
		return requiredVersion;
	}
}
