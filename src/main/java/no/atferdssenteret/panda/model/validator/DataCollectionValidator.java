package no.atferdssenteret.panda.model.validator;

import java.util.List;

import no.atferdssenteret.panda.InvalidUserInputException;
import no.atferdssenteret.panda.model.entity.DataCollection.ProgressStatuses;
import no.atferdssenteret.panda.model.entity.Questionnaire;
import no.atferdssenteret.panda.util.StandardMessages;
import no.atferdssenteret.panda.util.StringUtil;
import no.atferdssenteret.panda.view.DataCollectionDialog;

public class DataCollectionValidator implements UserInputValidator {
	private DataCollectionDialog view;
	private List<Questionnaire> questionnaires;

	public DataCollectionValidator(DataCollectionDialog view, List<Questionnaire> questionnaires) {
		this.view = view;
		this.questionnaires = questionnaires;
	}

	@Override
	public void validateUserInput() {
		// where do we check for date format?
		if (ValidationUtil.notFilledIn(view.getTargetDate())) {
			throw new InvalidUserInputException(StandardMessages.missingField("Måldato"));
		}
		else {
			StringUtil.parseDate(view.getTargetDate());
		}
		if (ValidationUtil.notFilledIn(view.getProgressDate()) && progressStatus() == ProgressStatuses.COMPLETED) {
			throw new InvalidUserInputException(StandardMessages.missingField("Dato utført"));
		}
		if (progressStatus() == ProgressStatuses.COMPLETED && !allQuesitonnairesHaveEvents()) {
			throw new InvalidUserInputException("Kan ikke sette datainnsamlingen til \"" + 
					ProgressStatuses.COMPLETED + "\" før hvert spørreskjema har minst én hendelse.");
		}
		if (!ValidationUtil.notFilledIn(view.getProgressDate())) {
			StringUtil.parseDate(view.getProgressDate());
		}
	}

	private boolean allQuesitonnairesHaveEvents() {
		for (Questionnaire questionnaire : questionnaires) {
			if (!questionnaire.hasEvents()) {
				return false;
			}
		}
		return true;
	}

	private ProgressStatuses progressStatus() {
		return (ProgressStatuses)view.getProgressStatus();
	}
}
