package no.atferdssenteret.panda.view;

import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;

import no.atferdssenteret.panda.view.util.ButtonUtil;
import no.atferdssenteret.panda.view.util.DefaultTextArea;
import no.atferdssenteret.panda.view.util.GridBagLayoutAutomat;
import no.atferdssenteret.panda.view.util.LabelFieldPair;

public class TargetNoteDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private ActionListener actionListener;
	private DefaultTextArea txtNote = new DefaultTextArea();
	
	public TargetNoteDialog(Window parentWindow, ActionListener actionListener) {
		this.actionListener = actionListener;
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Rediger notat");
		layoutContent();
		pack();
		setLocationRelativeTo(parentWindow);
	}

	private void layoutContent() {
		setLayout(new GridBagLayout());
		List<LabelFieldPair> labelsAndFields = new LinkedList<LabelFieldPair>();
		labelsAndFields.add(new LabelFieldPair(new JLabel("Notat"), txtNote));
		add(GridBagLayoutAutomat.createPanelFor(labelsAndFields, true), GridBagLayoutAutomat.typicalConstraintsForPanel(0, 0));
		add(ButtonUtil.createSaveCancelButtonPanel(actionListener, this), GridBagLayoutAutomat.constraintsForButtonPanel(1));
	}
	
	public void setNote(String note) {
		txtNote.setText(note);
	}
	
	public String getNote() {
		return txtNote.getText();
	}
}
