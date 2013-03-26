package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.TargetBelonging;
import no.atferdssenteret.panda.model.table.TableObserver;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.util.StringUtil;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;
import no.atferdssenteret.panda.view.DefaultTablePanel;
import no.atferdssenteret.panda.view.ErrorMessageDialog;
import no.atferdssenteret.panda.view.util.ButtonUtil;

/**
 * This is the basic controller for the table view.
 * Extend it to provide functionality for a specific model. 
 *
 */
public abstract class AbstractTableController implements StandardController, ListSelectionListener, MouseListener, ActionListener {
	private String title;
	private List<TableObserver> tableObservers;
	private List<JButton> buttons;
	private List<JButton> restrictedButtons = new LinkedList<JButton>();

	public AbstractTableController(String title) {
		this.title = title;
		tableObservers  = new LinkedList<TableObserver>();
		buttons = ButtonUtil.createCRUDButtons(this);
	}

	public String title() {
		return title;
	}

	public abstract DefaultTablePanel view();

	public abstract DefaultAbstractTableModel tableModel();


	public boolean isModelInitialized() {
		if (tableModel() == null) {
			return false;
		}
		return (tableModel().getRowCount() > 0);
	}

	public void updateTableModel() {
		view().setWaitingState();
		tableModel().setModels(retrieve(view().selectedFilterValues()));
		view().updateTableCounters();
		setButtonEnabledStates();
		view().endWaitingState();
		view().revalidate();
	}

	protected abstract List<? extends Model> retrieve(List<Object> filterValues);

	public abstract void evaluateActionEvent(ActionEvent event);

	public void addTableObserver(TableObserver o) {
		tableObservers.add(o);
	}

	public List<JButton> buttons() {
		buttons.removeAll(restrictedButtons);
		return buttons;
	}

	protected void restrictAccessToButton(String actionCommand) {
		for (JButton button : buttons()) {
			if (button.getActionCommand().equals(actionCommand)) {
				button.setEnabled(false);
				restrictedButtons.add(button);
			}
		}
	}

	protected void setButtonEnabledStates() {
		boolean hasSelection = view().selectedTableRow() >= 0;
		for (JButton button : buttons()) {
			if (restrictedButtons.contains(button)) {
				continue;
			}
			if (button.getActionCommand().equals(ButtonUtil.COMMAND_EDIT)) {
				button.setEnabled(hasSelection);
			}
			else if (button.getActionCommand().equals(ButtonUtil.COMMAND_DELETE)) {
				button.setEnabled(hasSelection);
			}
			else if (button.getActionCommand().equals("ASSIGN")) {
				button.setEnabled(hasSelection);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			if (event.getSource() instanceof JComboBox) {
				// A filter has been activated
				updateTableModel();
			}
			if (event.getActionCommand().equals(ButtonUtil.COMMAND_DELETE)) {
				processDeleteCommand();
			}
			evaluateActionEvent(event);
		}
		catch (Exception e) {
			e.printStackTrace();
			new ErrorMessageDialog(StringUtil.splitString(e.getMessage(), 80, 0), null, view().getWindow());
		}
	}

	protected void processDeleteCommand() {
		final String delete = "Slett";
		final String cancel = "Avbryt";
		String[] options = {delete, cancel};

		int answer = JOptionPane.showOptionDialog(view(),
				StringUtil.splitString("Bekreft sletting av\n" + modelForSelectedTableRow().referenceName(), 80, 0),
				"Slett " + title, JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null, options, options[1]);

		if (answer >= 0 && options[answer].equals(delete)) {
			deleteModelForSelectedTableRow();
		}
	}

	private void deleteModelForSelectedTableRow() {
		Model model = JPATransactor.getInstance().mergeIfDetached(modelForSelectedTableRow());
		try {
			JPATransactor.getInstance().transaction().begin();
			JPATransactor.getInstance().entityManager().remove(model);
			JPATransactor.getInstance().transaction().commit();
			tableModel().deleteRow(modelForSelectedTableRow());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		if (modelForSelectedTableRow() != null) {
			System.err.println("ATC: " + JPATransactor.getInstance().entityManager().contains(modelForSelectedTableRow()));
		}
		setButtonEnabledStates();
		view().updateTableCounters();
		if (modelForSelectedTableRow() instanceof TargetBelonging) {
			notifyTableObservers(TableObserver.TableAction.TARGET_ID_SELECTED,
					((TargetBelonging)modelForSelectedTableRow()).getTargetId());
		}
	}

	public Model modelForSelectedTableRow() {
		if (view().selectedTableRow() < 0) {
			return null;
		}
		return (Model)tableModel().metadataAt(view().selectedTableRow());
	}


	@Override
	public void mouseClicked(MouseEvent event) {
		if (event.getClickCount() >= 2) {
			actionPerformed(new ActionEvent(event.getSource(), event.getID(), ButtonUtil.COMMAND_DOUBLE_CLICK));
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	public void notifyTableObservers(TableObserver.TableAction tableAction, long targetId) {
		for (TableObserver o : tableObservers) {
			o.tableActionPerformed(tableAction, targetId);
		}
	}
}
