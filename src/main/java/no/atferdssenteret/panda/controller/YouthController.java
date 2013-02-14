package no.atferdssenteret.panda.controller;

import java.awt.Window;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.fft.Youth;
import no.atferdssenteret.panda.view.YouthDialog;

public class YouthController extends TargetController {
	private Youth model;
	private YouthDialog view;

	public YouthController(Window parentWindow, Youth model) {
		super(model);
		this.model = model;
		this.view = new YouthDialog(parentWindow, this);
		if (getMode() == Mode.EDIT) {
			transferModelToView();
		}
		else if (getMode() == Mode.CREATE) {
			view.setFirstName("Ingatorre");
			view.setLastName("Andolini");
		}
		if (!MainController.session.user().hasAccessToRestrictedFields()) {
			view.restrictAccess();
		}
		view.setVisible(true);
	}    


	@Override
	public String title() {
		return "Opprett/endre ungdom";
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
		super.transferModelToView();
		view.setGender(model.getGender());
		view.setRegion(model.getRegion());
		view.setTreatmentGroup(model.getTreatmentGroup());
	}

	@Override
	protected void transferUserInputToModel() {
		if (getMode() == Mode.CREATE) {
			model = new Youth();
		}
		super.transferUserInputToModel();
		model.setGender((Youth.Genders)view.getGender());
		model.setRegion((String)view.getRegion());
		model.setTreatmentGroup((Youth.TreatmentGroups)view.getTreatmentGroup());
	}

	@Override
	protected void setModel(Model model) {
		this.model = (Youth)model;
	}
}
