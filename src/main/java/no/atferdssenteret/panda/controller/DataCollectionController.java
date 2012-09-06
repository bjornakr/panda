package no.atferdssenteret.panda.controller;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import no.atferdssenteret.panda.QuestionnairesForDataCollectionType;
import no.atferdssenteret.panda.controller.table.QuestionnaireTableController;
import no.atferdssenteret.panda.model.DataCollection;
import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Questionnaire;
import no.atferdssenteret.panda.view.DataCollectionDialog;

public class DataCollectionController extends ApplicationController {
    private DataCollection model;
    private DataCollectionDialog view;
    private QuestionnaireTableController questionnaireTableController = new QuestionnaireTableController();
//    private QuestionnaireTable questionnaireTable = new QuestionnaireTable();

    public DataCollectionController(Window parentWindow, DataCollection model) {
	super(model);
	this.model = model;
	view = new DataCollectionDialog(parentWindow, this, questionnaireTableController.view());
	view.initializeTypeComboBox();
	if (getMode() == Mode.EDIT) {
	    transferModelToView();
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
	//	view.setFirstName(model.getFirstName());
	//	view.setLastName(model.getLastName());
	//	view.setRole(model.getRole());
	//	view.setTlfNo(model.getTlfNo());
	//	view.seteMail(model.geteMail());
	//	view.setContactInfo(model.getContactInfo());
	//	view.setComment(model.getComment());
    }

    @Override
    protected void transferUserInputToModel() {
	if (getMode() == Mode.CREATE) {
	    model = new DataCollection();
	}
	//	model.setFirstName(StringUtil.groomString(view.getFirstName()));
	//	model.setLastName(StringUtil.groomString(view.getLastName()));
	//	model.setRole((String)view.getRole());
	//	model.setTlfNo(StringUtil.groomString(view.getTlfNo()));
	//	model.seteMail(StringUtil.groomString(view.geteMail()));
	//	model.setContactInfo(StringUtil.groomString(view.getContactInfo()));
	//	model.setComment(StringUtil.groomString(view.getComment()));
    }

    @Override public void actionPerformed(ActionEvent event) {
	super.actionPerformed(event);
	if (event.getActionCommand().equals("TYPE_COMBO_BOX") && getMode() == Mode.CREATE) {
	    List<Questionnaire> questionnaires = new LinkedList<Questionnaire>();
	    for (String questionnaireName : QuestionnairesForDataCollectionType.getInstance().getQuestionnaireNamesFor((String)view.getType())) {
		Questionnaire questionnaire = new Questionnaire();
		questionnaire.setName(questionnaireName);
		questionnaires.add(questionnaire);
	    }
	    questionnaireTableController.updateModel(questionnaires);
	}
    }
}
