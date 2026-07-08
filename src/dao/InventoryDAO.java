package dao;

import model.InventoryItem;
import util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {

    public boolean addIngredient(InventoryItem item) {
        String sql = "INSERT INTO inventory (ingredient_name, stock_quantity, unit, purchase_price, minimum_threshold) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getIngredientName());
            pstmt.setDouble(2, item.getStockQuantity());
            pstmt.setString(3, item.getUnit());
            pstmt.setDouble(4, item.getPurchasePrice());
            pstmt.setDouble(5, item.getMinimumThreshold());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<InventoryItem> getAllInventory() {
        List<InventoryItem> inventory = new ArrayList<>();
        String sql = "SELECT * FROM inventory";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                inventory.add(new InventoryItem(
                    rs.getInt("ingredient_id"), rs.getString("ingredient_name"), rs.getDouble("stock_quantity"),
                    rs.getString("unit"), rs.getDouble("purchase_price"), rs.getDouble("minimum_threshold")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return inventory;
    }

    public InventoryItem getIngredientById(int id) {
        String sql = "SELECT * FROM inventory WHERE ingredient_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new InventoryItem(
                    rs.getInt("ingredient_id"), rs.getString("ingredient_name"), rs.getDouble("stock_quantity"),
                    rs.getString("unit"), rs.getDouble("purchase_price"), rs.getDouble("minimum_threshold")
                );
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean updateStock(int ingredientId, double newStockQuantity) {
        String sql = "UPDATE inventory SET stock_quantity = ? WHERE ingredient_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, newStockQuantity);
            pstmt.setInt(2, ingredientId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // NEW: Allows Manager to mark items as used (deducts from current stock)
    public boolean deductStockById(int ingredientId, double amountUsed) {
        String sql = "UPDATE inventory SET stock_quantity = stock_quantity - ? WHERE ingredient_id = ? AND stock_quantity >= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, amountUsed);
            pstmt.setInt(2, ingredientId);
            pstmt.setDouble(3, amountUsed);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean decreaseStock(String ingredientName, double amount) {
        String sql = "UPDATE inventory SET stock_quantity = stock_quantity - ? WHERE ingredient_name = ? AND stock_quantity >= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, amount);
            pstmt.setString(2, ingredientName);
            pstmt.setDouble(3, amount);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<InventoryItem> getLowStockItems() {
        List<InventoryItem> lowStock = new ArrayList<>();
        String sql = "SELECT * FROM inventory WHERE stock_quantity <= minimum_threshold";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                lowStock.add(new InventoryItem(
                    rs.getInt("ingredient_id"), rs.getString("ingredient_name"), rs.getDouble("stock_quantity"),
                    rs.getString("unit"), rs.getDouble("purchase_price"), rs.getDouble("minimum_threshold")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lowStock;
    }
}