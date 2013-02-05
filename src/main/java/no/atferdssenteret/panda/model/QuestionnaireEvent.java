package no.atferdssenteret.panda.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import no.atferdssenteret.panda.controller.MainController;
import no.atferdssenteret.panda.util.StandardMessages;

@Entity
public class QuestionnaireEvent implements Model, Comparable<QuestionnaireEvent> {    
	public enum Types {
		FILLED_OUT_WITH_DATACOLLECTOR_PRESENT("Fylt ut med datainnsamler tilstede"),
		TELEPHONE_INTERVIEW("Telefonintervju"),
		MAILED_TO_RESPONDENT("Sendt per post til deltaker"),
		SENT_FOR_PROCESSING("Sendt per post til databehandler"),
		SENT_REMINDER("Purret"),
		RECIEVED("Mottatt"),
		GIVEN_UP("Gitt opp"),
		LOST("Mistet"),
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
	private String comment;
	private Date created;
	private Date updated;
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
		created = new Date(System.currentTimeMillis());
		updatedBy = MainController.session.user().getUserName();
	}

	@PreUpdate
	protected void onUpdate() {
		updated = new Date(System.currentTimeMillis());
		updatedBy = MainController.session.user().getUserName();
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
	
	public String toString() {
		return type.toString();
	}

	@Override
	public int compareTo(QuestionnaireEvent questionnaireEvent2) {
		return (date.after(questionnaireEvent2.getDate())) ? 0 : 1;
	}
	
	public void validate() {
		if (date == null) {
			throw new IllegalStateException(StandardMessages.missingField("Dato"));
		}
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}
