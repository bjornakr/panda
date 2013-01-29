package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Observer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Observable;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;
import no.atferdssenteret.panda.view.DefaultTablePanel;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public abstract class AbstractTableController implements ListSelectionListener, MouseListener, ActionListener, Observable {
	private String title;
	private List<Observer> observers;
	private List<JButton> buttons;

	public AbstractTableController(String title) {
		this.title = title;
		observers  = new LinkedList<Observer>();	
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
		CriteriaBuilder criteriaBuilder = JPATransactor.getInstance().entityManager().getCriteriaBuilder();
		CriteriaQuery<? extends Model> criteriaQuery = criteriaBuilder.createQuery(getModelClass());
		criteriaQuery.where(predicates);
		return JPATransactor.getInstance().entityManager().createQuery(criteriaQuery).getResultList();
	}

	
	
	protected abstract Class<? extends Model> getModelClass();

	public abstract void evaluateActionEvent(ActionEvent event);

	@Override
	public void addObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
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
	
	public List<JButton> buttons() {
		if (buttons == null) {
			buttons = ButtonUtil.createCRUDButtons(this);
		}
		return buttons;
	}

	protected void setButtonEnabledStates() {
		boolean hasSelection = view().selectedTableRow() >= 0;
		for (JButton button : buttons()) {
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
			evaluateActionEvent(new ActionEvent(event.getSource(), event.getID(), ButtonUtil.COMMAND_DOUBLE_CLICK));
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

	@Override
	public void notifyObservers() {
		for (Observer o : observers) {
			o.update(null, null);
		}
	}
}
