package no.atferdssenteret.tibirbase;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import no.atferdssenteret.panda.InvalidUserInputException;
import no.atferdssenteret.panda.model.entity.Target;
import no.atferdssenteret.panda.model.entity.User;
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
		NOT_YET_RANDOMIZED("Ikke randomisert"),
		INTERVENTION("Intervensjon"),
		CONTROL("Kontroll");

		private String name;

		private TreatmentGroups(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

	}

	public static String[] regions = {"Trondheim", "Sandvika", "Stavanger", "Skien"};

	@Column(nullable = false)
	private Genders gender;
	@Column(nullable = false)
	private TreatmentGroups treatmentGroup;
//	private Date treatmentStart;
	@Column(nullable = false)
	private String region;

	@ManyToOne(cascade = {CascadeType.DETACH})
	private User dataCollectorTelephone;
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public TreatmentGroups getTreatmentGroup() {
		return treatmentGroup;
	}

	public void setTreatmentGroup(TreatmentGroups treatmentGroup) {
		this.treatmentGroup = treatmentGroup;
	}

//	public Date getTreatmentStart() {
//		return treatmentStart;
//	}
//
//	public void setTreatmentStart(Date treatmentStart) {
//		this.treatmentStart = treatmentStart;
//	}

	public Genders getGender() {
		return gender;
	}

	public void setGender(Genders gender) {
		this.gender = gender;
	}
	
	public User getDataCollectorTelephone() {
		return dataCollectorTelephone;
	}

	public void setDataCollectorTelephone(User dataCollectorTelephone) {
		this.dataCollectorTelephone = dataCollectorTelephone;
	}
	
	@Override
	public void validateUserInput() {
		super.validateUserInput();
		if (gender == null) {
			throw new InvalidUserInputException(StandardMessages.missingField("Kjønn"));
		}
		else if (treatmentGroup == null) {
			throw new InvalidUserInputException(StandardMessages.missingField("Gruppe"));
		}
		else if (region == null) {
			throw new InvalidUserInputException(StandardMessages.missingField("Region"));
		}
	}
	
	@Override
	public String toString() {
		return formattedIdWithLetterAppendix() + " - " + getFirstName() + " " + getLastName();
	}
}
