package no.atferdssenteret.panda;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class DataCollector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    
    @OneToMany(mappedBy="dataCollector")
    private List<Target> targets = new LinkedList<Target>();
    @OneToMany(mappedBy="dataCollector")
    private List<DataCollection> dataCollections = new LinkedList<DataCollection>();
    
    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getFirstName() {
	return firstName;
    }
    
    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public List<Target> getTargets() {
	return targets;
    }

    public void setTargets(List<Target> targets) {
	this.targets = targets;
    }

    public List<DataCollection> getDataCollections() {
	return dataCollections;
    }

    public void setDataCollections(List<DataCollection> dataCollections) {
	this.dataCollections = dataCollections;
    }
}
