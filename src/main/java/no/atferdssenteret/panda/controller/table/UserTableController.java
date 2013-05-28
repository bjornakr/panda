package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.JOptionPane;

import no.atferdssenteret.panda.controller.UserController;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.model.entity.DataCollection_;
import no.atferdssenteret.panda.model.entity.User;
import no.atferdssenteret.panda.model.entity.User_;
import no.atferdssenteret.panda.model.table.UserTable;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.util.StringUtil;
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
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get(User_.username)));
		return JPATransactor.getInstance().entityManager().createQuery(criteriaQuery).getResultList();
	}
	
	protected void processDeleteCommand() {
		final String delete = "Slett";
		final String cancel = "Avbryt";
		String[] options = {delete, cancel};

		int answer = JOptionPane.showOptionDialog(view(),
				StringUtil.splitString("Bekreft sletting av\n" + modelForSelectedTableRow().referenceName(), 80, 0),
				"Slett " + title(), JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null, options, options[1]);

		if (answer >= 0 && options[answer].equals(delete)) {
			okToDelete((User)modelForSelectedTableRow());
			deleteModelForSelectedTableRow();
		}
	}
	
	private void okToDelete(User user) {
		CriteriaBuilder criteriaBuilder = JPATransactor.getInstance().criteriaBuilder();
		CriteriaQuery<DataCollection> criteriaQuery = criteriaBuilder.createQuery(DataCollection.class);
		Root<DataCollection> root = criteriaQuery.from(DataCollection.class);
		criteriaQuery.where(criteriaBuilder.equal(root.get(DataCollection_.dataCollector), user));
		if (JPATransactor.getInstance().entityManager().createQuery(criteriaQuery).getResultList().size() > 0) {
			throw new RuntimeException("Kan ikke slette en datainnsamler med tilordnede datainnsamlinger.");
		}
		
	}
}
