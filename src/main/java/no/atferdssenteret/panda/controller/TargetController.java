package no.atferdssenteret.panda.controller;

import no.atferdssenteret.panda.DataCollectionManager;
import no.atferdssenteret.panda.model.ParticipationStatuses;
import no.atferdssenteret.panda.model.TargetNoteFactory;
import no.atferdssenteret.panda.model.entity.Target;
import no.atferdssenteret.panda.model.entity.TargetNote;
import no.atferdssenteret.panda.model.entity.User;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.util.StringUtil;
import no.atferdssenteret.panda.view.TargetDialog;

public abstract class TargetController extends ApplicationController {
	public TargetController(Target model) {
		super(model);
	}

	@Override
	public void transferModelToView() {
		final TargetDialog view = (TargetDialog)view();
		final Target model = (Target)model();
		view.setId(model.formattedIdWithLetterAppendix());
		view.setFirstName(model.getFirstName());
		view.setLastName(model.getLastName());
		view.setStatus(model.getStatus());
		view.setDataCollector(model.getDataCollector());
		view.setComment(model.getComment());
	}

	@Override
	protected void transferUserInputToModel() {
		final TargetDialog view = (TargetDialog)view();
		final Target model = (Target)model();
		model.setFirstName(StringUtil.groomString(view.getFirstName()));
		model.setLastName(StringUtil.groomString(view.getLastName()));
		if (getMode() == Mode.EDIT && model.getStatus() != view.getStatus()) {
			TargetNote systemNote = TargetNoteFactory.createChangeNote("Status",
					model.getStatus().toString(), view.getStatus().toString());
			model.addTargetNote(systemNote);
		}
		model.setStatus((ParticipationStatuses)view.getStatus());
		model.setDataCollector((User)view.getDataCollector());
		model.setComment(StringUtil.groomString(view.getComment()));
	}
	
	@Override
	protected void performTransaction() {
		JPATransactor.getInstance().transaction().begin();
		transferUserInputToModel();
		model().validateUserInput();
		DataCollectionManager.getInstance().generateDataCollections((Target)model());
		if (getMode() == Mode.CREATE) {
			JPATransactor.getInstance().entityManager().persist(model());
		}
		JPATransactor.getInstance().transaction().commit();
	}
}
