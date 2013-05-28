package no.atferdssenteret.panda.model.validator;

import no.atferdssenteret.panda.InvalidUserInputException;
import no.atferdssenteret.panda.util.StandardMessages;
import no.atferdssenteret.panda.view.PasswordDialog;
import no.atferdssenteret.panda.view.UserDialog;

public class UserValidator implements UserInputValidator {
	private UserDialog view;
	private PasswordDialog passwordView;

	public UserValidator(UserDialog view, PasswordDialog passwordView) {
		this.view = view;
		this.passwordView = passwordView;
	}

	@Override
	public void validateUserInput() {
		if (ValidationUtil.notFilledIn(view.getUsername())) {
			throw new InvalidUserInputException(StandardMessages.missingField("Brukernavn"));
		}
		if (ValidationUtil.notFilledIn(view.getFirstName())){
			throw new InvalidUserInputException(StandardMessages.missingField("Fornavn"));			
		}
		if (ValidationUtil.notFilledIn(view.getLastName())) {
			throw new InvalidUserInputException(StandardMessages.missingField("Etternavn"));
		}
		if (passwordView == null || ValidationUtil.notFilledIn(new String(passwordView.getPassword()))) {
			throw new InvalidUserInputException(StandardMessages.missingField("Passord"));			
		}
		else {
			if (passwordView.getPassword().length < 6) {
				throw new InvalidUserInputException(StandardMessages.missingField("Passordet må være minst seks tegn."));							
			}
			if (ValidationUtil.exceedsMaximumLength(passwordView.getPassword().toString(), 255)) {
				throw new InvalidUserInputException(StandardMessages.missingField("Passordet må være minst seks tegn."));							
			}
		}
		if (ValidationUtil.exceedsMaximumLength(view.getUsername(), 256)) {
			throw new InvalidUserInputException(StandardMessages.textTooLong("Brukernavn"));			
		}
		if (ValidationUtil.exceedsMaximumLength(view.getFirstName(), 255)) {
			throw new InvalidUserInputException(StandardMessages.textTooLong("Fornavn"));			
		}
		if (ValidationUtil.exceedsMaximumLength(view.getLastName(), 255)) {
			throw new InvalidUserInputException(StandardMessages.textTooLong("Etternavn"));			
		}
	}
}
