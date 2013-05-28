package no.atferdssenteret.tibirbase;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import no.atferdssenteret.panda.view.TabsAndTablesPanel;
import no.atferdssenteret.panda.view.util.DefaultTextArea;
import no.atferdssenteret.panda.view.util.GuiUtil;

public class YouthFocusPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Youth model; 
	private JLabel labStatus = new JLabel();
	private JLabel labRegion = new JLabel();
	private JLabel labGender = new JLabel();
	private JLabel labFullName = new JLabel();
	private JLabel labTreatmentGroup = new JLabel();
//	private JLabel labTreatmentStart = new JLabel();
	private JLabel labDataCollector = new JLabel();
	private JLabel labDataCollectorTelephone = new JLabel();
	private DefaultTextArea txtComment = new DefaultTextArea();

	public YouthFocusPanel(ActionListener controller, Youth model, TabsAndTablesPanel tabsAndTablesPanel) {
		this.model = model;

		copyDataFromModelToView();

		setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER; 
		add(headerPanel(), c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER; 
		c.fill = GridBagConstraints.BOTH;
		add(infoPanel(), c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START; 
		c.fill = GridBagConstraints.BOTH;
		add(tabsAndTablesPanel, c);

		setValueLabelColors();
	}








	private JPanel headerPanel() {
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new GridBagLayout());

		JLabel labID = new JLabel(model.formattedIdWithLetterAppendix());
		labID.setFont(GuiUtil.HEADER_FONT_PRIMARY);	

		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.gridx = 0;
		headerPanel.add(labID, c);

		JPanel statusPanel = new JPanel();
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.LINE_AXIS));
		statusPanel.add(new JLabel("Status: "));
		statusPanel.add(labStatus);

		c = new GridBagConstraints();
		c.gridy = 1;
		headerPanel.add(statusPanel, c);
		headerPanel.setBorder(GuiUtil.createPaddedEtchedBorder(5, 0));

		return headerPanel;
	}

	private JPanel infoPanel() {
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = null;

		Insets insetsForLabel = new Insets(0, 0, 0, 5);  // top, left, bottom, right
		Insets insetsForField = new Insets(0, 0, 0, 20);


		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = insetsForLabel;
		infoPanel.add(new JLabel("Navn: "), c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = insetsForField;
		infoPanel.add(labFullName, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;	
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = insetsForLabel;
		infoPanel.add(new JLabel("Kjønn: "), c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;	
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = insetsForField;
		infoPanel.add(labGender, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;	
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = insetsForLabel;
		infoPanel.add(new JLabel("Gruppe: "), c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;	
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = insetsForField;
		infoPanel.add(labTreatmentGroup, c);

//		c = new GridBagConstraints();
//		c.gridx = 0;
//		c.gridy = 3;	
//		c.weightx = 1;
//		c.weighty = 1;
//		c.anchor = GridBagConstraints.LINE_START;
//		c.insets = insetsForLabel;
//		infoPanel.add(new JLabel("Behandlingsstart: "), c);
//
//		c = new GridBagConstraints();
//		c.gridx = 1;
//		c.gridy = 3;	
//		c.weightx = 1;
//		c.weighty = 1;
//		c.anchor = GridBagConstraints.LINE_START;
//		c.insets = insetsForField;
//		infoPanel.add(labTreatmentStart, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 4;	
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = insetsForLabel;
		infoPanel.add(new JLabel("Region: "), c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 4;	
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = insetsForField;
		infoPanel.add(labRegion, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 5;	
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = insetsForLabel;
		infoPanel.add(new JLabel("Datainnsamler: "), c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 5;	
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = insetsForField;
		infoPanel.add(labDataCollector, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 6;	
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = insetsForLabel;
		infoPanel.add(new JLabel("Telefonintervjuer: "), c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 6;	
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = insetsForField;
		infoPanel.add(labDataCollectorTelephone, c);

		c = new GridBagConstraints();
		c.gridx = 4;
		c.gridy = 0;	
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = insetsForLabel;
		infoPanel.add(new JLabel("Kommentar: "), c);

		c = new GridBagConstraints();
		c.gridx = 4;
		c.gridy = 1;	
		c.anchor = GridBagConstraints.LINE_START;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.BOTH;
		txtComment.setLineWrap(true);
		txtComment.setWrapStyleWord(true);
		txtComment.setEditable(false);
		infoPanel.add(txtComment.scrollPane(), c);

		infoPanel.setBorder(GuiUtil.createPaddedEtchedBorder(0, 0));
		return infoPanel;
	}


	private void setValueLabelColors() {
		Color color = new Color(0x006699);
		labStatus.setForeground(color);
		labStatus.setFont(GuiUtil.valueFont());
		labFullName.setForeground(color);
		labFullName.setFont(GuiUtil.valueFont());
		labGender.setForeground(color);
		labGender.setFont(GuiUtil.valueFont());
		labTreatmentGroup.setForeground(color);
		labTreatmentGroup.setFont(GuiUtil.valueFont());
//		labTreatmentStart.setForeground(color);
//		labTreatmentStart.setFont(GuiUtil.valueFont());
		labRegion.setForeground(color);
		labRegion.setFont(GuiUtil.valueFont());
		labDataCollector.setForeground(color);
		labDataCollector.setFont(GuiUtil.valueFont());
		labDataCollectorTelephone.setForeground(color);
		labDataCollectorTelephone.setFont(GuiUtil.valueFont());
	}

	private void copyDataFromModelToView() {
		labStatus.setText(model.getStatus().toString());
		labFullName.setText(model.getFirstName() + " " + model.getLastName());
		labGender.setText(model.getGender().toString());
		labTreatmentGroup.setText(model.getTreatmentGroup().toString());
//		if (model.getTreatmentStart() != null) {
//			labTreatmentStart.setText(DateUtil.formatDate(model.getTreatmentStart()));
//		}
		labRegion.setText(model.getRegion().toString());
		if (model.getDataCollector() != null) {
			labDataCollector.setText(model.getDataCollector().toString());
		}
		if (model.getDataCollectorTelephone() != null) {
			labDataCollectorTelephone.setText(model.getDataCollectorTelephone().toString());
		}
		txtComment.setText(model.getComment());
	}

}
