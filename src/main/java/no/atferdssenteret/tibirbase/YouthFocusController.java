package no.atferdssenteret.tibirbase;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import no.atferdssenteret.panda.controller.table.AbstractTableController;
import no.atferdssenteret.panda.controller.table.DataCollectionTableController;
import no.atferdssenteret.panda.controller.table.ParticipantTableController;
import no.atferdssenteret.panda.controller.table.StandardController;
import no.atferdssenteret.panda.controller.table.TargetNoteTableController;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.view.TabsAndTablesPanel;

public class YouthFocusController implements StandardController {
	private YouthFocusPanel view;
	private Youth model;
	private List<AbstractTableController> tableControllers = new LinkedList<AbstractTableController>();

	public YouthFocusController(Window parentWindow, Youth model) {
		this.model = model;
		JPATransactor.getInstance().entityManager().refresh(model);
		createTableControllers();
		TabsAndTablesPanel tabsAndTablesPanel = new TabsAndTablesPanel(tableControllers);
		view = new YouthFocusPanel(this, model, tabsAndTablesPanel);
	}

	@Override
	public Component view() {
		return view;
	}

	private List<AbstractTableController> createTableControllers() {
		DataCollectionTableController dataCollectionTableController = new DataCollectionTableController(model);
		dataCollectionTableController.updateTableModel();
		tableControllers.add(dataCollectionTableController);
		tableControllers.add(new ParticipantTableController(model));
		tableControllers.add(new TargetNoteTableController(model));
		return tableControllers;
	}

	@Override
	public String title() {
		return model.formattedIdWithLetterAppendix(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	}

//	@Override
//	public List<AbstractTableController> tableControllers() {
//		return tableControllers;
//	}

}
