package model;

import java.sql.Date;

public class DailyReport {
    private Date reportDate;
    private int totalOrders;
    private double totalRevenue;

    public DailyReport() {}

    public DailyReport(Date reportDate, int totalOrders, double totalRevenue) {
        this.reportDate = reportDate;
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
    }

    public Date getReportDate() { return reportDate; }
    public void setReportDate(Date reportDate) { this.reportDate = reportDate; }

    public int getTotalOrders() { return totalOrders; }
    public void setTotalOrders(int totalOrders) { this.totalOrders = totalOrders; }

    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }
}