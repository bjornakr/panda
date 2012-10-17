package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Participant;
import no.atferdssenteret.panda.model.table.ParticipantTable;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;
import no.atferdssenteret.panda.view.DefaultTablePanel;

public class ParticipantTableController extends AbstractTableController {
    private DefaultTablePanel view;
    private ParticipantTable tableModel = new ParticipantTable();
    
    public ParticipantTableController() {
	super("Deltakere");
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
    
    public List<Participant> allModels() {
	List<Participant> models = new LinkedList<Participant>();
	for (Model model : tableModel.allModels()) {
	    models.add((Participant)model);
	}
	return models;
    }
   
    @Override
    protected List<? extends Model> retrieveModelsForCurrentConditions() {
	return allModels();
    }
    
    @Override
    public void evaluateActionEvent(ActionEvent event) {
    }
}
