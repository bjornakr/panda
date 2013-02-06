package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import no.atferdssenteret.panda.controller.QuestionnaireEventController;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Participant;
import no.atferdssenteret.panda.model.Participant_;
import no.atferdssenteret.panda.model.QuestionnaireEvent;
import no.atferdssenteret.panda.model.table.QuestionnaireEventTable;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;
import no.atferdssenteret.panda.view.DefaultTablePanel;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public class QuestionnaireEventTableController extends AbstractTableController {
	private QuestionnaireEventTable tableModel = new QuestionnaireEventTable();
	private DefaultTablePanel view;

	public QuestionnaireEventTableController() {
		super("Hendelser");
		view = new DefaultTablePanel(this, null);
	}

	@Override
	public DefaultTablePanel view() {
		return view;
	}

	@Override
	public DefaultAbstractTableModel tableModel() {
		return tableModel;
	}

	@Override
	protected String getWarningBeforeDelete() {
		return null;
	}

//	public List<QuestionnaireEvent> allModels() {
//		List<QuestionnaireEvent> models = new LinkedList<QuestionnaireEvent>();
//		for (Model model : tableModel.allModels()) {
//			models.add((QuestionnaireEvent)model);
//		}
//		return models;
//	}
//
//	@Override
//	protected List<? extends Model> retrieve(Predicate[] predicates) {
//		return allModels();
//	}

	public List<QuestionnaireEvent> currentModels() {
		List<QuestionnaireEvent> models = new LinkedList<QuestionnaireEvent>();
		for (Model model : tableModel.allModels()) {
			models.add((QuestionnaireEvent)model);
		}
		return models;
	}

	@Override
	public void evaluateActionEvent(ActionEvent event) {
		if (event.getActionCommand().equals(ButtonUtil.COMMAND_CREATE)) {
			QuestionnaireEventController questionnaireEventController = new QuestionnaireEventController(view.getWindow(), null);
			if (questionnaireEventController.model() != null) {
				tableModel.addRow(questionnaireEventController.model());
			}
		}
		else if (event.getActionCommand().equals(ButtonUtil.COMMAND_EDIT)
				|| event.getActionCommand().equals(ButtonUtil.COMMAND_DOUBLE_CLICK)) {
			QuestionnaireEvent model = (QuestionnaireEvent)modelForSelectedTableRow();
			new QuestionnaireEventController(view.getWindow(), model);
			tableModel.update(model);
		}
	}

//	@Override
//	protected Class<? extends Model> getModelClass() {
//		return QuestionnaireEvent.class;
//	}
	
	protected List<? extends Model> retrieve(Predicate[] predicates) {
		CriteriaBuilder criteriaBuilder = JPATransactor.getInstance().criteriaBuilder();
		CriteriaQuery<Participant> criteriaQuery = criteriaBuilder.createQuery(Participant.class);
		criteriaQuery.where(predicates);
		Root<Participant> root = criteriaQuery.from(Participant.class);
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Participant_.id)));
		return JPATransactor.getInstance().entityManager().createQuery(criteriaQuery).getResultList();
	}
}
