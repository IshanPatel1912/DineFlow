package model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private int tableId;
    private Integer customerId;
    private String orderStatus;
    private Timestamp orderTime;
    private double totalAmount;
    private List<OrderItem> items;

    public Order() {
        this.items = new ArrayList<>();
    }

    public Order(int orderId, int tableId, Integer customerId, String orderStatus, Timestamp orderTime, double totalAmount) {
        this.orderId = orderId;
        this.tableId = tableId;
        this.customerId = customerId;
        this.orderStatus = orderStatus;
        this.orderTime = orderTime;
        this.totalAmount = totalAmount;
        this.items = new ArrayList<>();
    }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getTableId() { return tableId; }
    public void setTableId(int tableId) { this.tableId = tableId; }

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public Timestamp getOrderTime() { return orderTime; }
    public void setOrderTime(Timestamp orderTime) { this.orderTime = orderTime; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
    public void addItem(OrderItem item) { this.items.add(item); }
}