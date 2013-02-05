package no.atferdssenteret.panda.model.fft;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import no.atferdssenteret.panda.model.Target;
import no.atferdssenteret.panda.util.StandardMessages;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Youth extends Target {
	public enum Genders {
		MALE("Gutt"),
		FEMALE("Jente");

		private String name;

		private Genders(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

	}

	public enum TreatmentGroups {
		INTERVENTION("Intervensjon"),
		CONTROL("Kontroll"),
		NOT_YET_RANDOMIZED("Ikke randomisert");

		private String name;

		private TreatmentGroups(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

	}

	public static String[] Regions = {"Trondheim", "Sandvika", "Stavanger", "Skien"};

	@Column(nullable = false)
	private Genders gender;
	@Column(nullable = false)
	private TreatmentGroups treatmentGroup;
	@Column(nullable = false)
	private String region;

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String toString() {
		return this.getClass().toString();
	}

	public TreatmentGroups getTreatmentGroup() {
		return treatmentGroup;
	}

	public void setTreatmentGroup(TreatmentGroups treatmentGroup) {
		this.treatmentGroup = treatmentGroup;
	}

	public Genders getGender() {
		return gender;
	}

	public void setGender(Genders gender) {
		this.gender = gender;
	}
	
	public void validate() {
		super.validate();
		if (gender == null) {
			throw new IllegalStateException(StandardMessages.missingField("Kjønn"));
		}
		else if (treatmentGroup == null) {
			throw new IllegalStateException(StandardMessages.missingField("Gruppe"));
		}
		else if (region == null) {
			throw new IllegalStateException(StandardMessages.missingField("Region"));
		}
	}
}
