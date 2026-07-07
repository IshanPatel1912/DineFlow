package service;

import dao.BillDAO;
import model.Bill;
import model.Payment;
import util.InvoiceFileHandler;

public class BillingService implements Payment {

    private BillDAO billDAO;
    private InvoiceFileHandler fileHandler;
    private InventoryService inventoryService;
    private static final double TAX_RATE = 0.05;

    public BillingService() {
        this.billDAO = new BillDAO();
        this.fileHandler = new InvoiceFileHandler();
        this.inventoryService = new InventoryService();
    }

    @Override
    public boolean processPayment(double amount, String method) {
        return amount > 0 && method != null && !method.trim().isEmpty();
    }

    public Bill generateBill(int orderId, double totalAmount, double discount, String paymentMethod) {
        if (orderId <= 0 || !processPayment(totalAmount, paymentMethod)) {
            return null;
        }

        double tax = totalAmount * TAX_RATE;
        double finalAmount = (totalAmount + tax) - discount;

        Bill newBill = new Bill();
        newBill.setOrderId(orderId);
        newBill.setTotalAmount(totalAmount);
        newBill.setTax(tax);
        newBill.setDiscount(discount);
        newBill.setFinalAmount(finalAmount);
        newBill.setPaymentMethod(paymentMethod);

        Bill savedBill = billDAO.createBill(newBill);

        if (savedBill != null) {
            fileHandler.generateInvoiceFile(savedBill);
            
            inventoryService.deductStockAutomatically("Beef Patties", 1.0);
            inventoryService.deductStockAutomatically("Potatoes", 0.5);
            inventoryService.deductStockAutomatically("Soda Syrup", 0.2);
            
            savedBill.printDetails();
        }

        return savedBill;
    }
}