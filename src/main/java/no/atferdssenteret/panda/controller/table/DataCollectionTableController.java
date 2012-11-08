package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;

import no.atferdssenteret.panda.controller.DataCollectionController;
import no.atferdssenteret.panda.model.DataCollection;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Target;
import no.atferdssenteret.panda.model.table.DataCollectionTable;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;
import no.atferdssenteret.panda.view.DefaultTablePanel;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public class DataCollectionTableController extends AbstractTableController {
	private DefaultTablePanel view;
	private DataCollectionTable tableModel = new DataCollectionTable();
	private Target target;

	public DataCollectionTableController(Target target) {
		super("Datainnsamlinger");
		this.target = target;
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

	public List<DataCollection> allModels() {
		//	List<DataCollection> models = new LinkedList<DataCollection>();
		TypedQuery<DataCollection> query = JPATransactor.getInstance().entityManager().createQuery("SELECT dc FROM DataCollection dc", DataCollection.class);
		return query.getResultList(); //List<DataCollection>
		//	for (Model model : tableModel.allModels()) {
		//	    models.add((DataCollection)model);
		//	}
		//	return models;
	}
	
	public List<DataCollection> modelsForTarget(long targetId) {
		TypedQuery<DataCollection> query = JPATransactor.getInstance().entityManager().createQuery("SELECT dc FROM DataCollection dc WHERE dc.target.id = " + targetId, DataCollection.class);
		return query.getResultList();
	}
	

	@Override
	protected List<? extends Model> retrieve(Predicate[] predicates) {
		if (target != null) {
			return modelsForTarget(target.getId());
		}
		else {
			return allModels();
		}
	}    


	@Override
	public void evaluateActionEvent(ActionEvent event) {
		if (event.getActionCommand().equals(ButtonUtil.COMMAND_CREATE)) {
			DataCollectionController dataCollectionController = new DataCollectionController(view.getWindow(), null, target);
			if (dataCollectionController.model() != null) {
				//		target.addDataCollection((DataCollection)dataCollectionController.model());
				tableModel.addRow(dataCollectionController.model());
			}
		}
		else if (event.getActionCommand().equals(ButtonUtil.COMMAND_EDIT)
				|| event.getActionCommand().equals(ButtonUtil.COMMAND_DOUBLE_CLICK)) {
			new DataCollectionController(view.getWindow(), (DataCollection)modelForSelectedTableRow(), target);
			updateTableModel();
		}
	}
}
