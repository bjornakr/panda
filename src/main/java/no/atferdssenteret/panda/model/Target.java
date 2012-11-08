package no.atferdssenteret.panda.model;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import no.atferdssenteret.panda.DataCollectionManager;

@Entity
public class Target implements Model {
    public enum Statuses {
    	WAITING_FOR_CONSENT("Venter på samtykke"),
    	PARTICIPATING("Deltar");
    	
    	private String name;
    	
    	private Statuses(String name) {
    		this.name = name;
    	}
    	
    	@Override
    	public String toString() {
    		return name;
    	}
    };    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private Statuses status;
    private String firstName;
    private String lastName;
    private Date treatmentStart;
    private String comment;
    private Date created;
    private Date updated;

    @OneToMany(mappedBy = "target", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private final List<DataCollection> dataCollections = new LinkedList<DataCollection>();
    @OneToMany(mappedBy = "target", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private final List<Participant> participants = new LinkedList<Participant>();
    @ManyToOne
    private DataCollector dataCollector;

    @PrePersist
    protected void onCreate() {
	setCreated(new Date(System.currentTimeMillis()));
	DataCollectionManager.getInstance().notifyTargetCreated(this);
    }

    @PreUpdate
    protected void onUpdate() {
	setUpdated(new Date(System.currentTimeMillis()));
//	DataCollectionManager.getInstance().notifyTargetUpdated(this);
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public Statuses getStatus() {
	return status;
    }

    public void setStatus(Statuses status) {
	if (this.status != null && !this.status.equals(status)) {
	    DataCollectionManager.getInstance().notifyTargetUpdated(this);
	}
	this.status = status;	
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public Date getTreatmentStart() {
	return treatmentStart;
    }
    
    public void setTreatmentStart(Date treatmentStart) {
	if (this.treatmentStart != null && !this.treatmentStart.equals(treatmentStart)) {
	    DataCollectionManager.getInstance().notifyTargetUpdated(this);
	}
	this.treatmentStart = treatmentStart;
    }
    
    public void addDataCollection(final DataCollection dataCollection) {
	dataCollection.setTarget(this);
	dataCollections.add(dataCollection);
    }
    
    public List<DataCollection> getDataCollections() {
	return dataCollections;
    }

    public DataCollection getDataCollection(String dataCollectionType) {
	for (DataCollection dataCollection : dataCollections) {
	    if (dataCollectionType.equals(dataCollection.getType())) {
		return dataCollection;
	    }
	}
	return null;
    }

    public Date getCreated() {
	return created;
    }

    public void setCreated(Date created) {
	this.created = created;
    }

    public Date getUpdated() {
	return updated;
    }

    public void setUpdated(Date updated) {
	this.updated = updated;
    }

    @Override
    public String toString() {
	return Target.class.getName() + ": " + id + ", " + firstName + ", " + lastName + ", " +
		created + ", " + updated + ", dataCollections.size() = " + dataCollections.size();
    }

    public boolean isParticipating() {
	return (status == Statuses.PARTICIPATING);
    }

    public DataCollector getDataCollector() {
	return dataCollector;
    }

    public void setDataCollector(DataCollector dataCollector) {
	this.dataCollector = dataCollector;
	DataCollectionManager.getInstance().notifyTargetUpdated(this);
    }

    public List<Participant> getParticipants() {
	return participants;
    }

    public String getComment() {
	return comment;
    }

    public void setComment(String comment) {
	this.comment = comment;
    }
}
