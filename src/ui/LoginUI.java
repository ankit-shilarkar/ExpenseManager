package ui;

import dao.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, signupButton;

    public LoginUI() {
        setTitle("Login - Expense Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null); // center the window

        // Panel setup
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(20);
        panel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginButton = new JButton("Login");
        panel.add(loginButton, gbc);

        gbc.gridx = 1;
        signupButton = new JButton("Sign Up");
        panel.add(signupButton, gbc);

        // Button Actions
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Placeholder for login logic
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginUI.this, "Please enter both fields.");
                    return;
                }
//                JOptionPane.showMessageDialog(LoginUI.this, "Login attempt: " + username + " / " + password);
                boolean success = UserDAO.loginUser(username, password);

                if (success) {
                    JOptionPane.showMessageDialog(LoginUI.this, "Login successful!");
                    dispose(); // Close login window

                    // TODO: Open dashboard/main app window
                    new DashboardUI(username);
                } else {
                    JOptionPane.showMessageDialog(LoginUI.this, "Invalid username or password.");
                }
            }
        });

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Placeholder for signup logic
//                JOptionPane.showMessageDialog(LoginUI.this, "Sign up screen coming soon!");
                new SignUpUI();
            }
        });

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginUI();
    }
}
