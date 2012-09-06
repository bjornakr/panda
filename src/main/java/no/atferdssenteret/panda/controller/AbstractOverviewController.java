package no.atferdssenteret.panda.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Observable;
import no.atferdssenteret.panda.view.AbstractOverviewTableModel;
import no.atferdssenteret.panda.view.DefaultOverviewPanel;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public abstract class AbstractOverviewController implements ListSelectionListener, MouseListener, ActionListener, Observable {
//    private DefaultOverviewPanel view;
//    private AbstractOverviewTableModel tableModel;
//    private Model model;
//    private String title;

    private List<Observer> observers;
//    private List<Condition> conditionList;

//    private ObserverCommand childCommand;

    public AbstractOverviewController(String title) {
//	this.title = title;
	observers  = new LinkedList<Observer>();	
    }

//    @Override
    public abstract DefaultOverviewPanel view();

    public abstract AbstractOverviewTableModel tableModel();
//    @Override
//    public String getTitle() {
//	return title;
//    }

    protected abstract String getWarningBeforeDelete();
    
//    public void setObserverCommand(ObserverCommand childCommand) {
//	this.childCommand = childCommand;
//    }

//    @Override
//    public boolean isModelInitialized() {
//	if (tableModel == null) {
//	    return false;
//	}
//
//	return (tableModel.getRowCount() > 0);
//    }

//    @Override
    public void updateModel(List<? extends Model> data) {
	view().setWaitingState();
//	conditionList = view.getSelectedFilterConditions();
	tableModel().insertNewData(data);
	setButtonEnabledStates();
	view().endWaitingState();
	view().revalidate();
    }

    public abstract void evaluateActionEvent(ActionEvent event);

    @Override
    public void addObserver(Observer o) {
	observers.add(o);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
//	if (event.getSource() instanceof JComboBox) {
//	    updateModel();
//	}
//
//	try {
//	    if (event.getActionCommand().equals(ButtonUtil.COMMAND_DELETE)) {
//		String[] options = {"Slett", "Avbryt"};
//
//		int answer = JOptionPane.showOptionDialog(view,
//			StringParser.splitString(getWarningBeforeDelete(), 80, 0),
//			"Slett " + getTitle(), JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
//			null, options, options[1]);
//		
//		if (options[answer].equals("Avbryt")) {
//		    return;
//		}
//	    }

	    evaluateActionEvent(event);
//	}
//	catch (IllegalArgumentException e) {
//	    new ErrorMessageDialog(e.getMessage(), null, view.getWindow());
//	}
//	catch (IllegalStateException e) {
//	    new ErrorMessageDialog(e.getMessage(), null, view.getWindow());
//	}
//	catch (Exception e) {
//	    e.printStackTrace();
//	    new ErrorMessageDialog(e.getMessage(), e, view.getWindow());
//	}
    }    

    /**
     *  Some buttons should be enabled or disabled depending
     *  if there are at least one selected row in the table.
     */
    protected void setButtonEnabledStates() {
//	List<JButton> buttons = getButtons();
//	if (buttons == null) return;
	boolean hasSelection = view().getSelectedTableRow() >= 0;
	for (JButton button : view().buttons()) {
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
//	Model selectedModel = getModelForSelectedTableRow();
//	if (event.getSource() instanceof ListSelectionModel
//		&& selectedModel != null
//		&& selectedModel instanceof ChildIDCarrier) {
//	    ChildIDCarrier childIDCarrier = (ChildIDCarrier)getModelForSelectedTableRow();
//
//	    childCommand = new ObserverCommand(childIDCarrier.getChildID(), Command.DISPLAY_CHILD_ID);
//	    notifyObservers();
//	}
//
//
//	setButtonEnabledStates();
//	view.updateTableCounters();

    }

    public Model getModelForSelectedTableRow() {
	if (view().getSelectedTableRow() < 0) {
	    return null;
	}
	return (Model)tableModel().getMetadataAt(view().getSelectedTableRow());
    }


    @Override
    public void mouseClicked(MouseEvent event) {
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
