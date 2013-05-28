package no.atferdssenteret.panda.model.entity;

import java.sql.Date;
import java.sql.Timestamp;
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

import no.atferdssenteret.panda.InvalidUserInputException;
import no.atferdssenteret.panda.QuestionnairesForDataCollectionType;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.TargetBelonging;
import no.atferdssenteret.panda.model.TimeStamper;
import no.atferdssenteret.panda.util.DateUtil;
import no.atferdssenteret.panda.util.StandardMessages;

@Entity
public class DataCollection implements Model, TargetBelonging, Comparable<DataCollection> {
	public enum Statuses {
		PLANNED("Ubehandlet"),
		FORTHCOMING("Aktuell"),
		DELAYED("Forsinket"),
		CONCLUDED("Avklart");

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
		APPOINTED("Avtalt"),
		INITIATED("Påbegynt"),
		COMPLETED("Utført"),
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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)    
	private long id;
	@JoinColumn(nullable = false)
	private Target target;
	@Column(nullable = false)
	private String type;
	@Column(nullable = false)
	private Date targetDate;
	@Column(nullable = false)
	private ProgressStatuses progressStatus;
	private Date progressDate;
	@Column(nullable = false)
	private Timestamp created;
	@Column(nullable = false)
	private Timestamp updated;
	@Column(nullable = false)
	private String createdBy;
	@Column(nullable = false)
	private String updatedBy;

	@OneToMany(mappedBy="dataCollection", cascade = {CascadeType.ALL}, orphanRemoval = true)
	@OrderBy("name ASC")
	private List<Questionnaire> questionnaires = new LinkedList<Questionnaire>();
	@ManyToOne(cascade = {CascadeType.DETACH})
	private User dataCollector;

	@PrePersist
	protected void onCreate() {
		TimeStamper.onCreate(this);
	}

	@PreUpdate
	protected void onUpdate() {
		TimeStamper.onUpdate(this);
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

	@Override
	public Timestamp getCreated() {
		return created;
	}

	@Override
	public void setCreated(Timestamp created) {
		this.created = created;
	}

	@Override
	public Timestamp getUpdated() {
		return updated;
	}

	@Override
	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}

	public void setDefaultQuestionnaires() {
		setQuestionnaires(QuestionnairesForDataCollectionType.getInstance().getQuestionnairesFor(type));
	}

	public Statuses status() {
		if (progressStatus == ProgressStatuses.GIVEN_UP
				|| progressStatus == ProgressStatuses.COMPLETED) {
			return Statuses.CONCLUDED;

		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.today());
		calendar.add(Calendar.DAY_OF_MONTH, -7);
		if (calendar.getTime().after(targetDate)) {
			return Statuses.DELAYED;
		}

		calendar.setTime(DateUtil.today());
		calendar.add(Calendar.DAY_OF_WEEK, 30);
		if (calendar.getTime().after(targetDate)) {
			return Statuses.FORTHCOMING;
		}
		return Statuses.PLANNED;
	}

	@Override
	public void validateUserInput() {
		if (target == null) {
			throw new InvalidUserInputException(StandardMessages.missingField("Target"));
		}
		else if (type == null) {
			throw new InvalidUserInputException(StandardMessages.missingField("Type"));
		}
		else if (targetDate == null) {
			throw new InvalidUserInputException(StandardMessages.missingField("Måldato"));
		}
		else if (progressStatus == null) {
			throw new InvalidUserInputException(StandardMessages.missingField("Framdrift"));
		}
		else if (progressStatus == ProgressStatuses.COMPLETED && !allQuesitonnairesHaveEvents()) {
			throw new InvalidUserInputException("Kan ikke sette datainnsamlingen til \"" + 
					ProgressStatuses.COMPLETED + "\" før hvert spørreskjema har minst én hendelse.");
		}
		else if ((progressStatus == ProgressStatuses.APPOINTED || progressStatus == ProgressStatuses.COMPLETED)
				&& progressDate == null) {
			throw new InvalidUserInputException(StandardMessages.missingField("Dato"));
		}
	}

	private boolean allQuesitonnairesHaveEvents() {
		for (Questionnaire questionnaire : questionnaires) {
			if (!questionnaire.hasEvents()) {
				return false;
			}
		}
		return true;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String getCreatedBy() {
		return createdBy;
	}

	@Override
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

	@Override
	public String toString() {
		return "Datainnsamling: " + type;
	}

	@Override
	public String referenceName() {
		return toString();
	}

	public boolean isUntouched() {
//		return (progressStatus == ProgressStatuses.NOT_INITIATED
//				&& !hasQuestionnairesWithQuestionnaireEvents());
//				&& dataCollector == target.getDataCollector());
		System.out.println("created == null: " + (created == null));
		if (created != null) {
			System.out.println("created.equals(updated): " + (created.equals(updated)));
		}
		return (created == null || created.equals(updated));
	}

//	private boolean hasQuestionnairesWithQuestionnaireEvents() {
//		for (Questionnaire questionnaire : questionnaires) {
//			if (questionnaire.getQuestionnaireEvents().size() > 0) {
//				return true;
//			}
//		}
//		return false;
//	}
}
