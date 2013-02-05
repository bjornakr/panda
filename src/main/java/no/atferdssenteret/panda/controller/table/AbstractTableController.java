package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.ModelRootFactory;
import no.atferdssenteret.panda.model.TargetBelonging;
import no.atferdssenteret.panda.model.table.TableObserver;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;
import no.atferdssenteret.panda.view.DefaultTablePanel;
import no.atferdssenteret.panda.view.ErrorMessageDialog;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public abstract class AbstractTableController implements ListSelectionListener, MouseListener, ActionListener {
	private String title;
	private List<TableObserver> tableObservers;
	private List<JButton> buttons;
	private List<JButton> restrictedButtons = new LinkedList<JButton>();

	public AbstractTableController(String title) {
		this.title = title;
		tableObservers  = new LinkedList<TableObserver>();
		buttons = ButtonUtil.createCRUDButtons(this);
		System.err.println("0");
	}

	public abstract DefaultTablePanel view();

	public abstract DefaultAbstractTableModel tableModel();

	public String title() {
		return title;
	}

	protected abstract String getWarningBeforeDelete();

	public boolean isModelInitialized() {
		if (tableModel() == null) {
			return false;
		}
		return (tableModel().getRowCount() > 0);
	}

	public void updateTableModel() {
		view().setWaitingState();
		Predicate[] predicates = new Predicate[view().selectedFilterPredicates().size()];
		view().selectedFilterPredicates().toArray(predicates);
		tableModel().setModels(retrieve(predicates));
		setButtonEnabledStates();
		view().endWaitingState();
		view().revalidate();
	}

	protected List<? extends Model> retrieve(Predicate[] predicates) {
		System.err.println("Predicates.size(): " + predicates.length);
		CriteriaBuilder criteriaBuilder = JPATransactor.getInstance().entityManager().getCriteriaBuilder();
		CriteriaQuery<? extends Model> criteriaQuery = criteriaBuilder.createQuery(getModelClass());
		//		Root<DataCollection> root = criteriaQuery.from(DataCollection.class);
		criteriaQuery.where(predicates);
		if (orderAttribute() != null) {
			Root<? extends Model> root = new ModelRootFactory().root(getModelClass());
			criteriaQuery.orderBy(criteriaBuilder.asc(root.get(orderAttribute())));
		}
		return JPATransactor.getInstance().entityManager().createQuery(criteriaQuery).getResultList();
	}

//		protected SingularAttribute<Model, ?> orderAttribute() {
//			return null;
//		}

	protected String orderAttribute() {
		return null;
	}

	protected abstract Class<? extends Model> getModelClass();

	public abstract void evaluateActionEvent(ActionEvent event);

	public void addTableObserver(TableObserver o) {
		tableObservers.add(o);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			if (event.getSource() instanceof JComboBox) {
				updateTableModel();
			}

			if (event.getActionCommand().equals(ButtonUtil.COMMAND_DELETE)) {
				JPATransactor.getInstance().transaction().begin();
				JPATransactor.getInstance().entityManager().remove(modelForSelectedTableRow());
				JPATransactor.getInstance().transaction().commit();
				tableModel().deleteRow(modelForSelectedTableRow());
			}
			evaluateActionEvent(event);
		}
		catch (Exception e) {
			e.printStackTrace();
			new ErrorMessageDialog(e.getMessage(), null, view().getWindow());
		}
	}

	public List<JButton> buttons() {
		buttons.removeAll(restrictedButtons);
		return buttons;
	}

	public void restrictAccessToButton(String actionCommand) {
		for (JButton button : buttons) {
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
	public void valueChanged(ListSelectionEvent event) {
		setButtonEnabledStates();
		view().updateTableCounters();
		if (modelForSelectedTableRow() instanceof TargetBelonging) {
			notifyTableObservers(TableObserver.TableAction.TARGET_ID_SELECTED,
					((TargetBelonging)modelForSelectedTableRow()).getTargetId());
		}
		// find selected target id (if it is in table)
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
