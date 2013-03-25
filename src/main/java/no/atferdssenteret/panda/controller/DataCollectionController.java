package no.atferdssenteret.panda.controller;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.sql.Timestamp;

import no.atferdssenteret.panda.DataCollectionManager;
import no.atferdssenteret.panda.controller.table.QuestionnaireTableController;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Session;
import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.model.entity.User;
import no.atferdssenteret.panda.util.DateUtil;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.util.StringUtil;
import no.atferdssenteret.panda.view.DataCollectionDialog;

public class DataCollectionController extends ApplicationController {
	private DataCollection model;
	private DataCollectionDialog view;
	private QuestionnaireTableController questionnaireTableController;

	public DataCollectionController(Window parentWindow, DataCollection model) {
		super(model);
		this.model = model;
		questionnaireTableController = new QuestionnaireTableController(this.model);
		view = new DataCollectionDialog(parentWindow, this, questionnaireTableController.view());
		transferModelToView();
		if (!Session.currentSession.user().hasAccessToRestrictedFields()) {
			view.restrictAccess();
		}
		view.setVisible(true);
	}

	@Override
	public String title() {
		return "Rediger datainnsamling";
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
		model.setTargetDate(StringUtil.parseDate(view.getTargetDate()));
		model.setProgressStatus((DataCollection.ProgressStatuses)view.getProgressStatus());
		model.setProgressDate(StringUtil.parseDate(view.getProgressDate()));
		model.setQuestionnaires(questionnaireTableController.currentModels());
		model.setDataCollector((User)view.getDataCollector());
		model.setUpdated(new Timestamp(System.currentTimeMillis())); // Must be set explicitly to prevent deletion by DataCollectionManager
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		super.actionPerformed(event);
		if (event.getActionCommand().equals(DataCollectionDialog.CBOX_PROGRESS_DATE) && shouldAutoInsertDate()) {
			view.setProgressDate(DateUtil.today());
		}
	}

	private boolean shouldAutoInsertDate() {
		return StringUtil.groomString(view.getProgressDate()) == null
				&& (view.getProgressStatus() == DataCollection.ProgressStatuses.APPOINTED
				|| view.getProgressStatus() == DataCollection.ProgressStatuses.COMPLETED);
	}

	@Override
	protected void setModel(Model model) {
		this.model = (DataCollection)model;
	}

	@Override
	protected void performTransaction() {
		JPATransactor.getInstance().transaction().begin();
		transferUserInputToModel();
		model().validateUserInput();
		DataCollectionManager.getInstance().generateDataCollections(model.getTarget());
		
		JPATransactor.getInstance().transaction().commit();
	}
}
