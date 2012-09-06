package no.atferdssenteret.panda.view.util;

import java.awt.Component;

import javax.swing.JLabel;

public class LabelFieldPair {
    private JLabel label;
    private Component field;
    
    public LabelFieldPair(JLabel label, Component field) {
	this.label = label;
	this.field = field;
    }
    
    public JLabel label() {
	return label;
    }
    
    public Component field() {
	return field;
    }
}
