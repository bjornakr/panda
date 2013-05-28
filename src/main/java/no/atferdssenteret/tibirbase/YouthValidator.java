package no.atferdssenteret.tibirbase;

import no.atferdssenteret.panda.model.validator.TargetValidator;
import no.atferdssenteret.panda.model.validator.UserInputValidator;
import no.atferdssenteret.panda.util.StringUtil;

public class YouthValidator implements UserInputValidator {
	private YouthDialog view;
	
	public YouthValidator(YouthDialog view) {
		this.view = view;
	}

	@Override
	public void validateUserInput() {
		new TargetValidator(view).validateUserInput();
		
		if (StringUtil.groomString(view.getTreatmentStart()) != null) {
			StringUtil.parseDate(view.getTreatmentStart());
		}
	}
}
