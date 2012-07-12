package no.atferdssenteret.panda;

import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
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

@Entity
public class DataCollection {
    public enum ProgressStatus {NOT_INITIATED}; 
//    public enum Types {T1, T2, T3};
//    public static final Collection<String> types = new HashSet<String>();
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private long id;
    private Target target;
    private String type;
    private Date targetDate;
    private ProgressStatus progressStatus;
    
    @OneToMany(mappedBy="dataCollection", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Questionnaire> questionnaires = new LinkedList<Questionnaire>();
    @ManyToOne
    private DataCollector dataCollector;
    
    
    @PrePersist
    public void addQuestionnaires() {
	for (String questionnaireName : QuestionnairesForDataCollectionType.getInstance().getQuestionnairesFor(type)) {
	    Questionnaire questionnaire = new Questionnaire();
	    questionnaire.setName(questionnaireName);
	    addQuestionnaire(questionnaire);
	}
	System.out.println("DC: " + this + ", ID: " + id);
    }
    
    @PostPersist
    public void debug() {
	System.out.println("DC: " + this + ", ID: " + id);
    }
    
    public DataCollection() {
	progressStatus = ProgressStatus.NOT_INITIATED;
    }
    
    public long getId() {
	return id;
    }
    
    public void setId(long id) {
	this.id = id;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getType() {
	return type;
    }

    public Date getTargetDate() {
	return targetDate;
    }

    public void setTargetDate(Date targetDate) {
	this.targetDate = targetDate;
    }

    public Target getTarget() {
	return target;
    }

    public void setTarget(Target target) {
	this.target = target;
    }

    public ProgressStatus getProgressStatus() {
	return progressStatus;
    }

    public void setProgressStatus(ProgressStatus progressStatus) {
	this.progressStatus = progressStatus;
    }

    public List<Questionnaire> getQuestionnaires() {
	return questionnaires;
    }
    
    public Questionnaire getQuestionnaire(String name) {
	for (Questionnaire questionnaire : questionnaires) {
	    if (name.equals(questionnaire.getName())) {
		return questionnaire;
	    }
	}
	return null;
    }

    public void addQuestionnaire(Questionnaire questionnaire) {
	questionnaire.setDataCollection(this);
	questionnaires.add(questionnaire);
    }
    
    public void setQuestionnaires(List<Questionnaire> questionnaires) {
	this.questionnaires = questionnaires;
    }

    public DataCollector getDataCollector() {
	return dataCollector;
    }

    public void setDataCollector(DataCollector dataCollector) {
	this.dataCollector = dataCollector;
    }
}
