package model;

import java.sql.Timestamp;

public class Bill implements Printable {
    private int billId;
    private int orderId;
    private double totalAmount;
    private double tax;
    private double discount;
    private double finalAmount;
    private String paymentMethod;
    private Timestamp billTime;

    public Bill() {}

    public Bill(int billId, int orderId, double totalAmount, double tax, double discount, double finalAmount, String paymentMethod, Timestamp billTime) {
        this.billId = billId;
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.tax = tax;
        this.discount = discount;
        this.finalAmount = finalAmount;
        this.paymentMethod = paymentMethod;
        this.billTime = billTime;
    }

    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public double getTax() { return tax; }
    public void setTax(double tax) { this.tax = tax; }

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }

    public double getFinalAmount() { return finalAmount; }
    public void setFinalAmount(double finalAmount) { this.finalAmount = finalAmount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public Timestamp getBillTime() { return billTime; }
    public void setBillTime(Timestamp billTime) { this.billTime = billTime; }

    @Override
    public void printDetails() {
        System.out.printf("Bill #%d | Order #%d | Total: ₹%.2f | Method: %s\n", 
            this.billId, this.orderId, this.finalAmount, this.paymentMethod);
    }
}