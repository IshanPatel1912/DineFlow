package service;

import dao.UserDAO;
import model.User;

public class AuthService {
    
    private UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
    }
    
    public boolean adminExists() {
        return userDAO.adminExists();
    }

    public boolean signup(String username, String password, String role) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return false;
        }
        User newUser = new User(0, username, password, role);
        return userDAO.registerUser(newUser);
    }

    public User login(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        return userDAO.authenticateUser(username, password);
    }
}