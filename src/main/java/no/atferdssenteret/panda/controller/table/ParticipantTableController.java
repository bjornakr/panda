package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;

import no.atferdssenteret.panda.controller.ParticipantController;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Participant;
import no.atferdssenteret.panda.model.table.ParticipantTable;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;
import no.atferdssenteret.panda.view.DefaultTablePanel;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public class ParticipantTableController extends AbstractTableController {
	private DefaultTablePanel view;
	private ParticipantTable tableModel = new ParticipantTable();

	public ParticipantTableController() {
		super("Deltakere");
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

	public List<Participant> allModels() {
//		List<Participant> models = new LinkedList<Participant>();
//		for (Model model : tableModel.allModels()) {
//			models.add((Participant)model);
//		}
//		return models;
		TypedQuery<Participant> query = JPATransactor.getInstance().entityManager().createQuery("SELECT p FROM Participant p", Participant.class);
		return query.getResultList(); //List<DataCollection>

	}

	@Override
	protected List<? extends Model> retrieve(Predicate[] predicates) {
		return allModels();
	}

	@Override
	public void evaluateActionEvent(ActionEvent event) {
		if (event.getActionCommand().equals(ButtonUtil.COMMAND_CREATE)) {
			ParticipantController dataCollectionController = new ParticipantController(view.getWindow(), null);
			if (dataCollectionController.model() != null) {
				tableModel.addRow(dataCollectionController.model());
			}
		}
		else if (event.getActionCommand().equals(ButtonUtil.COMMAND_EDIT)
				|| event.getActionCommand().equals(ButtonUtil.COMMAND_DOUBLE_CLICK)) {
			new ParticipantController(view.getWindow(), (Participant)modelForSelectedTableRow());
			updateTableModel();
		}
	}
}
