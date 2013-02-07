package no.atferdssenteret.panda.model;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import no.atferdssenteret.panda.QuestionnairesForDataCollectionType;
import no.atferdssenteret.panda.controller.MainController;
import no.atferdssenteret.panda.util.DateUtil;
import no.atferdssenteret.panda.util.StandardMessages;

@Entity
public class DataCollection implements Model, TargetBelonging, Comparable<DataCollection> {
	public enum Statuses {
		PLANNED("Ubehandlet"),
		CONCLUDED("Avklart"),
		FORTHCOMING("Aktuell"),
		DELAYED("Forsinket");

		private String name;

		Statuses(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

	public enum ProgressStatuses {
		NOT_INITIATED("Ubehandlet"),
		TRYING_TO_CONTACT("Prøver fortsatt"),
		APPOINTED("Avtalt"),
		PERFORMED("Utført"),
		GIVEN_UP("Gitt opp");

		private String name;

		private ProgressStatuses(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}	
	};
	//	public enum QuestionnaireStatuses {NOT_INITIATED, IN_PROGRESS, COMPLETED, COMPLETED_WITH_MISSING};
	//    public enum Types {T1, T2, T3};
	//    public static final Collection<String> types = new HashSet<String>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)    
	private long id;
	@JoinColumn(nullable = false)
	private Target target;
	@Column(nullable = false)
	private String type;
	@Column(nullable = false)
	@OrderBy("targetDate")
	private Date targetDate;
	@Column(nullable = false)
	private ProgressStatuses progressStatus;
	private Date progressDate;
	private Date created;
	private Date updated;
	private String createdBy;
	private String updatedBy;
	
	@OneToMany(mappedBy="dataCollection", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Questionnaire> questionnaires = new LinkedList<Questionnaire>();
	@ManyToOne(cascade = {CascadeType.DETACH})
	private User dataCollector;

	@PrePersist
	protected void onCreate() {
		created = new Date(System.currentTimeMillis());
		updatedBy = MainController.session.user().getUserName();
	}

	@PreUpdate
	protected void onUpdate() {
		updated = new Date(System.currentTimeMillis());
		updatedBy = MainController.session.user().getUserName();
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

	public User getDataCollector() {
		return dataCollector;
	}

	public void setDataCollector(User dataCollector) {
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

	public void setDefaultQuestionnaires() {
		setQuestionnaires(QuestionnairesForDataCollectionType.getInstance().getQuestionnairesFor(type));
	}

	public Statuses status() {
		if (progressStatus == ProgressStatuses.GIVEN_UP
				|| progressStatus == ProgressStatuses.PERFORMED) {
			return Statuses.CONCLUDED;
		
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.today());
		calendar.add(Calendar.DAY_OF_MONTH, -7);
		if (calendar.getTime().after(targetDate)) {
			return Statuses.DELAYED;
		}
		
		calendar.setTime(DateUtil.today());
		calendar.add(Calendar.MONTH, 1);
		if (calendar.getTime().after(targetDate)) {
			return Statuses.FORTHCOMING;
		}
		return Statuses.PLANNED;
	}
	
	public void validate() {
		if (target == null) {
			throw new IllegalStateException(StandardMessages.missingField("Target"));
		}
		else if (type == null) {
			throw new IllegalStateException(StandardMessages.missingField("Type"));
		}
		else if (targetDate == null) {
			throw new IllegalStateException(StandardMessages.missingField("Måldato"));
		}
		else if (progressStatus == null) {
			throw new IllegalStateException(StandardMessages.missingField("Framdrift"));
		}
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public long getTargetId() {
		return target.getId();
	}
	
	@Override
	public int compareTo(DataCollection other) {
		return targetDate.compareTo(other.getTargetDate());
		
	}

}
