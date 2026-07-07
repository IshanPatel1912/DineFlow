package service;

import dao.EmployeeDAO;
import model.*;
import java.util.List;

public class EmployeeService {

    private EmployeeDAO employeeDAO;

    public EmployeeService() {
        this.employeeDAO = new EmployeeDAO();
    }

    public boolean hireEmployee(String name, String phone, String role, double salary, String username, String password) {
        if (name == null || username == null || password == null || salary < 0) {
            return false;
        }
        
        Employee newEmp = null;
        switch (role.toUpperCase()) {
            case "RECEPTIONIST":
                newEmp = new Receptionist(0, name, phone, salary, username);
                break;
            case "CHEF":
                newEmp = new Chef(0, name, phone, salary, username);
                break;
            case "MANAGER":
                newEmp = new Manager(0, name, phone, salary, username);
                break;
            default:
                return false;
        }
        
        return employeeDAO.addEmployee(newEmp, password);
    }

    public List<Employee> viewAllEmployees() {
        return employeeDAO.getAllEmployees();
    }
}