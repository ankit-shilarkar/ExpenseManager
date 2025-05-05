package ui;

import javax.swing.*;
import java.awt.*;

public class DashboardUI extends JFrame {

    private JLabel welcomeLabel;
    private JLabel budgetLabel;
    private JLabel spentLabel;
    private JButton addExpenseButton;
    private JButton setBudgetButton;
    private JButton logoutButton;
    private JButton viewHistoryButton;

    private String username;

    // Version 1
//    private void updateBudgetLabels() {
//        String monthYear = util.DateUtil.getCurrentMonthYear();
//        double budget = dao.BudgetDAO.getBudget(username, monthYear);
//
//        budgetLabel.setText("Monthly Budget: ₹" + budget);
//        // Spent will be updated later when we track expenses
//    }
    // Version 2
    private void updateBudgetLabels() {
        String monthYear = util.DateUtil.getCurrentMonthYear();
        double budget = dao.BudgetDAO.getBudget(username, monthYear);
        double spent = dao.ExpenseDAO.getTotalSpent(username, monthYear);

        budgetLabel.setText("Monthly Budget: ₹" + budget);
        spentLabel.setText("Total Spent: ₹" + spent);

        // Optional: show warning or reward
        if (budget > 0) {
            if (spent > budget) {
                JOptionPane.showMessageDialog(this, "⚠️ You are over budget!", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (budget - spent >= 1000) {
                JOptionPane.showMessageDialog(this, "🎉 You’re managing well! Keep it up!", "Great!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


    public DashboardUI(String username) {
        this.username = username;

        setTitle("Dashboard - Expense Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        welcomeLabel = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        budgetLabel = new JLabel("Monthly Budget: ₹0", SwingConstants.CENTER);
        spentLabel = new JLabel("Total Spent: ₹0", SwingConstants.CENTER);

        setBudgetButton = new JButton("Set Monthly Budget");
        addExpenseButton = new JButton("Add Expense");
        logoutButton = new JButton("Logout");
        viewHistoryButton = new JButton("View Expense History");

        panel.add(welcomeLabel);
        panel.add(budgetLabel);
        panel.add(spentLabel);
        panel.add(setBudgetButton);
        panel.add(addExpenseButton);
        panel.add(viewHistoryButton);
        panel.add(logoutButton);

        add(panel);
        String monthYear = util.DateUtil.getCurrentMonthYear();
        double budget = dao.BudgetDAO.getBudget(username, monthYear);

        if (budget == 0.0) {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "No budget set for " + monthYear + ". Would you like to set it now?",
                    "Set Budget",
                    JOptionPane.YES_NO_OPTION
            );
            if (choice == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(() -> {
                    String amountStr = JOptionPane.showInputDialog(DashboardUI.this, "Enter monthly budget (₹):");

                    if (amountStr != null && !amountStr.trim().isEmpty()) {
                        try {
                            double amount = Double.parseDouble(amountStr);
                            boolean success = dao.BudgetDAO.setBudget(username, monthYear, amount);

                            if (success) {
                                JOptionPane.showMessageDialog(DashboardUI.this, "Budget set for " + monthYear);
                                updateBudgetLabels(); // Refresh display
                            } else {
                                JOptionPane.showMessageDialog(DashboardUI.this, "Failed to set budget.");
                            }

                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(DashboardUI.this, "Invalid amount.");
                        }
                    }
                });
            }
        } else {
            updateBudgetLabels();
        }

        setVisible(true);

        // TODO: Add action listeners to handle buttons
        setBudgetButton.addActionListener(e -> {
            String amountStr = JOptionPane.showInputDialog(DashboardUI.this, "Enter monthly budget (₹):");

            if (amountStr != null && !amountStr.trim().isEmpty()) {
                try {
                    double amount = Double.parseDouble(amountStr);
//                    String monthYear = util.DateUtil.getCurrentMonthYear();

                    boolean success = dao.BudgetDAO.setBudget(username, monthYear, amount);
                    if (success) {
                        JOptionPane.showMessageDialog(DashboardUI.this, "Budget set for " + monthYear);
                        updateBudgetLabels(); // Refresh display
                    } else {
                        JOptionPane.showMessageDialog(DashboardUI.this, "Failed to set budget.");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(DashboardUI.this, "Invalid amount.");
                }
            }
        });
        addExpenseButton.addActionListener(e -> {
            JTextField amountField = new JTextField();
            JTextField categoryField = new JTextField();
            JTextField descField = new JTextField();
            JPanel expensePanel = new JPanel(new GridLayout(0, 1));

            expensePanel.add(new JLabel("Amount:"));
            expensePanel.add(amountField);
            expensePanel.add(new JLabel("Category:"));
            expensePanel.add(categoryField);
            expensePanel.add(new JLabel("Description:"));
            expensePanel.add(descField);

            int result = JOptionPane.showConfirmDialog(DashboardUI.this, expensePanel, "Add Expense", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    double amount = Double.parseDouble(amountField.getText().trim());
                    String category = categoryField.getText().trim();
                    String description = descField.getText().trim();

                    boolean success = dao.ExpenseDAO.addExpense(username, amount, category, description, java.time.LocalDate.now());

                    if (success) {
                        JOptionPane.showMessageDialog(DashboardUI.this, "Expense added.");
                        updateBudgetLabels(); // Update spent amount
                    } else {
                        JOptionPane.showMessageDialog(DashboardUI.this, "Failed to add expense.");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(DashboardUI.this, "Invalid amount.");
                }
            }
        });
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    DashboardUI.this,
                    "Are you sure you want to logout?",
                    "Logout Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                dispose(); // Close DashboardUI
                new ui.LoginUI(); // Launch LoginUI
            }
        });
        viewHistoryButton.addActionListener(e -> new ExpenseHistoryUI(username));

    }
}
