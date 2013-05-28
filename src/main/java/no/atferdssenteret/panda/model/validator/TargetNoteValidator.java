package no.atferdssenteret.panda.model.validator;

import no.atferdssenteret.panda.InvalidUserInputException;
import no.atferdssenteret.panda.util.StandardMessages;
import no.atferdssenteret.panda.view.TargetNoteDialog;

public class TargetNoteValidator implements UserInputValidator {
	private TargetNoteDialog view;
	
	public TargetNoteValidator(TargetNoteDialog view) {
		this.view = view;
	}

	@Override
	public void validateUserInput() {
		if (ValidationUtil.notFilledIn(view.getNote())) {
			throw new InvalidUserInputException(StandardMessages.missingField("Notat"));
		}
		if (ValidationUtil.exceedsMaximumLength(view.getNote(), 1024)) {
			throw new InvalidUserInputException(StandardMessages.textTooLong("Notat"));
		}
	}
}
