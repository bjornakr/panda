package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import no.atferdssenteret.panda.controller.QuestionnaireEventController;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.entity.QuestionnaireEvent;
import no.atferdssenteret.panda.model.entity.QuestionnaireEvent_;
import no.atferdssenteret.panda.model.table.QuestionnaireEventTable;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;
import no.atferdssenteret.panda.view.DefaultTablePanel;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public class QuestionnaireEventTableController extends AbstractTableController {
	public final static String CONTEXT = "QUESTIONNAIRE_EVENT";
	private QuestionnaireEventTable tableModel = new QuestionnaireEventTable();
	private DefaultTablePanel view;

	public QuestionnaireEventTableController() {
		super("Hendelser");
		view = new DefaultTablePanel(this, null);
		setButtonEnabledStates();
	}
	
	@Override protected String getContext() {
		return CONTEXT;
	}
	
	@Override
	public DefaultTablePanel view() {
		return view;
	}

	@Override
	public DefaultAbstractTableModel tableModel() {
		return tableModel;
	}

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
			tableModel().update(model);
		}
		setButtonEnabledStates();
	}

	protected List<? extends Model> retrieve(List<Object> filterValues) {
		CriteriaBuilder criteriaBuilder = JPATransactor.getInstance().criteriaBuilder();
		CriteriaQuery<QuestionnaireEvent> criteriaQuery = criteriaBuilder.createQuery(QuestionnaireEvent.class);
		Root<QuestionnaireEvent> root = criteriaQuery.from(QuestionnaireEvent.class);
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get(QuestionnaireEvent_.date)),
				criteriaBuilder.asc(root.get(QuestionnaireEvent_.id)));
		return JPATransactor.getInstance().entityManager().createQuery(criteriaQuery).getResultList();
	}
}
