package no.atferdssenteret.panda.controller.table;

import java.awt.Component;
import java.awt.event.ActionListener;

public interface StandardController extends ActionListener {
	public abstract Component view();
	public abstract String title();
}
