package no.atferdssenteret.panda.controller;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.sql.Date;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.TimeStamper;
import no.atferdssenteret.panda.model.entity.QuestionnaireEvent;
import no.atferdssenteret.panda.model.validator.QuestionnaireEventValidator;
import no.atferdssenteret.panda.model.validator.UserInputValidator;
import no.atferdssenteret.panda.util.StringUtil;
import no.atferdssenteret.panda.view.ErrorMessageDialog;
import no.atferdssenteret.panda.view.QuestionnaireEventDialog;

public class QuestionnaireEventController extends ApplicationController {
	private QuestionnaireEvent model;
	private QuestionnaireEventDialog view;

	public QuestionnaireEventController(Window mainWindow, QuestionnaireEvent model) {
		super(model);
		this.model = model;
		view = new QuestionnaireEventDialog(mainWindow, this);

		if (getMode() == Mode.EDIT) {
			transferModelToView();
		} else
		if (getMode() == Mode.CREATE) {
			view.setDate(new Date(System.currentTimeMillis()));
		}

		view.setVisible(true);
	}

	@Override
	public String title() {
		return "Rediger hendelse";
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
		view.setDate(model.getDate());
		view.setType(model.getType());
		view.setComment(model.getComment());	
	}

	@Override
	protected void transferUserInputToModel() {
		if (getMode() == Mode.CREATE) {
			model = new QuestionnaireEvent();
		}
		TimeStamper.onUpdate(model);
		model.setDate(StringUtil.parseDate(view.getDate()));
		model.setType((QuestionnaireEvent.Types)view.getQuestionnaireEventType());
		model.setComment(StringUtil.groomString(view.getComment()));
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals(COMMAND_SAVE)) {
			try {
				getValidator().validateUserInput();
//				JPATransactor.getInstance().transaction().begin();
				transferUserInputToModel();
				model.validateUserInput();
//				JPATransactor.getInstance().transaction().commit();
				view.dispose();
			}
			catch (Exception e) {
				new ErrorMessageDialog(e.getMessage(), null, view());
			}
		}
		else if (event.getActionCommand().equals(COMMAND_CANCEL)) {
			view().dispose();
		}
	}
	
	@Override
	protected void setModel(Model model) {
		this.model = (QuestionnaireEvent)model;
	}

	@Override
	protected UserInputValidator getValidator() {
		return new QuestionnaireEventValidator(view);
	}
}
