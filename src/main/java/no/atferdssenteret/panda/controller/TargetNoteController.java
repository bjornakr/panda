package no.atferdssenteret.panda.controller;

import java.awt.Window;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.entity.Target;
import no.atferdssenteret.panda.model.entity.TargetNote;
import no.atferdssenteret.panda.util.StringUtil;
import no.atferdssenteret.panda.view.TargetNoteDialog;

public class TargetNoteController extends ApplicationController {
	private TargetNote model;
	private TargetNoteDialog view;
	private Target target;
	
	public TargetNoteController(Window parentWindow, TargetNote model, Target target) {
		super(model);
		this.model = model;
		this.target = target;
		view = new TargetNoteDialog(parentWindow, this);
		if (getMode() == Mode.EDIT) {
			transferModelToView();
		}
		view.setVisible(true);
	}

	@Override
	public String title() {
		return "Opprett/endre notat";
	}

	@Override
	public Model model() {
		return model;
	}

	@Override
	public Window view() {
		return view;
	}

	@Override
	public void transferModelToView() {
		view.setNote(model.getNote());
	}

	@Override
	protected void transferUserInputToModel() {
		if (getMode() == Mode.CREATE) {
			model = new TargetNote();
			target.addTargetNote(model);
		}
		model.setNote(StringUtil.groomString(view.getNote()));
	}

	@Override
	protected void setModel(Model model) {
		this.model = (TargetNote)model;
	}

}
