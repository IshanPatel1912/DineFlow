package util;

import model.Bill;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InvoiceFileHandler {

    private static final String FOLDER_PATH = "invoices/";

    public boolean generateInvoiceFile(Bill bill) {
        File directory = new File(FOLDER_PATH);
        if (!directory.exists()) directory.mkdir();

        String fileName = FOLDER_PATH + "Invoice_Order_" + bill.getOrderId() + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("======================================\n");
            writer.write("           DINEFLOW INVOICE           \n");
            writer.write("======================================\n");
            writer.write("Bill ID: " + bill.getBillId() + "\n");
            writer.write("Order ID: " + bill.getOrderId() + "\n");
            writer.write("Payment Method: " + bill.getPaymentMethod() + "\n");
            writer.write("--------------------------------------\n");
            writer.write("Subtotal: ₹" + String.format("%.2f", bill.getTotalAmount()) + "\n");
            writer.write("Tax (5%): ₹" + String.format("%.2f", bill.getTax()) + "\n");
            writer.write("Discount: -₹" + String.format("%.2f", bill.getDiscount()) + "\n");
            writer.write("--------------------------------------\n");
            writer.write("FINAL TOTAL: ₹" + String.format("%.2f", bill.getFinalAmount()) + "\n");
            writer.write("======================================\n");
            writer.write("      Thank you for dining with us!   \n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}