package dao;

import model.Bill;
import util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BillDAO {

    public Bill createBill(Bill bill) {
        String sql = "INSERT INTO bills (order_id, total_amount, tax, discount, final_amount, payment_method) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, bill.getOrderId());
            pstmt.setDouble(2, bill.getTotalAmount());
            pstmt.setDouble(3, bill.getTax());
            pstmt.setDouble(4, bill.getDiscount());
            pstmt.setDouble(5, bill.getFinalAmount());
            pstmt.setString(6, bill.getPaymentMethod());
            
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                bill.setBillId(rs.getInt(1));
                return bill;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}