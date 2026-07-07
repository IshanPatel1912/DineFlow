package model;

public class InventoryItem {
    private int ingredientId;
    private String ingredientName;
    private double stockQuantity;
    private String unit;
    private double minimumThreshold;

    public InventoryItem() {}

    public InventoryItem(int ingredientId, String ingredientName, double stockQuantity, String unit, double minimumThreshold) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.stockQuantity = stockQuantity;
        this.unit = unit;
        this.minimumThreshold = minimumThreshold;
    }

    public int getIngredientId() { return ingredientId; }
    public void setIngredientId(int ingredientId) { this.ingredientId = ingredientId; }

    public String getIngredientName() { return ingredientName; }
    public void setIngredientName(String ingredientName) { this.ingredientName = ingredientName; }

    public double getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(double stockQuantity) { this.stockQuantity = stockQuantity; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public double getMinimumThreshold() { return minimumThreshold; }
    public void setMinimumThreshold(double minimumThreshold) { this.minimumThreshold = minimumThreshold; }
}