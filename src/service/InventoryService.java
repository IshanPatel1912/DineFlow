package service;

import dao.InventoryDAO;
import dao.ReportDAO;
import model.InventoryItem;
import java.util.List;

public class InventoryService {
    private InventoryDAO inventoryDAO = new InventoryDAO();
    private ReportDAO reportDAO = new ReportDAO(); // Hooked into the financial reports!

    public boolean addNewIngredient(String name, double quantity, String unit, double purchasePrice, double threshold) {
        if (name == null || name.trim().isEmpty() || quantity < 0 || threshold < 0 || purchasePrice < 0) return false;
        InventoryItem item = new InventoryItem(0, name, quantity, unit, purchasePrice, threshold);
        return inventoryDAO.addIngredient(item);
    }

    public List<InventoryItem> viewFullInventory() {
        return inventoryDAO.getAllInventory();
    }

    public boolean updateIngredientStock(int ingredientId, double newQuantity) {
        if (ingredientId <= 0 || newQuantity < 0) return false;
        return inventoryDAO.updateStock(ingredientId, newQuantity);
    }

    // THE MAGIC LOGIC: Deducts stock AND logs the financial loss
    public boolean manuallyDeductStock(int ingredientId, double amountUsed) {
        if (ingredientId <= 0 || amountUsed <= 0) return false;
        
        InventoryItem item = inventoryDAO.getIngredientById(ingredientId);
        
        if (item != null && inventoryDAO.deductStockById(ingredientId, amountUsed)) {
            // Calculate exact cash value lost
            double financialLoss = item.getPurchasePrice() * amountUsed;
            
            // Log it as an expense automatically
            if (financialLoss > 0) {
                reportDAO.logExpense("Inventory Loss/Spoilage (" + item.getIngredientName() + ")", financialLoss);
            }
            return true;
        }
        return false;
    }

    public boolean deductStockAutomatically(String ingredientName, double amount) {
        return inventoryDAO.decreaseStock(ingredientName, amount);
    }

    public List<InventoryItem> checkLowStockAlerts() {
        return inventoryDAO.getLowStockItems();
    }
}