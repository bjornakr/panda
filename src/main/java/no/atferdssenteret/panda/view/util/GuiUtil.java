package no.atferdssenteret.panda.view.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class GuiUtil {
    public final static Font HEADER_FONT_PRIMARY = new Font("Verdana", Font.BOLD, 25);
    public final static Font HEADER_FONT_SECONDARY = new Font("Verdana", Font.BOLD, 20);

    public static Border createPaddedEtchedBorder(int paddingTop, int paddingBottom) {
	Border b = BorderFactory.createEmptyBorder(5, 5, 5, 5);
	b = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(), b);
	b = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(paddingTop, 5, paddingBottom, 5), b);

	return b;
    }

    public static Component createHorizontalSpace() {
	return Box.createRigidArea(new Dimension(10, 0));
    }

    public static Component createHorizontalSpace(int width) {
	return Box.createRigidArea(new Dimension(width, 0));
    }

    public static Font valueFont() {
	return new Font("SansSerif", Font.BOLD, 14);
    }

    public static Border createTitledBorder(String title) {
	Border border = new TitledBorder(BorderFactory.createEtchedBorder(), title,
		TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
		GuiUtil.HEADER_FONT_SECONDARY);

	return border;
    }

    public static String formatChildID(Integer childID) {
	DecimalFormat idFormat = new DecimalFormat("0000");
	return idFormat.format(childID);
    }


    /**
     * Adds a red bullet icon to a label, which is useful for marking fieds
     * that must be filled out. 
     */
    public static void setNotNullFlag(JLabel label) {
	label.setIcon(new ImageIcon("graphics/icons/bullet_red.png"));
    }
}

