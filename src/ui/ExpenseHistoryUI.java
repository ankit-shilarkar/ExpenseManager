package ui;

import model.Expense;
import dao.ExpenseDAO;
import util.DateUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ExpenseHistoryUI extends JFrame {

    public ExpenseHistoryUI(String username) {
        setTitle("Expense History - " + DateUtil.getCurrentMonthYear());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Table setup
        String[] columns = {"Amount", "Category", "Description", "Date"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Get expenses from DAO
        List<Expense> expenses = ExpenseDAO.getMonthlyExpenses(username, DateUtil.getCurrentMonthYear());

        for (Expense exp : expenses) {
            Object[] row = {
                    "₹" + exp.getAmount(),
                    exp.getCategory(),
                    exp.getDescription(),
                    exp.getDate().toString()
            };
            tableModel.addRow(row);
        }

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
