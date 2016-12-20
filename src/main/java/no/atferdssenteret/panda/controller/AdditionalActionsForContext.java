package no.atferdssenteret.panda.controller;

import java.util.HashMap;
import java.util.List;


public class AdditionalActionsForContext extends HashMap<String, List<AdditionalAction>> {
	private static final long serialVersionUID = 1L;
	private static AdditionalActionsForContext self;

	private AdditionalActionsForContext() {
	}

	public static AdditionalActionsForContext getInstance() {
		if (self == null) {
			self = new AdditionalActionsForContext();
		}
		return self;
	}

	public boolean hasActionCommand(String context, String actionCommand) {
		if (self.get(context) != null) {
			for (AdditionalAction action : self.get(context)) {
				if (action.getActionCommand().equals(actionCommand)) {
					return true;
				}
			}
		}
		return false;
	}
}
