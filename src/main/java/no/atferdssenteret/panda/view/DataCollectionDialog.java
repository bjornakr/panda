package no.atferdssenteret.panda.view;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import no.atferdssenteret.panda.model.DataCollection;
import no.atferdssenteret.panda.view.util.ButtonUtil;
import no.atferdssenteret.panda.view.util.DefaultTextField;
import no.atferdssenteret.panda.view.util.GridBagLayoutAutomat;
import no.atferdssenteret.panda.view.util.GuiUtil;
import no.atferdssenteret.panda.view.util.LabelFieldPair;

public class DataCollectionDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private ActionListener actionListener;
	private String[] types = {"T1", "T2", "T3"}; // TODO
	private JComboBox cboxTypes = new JComboBox(types);
	private DefaultTextField txtTargetDate = new DefaultTextField(DefaultTextField.DATE_WIDTH);
	private JComboBox cboxProgressStatuses = new JComboBox(DataCollection.ProgressStatuses.values());
	private DefaultTextField txtProgressDate = new DefaultTextField(DefaultTextField.DATE_WIDTH);
	private DefaultTablePanel questionnaireView;

	public DataCollectionDialog(Window parentWindow, ActionListener actionListener, DefaultTablePanel questionnaireView) {
		this.actionListener = actionListener;
		this.questionnaireView = questionnaireView;
		setModalityType(ModalityType.APPLICATION_MODAL);
		cboxTypes.setActionCommand("TYPE_COMBO_BOX");
		cboxTypes.addActionListener(this.actionListener);
		layoutContent();
		pack();
		setLocationRelativeTo(parentWindow);
	}

	public void layoutContent() {
		setLayout(new GridBagLayout());

		List<LabelFieldPair> labelsAndFields = new LinkedList<LabelFieldPair>();
		labelsAndFields.add(new LabelFieldPair(new JLabel("Datainnsamling"), cboxTypes));
		labelsAndFields.add(new LabelFieldPair(new JLabel("Måldato"), txtTargetDate));
		labelsAndFields.add(new LabelFieldPair(new JLabel("Framdrift"), cboxProgressStatuses));
		labelsAndFields.add(new LabelFieldPair(new JLabel("Dato"), txtProgressDate));
		add(GridBagLayoutAutomat.createPanelFor(labelsAndFields, true), GridBagLayoutAutomat.typicalConstraintsForPanel(0, 0));
		add(createQuestionnairePanel(), GridBagLayoutAutomat.typicalConstraintsForPanel(1, 1));
		add(ButtonUtil.createSaveCancelButtonPanel(actionListener, this), GridBagLayoutAutomat.constraintsForButtonPanel(2));	
	}

	private JPanel createQuestionnairePanel() {
		JPanel questionnairePanel = new JPanel();
		questionnairePanel.setLayout(new BorderLayout());
		questionnaireView.setTableHeight(100);
		questionnaireView.showCounters(false);
		questionnairePanel.add(questionnaireView, BorderLayout.CENTER);
		questionnairePanel.setBorder(GuiUtil.createTitledBorder("Spørreskjemaer"));
		return questionnairePanel;
	}
	
	
	public Object getType() {
		return cboxTypes.getSelectedItem();
	}

	public void initializeTypeComboBox() {
		cboxTypes.setSelectedIndex(0);	
	}

//	public Object getDataCollectionType() {
//		return cboxTypes.getSelectedItem();
//	}

	public String getTargetDate() {
		return txtTargetDate.getText();
	}

	public Object getProgressStatus() {
		return cboxProgressStatuses.getSelectedItem();
	}

	public String getProgressDate() {
		return txtProgressDate.getText();
	}
	
	public void setType(String type) {
		cboxTypes.setSelectedItem(type);
	}
	
	public void setTargetDate(Date targetDate) {
		txtTargetDate.setText(targetDate);
	}
	
	public void setProgressStatus(DataCollection.ProgressStatuses progressStatus) {
		cboxProgressStatuses.setSelectedItem(progressStatus);
	}
	
	public void setProgressDate(Date progressDate) {
		txtProgressDate.setText(progressDate);
	}
}
