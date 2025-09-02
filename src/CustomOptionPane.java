import javax.swing.*;
import java.awt.*;

public class CustomOptionPane {
    public static void showErrorDialog(JFrame parent, String message) {
        // Set Custom Background and Foreground
        UIManager.put("OptionPane.background", new Color(238,238,238));
        UIManager.put("OptionPane.messageForeground", new Color(60, 61, 55));

        // Set custom button color and font
        UIManager.put("Button.background", new Color(105, 117, 101));
        UIManager.put("Button.foreground", new Color(236, 223, 204));
        UIManager.put("Button.focusPainted",false);
        UIManager.put("Button.font", new Font("Netflix Sans", Font.PLAIN, 12));
        UIManager.put("Button.border",(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true),
                BorderFactory.createEmptyBorder(5, 50, 5, 50))));

        // Customize message font
        UIManager.put("OptionPane.messageFont", new Font("Netflix Sans", Font.PLAIN, 12));
        UIManager.put("OptionPane.buttonFont", new Font("Netflix Sans", Font.PLAIN, 12));

        // Display the dialog
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showMsgDialog(Component parent, String message, String title) {
        // Custom JOptionPane settings
        UIManager.put("OptionPane.messageFont", new Font("Netflix Sans", Font.PLAIN, 12));
        UIManager.put("OptionPane.background", new Color(238,238,238));
        UIManager.put("OptionPane.messageForeground", new Color(60, 61, 55));
        UIManager.put("Button.focusPainted",false);
        UIManager.put("Button.background", new Color(105, 117, 101));
        UIManager.put("Button.foreground", new Color(236, 223, 204));
        UIManager.put("Button.font", new Font("Netflix Sans", Font.BOLD, 12));
        UIManager.put("Button.border",(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2, true),
                BorderFactory.createEmptyBorder(5, 50, 5, 50))));

        // Use PLAIN_MESSAGE to avoid any default icons
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.CLOSED_OPTION);

        // Reset UIManager settings after showing the dialog
        UIManager.put("OptionPane.messageFont", null);
        UIManager.put("OptionPane.messageForeground", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
        UIManager.put("Button.focusPainted", null);
        UIManager.put("Button.font", null);
        UIManager.put("Button.border",(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2, true),
                BorderFactory.createEmptyBorder(5, 50, 5, 50))));
    }


}

