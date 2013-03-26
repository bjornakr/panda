package no.atferdssenteret.panda.controller;

import java.awt.Window;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.ParticipationStatuses;
import no.atferdssenteret.panda.model.Session;
import no.atferdssenteret.panda.model.entity.Participant;
import no.atferdssenteret.panda.model.entity.Target;
import no.atferdssenteret.panda.util.StringUtil;
import no.atferdssenteret.panda.view.ParticipantDialog;

public class ParticipantController extends ApplicationController {
	private Participant model;
	private ParticipantDialog view;
	private Target target;

	public ParticipantController(Window parentWindow, Participant model, Target target) {
		super(model);
		this.model = model;
		this.target = target;
		this.view = new ParticipantDialog(parentWindow, this);
		if (getMode() == Mode.EDIT) {
			transferModelToView();
		}
		if (!Session.currentSession.user().hasAccessToRestrictedFields()) {
			view.restrictAccess();
		}
		view.setVisible(true);
	}    

	@Override
	public String title() {
		return "Opprett/endre deltaker";
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
		view.setRole(model.getRole());
		view.setStatus(model.getStatus());
		view.setPhoneNo(model.getPhoneNo());
		view.setEMail(model.getEMail());
		view.setAddress(model.getAddress());
		view.setContactInfo(model.getContactInfo());
		view.setComment(model.getComment());
	}

	@Override
	protected void transferUserInputToModel() {
		if (getMode() == Mode.CREATE) {
			model = new Participant();
			model.setTarget(target);
//			target.addParticipant(model);
		}
		model.setFirstName(StringUtil.groomString(view.getFirstName()));
		model.setLastName(StringUtil.groomString(view.getLastName()));
		model.setRole((String)view.getRole());
		model.setStatus((ParticipationStatuses)view.getStatus());
		model.setPhoneNo(StringUtil.groomString(view.getPhoneNo()));
		model.setEMail(StringUtil.groomString(view.getEMail()));
		model.setAddress(StringUtil.groomString(view.getAddress()));
		model.setContactInfo(StringUtil.groomString(view.getContactInfo()));
		model.setComment(StringUtil.groomString(view.getComment()));
	}
	
	@Override
	protected void setModel(Model model) {
		this.model = (Participant)model;
	}
	
//	@Override
//	public void actionPerformed(ActionEvent event) {
//		if (event.getActionCommand().equals(COMMAND_SAVE)) {
//			save();
//		}
//		else if (event.getActionCommand().equals(COMMAND_CANCEL)) {
//			view().dispose();
//		}
//	}
//
//	private void save() {
//		try {
//			if (getMode() == Mode.EDIT) {
//				setModel(JPATransactor.getInstance().mergeIfDetached(model()));
//			}
//			performTransaction();
//			view().dispose();
//		}
//		catch (InvalidUserInputException e) {
//			if (JPATransactor.getInstance().transaction().isActive()) {
//				JPATransactor.getInstance().transaction().rollback();
//			}
//			new ErrorMessageDialog(e.getMessage(), null, view());
//		}
//		catch (Exception e) {
//			if (JPATransactor.getInstance().transaction().isActive()) {
//				JPATransactor.getInstance().transaction().rollback();
//			}
//			e.printStackTrace();
//		}
//	}
//	
//	protected void performTransaction() {
//		JPATransactor.getInstance().transaction().begin();
//		transferUserInputToModel();
//		model().validateUserInput();
//		System.out.println(model());
//		if (getMode() == Mode.CREATE) {
////			target.addParticipant(model);
//			JPATransactor.getInstance().entityManager().persist(model());
//		}
//		JPATransactor.getInstance().transaction().commit();
//	}
}
