package dao;

import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BudgetDAO {

    public static boolean setBudget(String username, String monthYear, double amount) {
        String sql = "REPLACE INTO budget (username, month_year, amount) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, monthYear);
            stmt.setDouble(3, amount);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static double getBudget(String username, String monthYear) {
        String sql = "SELECT amount FROM budget WHERE username = ? AND month_year = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, monthYear);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble("amount");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }
}
