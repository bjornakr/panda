package no.atferdssenteret.panda.model.validator;

import no.atferdssenteret.panda.util.StringUtil;

public class ValidationUtil {
	public static boolean notFilledIn(String s) {
		return StringUtil.groomString(s) == null;
	}

	public static boolean exceedsMaximumLength(String field, int maximumLength) {
		return (field != null && field.length() > maximumLength);
	}
}
