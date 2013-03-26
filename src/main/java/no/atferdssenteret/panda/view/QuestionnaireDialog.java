package no.atferdssenteret.panda.view;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import no.atferdssenteret.panda.model.QuestionnaireTypes;
import no.atferdssenteret.panda.view.util.ButtonUtil;
import no.atferdssenteret.panda.view.util.GridBagLayoutAutomat;
import no.atferdssenteret.panda.view.util.GuiUtil;
import no.atferdssenteret.panda.view.util.LabelFieldPair;

public class QuestionnaireDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private ActionListener actionListener;
	private DefaultTablePanel questionnaireEventView;
	private JComboBox cboxName = new JComboBox(QuestionnaireTypes.getInstance().toArray());

	public QuestionnaireDialog(Window parentWindow, ActionListener actionListener, DefaultTablePanel questionnaireEventPanel) {
		this.actionListener = actionListener;
		this.questionnaireEventView = questionnaireEventPanel;
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Rediger spørreskjema");
		layoutContent();
		pack();
		setLocationRelativeTo(parentWindow);
	}

	private void layoutContent() {
		setLayout(new GridBagLayout());

		List<LabelFieldPair> labelsAndFields = new LinkedList<LabelFieldPair>();
		labelsAndFields.add(new LabelFieldPair(new JLabel("Spørreskjema"), cboxName));
		add(GridBagLayoutAutomat.createPanelFor(labelsAndFields, true), GridBagLayoutAutomat.typicalConstraintsForPanel(0, 0));
		questionnaireEventView.setTableHeight(100);
		questionnaireEventView.showCounters(false);
		add(createQuestionnaireEventPanel(), GridBagLayoutAutomat.typicalConstraintsForPanel(1, 1));
		add(ButtonUtil.createSaveCancelButtonPanel(actionListener, this), GridBagLayoutAutomat.constraintsForButtonPanel(2));
	}

	private JPanel createQuestionnaireEventPanel() {
		JPanel questionnaireEventPanel = new JPanel();
		questionnaireEventPanel.setLayout(new BorderLayout());
		questionnaireEventView.setTableHeight(100);
		questionnaireEventView.showCounters(false);
		questionnaireEventPanel.add(questionnaireEventView, BorderLayout.CENTER);
		questionnaireEventPanel.setBorder(GuiUtil.createTitledBorder("Hendelser"));
		return questionnaireEventPanel;
	}
	
	public Object getQuestionnaireName() {
		return cboxName.getSelectedItem();
	}

	public void setQuestionnaireName(String name) {
		cboxName.setSelectedItem(name);
	}
}
