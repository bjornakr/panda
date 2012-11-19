package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.persistence.criteria.Predicate;

import no.atferdssenteret.panda.controller.ParticipantController;
import no.atferdssenteret.panda.filter.ParticipantFilterCreator;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Participant;
import no.atferdssenteret.panda.model.Target;
import no.atferdssenteret.panda.model.table.ParticipantTable;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;
import no.atferdssenteret.panda.view.DefaultTablePanel;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public class ParticipantTableController extends AbstractTableController {
	private DefaultTablePanel view;
	private ParticipantTable tableModel = new ParticipantTable();
	private Target target;

	public ParticipantTableController(Target target) {
		super("Deltakere");
		this.target = target;
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
	protected List<? extends Model> retrieve(Predicate[] predicates) {
		System.err.println("RETREIVE");
		if (target != null) {
			return target.getParticipants();
		}
		else {
			return super.retrieve(predicates);
		}
	}    
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

	@Override
	protected Class<? extends Model> getModelClass() {
		return Participant.class;
	}
}
