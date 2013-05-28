package no.atferdssenteret.panda.model.validator;

import no.atferdssenteret.panda.InvalidUserInputException;
import no.atferdssenteret.panda.util.StandardMessages;
import no.atferdssenteret.panda.util.StringUtil;
import no.atferdssenteret.panda.view.QuestionnaireEventDialog;

public class QuestionnaireEventValidator implements UserInputValidator {
	private QuestionnaireEventDialog view;
	
	public QuestionnaireEventValidator(QuestionnaireEventDialog view) {
		this.view = view;
	}
	
	@Override
	public void validateUserInput() {
		if (ValidationUtil.notFilledIn(view.getDate())) {
			throw new InvalidUserInputException(StandardMessages.missingField("Dato"));
		}
		else {
			StringUtil.parseDate(view.getDate());
		}
		if (ValidationUtil.exceedsMaximumLength(view.getComment(), 1024)) {
			throw new InvalidUserInputException(StandardMessages.textTooLong("Kommentar"));
		}
	}

}
