package no.atferdssenteret.panda.view.util;

import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;

public class ButtonUtil {
	public final static String COMMAND_GOTO = "GOTO";
	public final static String COMMAND_CREATE = "CREATE";
	public final static String COMMAND_EDIT = "EDIT";
	public final static String COMMAND_DELETE = "DELETE";
	public final static String COMMAND_SAVE = "SAVE";
	public final static String COMMAND_CANCEL = "CANCEL";
	public final static String COMMAND_DOUBLE_CLICK = "DOUBLE_CLICK";

	public static List<JButton> createCRUDButtons(ActionListener actionListener) {
		List<JButton> buttons = new LinkedList<JButton>();
		buttons.add(deleteButton(actionListener));
		buttons.add(editButton(actionListener));
		buttons.add(createButton(actionListener));
		return buttons;
	}

	public static List<JButton> createSaveCancelButtons(ActionListener actionListener, RootPaneContainer window) {
		JButton saveButton = new JButton("Lagre");
		saveButton.setActionCommand(COMMAND_SAVE);
		saveButton.addActionListener(actionListener);

		JButton cancelButton = new JButton("Avbryt");
		cancelButton.setActionCommand(COMMAND_CANCEL);
		cancelButton.addActionListener(actionListener);

		window.getRootPane().setDefaultButton(saveButton);

		List<JButton> buttons = new LinkedList<JButton>();
		buttons.add(saveButton);
		buttons.add(cancelButton);

		return buttons;
	}

	public static JPanel createSaveCancelButtonPanel(ActionListener actionListener, RootPaneContainer window) {	
		return createButtonPanel(createSaveCancelButtons(actionListener, window));
	}

	public static JPanel createCRUDButtonPanel(ActionListener actionListener) {
		return createButtonPanel(createCRUDButtons(actionListener));
	}

	public static JPanel createButtonPanel(List<JButton> buttons) {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));	

		buttonPanel.add(Box.createHorizontalGlue());

		for (int i = 0; i < buttons.size(); i++) {
			buttonPanel.add(buttons.get(i));
			if (i < buttons.size()-1) {
				buttonPanel.add(GuiUtil.createHorizontalSpace());
			}
		}	
		return buttonPanel;
	}

	public static JButton createButton(ActionListener actionListener) {
		JButton button = new JButton("Opprett..."); 
		button.setActionCommand(COMMAND_CREATE);
		button.addActionListener(actionListener);
		return button;
	}
	
	public static JButton editButton(ActionListener actionListener) {
		JButton button = new JButton("Endre..."); 
		button.setActionCommand(COMMAND_EDIT);
		button.addActionListener(actionListener);
		return button;
	}
	
	public static JButton deleteButton(ActionListener actionListener) {
		JButton button = new JButton("Slett..."); 
		button.setActionCommand(COMMAND_DELETE);
		button.addActionListener(actionListener);
		return button;
	}
}

