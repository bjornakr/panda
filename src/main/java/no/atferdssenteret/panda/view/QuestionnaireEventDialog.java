package no.atferdssenteret.panda.view;

import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import no.atferdssenteret.panda.model.entity.QuestionnaireEvent;
import no.atferdssenteret.panda.view.util.ButtonUtil;
import no.atferdssenteret.panda.view.util.DefaultTextArea;
import no.atferdssenteret.panda.view.util.DefaultTextField;
import no.atferdssenteret.panda.view.util.GridBagLayoutAutomat;
import no.atferdssenteret.panda.view.util.GuiUtil;
import no.atferdssenteret.panda.view.util.LabelFieldPair;

public class QuestionnaireEventDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private ActionListener actionListener;
	private DefaultTextField txtDate = new DefaultTextField(DefaultTextField.DATE_WIDTH);
	private JComboBox cboxType = new JComboBox(QuestionnaireEvent.Types.values());
	private DefaultTextArea txtComment = new DefaultTextArea();

	public QuestionnaireEventDialog(Window parentWindow, ActionListener actionListener) {
		this.actionListener = actionListener;
		setModalityType(ModalityType.APPLICATION_MODAL);
		layoutContent();
		pack();
		setLocationRelativeTo(parentWindow);
	}

	private void layoutContent() {
		setLayout(new GridBagLayout());
		List<LabelFieldPair> labelsAndFields = new LinkedList<LabelFieldPair>();
		JLabel labDate = new JLabel("Dato");
		GuiUtil.setNotNullFlag(labDate);
		labelsAndFields.add(new LabelFieldPair(labDate, txtDate));
		labelsAndFields.add(new LabelFieldPair(new JLabel("Hendelse"), cboxType));
		labelsAndFields.add(new LabelFieldPair(new JLabel("Kommentar"), txtComment));
		add(GridBagLayoutAutomat.createPanelFor(labelsAndFields, true), GridBagLayoutAutomat.typicalConstraintsForPanel(0, 0));
		add(ButtonUtil.createSaveCancelButtonPanel(actionListener, this), GridBagLayoutAutomat.constraintsForButtonPanel(1));	
	}

	public String getDate() {
		return txtDate.getText();	
	}

	public void setDate(Date date) {
		txtDate.setText(date);
	}

	public Object getType() {
		return cboxType.getSelectedItem();	
	}

	public void setType(Object type) {
		cboxType.setSelectedItem(type);
	}

	public String getComment() {
		return txtComment.getText();	
	}

	public void setComment(String comment) {
		txtComment.setText(comment);
	}
}
