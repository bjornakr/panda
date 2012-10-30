package no.atferdssenteret.panda.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import no.atferdssenteret.panda.model.fft.Youth;
import no.atferdssenteret.panda.view.util.DefaultTextArea;
import no.atferdssenteret.panda.view.util.GuiUtil;

public class YouthPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private ActionListener controller;
    private Youth model; 
    private JLabel labStatus = new JLabel();
    private JLabel labRegion = new JLabel();
    private JLabel labGender = new JLabel();
    private JLabel labFullName = new JLabel();
    private JLabel labTreatmentGroup = new JLabel();
    private DefaultTextArea txtComment = new DefaultTextArea();

    public YouthPanel(ActionListener controller, Youth model, TabsAndTablesPanel tabsAndTablesPanel) {
	this.controller = controller;
	this.model = model;
//	this.model.addObserver(this);

	copyDataFromModelToView();

	setLayout(new GridBagLayout());

	GridBagConstraints c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = 0;
	c.fill = GridBagConstraints.BOTH;
	c.anchor = GridBagConstraints.CENTER; 
	add(createBarnHeaderPanel(), c);

	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = 1;
	c.anchor = GridBagConstraints.CENTER; 
	c.fill = GridBagConstraints.BOTH;
	add(createBarnInfoPanel(), c);

//	c = new GridBagConstraints();
//	c.gridx = 0;
//	c.gridy = 2;
//	c.anchor = GridBagConstraints.LINE_END; 
//	add(createButtonPanel(), c);

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








    private JPanel createBarnHeaderPanel() {
	JPanel barnHeaderPanel = new JPanel();
	barnHeaderPanel.setLayout(new GridBagLayout());

	JLabel labID = new JLabel("Barn " + model.getId());
	labID.setFont(GuiUtil.HEADER_FONT_PRIMARY);	

	GridBagConstraints c = new GridBagConstraints();
	c.gridy = 0;
	c.gridx = 0;
	barnHeaderPanel.add(labID, c);

	JPanel statusPanel = new JPanel();
	statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.LINE_AXIS));
	statusPanel.add(new JLabel("Status: "));
	statusPanel.add(labStatus);

	c = new GridBagConstraints();
	c.gridy = 1;
	barnHeaderPanel.add(statusPanel, c);
	barnHeaderPanel.setBorder(GuiUtil.createPaddedEtchedBorder(5, 0));

	return barnHeaderPanel;
    }

    private JPanel createBarnInfoPanel() {
	JPanel barnInfoPanel = new JPanel();
	barnInfoPanel.setLayout(new GridBagLayout());
	GridBagConstraints c = null;

	Insets insetsForLabel = new Insets(0, 0, 0, 5);
	Insets insetsForField = new Insets(0, 0, 0, 20);


	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = 0;
	c.weightx = 1;
	c.weighty = 1;
	c.anchor = GridBagConstraints.LINE_START;
	c.insets = insetsForLabel;
	barnInfoPanel.add(new JLabel("Navn: "), c);

	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = 0;
//	c.weightx = 0.33;
	c.weightx = 1;
	c.weighty = 1;
	c.anchor = GridBagConstraints.LINE_START;
//	c.fill = GridBagConstraints.HORIZONTAL;
	c.insets = insetsForField;
	barnInfoPanel.add(labFullName, c);

	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = 1;	
	c.weightx = 1;
	c.weighty = 1;
	c.anchor = GridBagConstraints.LINE_START;
	c.insets = insetsForLabel;
	barnInfoPanel.add(new JLabel("Kjønn: "), c);

	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = 1;	
	c.weightx = 1;
	c.weighty = 1;
	c.anchor = GridBagConstraints.LINE_START;
//	c.fill = GridBagConstraints.HORIZONTAL;
	c.insets = insetsForField;
	barnInfoPanel.add(labGender, c);

	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = 2;	
	c.weightx = 1;
	c.weighty = 1;
	c.anchor = GridBagConstraints.LINE_START;
	c.insets = insetsForLabel;
	barnInfoPanel.add(new JLabel("Gruppe: "), c);

	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = 2;	
	c.weightx = 1;
	c.weighty = 1;
	c.anchor = GridBagConstraints.LINE_START;
//	c.fill = GridBagConstraints.HORIZONTAL;
	c.insets = insetsForField;
	barnInfoPanel.add(labTreatmentGroup, c);

	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = 3;	
	c.weightx = 1;
	c.weighty = 1;
	c.anchor = GridBagConstraints.LINE_START;
	c.insets = insetsForLabel;
	barnInfoPanel.add(new JLabel("Region: "), c);

	c = new GridBagConstraints();
	c.gridx = 1;
	c.gridy = 3;	
	c.weightx = 1;
	c.weighty = 1;
	c.anchor = GridBagConstraints.LINE_START;
//	c.fill = GridBagConstraints.HORIZONTAL;
	c.insets = insetsForField;
	barnInfoPanel.add(labRegion, c);
//
//
//	c = new GridBagConstraints();
//	c.gridx = 2;
//	c.gridy = 0;	
//	c.weightx = 1;
//	c.weighty = 1;
//	c.anchor = GridBagConstraints.LINE_START;
//	c.insets = insetsForLabel;
//	barnInfoPanel.add(new JLabel("Hovedintervjuer: "), c);

//	c = new GridBagConstraints();
//	c.gridx = 3;
//	c.gridy = 0;	
//	c.weightx = 1;
//	c.weighty = 1;
////	c.weightx = 0.33;
//	c.anchor = GridBagConstraints.LINE_START;
////	c.fill = GridBagConstraints.HORIZONTAL;
//	c.insets = insetsForField;
//	barnInfoPanel.add(txtHovedintervjuer, c);
//
//	c = new GridBagConstraints();
//	c.gridx = 2;
//	c.gridy = 1;	
//	c.weightx = 1;
//	c.weighty = 1;
//	c.anchor = GridBagConstraints.LINE_START;
////	c.fill = GridBagConstraints.HORIZONTAL;
//	c.insets = insetsForLabel;
//	barnInfoPanel.add(new JLabel("Telefonintervjuer: "), c);
//
//	c = new GridBagConstraints();
//	c.gridx = 3;
//	c.gridy = 1;	
//	c.weightx = 1;
//	c.weighty = 1;
//	c.anchor = GridBagConstraints.LINE_START;
////	c.fill = GridBagConstraints.HORIZONTAL;
//	c.insets = insetsForField;
//	barnInfoPanel.add(txtTelefonintervjuer, c);
//
//	c = new GridBagConstraints();
//	c.gridx = 2;
//	c.gridy = 2;	
//	c.weightx = 1;
//	c.weighty = 1;
//	c.anchor = GridBagConstraints.LINE_START;
//	c.insets = insetsForLabel;
//	barnInfoPanel.add(new JLabel("Sample: "), c);
//
//	c = new GridBagConstraints();
//	c.gridx = 3;
//	c.gridy = 2;	
//	c.weightx = 1;
//	c.weighty = 1;
//	c.anchor = GridBagConstraints.LINE_START;
////	c.fill = GridBagConstraints.HORIZONTAL;
//	c.insets = insetsForField;
//	barnInfoPanel.add(txtSample, c);
//
//
//	c = new GridBagConstraints();
//	c.gridx = 2;
//	c.gridy = 3;	
//	c.weightx = 1;
//	c.weighty = 1;
//	c.anchor = GridBagConstraints.LINE_START;
//	c.insets = insetsForLabel;
//	barnInfoPanel.add(new JLabel("Delsample: "), c);
//
//	c = new GridBagConstraints();
//	c.gridx = 3;
//	c.gridy = 3;	
//	c.weightx = 1;
//	c.weighty = 1;
//	c.anchor = GridBagConstraints.LINE_START;
////	c.fill = GridBagConstraints.HORIZONTAL;
//	c.insets = insetsForField;
//	barnInfoPanel.add(txtDelsample, c);

	c = new GridBagConstraints();
	c.gridx = 4;
	c.gridy = 0;	
	c.anchor = GridBagConstraints.LINE_START;
	c.insets = insetsForLabel;
	barnInfoPanel.add(new JLabel("Kommentar: "), c);

	c = new GridBagConstraints();
	c.gridx = 4;
	c.gridy = 1;	
	c.anchor = GridBagConstraints.LINE_START;
	c.gridheight = 3;
	c.fill = GridBagConstraints.BOTH;
	txtComment.setLineWrap(true);
	txtComment.setWrapStyleWord(true);
	txtComment.setEditable(false);
	barnInfoPanel.add(txtComment.scrollPane(), c);

	//	c = new GridBagConstraints();
	//	c.gridx = 4;
	//	c.gridy = 3;
	//	barnInfoPanel.add(butEdit, c);


	barnInfoPanel.setBorder(GuiUtil.createPaddedEtchedBorder(0, 0));

	return barnInfoPanel;
    }


