package no.atferdssenteret.panda.model;

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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class DataCollection implements Model {
    public enum ProgressStatuses {NOT_INITIATED, TRYING_TO_CONTACT, APPOINTED, PERFORMED, GIVEN_UP};
    public enum QuestionnaireStatuses {NOT_INITIATED, IN_PROGRESS, COMPLETED, COMPLETED_WITH_MISSING};
//    public enum Types {T1, T2, T3};
//    public static final Collection<String> types = new HashSet<String>();
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private long id;
    private Target target;
    private String type;
    private Date targetDate;
    private ProgressStatuses progressStatus;
    private Date progressDate;
    private Date created;
    private Date updated;
    
    @OneToMany(mappedBy="dataCollection", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Questionnaire> questionnaires = new LinkedList<Questionnaire>();
    @ManyToOne
    private DataCollector dataCollector;
    
    
    @PrePersist
    protected void onCreate() {
	setCreated(new Date(System.currentTimeMillis()));
    }

    @PreUpdate
    protected void onUpdate() {
	setUpdated(new Date(System.currentTimeMillis()));
    }
    
    
    public DataCollection() {
	progressStatus = ProgressStatuses.NOT_INITIATED;
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

    public ProgressStatuses getProgressStatus() {
	return progressStatus;
    }

    public void setProgressStatus(ProgressStatuses progressStatus) {
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
	for (Questionnaire questionnaire : questionnaires) {
	    questionnaire.setDataCollection(this);
	}
	this.questionnaires = questionnaires;
    }

    public DataCollector getDataCollector() {
	return dataCollector;
    }

    public void setDataCollector(DataCollector dataCollector) {
	this.dataCollector = dataCollector;
    }

    public Date getProgressDate() {
	return progressDate;
    }

    public void setProgressDate(Date progressDate) {
	this.progressDate = progressDate;
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
}
