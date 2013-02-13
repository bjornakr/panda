package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import no.atferdssenteret.panda.controller.UserController;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.entity.User;
import no.atferdssenteret.panda.model.entity.User_;
import no.atferdssenteret.panda.model.table.UserTable;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;
import no.atferdssenteret.panda.view.DefaultTablePanel;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public class UserTableController extends AbstractTableController {
	private DefaultTablePanel view = new DefaultTablePanel(this, null);
	private UserTable tableModel = new UserTable();
	
	public UserTableController() {
		super("Brukere");
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

	@Override
	public void evaluateActionEvent(ActionEvent event) {
		if (event.getActionCommand().equals(ButtonUtil.COMMAND_CREATE)) {
			UserController userController = new UserController(view.getWindow(), null);
			if (userController.model() != null) {
//				tableModel.addRow(youthController.model());
				updateTableModel();
			}
		}
		else if (event.getActionCommand().equals(ButtonUtil.COMMAND_EDIT)
				|| event.getActionCommand().equals(ButtonUtil.COMMAND_DOUBLE_CLICK)) {
			new UserController(view.getWindow(), (User)modelForSelectedTableRow());
			updateTableModel();
		}
	}
	
	protected List<? extends Model> retrieve(List<Object> filterValues) {
		CriteriaBuilder criteriaBuilder = JPATransactor.getInstance().criteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get(User_.userName)));
		return JPATransactor.getInstance().entityManager().createQuery(criteriaQuery).getResultList();
	}
}
