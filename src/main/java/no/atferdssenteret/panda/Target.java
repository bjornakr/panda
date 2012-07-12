package no.atferdssenteret.panda;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class Target {
    public enum Statuses {WAITING_FOR_CONSENT, PARTICIPATING};
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Statuses status;
    private String firstName;
    private String lastName;
    private Date treatmentStart;
    private Date created;
    private Date updated;

    @OneToMany(mappedBy = "target", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private final List<DataCollection> dataCollections = new LinkedList<DataCollection>();
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
	this.status = status;
	DataCollectionManager.getInstance().notifyTargetUpdated(this);
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
	this.treatmentStart = treatmentStart;
	DataCollectionManager.getInstance().notifyTargetUpdated(this);
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
}
