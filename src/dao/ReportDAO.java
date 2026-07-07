package dao;

import util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportDAO {

    public double getRevenue(String period) {
        String sql = "";
        if (period.equals("DAILY")) {
            sql = "SELECT SUM(final_amount) FROM bills WHERE DATE(bill_time) = CURDATE()";
        } else if (period.equals("WEEKLY")) {
            sql = "SELECT SUM(final_amount) FROM bills WHERE YEARWEEK(bill_time, 1) = YEARWEEK(CURDATE(), 1)";
        } else if (period.equals("YEARLY")) {
            sql = "SELECT SUM(final_amount) FROM bills WHERE YEAR(bill_time) = YEAR(CURDATE())";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public double getTotalMonthlySalaries() {
        String sql = "SELECT SUM(salary) FROM employees";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}