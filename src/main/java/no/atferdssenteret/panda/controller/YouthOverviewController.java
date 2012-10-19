package no.atferdssenteret.panda.controller;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import no.atferdssenteret.panda.controller.table.AbstractTableController;
import no.atferdssenteret.panda.controller.table.AbstractTabsAndTablesController;
import no.atferdssenteret.panda.controller.table.DataCollectionTableController;
import no.atferdssenteret.panda.controller.table.ParticipantTableController;
import no.atferdssenteret.panda.model.fft.Youth;
import no.atferdssenteret.panda.view.TabsAndTablesPanel;
import no.atferdssenteret.panda.view.YouthPanel;

public class YouthOverviewController extends AbstractTabsAndTablesController {
    private YouthPanel view;
    private Youth model;
    private List<AbstractTableController> tableControllers = new LinkedList<AbstractTableController>();
    
    public YouthOverviewController(Window parentWindow, Youth model) {
	this.model = model;
	
	createTableControllers();
	TabsAndTablesPanel tabsAndTablesPanel = new TabsAndTablesPanel(this, tableControllers);
	view = new YouthPanel(this, model, tabsAndTablesPanel);
    }
    
    @Override
    public Component view() {
	return view;
    }

    private List<AbstractTableController> createTableControllers() {
//	List<AbstractTableController> tableControllers = new LinkedList<AbstractTableController>();
	tableControllers.add(new DataCollectionTableController(model));
	tableControllers.add(new ParticipantTableController());
	return tableControllers;
    }

    @Override
    public String title() {
	return "U ID " + model.getId();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
    }

    @Override
    public List<AbstractTableController> tableControllers() {
	return tableControllers;
    }

}