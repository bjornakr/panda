package no.atferdssenteret.panda.model.fft;

import javax.persistence.Entity;

import no.atferdssenteret.panda.model.Target;

@Entity
public class Youth extends Target {
    public enum Genders {MALE, FEMALE}
    public enum TreatmentGroups {INTERVENTION, CONTROL, NOT_YET_RANDOMIZED}
    public enum Regions {TRONDHEIM, SANDVIKA, STAVANGER, SKIEN}

    private Genders gender;
    private TreatmentGroups treatmentGroup;
    private Regions region;

    public Regions getRegion() {
	return region;
    }
    
    public void setRegion(Regions region) {
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
}
