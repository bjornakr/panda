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

import no.atferdssenteret.panda.model.DataCollectionTypes;
import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.model.entity.User;
import no.atferdssenteret.panda.view.util.ButtonUtil;
import no.atferdssenteret.panda.view.util.DefaultTextField;
import no.atferdssenteret.panda.view.util.GridBagLayoutAutomat;
import no.atferdssenteret.panda.view.util.GuiUtil;
import no.atferdssenteret.panda.view.util.LabelFieldPair;

public class DataCollectionDialog extends JDialog {
	public final static String CBOX_TYPE = "CBOX_TYPE";
	public final static String CBOX_PROGRESS_DATE = "CBOX_PROGRESS_DATE";	
	private static final long serialVersionUID = 1L;
	private ActionListener actionListener;
	private JComboBox cboxTypes = new JComboBox(DataCollectionTypes.getInstance().toArray());
	private DefaultTextField txtTargetDate = new DefaultTextField(DefaultTextField.DATE_WIDTH);
	private JComboBox cboxProgressStatuses = new JComboBox(DataCollection.ProgressStatuses.values());
	private DefaultTextField txtProgressDate = new DefaultTextField(DefaultTextField.DATE_WIDTH);
	private JComboBox cboxDataCollector = new JComboBox(User.dataCollectors().toArray());
	private DefaultTablePanel questionnaireView;

	public DataCollectionDialog(Window parentWindow, ActionListener actionListener, DefaultTablePanel questionnaireView) {
		this.actionListener = actionListener;
		this.questionnaireView = questionnaireView;
		setModalityType(ModalityType.APPLICATION_MODAL);
		cboxTypes.setActionCommand(CBOX_TYPE);
		cboxTypes.addActionListener(this.actionListener);
		cboxProgressStatuses.setActionCommand(CBOX_PROGRESS_DATE);
		cboxProgressStatuses.addActionListener(actionListener);
		layoutContent();
		pack();
		setLocationRelativeTo(parentWindow);
	}

	public void layoutContent() {
		setLayout(new GridBagLayout());

		List<LabelFieldPair> labelsAndFields = new LinkedList<LabelFieldPair>();
		labelsAndFields.add(new LabelFieldPair(new JLabel("Datainnsamling"), cboxTypes));
		JLabel labTargetDate = new JLabel("Måldato");
		GuiUtil.setNotNullFlag(labTargetDate);
		labelsAndFields.add(new LabelFieldPair(labTargetDate, txtTargetDate));
		labelsAndFields.add(new LabelFieldPair(new JLabel("Framdrift"), cboxProgressStatuses));
		labelsAndFields.add(new LabelFieldPair(new JLabel("Dato"), txtProgressDate));
		cboxDataCollector.insertItemAt(null, 0);
		cboxDataCollector.setSelectedIndex(0);
		labelsAndFields.add(new LabelFieldPair(new JLabel("DataCollector"), cboxDataCollector));
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
	
	public void restrictAccess() {
		cboxTypes.setEnabled(false);
		cboxDataCollector.setEnabled(false);
		txtTargetDate.setEnabled(false);
		cboxDataCollector.setEnabled(false);
		
	}
	
	public Object getType() {
		return cboxTypes.getSelectedItem();
	}

	public void initializeTypeComboBox() {
		if (cboxTypes.getModel().getSize() > 0) {
			cboxTypes.setSelectedIndex(0);
		}
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
	
	public Object getDataCollector() {
		return cboxDataCollector.getSelectedItem();
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
	
	public void setDataCollector(User dataCollector) {
		cboxDataCollector.setSelectedItem(dataCollector);
	}
}
