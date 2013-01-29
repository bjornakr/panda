package no.atferdssenteret.panda.controller;

import java.awt.Window;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.ParticipationStatuses;
import no.atferdssenteret.panda.model.User;
import no.atferdssenteret.panda.model.fft.Youth;
import no.atferdssenteret.panda.util.DateUtil;
import no.atferdssenteret.panda.util.StringUtil;
import no.atferdssenteret.panda.view.YouthDialog;

public class YouthController extends ApplicationController {
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
		view.setFirstName(model.getFirstName());
		view.setLastName(model.getLastName());
		view.setGender(model.getGender());
		view.setRegion(model.getRegion());
		view.setStatus(model.getStatus());
		view.setTreatmentGroup(model.getTreatmentGroup());
		view.setDataCollector(model.getDataCollector());
		view.setTreatmentStart(DateUtil.formatDate(model.getTreatmentStart()));
		view.setComment(model.getComment());
	}

	@Override
	protected void transferUserInputToModel() {
		if (getMode() == Mode.CREATE) {
			model = new Youth();
		}
		model.setFirstName(StringUtil.groomString(view.getFirstName()));
		model.setLastName(StringUtil.groomString(view.getLastName()));
		model.setGender((Youth.Genders)view.getGender());
		model.setRegion((String)view.getRegion());
		model.setStatus((ParticipationStatuses)view.getStatus());
		model.setTreatmentGroup((Youth.TreatmentGroups)view.getTreatmentGroup());
		model.setDataCollector((User)view.getDataCollector());
		model.setTreatmentStart(StringUtil.parseDate(view.getTreatmentStart()));
		model.setComment(StringUtil.groomString(view.getComment()));
	}
}
