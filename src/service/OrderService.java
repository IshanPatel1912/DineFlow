package service;

import dao.OrderDAO;
import model.Order;
import model.OrderItem;
import java.util.Queue;
import java.util.List;

public class OrderService {

    private OrderDAO orderDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO();
    }

    public boolean placeOrder(int tableId, Integer customerId, List<OrderItem> items) {
        if (tableId <= 0 || items == null || items.isEmpty()) return false;
        
        double totalAmount = 0.0;
        for (OrderItem item : items) {
            totalAmount += item.getSubtotal();
        }
        
        Order newOrder = new Order();
        newOrder.setTableId(tableId);
        newOrder.setCustomerId(customerId);
        newOrder.setTotalAmount(totalAmount);
        newOrder.setItems(items);
        
        return orderDAO.createOrder(newOrder);
    }

    public Order getOrderDetails(int orderId) {
        return orderDAO.getOrderById(orderId);
    }

    public Queue<Order> getKitchenQueue() {
        return orderDAO.getPendingOrders();
    }

    public boolean updateOrderStatus(int orderId, String status) {
        if (orderId <= 0 || status == null || status.trim().isEmpty()) return false;
        return orderDAO.updateOrderStatus(orderId, status);
    }

    // NEW METHOD
    public List<OrderItem> getItemsForOrder(int orderId) {
        return orderDAO.getOrderItemsByOrderId(orderId);
    }
}