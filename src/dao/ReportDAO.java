package dao;

import util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportDAO {

    public double getRevenue(String period) {
        String sql = buildDateQuery("SUM(final_amount)", "bills", "bill_time", period);
        return executeDoubleQuery(sql);
    }

    public double getActualSalariesPaid(String period) {
        String sql = buildDateQuery("SUM(amount)", "salary_payments", "payment_date", period);
        return executeDoubleQuery(sql);
    }

    public double getOtherExpenses(String period) {
        String sql = buildDateQuery("SUM(amount)", "expenses", "expense_date", period);
        return executeDoubleQuery(sql);
    }
    
    public double getTotalInventoryAssetValue() {
        String sql = "SELECT SUM(stock_quantity * purchase_price) FROM inventory";
        return executeDoubleQuery(sql);
    }

    public void logExpense(String desc, double amount) {
        String sql = "INSERT INTO expenses (description, amount) VALUES (?, ?)";
        try (java.sql.Connection conn = util.DatabaseConnection.getConnection();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, desc);
            pstmt.setDouble(2, amount);
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void paySalary(int empId, double amount) {
        String sql = "INSERT INTO salary_payments (emp_id, amount) VALUES (?, ?)";
        try (java.sql.Connection conn = util.DatabaseConnection.getConnection();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, empId);
            pstmt.setDouble(2, amount);
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private String buildDateQuery(String select, String table, String dateCol, String period) {
        if (period.equals("DAILY")) return "SELECT " + select + " FROM " + table + " WHERE DATE(" + dateCol + ") = CURDATE()";
        if (period.equals("WEEKLY")) return "SELECT " + select + " FROM " + table + " WHERE YEARWEEK(" + dateCol + ", 1) = YEARWEEK(CURDATE(), 1)";
        return "SELECT " + select + " FROM " + table + " WHERE YEAR(" + dateCol + ") = YEAR(CURDATE())";
    }

    private double executeDoubleQuery(String sql) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0.0;
    }
}