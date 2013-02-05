package no.atferdssenteret.panda.controller;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.sql.Date;

import no.atferdssenteret.panda.QuestionnairesForDataCollectionType;
import no.atferdssenteret.panda.controller.table.QuestionnaireTableController;
import no.atferdssenteret.panda.model.DataCollection;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Questionnaire;
import no.atferdssenteret.panda.model.Target;
import no.atferdssenteret.panda.model.User;
import no.atferdssenteret.panda.util.StringUtil;
import no.atferdssenteret.panda.view.DataCollectionDialog;

public class DataCollectionController extends ApplicationController {
	private DataCollection model;
	private DataCollectionDialog view;
	private QuestionnaireTableController questionnaireTableController;
	private Target target;

	public DataCollectionController(Window parentWindow, DataCollection model, Target target) {
		super(model);
		if (model != null) {
			this.model = model;
		}
		else {
			this.model = new DataCollection();
		}
		this.target = target;
		questionnaireTableController = new QuestionnaireTableController(this.model);
		view = new DataCollectionDialog(parentWindow, this, questionnaireTableController.view());
		view.initializeTypeComboBox();
		if (getMode() == Mode.EDIT) {
			transferModelToView();
		}
		else if (getMode() == Mode.CREATE) {
			view.setTargetDate(new Date(System.currentTimeMillis()));
		}
		if (!MainController.session.user().hasAccessToRestrictedFields()) {
			view.restrictAccess();
		}
		
		view.setVisible(true);
	}

	@Override
	public String title() {
		return "Opprett/endre datainnsamling";
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
		view.setType(model.getType());
		view.setTargetDate(model.getTargetDate());
		view.setProgressStatus(model.getProgressStatus());
		view.setProgressDate(model.getProgressDate());
		view.setDataCollector(model.getDataCollector());
		questionnaireTableController.tableModel().setModels(model.getQuestionnaires());
	}

	@Override
	protected void transferUserInputToModel() {
		if (getMode() == Mode.CREATE) {
			model.setTarget(target);
			target.addDataCollection(model);
		}
		model.setType((String)view.getType());	
		model.setTargetDate(StringUtil.parseDate(view.getTargetDate()));
		model.setProgressStatus((DataCollection.ProgressStatuses)view.getProgressStatus());
		model.setProgressDate(StringUtil.parseDate(view.getProgressDate()));
		model.setQuestionnaires(questionnaireTableController.currentModels());
		model.setDataCollector((User)view.getDataCollector());
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		super.actionPerformed(event);
		if (event.getActionCommand().equals("TYPE_COMBO_BOX") && getMode() == Mode.CREATE) {
			model.getQuestionnaires().clear();
			for (String questionnaireName : QuestionnairesForDataCollectionType.getInstance().getQuestionnaireNamesFor((String)view.getType())) {
				Questionnaire questionnaire = new Questionnaire();
				questionnaire.setName(questionnaireName);
				model.addQuestionnaire(questionnaire);
			}
			questionnaireTableController.tableModel().setModels(model.getQuestionnaires());
		}
	}
}
