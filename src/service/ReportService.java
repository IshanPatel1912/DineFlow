package service;

import dao.ReportDAO;
import model.FinancialReport;

public class ReportService {
    private ReportDAO reportDAO = new ReportDAO();

   public FinancialReport generateFinancialReport(String period) {
        double revenue = reportDAO.getRevenue(period);
        double salaries = reportDAO.getActualSalariesPaid(period);
        double expenses = reportDAO.getOtherExpenses(period);
        double inventoryAsset = reportDAO.getTotalInventoryAssetValue(); 

        return new FinancialReport(period, revenue, salaries, expenses, inventoryAsset);
    }

    public void logExpense(String desc, double amount) {
        reportDAO.logExpense(desc, amount);
    }

    public void paySalary(int empId, double amount) {
        reportDAO.paySalary(empId, amount);
    }
    
}