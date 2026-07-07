package service;

import dao.ReportDAO;
import model.FinancialReport;

public class ReportService {

    private ReportDAO reportDAO;

    public ReportService() {
        this.reportDAO = new ReportDAO();
    }

    public FinancialReport generateFinancialReport(String period) {
        double revenue = reportDAO.getRevenue(period);
        double monthlySalaries = reportDAO.getTotalMonthlySalaries();

        double salaries = 0;
        if (period.equals("DAILY")) salaries = monthlySalaries / 30.0;
        else if (period.equals("WEEKLY")) salaries = monthlySalaries / 4.0;
        else if (period.equals("YEARLY")) salaries = monthlySalaries * 12.0;

        // Uses a standard restaurant Cost of Goods Sold (COGS) estimation of 30%
        double inventoryCost = revenue * 0.30; 
        double netProfit = revenue - salaries - inventoryCost;

        return new FinancialReport(period, revenue, salaries, inventoryCost, netProfit);
    }
}