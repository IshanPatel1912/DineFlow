package service;

import dao.CustomerDAO;
import model.Customer;
import java.util.List;

public class CustomerService {

    private CustomerDAO customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }

    public boolean registerCustomer(int userId, String fullName, String phone, String email) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return false;
        }
        Customer customer = new Customer(0, userId, fullName, phone, email);
        return customerDAO.addCustomer(customer);
    }

    public List<Customer> viewAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public Customer getCustomerByUserId(int userId) {
        if (userId <= 0) return null;
        return customerDAO.getCustomerByUserId(userId);
    }

    public boolean editCustomer(int customerId, String fullName, String phone, String email) {
        if (customerId <= 0 || fullName == null || fullName.trim().isEmpty()) {
            return false;
        }
        Customer customer = new Customer(customerId, 0, fullName, phone, email);
        return customerDAO.updateCustomer(customer);
    }

    public boolean removeCustomer(int customerId) {
        if (customerId <= 0) {
            return false;
        }
        return customerDAO.deleteCustomer(customerId);
    }
}