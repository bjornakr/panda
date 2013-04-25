package no.atferdssenteret.panda.model.validator;

import no.atferdssenteret.panda.InvalidUserInputException;
import no.atferdssenteret.panda.util.StandardMessages;
import no.atferdssenteret.panda.view.TargetDialog;

public class TargetValidator implements UserInputValidator {
	private TargetDialog view;
	
	public TargetValidator(TargetDialog view) {
		this.view = view;
	}
	
	public void validateUserInput() {
		if (ValidationUtil.notFilledIn(view.getFirstName())) {
			throw new InvalidUserInputException(StandardMessages.missingField("Fornavn"));			
		}
		if (ValidationUtil.notFilledIn(view.getLastName())) {
			throw new InvalidUserInputException(StandardMessages.missingField("Etternavn"));			
		}			
	}
}
