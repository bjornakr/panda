package no.atferdssenteret.panda.model.validator;

import no.atferdssenteret.panda.InvalidUserInputException;
import no.atferdssenteret.panda.util.StandardMessages;
import no.atferdssenteret.panda.view.ParticipantDialog;

public class ParticipantValidator implements UserInputValidator {
	private ParticipantDialog view;
	
	public ParticipantValidator(ParticipantDialog view) {
		this.view = view;
	}
	
	public void validateUserInput() {
		if (ValidationUtil.notFilledIn(view.getFirstName())) {
			throw new InvalidUserInputException(StandardMessages.missingField("Fornavn"));			
		}
		if (ValidationUtil.notFilledIn(view.getLastName())) {
			throw new InvalidUserInputException(StandardMessages.missingField("Etternavn"));			
		}
		if (ValidationUtil.exceedsMaximumLength(view.getFirstName(), 255)) {
			throw new InvalidUserInputException(StandardMessages.textTooLong("Fornavn"));			
		}
		if (ValidationUtil.exceedsMaximumLength(view.getLastName(), 255)) {
			throw new InvalidUserInputException(StandardMessages.textTooLong("Etternavn"));			
		}
		if (ValidationUtil.exceedsMaximumLength(view.getPhoneNo(), 255)) {
			throw new InvalidUserInputException(StandardMessages.textTooLong("Tlf. nr"));			
		}
		if (ValidationUtil.exceedsMaximumLength(view.getEMail(), 255)) {
			throw new InvalidUserInputException(StandardMessages.textTooLong("E-post"));			
		}
		if (ValidationUtil.exceedsMaximumLength(view.getAddress(), 1024)) {
			throw new InvalidUserInputException(StandardMessages.textTooLong("Adresse"));			
		}
		if (ValidationUtil.exceedsMaximumLength(view.getContactInfo(), 1024)) {
			throw new InvalidUserInputException(StandardMessages.textTooLong("Kontaktinformasjon"));			
		}
		if (ValidationUtil.exceedsMaximumLength(view.getComment(), 1024)) {
			throw new InvalidUserInputException(StandardMessages.textTooLong("Kommentar"));			
		}
	}
}
