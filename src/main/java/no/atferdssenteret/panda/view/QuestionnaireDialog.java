package no.atferdssenteret.panda.view;

import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import no.atferdssenteret.panda.QuestionnairesForDataCollectionType;
import no.atferdssenteret.panda.view.util.ButtonUtil;
import no.atferdssenteret.panda.view.util.GridBagLayoutAutomat;
import no.atferdssenteret.panda.view.util.LabelFieldPair;

public class QuestionnaireDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private ActionListener actionListener;
    private DefaultOverviewPanel questionnaireEventPanel;
    private JComboBox cboxName = new JComboBox(QuestionnairesForDataCollectionType.getInstance().allQuestionnaireNames().toArray());

    public QuestionnaireDialog(Window parentWindow, ActionListener actionListener, DefaultOverviewPanel questionnaireEventPanel) {
	this.actionListener = actionListener;
	this.questionnaireEventPanel = questionnaireEventPanel;
	setModalityType(ModalityType.APPLICATION_MODAL);
	layoutContent();
	pack();
	setLocationRelativeTo(parentWindow);
    }

    private void layoutContent() {
	setLayout(new GridBagLayout());
	
	List<LabelFieldPair> labelsAndFields = new LinkedList<LabelFieldPair>();
	labelsAndFields.add(new LabelFieldPair(new JLabel("Spørreskjema"), cboxName));
	add(GridBagLayoutAutomat.createPanelFor(labelsAndFields, true), GridBagLayoutAutomat.typicalConstraintsForPanel(0, 0));
	questionnaireEventPanel.setTableHeight(100);
	questionnaireEventPanel.showCounters(false);
	add(questionnaireEventPanel, GridBagLayoutAutomat.typicalConstraintsForPanel(1, 1));
	add(ButtonUtil.createSaveCancelButtonPanel(actionListener, this), GridBagLayoutAutomat.constraintsForButtonPanel(2));
    }
    
    public Object getQuestionnaireName() {
	return cboxName.getSelectedItem();
    }
    
    public void setQuestionnaireName(String name) {
	cboxName.setSelectedItem(name);
    }
}
