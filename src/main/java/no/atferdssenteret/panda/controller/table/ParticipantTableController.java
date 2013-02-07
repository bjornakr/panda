package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.JButton;

import no.atferdssenteret.panda.controller.ParticipantController;
import no.atferdssenteret.panda.filter.ParticipantFilterCreator;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Participant;
import no.atferdssenteret.panda.model.Participant_;
import no.atferdssenteret.panda.model.Target;
import no.atferdssenteret.panda.model.Target_;
import no.atferdssenteret.panda.model.table.ParticipantTable;
import no.atferdssenteret.panda.model.table.ParticipantTableForTargetFocus;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;
import no.atferdssenteret.panda.view.DefaultTablePanel;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public class ParticipantTableController extends AbstractTableController {
	private DefaultTablePanel view;
	private DefaultAbstractTableModel tableModel;
	private Target target;

	public ParticipantTableController(Target target) {
		super("Deltakere");
		this.target = target;
		if (target == null) {
			tableModel = new ParticipantTable();
		}
		else {
			tableModel = new ParticipantTableForTargetFocus();
		}
		view = new DefaultTablePanel(this, new ParticipantFilterCreator());
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

	@Override
	protected List<? extends Model> retrieve(List<Object> filterValues) {
		if (target != null) {
			return target.getParticipants();
		}
		else {
			return retrieveForAllTargets(filterValues);
		}
	}
	
	
//	@Override
//	protected List<? extends Model> retrieve(Predicate[] predicates) {
//		else {
//			return super.retrieve(predicates);
//		}
//	}    
//	public List<Participant> allModels() {
////		List<Participant> models = new LinkedList<Participant>();
////		for (Model model : tableModel.allModels()) {
////			models.add((Participant)model);
////		}
////		return models;
//		TypedQuery<Participant> query = JPATransactor.getInstance().entityManager().createQuery("SELECT p FROM Participant p", Participant.class);
//		return query.getResultList(); //List<DataCollection>
//
//	}
//
//	@Override
//	protected List<? extends Model> retrieve(Predicate[] predicates) {
//		return allModels();
//	}
	@Override
	public List<JButton> buttons() {
		if (target == null) {
			return new LinkedList<JButton>();
		}
		else {
			return super.buttons();
		}
	}

	@Override
	public void evaluateActionEvent(ActionEvent event) {
		if (event.getActionCommand().equals(ButtonUtil.COMMAND_CREATE)) {
			ParticipantController participantController = new ParticipantController(view.getWindow(), null, target);
			if (participantController.model() != null) {
				tableModel.addRow(participantController.model());
			}
		}
		else if (event.getActionCommand().equals(ButtonUtil.COMMAND_EDIT)
				|| event.getActionCommand().equals(ButtonUtil.COMMAND_DOUBLE_CLICK)) {
			new ParticipantController(view.getWindow(), (Participant)modelForSelectedTableRow(), target);
			updateTableModel();
		}
	}

//	@Override
//	protected Class<? extends Model> getModelClass() {
//		return Participant.class;
//	}
	
	protected List<? extends Model> retrieveForAllTargets(List<Object> filterValues) {
		CriteriaBuilder criteriaBuilder = JPATransactor.getInstance().criteriaBuilder();
		CriteriaQuery<Participant> criteriaQuery = criteriaBuilder.createQuery(Participant.class);
		Root<Participant> root = criteriaQuery.from(Participant.class);
		criteriaQuery.where(extractPredicatesFromFilterValues(filterValues, root));
		Join<Participant, Target> joinParticipantTarget = root.join(Participant_.target);
		criteriaQuery.orderBy(criteriaBuilder.asc(joinParticipantTarget.get(Target_.id)),
				criteriaBuilder.asc(root.get(Participant_.role)));
		return JPATransactor.getInstance().entityManager().createQuery(criteriaQuery).getResultList();
	}
	
	private Predicate[] extractPredicatesFromFilterValues(List<Object> filterValues, Root<Participant> root) {
		Predicate[] predicates = new Predicate[filterValues.size()];
		for (int i = 0; i < filterValues.size(); i++) {
			predicates[i] = ParticipantFilterCreator.createPredicate(filterValues.get(i), root);
		}
		return predicates;
	}
}
