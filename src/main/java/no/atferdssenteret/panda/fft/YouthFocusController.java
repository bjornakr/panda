package no.atferdssenteret.panda.fft;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import no.atferdssenteret.panda.controller.table.AbstractTableController;
import no.atferdssenteret.panda.controller.table.AbstractTabsAndTablesController;
import no.atferdssenteret.panda.controller.table.DataCollectionTableController;
import no.atferdssenteret.panda.controller.table.ParticipantTableController;
import no.atferdssenteret.panda.controller.table.TargetNoteTableController;
import no.atferdssenteret.panda.view.TabsAndTablesPanel;

public class YouthFocusController extends AbstractTabsAndTablesController {
	private YouthFocusPanel view;
	private Youth model;
	private List<AbstractTableController> tableControllers = new LinkedList<AbstractTableController>();

	public YouthFocusController(Window parentWindow, Youth model) {
		this.model = model;

		createTableControllers();
		TabsAndTablesPanel tabsAndTablesPanel = new TabsAndTablesPanel(this, tableControllers);
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
		return model.formattedIdWithLetterAppendix();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	}

	@Override
	public List<AbstractTableController> tableControllers() {
		return tableControllers;
	}

}