//    private JPanel createButtonPanel() {
//	butEdit.setActionCommand(ButtonUtil.COMMAND_EDIT);
//
//	JPanel buttonPanel = new JPanel();
//	buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
//	buttonPanel.add(Box.createHorizontalGlue());
//	buttonPanel.add(butEdit);
//	butEdit.addActionListener(controller);
//	buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
//
//	return buttonPanel; 
//    }

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
	labRegion.setForeground(color);
	labRegion.setFont(GuiUtil.valueFont());
//	txtPersonnr.setForeground(color);
//	txtPersonnr.setFont(GuiUtil.valueFont());
//	txtHovedintervjuer.setForeground(color);
//	txtHovedintervjuer.setFont(GuiUtil.valueFont());
//	txtTelefonintervjuer.setForeground(color);
//	txtTelefonintervjuer.setFont(GuiUtil.valueFont());
//	txtSample.setForeground(color);
//	txtSample.setFont(GuiUtil.valueFont());
//	txtDelsample.setForeground(color);
//	txtDelsample.setFont(GuiUtil.valueFont());
    }
    
    private void copyDataFromModelToView() {
	labStatus.setText(model.getStatus().toString());
	labFullName.setText(model.getFirstName() + " " + model.getLastName());
	labGender.setText(model.getGender().toString());
	labTreatmentGroup.setText(model.getTreatmentGroup().toString());
	labRegion.setText(model.getRegion().toString());
	txtComment.setText(model.getComment());
//	txtPersonnr.setText(model.getPersnr());
//	txtAktuelt.setText(model.getImportmemo());
//	txtStatusForts.setText(model.calculateFollowUpStatus().toString());
//	txtSample.setText(model.getSample().toString());
//	txtDelsample.setText(model.getDelsample());
//	txtHovedintervjuer.setText(model.getHovedintervjuer().toString());
//	txtTelefonintervjuer.setText(model.getTelefonintervjuer().toString());
//	labAdmNotat.setText(model.getAdmnotat());
    }

}
