package model;

public class RestaurantTable {
    private int tableId;
    private int tableNumber;
    private int capacity;
    private String status;

    public RestaurantTable(int tableId, int tableNumber, int capacity, String status) {
        this.tableId = tableId;
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.status = status;
    }

    public int getTableId() { return tableId; }
    public int getTableNumber() { return tableNumber; }
    public int getCapacity() { return capacity; }
    public String getStatus() { return status; }
}