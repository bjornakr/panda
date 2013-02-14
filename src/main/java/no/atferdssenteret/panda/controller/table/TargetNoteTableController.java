package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;
import java.util.List;

import no.atferdssenteret.panda.controller.TargetNoteController;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.entity.Target;
import no.atferdssenteret.panda.model.entity.TargetNote;
import no.atferdssenteret.panda.model.table.TargetNoteTable;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;
import no.atferdssenteret.panda.view.DefaultTablePanel;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public class TargetNoteTableController extends AbstractTableController {
	private Target target;
	private DefaultTablePanel view;
	private DefaultAbstractTableModel tableModel;
	
	public TargetNoteTableController(Target target) {
		super("Notater");
		this.target = target;
		tableModel = new TargetNoteTable();
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
	protected List<? extends Model> retrieve(List<Object> filterValues) {
		return target.getTargetNotes();
	}

	@Override
	public void evaluateActionEvent(ActionEvent event) {
		if (event.getActionCommand().equals(ButtonUtil.COMMAND_CREATE)) {
			new TargetNoteController(view.getWindow(), null, target);
			JPATransactor.getInstance().entityManager().refresh(target); // To reactivate ordering (@OrderBy)
			updateTableModel();
		}
		else if (event.getActionCommand().equals(ButtonUtil.COMMAND_EDIT)
				|| event.getActionCommand().equals(ButtonUtil.COMMAND_DOUBLE_CLICK)) {
			new TargetNoteController(view.getWindow(), (TargetNote)modelForSelectedTableRow(), target);
			updateTableModel();
		}
		else if (event.getActionCommand().equals(ButtonUtil.COMMAND_DELETE)) {
			JPATransactor.getInstance().entityManager().refresh(target);
			updateTableModel();
		}
	}
}
