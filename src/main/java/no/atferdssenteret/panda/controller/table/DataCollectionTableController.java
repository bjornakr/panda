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
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Session;
import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.model.entity.DataCollection_;
import no.atferdssenteret.panda.model.entity.Target;
import no.atferdssenteret.panda.model.entity.Target_;
import no.atferdssenteret.panda.model.entity.User;
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
	private List<JButton> buttons = new LinkedList<JButton>();

	public DataCollectionTableController(Target target) {
		super("Datainnsamlinger");
		this.target = target;
		buttons = createButtons();
		if (target == null) {
			tableModel = new DataCollectionTable();
		}
		else {
			tableModel = new DataCollectionTableForTargetFocus();			
		}
		if (Session.currentSession.user().getAccessLevel() != User.AccessLevel.SUPER_USER) {
			super.restrictAccessToButton(ButtonUtil.COMMAND_DELETE);
		}
		view = new DefaultTablePanel(this, new DataCollectionFilterCreator());
		if (Session.currentSession.user().getAccessLevel() == User.AccessLevel.DATA_COLLECTOR) {
			view().setSelectedFilter(DataCollectionFilterCreator.FILTER_NAME_DATA_COLLECTOR, Session.currentSession.user());
		}
		view().setSelectedFilter(DataCollectionFilterCreator.FILTER_NAME_STATUS, DataCollectionFilterCreator.Statuses.FORTHCOMING_AND_DELAYED);
	}

	@Override
	public DefaultTablePanel view() {
		return view;
	}

	@Override
	public DefaultAbstractTableModel tableModel() {
		return tableModel;
	}

	private List<JButton> createButtons() {
		List<JButton> buttons = new LinkedList<JButton>();
		buttons.add(ButtonUtil.deleteButton(this));
		buttons.add(ButtonUtil.editButton(this));
		return buttons;
	}
	
	@Override
	public List<JButton> buttons() {
		return buttons;
	}

	@Override
	public void evaluateActionEvent(ActionEvent event) {
		if (event.getActionCommand().equals(ButtonUtil.COMMAND_EDIT)
				|| event.getActionCommand().equals(ButtonUtil.COMMAND_DOUBLE_CLICK)) {
			new DataCollectionController(view.getWindow(), (DataCollection)modelForSelectedTableRow());
			updateTableModel();
		}
	}

	@Override
	protected List<? extends Model> retrieve(List<Object> filterValues) {
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
		int predicatesSize = filterValues.size() + (target == null ? 0 : 1);
		Predicate[] predicates = new Predicate[predicatesSize];
		for (int i = 0; i < filterValues.size(); i++) {
			predicates[i] = DataCollectionFilterCreator.createPredicate(filterValues.get(i), root);
		}
		if (target != null) {
			predicates[predicates.length-1] = JPATransactor.getInstance().criteriaBuilder().equal(root.get(DataCollection_.target), target);
		}
		return predicates;
	}
}
