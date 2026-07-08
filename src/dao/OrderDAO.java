package dao;

import model.Order;
import model.OrderItem;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OrderDAO {

    public boolean createOrder(Order order) {
        String insertOrderSql = "INSERT INTO orders (table_id, customer_id, order_status, total_amount) VALUES (?, ?, ?, ?)";
        String insertItemSql = "INSERT INTO order_items (order_id, item_id, quantity, subtotal) VALUES (?, ?, ?, ?)";
        
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            try (PreparedStatement pstmt = conn.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, order.getTableId());
                if (order.getCustomerId() != null && order.getCustomerId() > 0) {
                    pstmt.setInt(2, order.getCustomerId());
                } else {
                    pstmt.setNull(2, java.sql.Types.INTEGER);
                }
                pstmt.setString(3, "PENDING");
                pstmt.setDouble(4, order.getTotalAmount());
                
                pstmt.executeUpdate();
                
                ResultSet rs = pstmt.getGeneratedKeys();
                int newOrderId = 0;
                if (rs.next()) {
                    newOrderId = rs.getInt(1);
                }
                
                try (PreparedStatement itemStmt = conn.prepareStatement(insertItemSql)) {
                    for (OrderItem item : order.getItems()) {
                        itemStmt.setInt(1, newOrderId);
                        itemStmt.setInt(2, item.getItemId());
                        itemStmt.setInt(3, item.getQuantity());
                        itemStmt.setDouble(4, item.getSubtotal());
                        itemStmt.addBatch();
                    }
                    itemStmt.executeBatch();
                }
                
                conn.commit();
                return true;
            }
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try { 
                    conn.setAutoCommit(true);
                    conn.close(); 
                } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    public java.util.List<model.RestaurantTable> getAllTables() {
        java.util.List<model.RestaurantTable> tables = new java.util.ArrayList<>();
        String sql = "SELECT * FROM restaurant_tables";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                tables.add(new model.RestaurantTable(
                    rs.getInt("table_id"),
                    rs.getInt("table_number"),
                    rs.getInt("capacity"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return tables;
    }

    public boolean updateTable(int tableId, int capacity, String status) {
        String sql = "UPDATE restaurant_tables SET capacity = ?, status = ? WHERE table_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, capacity);
            pstmt.setString(2, status);
            pstmt.setInt(3, tableId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public void addTable(int tableNo, int capacity) {
        String sql = "INSERT INTO restaurant_tables (table_number, capacity, status) VALUES (?, ?, 'AVAILABLE')";
        try (java.sql.Connection conn = util.DatabaseConnection.getConnection();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tableNo);
            pstmt.setInt(2, capacity);
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Order(
                    rs.getInt("order_id"),
                    rs.getInt("table_id"),
                    (Integer) rs.getObject("customer_id"),
                    rs.getString("order_status"),
                    rs.getTimestamp("order_time"),
                    rs.getDouble("total_amount")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Queue<Order> getPendingOrders() {
        Queue<Order> queue = new LinkedList<>();
        String sql = "SELECT * FROM orders WHERE order_status = 'PENDING' ORDER BY order_time ASC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                queue.add(new Order(
                    rs.getInt("order_id"),
                    rs.getInt("table_id"),
                    (Integer) rs.getObject("customer_id"),
                    rs.getString("order_status"),
                    rs.getTimestamp("order_time"),
                    rs.getDouble("total_amount")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return queue;
    }

    public boolean updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET order_status = ? WHERE order_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, orderId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                items.add(new OrderItem(
                    rs.getInt("order_item_id"),
                    rs.getInt("order_id"),
                    rs.getInt("item_id"),
                    rs.getInt("quantity"),
                    rs.getDouble("subtotal")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}