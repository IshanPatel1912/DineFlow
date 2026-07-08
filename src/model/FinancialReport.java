package model;

public class FinancialReport {
    private String period;
    private double totalRevenue;
    private double salariesPaid;
    private double otherExpenses;
    private double inventoryAssetValue; // NEW
    private double netProfit;

    public FinancialReport(String period, double rev, double sal, double exp, double invAsset) {
        this.period = period;
        this.totalRevenue = rev;
        this.salariesPaid = sal;
        this.otherExpenses = exp;
        this.inventoryAssetValue = invAsset;
        // Standard restaurant math: Net profit = Revenue - Operating Expenses
        this.netProfit = rev - sal - exp; 
    }

    public void printReport() {
        System.out.println("\n--- " + period + " FINANCIAL REPORT ---");
        System.out.printf("Gross Revenue:        ₹%.2f\n", totalRevenue);
        System.out.printf("Salaries Paid:       -₹%.2f\n", salariesPaid);
        System.out.printf("Other Expenses:      -₹%.2f\n", otherExpenses);
        System.out.println("---------------------------------------------");
        if (netProfit >= 0) System.out.printf("NET PROFIT:           ₹%.2f\n", netProfit);
        else System.out.printf("NET LOSS:            -₹%.2f\n", Math.abs(netProfit));
        
        System.out.println("=============================================");
        System.out.printf("Current Inventory Asset Value: ₹%.2f\n", inventoryAssetValue);
        System.out.println("(Value of all stock currently in kitchen)");
    }
}