package no.atferdssenteret.panda.controller;

import java.awt.event.ActionListener;

import no.atferdssenteret.panda.controller.table.AbstractTableController;

public interface AdditionalAction extends ActionListener {
	public String getName();
	public String getActionCommand();
	public void setController(AbstractTableController tableController);
}
