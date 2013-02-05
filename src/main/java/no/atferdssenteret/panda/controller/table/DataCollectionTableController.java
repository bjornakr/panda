package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.swing.JButton;

import no.atferdssenteret.panda.controller.DataCollectionController;
import no.atferdssenteret.panda.filter.DataCollectionFilterCreator;
import no.atferdssenteret.panda.model.DataCollection;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Target;
import no.atferdssenteret.panda.model.table.DataCollectionTable;
import no.atferdssenteret.panda.model.table.DataCollectionTableForTargetFocus;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;
import no.atferdssenteret.panda.view.DefaultTablePanel;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public class DataCollectionTableController extends AbstractTableController {
	private DefaultTablePanel view;
	private DefaultAbstractTableModel tableModel;
	private Target target;

	public DataCollectionTableController(Target target) {
		super("Datainnsamlinger");
		this.target = target;
		if (target == null) {
			tableModel = new DataCollectionTable();
		}
		else {
			tableModel = new DataCollectionTableForTargetFocus();			
		}
		view = new DefaultTablePanel(this, new DataCollectionFilterCreator(target));
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
		if (target != null) {
			return new LinkedList<DataCollection>(target.getDataCollections());
		}
		else {
			return super.retrieve(predicates);
		}
	}
//	public List<DataCollection> allModels() {
//		//	List<DataCollection> models = new LinkedList<DataCollection>();
//		TypedQuery<DataCollection> query = JPATransactor.getInstance().entityManager().createQuery("SELECT dc FROM DataCollection dc", DataCollection.class);
//		return query.getResultList(); //List<DataCollection>
//		//	for (Model model : tableModel.allModels()) {
//		//	    models.add((DataCollection)model);
//		//	}
//		//	return models;
//	}
//	
//	public List<DataCollection> modelsForTarget(long targetId) {
//		TypedQuery<DataCollection> query = JPATransactor.getInstance().entityManager().createQuery("SELECT dc FROM DataCollection dc WHERE dc.target.id = " + targetId, DataCollection.class);
//		return query.getResultList();
//	}
//	

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
			new DataCollectionController(view.getWindow(), null, target);
//			if (dataCollectionController.model() != null) {
//				//		target.addDataCollection((DataCollection)dataCollectionController.model());
//				tableModel.addRow(dataCollectionController.model());
//			}
			updateTableModel();
		}
		else if (event.getActionCommand().equals(ButtonUtil.COMMAND_EDIT)
				|| event.getActionCommand().equals(ButtonUtil.COMMAND_DOUBLE_CLICK)) {
			new DataCollectionController(view.getWindow(), (DataCollection)modelForSelectedTableRow(), target);
			updateTableModel();
		}
	}

	@Override
	protected Class<? extends Model> getModelClass() {
		return DataCollection.class;
	}
	
//	@Override
//	protected SingularAttribute<? extends Model, ?> orderAttribute() {
//		return DataCollection_.targetDate;
//	}
	
	@Override
	protected String orderAttribute() {
		return "targetDate";
	}
}
