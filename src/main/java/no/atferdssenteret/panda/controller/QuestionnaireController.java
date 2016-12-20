package no.atferdssenteret.panda.controller;

import java.awt.Window;
import java.awt.event.ActionEvent;

import no.atferdssenteret.panda.controller.table.QuestionnaireEventTableController;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.entity.Questionnaire;
import no.atferdssenteret.panda.model.validator.QuestionnaireValidator;
import no.atferdssenteret.panda.model.validator.UserInputValidator;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.view.ErrorMessageDialog;
import no.atferdssenteret.panda.view.QuestionnaireDialog;

public class QuestionnaireController extends ApplicationController {
	private Questionnaire model;
	private QuestionnaireDialog view;
	private QuestionnaireEventTableController questionnaireEventTableController = new QuestionnaireEventTableController();
	private boolean saveWithDataCollection;

	public QuestionnaireController(Window parentWindow, Questionnaire model, boolean saveWithDataCollection) {
		super(model);
		this.model = model;
		view = new QuestionnaireDialog(parentWindow, this, questionnaireEventTableController.view());
		this.saveWithDataCollection = saveWithDataCollection;
		
		if (getMode() == Mode.EDIT) {
			transferModelToView();
			view.enableQuestionnaireComboBox(false);
		}

		view.setVisible(true);
	}

	@Override
	public String title() {
		return "Rediger spørreskjema";
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
		view.setQuestionnaireName(model.getName());
		view.setFormat(model.getFormat());
		questionnaireEventTableController.tableModel().setModels(model.getQuestionnaireEvents());
	}

	@Override
	protected void transferUserInputToModel() {
		if (getMode() == Mode.CREATE) {
			model = new Questionnaire();
		}
		model.setName((String)view.getQuestionnaireName());
		model.setFormat((Questionnaire.Formats)view.getFormat());
		model.setQuestionnaireEvents(questionnaireEventTableController.currentModels());
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals(COMMAND_SAVE)) {
			try {
				if (!saveWithDataCollection) {
					JPATransactor.getInstance().transaction().begin();
					transferUserInputToModel();
					JPATransactor.getInstance().transaction().commit();
				}
				else {
					transferUserInputToModel();
				}
				view.dispose();
			}
			catch (IllegalStateException e) {
				new ErrorMessageDialog(e.getMessage(), null, view());
			}
		}
		else if (event.getActionCommand().equals(COMMAND_CANCEL)) {
			view().dispose();
		}
	}
	
	@Override
	protected void setModel(Model model) {
		this.model = (Questionnaire)model;
	}

	@Override
	protected UserInputValidator getValidator() {
		return new QuestionnaireValidator();
	}
}
