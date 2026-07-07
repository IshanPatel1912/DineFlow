package dao;

import model.MenuItem;
import util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {

    public boolean addMenuItem(MenuItem item) {
        String sql = "INSERT INTO menu_items (name, description, price, category, is_available) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getDescription());
            pstmt.setDouble(3, item.getPrice());
            pstmt.setString(4, item.getCategory());
            pstmt.setBoolean(5, item.isAvailable());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<MenuItem> getAllMenuItems() {
        List<MenuItem> menu = new ArrayList<>();
        String sql = "SELECT * FROM menu_items";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                menu.add(new MenuItem(
                    rs.getInt("item_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getString("category"),
                    rs.getBoolean("is_available")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menu;
    }

    public boolean updateMenuItem(MenuItem item) {
        String sql = "UPDATE menu_items SET name = ?, description = ?, price = ?, category = ?, is_available = ? WHERE item_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getDescription());
            pstmt.setDouble(3, item.getPrice());
            pstmt.setString(4, item.getCategory());
            pstmt.setBoolean(5, item.isAvailable());
            pstmt.setInt(6, item.getItemId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMenuItem(int itemId) {
        String sql = "DELETE FROM menu_items WHERE item_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, itemId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}