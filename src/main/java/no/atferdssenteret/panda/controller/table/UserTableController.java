package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;

import no.atferdssenteret.panda.controller.UserController;
import no.atferdssenteret.panda.model.User;
import no.atferdssenteret.panda.model.table.UserTable;
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
	protected Class<User> getModelClass() {
		return User.class;
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
}
