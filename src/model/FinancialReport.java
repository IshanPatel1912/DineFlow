package model;

public class FinancialReport {
    private String period;
    private double totalRevenue;
    private double totalSalaries;
    private double inventoryCost;
    private double netProfit;

    public FinancialReport(String period, double totalRevenue, double totalSalaries, double inventoryCost, double netProfit) {
        this.period = period;
        this.totalRevenue = totalRevenue;
        this.totalSalaries = totalSalaries;
        this.inventoryCost = inventoryCost;
        this.netProfit = netProfit;
    }

    public void printReport() {
        System.out.println("\n--- " + period + " FINANCIAL REPORT ---");
        System.out.printf("Gross Revenue:        ₹%.2f\n", totalRevenue);
        System.out.printf("Employee Salaries:   -₹%.2f\n", totalSalaries);
        System.out.printf("Est. Inventory Cost: -₹%.2f (30%% of revenue)\n", inventoryCost);
        System.out.println("---------------------------------------------");
        if (netProfit >= 0) {
            System.out.printf("NET PROFIT:           ₹%.2f\n", netProfit);
        } else {
            System.out.printf("NET LOSS:            -₹%.2f\n", Math.abs(netProfit));
        }
    }
}