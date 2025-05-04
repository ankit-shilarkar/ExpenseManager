package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;

    public SignUpUI() {
        setTitle("Sign Up - Expense Manager");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
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

        // Register button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        registerButton = new JButton("Register");
        panel.add(registerButton, gbc);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(SignUpUI.this, "Please fill all fields.");
                    return;
                }

                User newUser = new User(username, password);
                boolean success = UserDAO.registerUser(newUser);

                if (success) {
                    JOptionPane.showMessageDialog(SignUpUI.this, "Registration successful!");
                    dispose(); // close window
                } else {
                    JOptionPane.showMessageDialog(SignUpUI.this, "Username already exists or error occurred.");
                }
            }
        });

        add(panel);
        setVisible(true);
    }
}
