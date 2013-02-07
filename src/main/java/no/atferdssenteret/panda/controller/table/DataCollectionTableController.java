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

import no.atferdssenteret.panda.controller.DataCollectionController;
import no.atferdssenteret.panda.filter.DataCollectionFilterCreator;
import no.atferdssenteret.panda.model.DataCollection;
import no.atferdssenteret.panda.model.DataCollection_;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Target;
import no.atferdssenteret.panda.model.Target_;
import no.atferdssenteret.panda.model.table.DataCollectionTable;
import no.atferdssenteret.panda.model.table.DataCollectionTableForTargetFocus;
import no.atferdssenteret.panda.util.JPATransactor;
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
		view = new DefaultTablePanel(this, new DataCollectionFilterCreator());
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
	protected List<? extends Model> retrieve(List<Object> filterUnits) {
		if (target != null) {
			return new LinkedList<DataCollection>(target.getDataCollections());
		}
		else {
			return retrieveForAllTargets(filterUnits);
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

	protected List<? extends Model> retrieveForAllTargets(List<Object> filterValues) {
		CriteriaBuilder criteriaBuilder = JPATransactor.getInstance().criteriaBuilder();
		CriteriaQuery<DataCollection> criteriaQuery = JPATransactor.getInstance().criteriaBuilder().createQuery(DataCollection.class);
		Root<DataCollection> root = criteriaQuery.from(DataCollection.class);
		criteriaQuery.where(extractPredicatesFromFilterValues(filterValues, root));
		Join<DataCollection, Target> joinDataCollectionTarget = root.join(DataCollection_.target);
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get(DataCollection_.targetDate)),
				criteriaBuilder.asc(joinDataCollectionTarget.get(Target_.id)),
				criteriaBuilder.asc(root.get(DataCollection_.type)));
		return JPATransactor.getInstance().entityManager().createQuery(criteriaQuery).getResultList();
	}
	
	private Predicate[] extractPredicatesFromFilterValues(List<Object> filterValues, Root<DataCollection> root) {
		Predicate[] predicates = new Predicate[filterValues.size()];
		for (int i = 0; i < filterValues.size(); i++) {
			predicates[i] = DataCollectionFilterCreator.createPredicate(filterValues.get(i), root);
		}
		return predicates;
	}
}
