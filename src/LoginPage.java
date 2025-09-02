import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginPage extends JFrame {

    public LoginPage() {
        setTitle("Book Store | Login");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBackground(new Color(238, 238, 238));  // Background color for Book Store
        loginPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Title Label
        JLabel titleLabel = new JLabel("Login to Your Account");
        titleLabel.setForeground(new Color(24, 28, 20));  // Dark blue color from Book Store palette
        titleLabel.setFont(new Font("Netflix Sans", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Username Field
        JTextField userField = createTextField("Enter Username");
        userField.setFont(new Font("Poppins", Font.BOLD, 15));

        // Password Field
        JPasswordField passwordField = createPasswordField("Enter Password");
        JCheckBox showPasswordCheckbox = createShowPasswordCheckbox(passwordField);

        // Create a panel for the login button to center it
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));  // Center the button
        buttonPanel.setBackground(new Color(238, 238, 238));  // Same background color

        // Login Button (Centered)
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Poppins", Font.BOLD, 18));
        loginButton.setForeground(new Color(236,223,204));
        loginButton.setBackground(new Color(105, 117, 101));  // Orange from Book Store palette
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(375, 45));
        loginButton.setMaximumSize(new Dimension(400, 45));
        loginButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        // In LoginPage.java
        getRootPane().setDefaultButton(loginButton);

        // Login Button Action Listener
        loginButton.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passwordField.getPassword());

            // Validation check for empty fields
            if (username.equals("Enter Username") || password.equals("Enter Password")) {
                CustomOptionPane.showErrorDialog(this, "All fields are required!");
            }
            else if (username.equals("admin") && password.equals("admin")){
                CustomOptionPane.showMsgDialog(this, "Login successful!", "Login Status");
                new HomePage();
                dispose();
            }
            else {
                CustomOptionPane.showErrorDialog(this, "Invalid Username or Password");
            }
        });

        // Adding components with spacing
        loginPanel.add(titleLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        loginPanel.add(userField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(passwordField);
        loginPanel.add(showPasswordCheckbox);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Add the login button inside the centered buttonPanel
        buttonPanel.add(loginButton);
        loginPanel.add(buttonPanel);  // Add the button panel to the main panel
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        add(loginPanel);
        setVisible(true);
    }

    // Utility methods to create reusable UI components
    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField(15);
        textField.setMaximumSize(new Dimension(300, 40));
        textField.setFont(new Font("Poppins", Font.BOLD, 15));
        textField.setForeground(new Color(88, 92, 94));
        textField.setText(placeholder);
        textField.setBackground(new Color(236, 223, 204));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        // Focus listener for placeholder behavior
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(new Color(60, 61, 55));  // Dark blue from Book Store palette
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(new Color(88, 92, 94));
                }
            }
        });
        return textField;
    }

    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setMaximumSize(new Dimension(300, 40));
        passwordField.setFont(new Font("Poppins", Font.BOLD, 15));
        passwordField.setBackground(new Color(236, 223, 204));
        passwordField.setForeground(new Color(88, 92, 94));
        passwordField.setText(placeholder);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        // Focus listener for placeholder behavior
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setForeground(new Color(60, 61, 55));  // Dark blue from Book Store palette
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setText(placeholder);
                    passwordField.setForeground(new Color(88, 92, 94));
                }
            }
        });
        return passwordField;
    }

    private JCheckBox createShowPasswordCheckbox(JPasswordField passwordField) {
        JCheckBox checkBox = new JCheckBox("Show Password");
        checkBox.setBackground(new Color(238, 238, 238));  // Light background color for Book Store
        checkBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkBox.setFont(new Font("Netflix Sans", Font.BOLD, 10));
        checkBox.setForeground(new Color(88, 92, 94));
        checkBox.setFocusPainted(false);
        checkBox.setBorder(BorderFactory.createEmptyBorder(8, 200, 0, 0));

        // Toggle password visibility
        checkBox.addActionListener(e -> passwordField.setEchoChar(checkBox.isSelected() ? '\0' : '•'));
        return checkBox;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPage::new);
    }
}
