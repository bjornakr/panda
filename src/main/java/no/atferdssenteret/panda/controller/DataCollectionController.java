package no.atferdssenteret.panda.controller;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.sql.Date;

import no.atferdssenteret.panda.DataCollectionManager;
import no.atferdssenteret.panda.QuestionnairesForDataCollectionType;
import no.atferdssenteret.panda.controller.table.QuestionnaireTableController;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Session;
import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.model.entity.Questionnaire;
import no.atferdssenteret.panda.model.entity.Target;
import no.atferdssenteret.panda.model.entity.User;
import no.atferdssenteret.panda.util.DateUtil;
import no.atferdssenteret.panda.util.JPATransactor;
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
		view.initializeTypeComboBox();  // Must do this before its actionListener is added.
		if (getMode() == Mode.EDIT) {
			transferModelToView();
		}
		else if (getMode() == Mode.CREATE) {
			view.setTargetDate(new Date(System.currentTimeMillis()));
		}
		if (!Session.currentSession.user().hasAccessToRestrictedFields()) {
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
			model.setSystemGenerated(false);
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
		if (event.getActionCommand().equals(DataCollectionDialog.CBOX_TYPE) && getMode() == Mode.CREATE) {
			createQuestionnairesForSelectedDataCollectionType();
		}
		else if (event.getActionCommand().equals(DataCollectionDialog.CBOX_PROGRESS_DATE) && shouldAutoInsertDate()) {
			view.setProgressDate(DateUtil.today());
		}
	}


	private boolean shouldAutoInsertDate() {
		return StringUtil.groomString(view.getProgressDate()) == null
				&& (view.getProgressStatus() == DataCollection.ProgressStatuses.APPOINTED
				|| view.getProgressStatus() == DataCollection.ProgressStatuses.COMPLETED);
	}

	private void createQuestionnairesForSelectedDataCollectionType() {
		model.getQuestionnaires().clear();
		for (String questionnaireName : QuestionnairesForDataCollectionType.getInstance().getQuestionnaireNamesFor(view.getType().toString())) {
			Questionnaire questionnaire = new Questionnaire();
			questionnaire.setName(questionnaireName);
			model.addQuestionnaire(questionnaire);
		}
		questionnaireTableController.tableModel().setModels(model.getQuestionnaires());
	}

	@Override
	protected void setModel(Model model) {
		this.model = (DataCollection)model;
	}

	@Override
	protected void performTransaction() {
		JPATransactor.getInstance().transaction().begin();
		transferUserInputToModel();
		model().validate();
		if (getMode() == Mode.CREATE) {
			target.addDataCollection(model);
		}
		DataCollectionManager.getInstance().generateDataCollections(model.getTarget());
		
		JPATransactor.getInstance().transaction().commit();
	}
}
