package dao;

import model.Reservation;
import util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.PriorityQueue;
import java.util.Comparator;

public class ReservationDAO {

    public boolean createReservation(Reservation reservation) {
        String sql = "INSERT INTO reservations (customer_id, table_id, reservation_time, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, reservation.getCustomerId());
            pstmt.setInt(2, reservation.getTableId());
            pstmt.setTimestamp(3, reservation.getReservationTime());
            pstmt.setString(4, reservation.getStatus());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public PriorityQueue<Reservation> getPendingReservations() {
        PriorityQueue<Reservation> queue = new PriorityQueue<>(Comparator.comparing(Reservation::getReservationTime));
        String sql = "SELECT * FROM reservations WHERE status = 'PENDING'";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                queue.add(new Reservation(
                    rs.getInt("reservation_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("table_id"),
                    rs.getTimestamp("reservation_time"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return queue;
    }

    public boolean updateReservationStatus(int reservationId, String status) {
        String sql = "UPDATE reservations SET status = ? WHERE reservation_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            pstmt.setInt(2, reservationId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}