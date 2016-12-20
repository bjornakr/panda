package no.atferdssenteret.panda.model.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.TimeStamper;
import no.atferdssenteret.panda.util.StandardMessages;

@Entity
public class QuestionnaireEvent implements Model {    
	public enum Types {
		FILLED_OUT_WITH_DATACOLLECTOR_PRESENT("Fylt ut med datainnsamler tilstede"),
		TELEPHONE_INTERVIEW("Telefonintervju"),
		E_MAILED_TO_RESPONDENT("Sendt per e-post til respondent"),
		MAILED_TO_RESPONDENT("Sendt per post til respondent"),
		SENT_FOR_PROCESSING("Sendt per post til databehandler"),
		SENT_REMINDER("Purret"),
		RECIEVED("Mottatt"),
		GIVEN_UP("Gitt opp"),
		LOST("Mistet"),
		NOT_APPLICABLE("Ikke aktuell"),
		OTHER("Annet");
		
		private String name;

		Types(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@JoinColumn(nullable=false)
	private Questionnaire questionnaire;
	@Column(nullable = false)
	private Date date;
	@Column(nullable = false)
	private Types type;
	@Column(length = 1024)
	private String comment;
	private Timestamp created;
	private Timestamp updated;
	private String createdBy;
	private String updatedBy;
	
	public QuestionnaireEvent() {	
		// Dummy constructor for JPA
	}

	public QuestionnaireEvent(Types type) {
		this.type = type;
	}
	
	@PrePersist
	protected void onCreate() {
		TimeStamper.onCreate(this);
	}

	@PreUpdate
	protected void onUpdate() {
		TimeStamper.onUpdate(this);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public boolean isConclusive() {
		return (type == Types.RECIEVED || type == Types.GIVEN_UP || type == Types.LOST);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public void validateUserInput() {
		if (date == null) {
			throw new IllegalStateException(StandardMessages.missingField("Dato"));
		}
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

	@Override
	public String getCreatedBy() {
		return createdBy;
	}

	@Override
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String getUpdatedBy() {
		return updatedBy;
	}

	@Override
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String toString() {
		return "Hendelse: " + type.toString();
	}
	
	@Override
	public String referenceName() {
		return toString();
	}
}
