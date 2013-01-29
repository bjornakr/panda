package no.atferdssenteret.panda.view.util;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class GridBagLayoutAutomat {

	public static JPanel createPanelFor(List<LabelFieldPair> labelsAndFields, boolean addBorder) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		int posY = 0;
		for (LabelFieldPair labelFieldPair : labelsAndFields) {
			Component field = labelFieldPair.field();

			c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = posY;
			c.anchor = GridBagConstraints.LINE_START;
			c.insets = new Insets(5, 5, 5, 5); // N, W, S, E
			if (field instanceof DefaultTextArea) {
				c.gridwidth = GridBagConstraints.REMAINDER;
			}
			panel.add(labelFieldPair.label(), c);

			c = new GridBagConstraints();
			c.anchor = GridBagConstraints.LINE_START;
			c.weightx = 1;
			if (field instanceof DefaultTextArea) {
				field = ((DefaultTextArea) field).scrollPane();
				c.gridx = 0;
				c.gridy = ++posY;
				c.weighty = 1;
				c.gridwidth = GridBagConstraints.REMAINDER;
				c.fill = GridBagConstraints.BOTH;
				c.insets = new Insets(0, 5, 5, 5);
			}
			else {
				c.gridx = 1;
				c.gridy = posY;
				c.insets = new Insets(5, 0, 5, 5);
			}
			c.anchor = GridBagConstraints.LINE_START;
			panel.add(field, c);
			posY++;
		}

		if (addBorder) {
			panel.setBorder(createPaddedEtchedBorder(10, 10));
		}

		return panel;
	}


	public static Border createPaddedEtchedBorder(int paddingTop, int paddingBottom) {
		Border b = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		b = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(), b);
		return b;
	}

	public static GridBagConstraints constraintsForButtonPanel(int gridy) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = gridy;
		c.fill = GridBagConstraints.HORIZONTAL;
		return c;
	}

	public static GridBagConstraints typicalConstraintsForPanel(int gridy, int weighty) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = gridy;
		c.weightx = 1;
		c.weighty = weighty;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.BOTH;
		return c;
	}
}
