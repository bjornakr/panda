package no.atferdssenteret.panda.view;

import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import no.atferdssenteret.panda.model.ParticipantRoles;
import no.atferdssenteret.panda.model.ParticipationStatuses;
import no.atferdssenteret.panda.view.util.ButtonUtil;
import no.atferdssenteret.panda.view.util.DefaultTextArea;
import no.atferdssenteret.panda.view.util.DefaultTextField;
import no.atferdssenteret.panda.view.util.GridBagLayoutAutomat;
import no.atferdssenteret.panda.view.util.GuiUtil;
import no.atferdssenteret.panda.view.util.LabelFieldPair;

public class ParticipantDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private ActionListener actionListener;
	private JComboBox cboxRoles = new JComboBox(ParticipantRoles.values());
	private JComboBox cboxStatuses = new JComboBox(ParticipationStatuses.values());
	private DefaultTextField txtFirstName = new DefaultTextField();
	private DefaultTextField txtLastName = new DefaultTextField();
	private DefaultTextArea txtContactInfo = new DefaultTextArea();
	private DefaultTextField txtTlfNo = new DefaultTextField();
	private DefaultTextField txtEmail = new DefaultTextField();
	private DefaultTextArea txtAddress = new DefaultTextArea();
	private DefaultTextArea txtComment = new DefaultTextArea();


	public ParticipantDialog(Window parentWindow, ActionListener actionListener) {
		this.actionListener = actionListener;
		setModalityType(ModalityType.APPLICATION_MODAL);
		layoutContent();
		pack();
		setLocationRelativeTo(parentWindow);
	}

	public void layoutContent() {
		setLayout(new GridBagLayout());

		List<LabelFieldPair> labelsAndFields = new LinkedList<LabelFieldPair>();
		labelsAndFields.add(new LabelFieldPair(new JLabel("Deltaker"), cboxRoles));
		cboxStatuses.setSelectedItem(ParticipationStatuses.WAITING_FOR_CONSENT);
		labelsAndFields.add(new LabelFieldPair(new JLabel("Status"), cboxStatuses));
		JLabel labFirstName = new JLabel("Fornavn");
		GuiUtil.setNotNullFlag(labFirstName);
		labelsAndFields.add(new LabelFieldPair(labFirstName, txtFirstName));
		JLabel labLastName = new JLabel("Etternavn");
		GuiUtil.setNotNullFlag(labLastName);
		labelsAndFields.add(new LabelFieldPair(labLastName, txtLastName));
		labelsAndFields.add(new LabelFieldPair(new JLabel("Tlf. nr"), txtTlfNo));
		labelsAndFields.add(new LabelFieldPair(new JLabel("E-post"), txtEmail));
		labelsAndFields.add(new LabelFieldPair(new JLabel("Adresse"), txtAddress));
		labelsAndFields.add(new LabelFieldPair(new JLabel("Kontaktinformasjon"), txtContactInfo));
		labelsAndFields.add(new LabelFieldPair(new JLabel("Kommentar"), txtComment));
		add(GridBagLayoutAutomat.createPanelFor(labelsAndFields, true), GridBagLayoutAutomat.typicalConstraintsForPanel(0, 0));
		add(ButtonUtil.createSaveCancelButtonPanel(actionListener, this), GridBagLayoutAutomat.constraintsForButtonPanel(1));
	}

	public void restrictAccess() {
		cboxStatuses.setEnabled(false);
	}
	
	public String getFirstName() {
		return txtFirstName.getText();
	}

	public void setFirstName(String firstName) {
		txtFirstName.setText(firstName);
	}

	public String getLastName() {
		return txtLastName.getText();
	}

	public void setLastName(String lastName) {
		txtLastName.setText(lastName);
	}

	public String getContactInfo() {
		return txtContactInfo.getText();
	}

	public void setContactInfo(String contactInfo) {
		txtContactInfo.setText(contactInfo);
	}

	public String getPhoneNo() {
		return txtTlfNo.getText();
	}

	public void setPhoneNo(String tlfNo) {
		txtTlfNo.setText(tlfNo);
	}

	public String getEMail() {
		return txtEmail.getText();
	}

	public void setEMail(String eMail) {
		txtEmail.setText(eMail);
	}
	
	public String getAddress() {
		return txtAddress.getText();
	}
	
	public void setAddress(String address) {
		txtAddress.setText(address);
	}

	public String getComment() {
		return txtComment.getText();
	}

	public void setComment(String comment) {
		txtComment.setText(comment);
	}

	public Object getRole() {
		return cboxRoles.getSelectedItem();
	}

	public void setRole(String role) {
		cboxRoles.setSelectedItem(role);
	}

	public void setStatus(ParticipationStatuses status) {
		cboxStatuses.setSelectedItem(status);

	}

	public Object getStatus() {
		return cboxStatuses.getSelectedItem();
	}
}
