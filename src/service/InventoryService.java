package service;

import dao.InventoryDAO;
import model.InventoryItem;
import java.util.List;

public class InventoryService {

    private InventoryDAO inventoryDAO;

    public InventoryService() {
        this.inventoryDAO = new InventoryDAO();
    }

    public boolean addNewIngredient(String name, double quantity, String unit, double threshold) {
        if (name == null || name.trim().isEmpty() || quantity < 0 || threshold < 0) {
            return false;
        }
        InventoryItem item = new InventoryItem(0, name, quantity, unit, threshold);
        return inventoryDAO.addIngredient(item);
    }

    public List<InventoryItem> viewFullInventory() {
        return inventoryDAO.getAllInventory();
    }

    public boolean updateIngredientStock(int ingredientId, double newQuantity) {
        if (ingredientId <= 0 || newQuantity < 0) {
            return false;
        }
        return inventoryDAO.updateStock(ingredientId, newQuantity);
    }

    public boolean removeIngredient(int ingredientId) {
        if (ingredientId <= 0) {
            return false;
        }
        return inventoryDAO.deleteIngredient(ingredientId);
    }

    public boolean deductStockAutomatically(String ingredientName, double amount) {
        if (ingredientName == null || amount <= 0) {
            return false;
        }
        return inventoryDAO.decreaseStock(ingredientName, amount);
    }
    
    public List<InventoryItem> checkLowStockAlerts() {
        return inventoryDAO.getLowStockItems();
    }
}