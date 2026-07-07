package dao;

import model.Feedback;
import util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {

    public boolean addFeedback(Feedback feedback) {
        String sql = "INSERT INTO feedback (customer_id, rating, comments) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, feedback.getCustomerId());
            pstmt.setInt(2, feedback.getRating());
            pstmt.setString(3, feedback.getComments());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Feedback> getAllFeedback() {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM feedback ORDER BY feedback_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                list.add(new Feedback(
                    rs.getInt("feedback_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("rating"),
                    rs.getString("comments"),
                    rs.getTimestamp("feedback_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}