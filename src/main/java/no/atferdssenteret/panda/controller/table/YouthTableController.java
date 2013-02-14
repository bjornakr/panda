package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import no.atferdssenteret.panda.controller.MainController;
import no.atferdssenteret.panda.controller.YouthController;
import no.atferdssenteret.panda.filter.YouthFilterCreator;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.entity.User;
import no.atferdssenteret.panda.model.fft.Youth;
import no.atferdssenteret.panda.model.fft.Youth_;
import no.atferdssenteret.panda.model.table.YouthTable;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;
import no.atferdssenteret.panda.view.DefaultTablePanel;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public class YouthTableController extends AbstractTableController {
	private MainController mainController;
	private DefaultTablePanel view;
	private YouthTable tableModel = new YouthTable();

	public YouthTableController(MainController mainController) {
		super("Ungdommer");
		this.mainController = mainController;
		if (!MainController.session.user().hasAccessToRestrictedFields()) {
			super.restrictAccessToButton(ButtonUtil.COMMAND_CREATE);
			super.restrictAccessToButton(ButtonUtil.COMMAND_DELETE);
		}
		if (MainController.session.user().getAccessLevel() != User.AccessLevel.SUPER_USER) {
			super.restrictAccessToButton(ButtonUtil.COMMAND_DELETE);
		}
		view = new DefaultTablePanel(this, new YouthFilterCreator());
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
	public void evaluateActionEvent(ActionEvent event) {
		if (event.getActionCommand().equals(ButtonUtil.COMMAND_CREATE)) {
			YouthController youthController = new YouthController(view.getWindow(), null);
			if (youthController.model() != null) {
				updateTableModel();
			}
		}
		else if (event.getActionCommand().equals(ButtonUtil.COMMAND_EDIT)) {
			new YouthController(view.getWindow(), (Youth)modelForSelectedTableRow());
			updateTableModel();
		}
		else if (event.getActionCommand().equals(ButtonUtil.COMMAND_DOUBLE_CLICK)) {
			mainController.addYouthTab((Youth)modelForSelectedTableRow());
		}
	}

	protected List<? extends Model> retrieve(List<Object> filterValues) {
		CriteriaQuery<? extends Model> criteriaQuery = JPATransactor.getInstance().criteriaQuery(Youth.class);
		Root<Youth> root = criteriaQuery.from(Youth.class);
		criteriaQuery.where(extractPredicatesFromFilterValues(filterValues, root));
		criteriaQuery.orderBy(JPATransactor.getInstance().criteriaBuilder().asc(root.get(Youth_.id)));
		return JPATransactor.getInstance().entityManager().createQuery(criteriaQuery).getResultList();
	}
	
	private Predicate[] extractPredicatesFromFilterValues(List<Object> filterValues, Root<Youth> root) {
		Predicate[] predicates = new Predicate[filterValues.size()];
		for (int i = 0; i < filterValues.size(); i++) {
			predicates[i] = YouthFilterCreator.createPredicate(filterValues.get(i), root);
		}
		return predicates;
	}
	
	
}
