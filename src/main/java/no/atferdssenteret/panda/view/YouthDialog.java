package no.atferdssenteret.panda.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import no.atferdssenteret.panda.model.Target;
import no.atferdssenteret.panda.model.fft.Youth;
import no.atferdssenteret.panda.view.util.ButtonUtil;
import no.atferdssenteret.panda.view.util.DefaultTextArea;
import no.atferdssenteret.panda.view.util.DefaultTextField;
import no.atferdssenteret.panda.view.util.GridBagLayoutAutomat;
import no.atferdssenteret.panda.view.util.LabelFieldPair;

public class YouthDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private ActionListener actionListener;
    private DefaultTextField txtFirstName = new DefaultTextField(DefaultTextField.STANDARD_TEXT_WIDTH);
    private DefaultTextField txtLastName = new DefaultTextField(DefaultTextField.STANDARD_TEXT_WIDTH);
    private JComboBox cboxStatuses = new JComboBox(Target.Statuses.values());
    private JComboBox cboxTreatmentGroups = new JComboBox(Youth.TreatmentGroups.values());
    private JComboBox cboxRegions = new JComboBox(Youth.Regions.values());
    private DefaultTextField txtTreatmentStart = new DefaultTextField(DefaultTextField.DATE_WIDTH);
    private DefaultTextArea txtComment = new DefaultTextArea();
    
    public YouthDialog(Window parentWindow, ActionListener actionListener) {
	this.actionListener = actionListener;
	setModalityType(ModalityType.APPLICATION_MODAL);
	layoutContent();
	pack();
	setLocationRelativeTo(parentWindow);
    }

    public void layoutContent() {
	setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = 0;
	add(new JLabel("ID 1234"), c);
	
	List<LabelFieldPair> labelsAndFields = new LinkedList<LabelFieldPair>();
	labelsAndFields.add(new LabelFieldPair(new JLabel("Gruppe"), cboxTreatmentGroups));
	labelsAndFields.add(new LabelFieldPair(new JLabel("Status"), cboxStatuses));
	
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = 1;
	c.fill = GridBagConstraints.BOTH;
	add(GridBagLayoutAutomat.createPanelFor(labelsAndFields, true), c);

	labelsAndFields = new LinkedList<LabelFieldPair>(); 
	labelsAndFields.add(new LabelFieldPair(new JLabel("Fornavn"), txtFirstName));
	labelsAndFields.add(new LabelFieldPair(new JLabel("Etternavn"), txtLastName));
	labelsAndFields.add(new LabelFieldPair(new JLabel("Region"), cboxRegions));
	labelsAndFields.add(new LabelFieldPair(new JLabel("Behandlingsstart"), txtTreatmentStart));
	labelsAndFields.add(new LabelFieldPair(new JLabel("Kommentar"), txtComment));
	
	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = 2;
	c.weightx = 1;
	c.weighty = 1;
	c.fill = GridBagConstraints.BOTH;
	add(GridBagLayoutAutomat.createPanelFor(labelsAndFields, true), c);

	c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = 3;
	c.weightx = 1;
	c.weighty = 1;
	c.fill = GridBagConstraints.BOTH;
	add(ButtonUtil.createSaveCancelButtonPanel(actionListener, this), c);
    }
    
    public String getFirstName() {
	return txtFirstName.getText();	
    }
    
    public String getLastName() {
	return txtLastName.getText();
    }
    
    public Object getStatus() {
	return cboxStatuses.getSelectedItem();
    }
    
    public Object getTreatmentGroup() {
	return cboxTreatmentGroups.getSelectedItem();
    }
    
    public Object getRegion() {
	return cboxRegions.getSelectedItem();
    }
    
    public String getTreatmentStart() {
	return txtTreatmentStart.getText();
    }
    
    public String getComment() {
	return txtComment.getText();
    }
    
    public void setFirstName(String firstName) {
	txtFirstName.setText(firstName);
    }
    
    public void setLastName(String lastName) {
	txtLastName.setText(lastName);
    }
    
    public void setStatus(Object status) {
	cboxStatuses.setSelectedItem(status);
    }
    
    public void setTreatmentGroup(Object treatmentGroup) {
	cboxTreatmentGroups.setSelectedItem(treatmentGroup);
    }
    
    public void setRegion(Object region) {
	cboxRegions.setSelectedItem(region);
    }
    
    public void setTreatmentStart(String treatmentStart) {
	txtTreatmentStart.setText(treatmentStart);
    }
    
    public void setComment(String comment) {
	txtComment.setText(comment);
    }    
}