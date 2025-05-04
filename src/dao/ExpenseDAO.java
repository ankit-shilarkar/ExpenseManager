package dao;

import util.DBUtil;

import java.sql.*;
import java.time.LocalDate;

public class ExpenseDAO {

    public static boolean addExpense(String username, double amount, String category, String description, LocalDate date) {
        String sql = "INSERT INTO expenses (username, amount, category, description, date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setDouble(2, amount);
            stmt.setString(3, category);
            stmt.setString(4, description);
            stmt.setDate(5, Date.valueOf(date));

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static double getTotalSpent(String username, String monthYear) {
        String sql = "SELECT SUM(amount) FROM expenses WHERE username = ? AND DATE_FORMAT(date, '%Y-%m') = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, monthYear);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }
}
