package no.atferdssenteret.panda.view.util;

import java.awt.Component;

public class LabelFieldPair {
	private Component label;
	private Component field;

	public LabelFieldPair(Component label, Component field) {
		this.label = label;
		this.field = field;
	}

	public Component label() {
		return label;
	}

	public Component field() {
		return field;
	}
}
