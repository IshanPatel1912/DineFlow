package dao;

import model.*;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public boolean addEmployee(Employee emp, String password) {
        String insertUserSql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        String insertEmpSql = "INSERT INTO employees (user_id, name, phone, role, salary) VALUES (?, ?, ?, ?, ?)";
        
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            int userId = 0;
            try (PreparedStatement userStmt = conn.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS)) {
                String dbRole = emp.getRole().equals("MANAGER") ? "MANAGER" : emp.getRole().equals("CHEF") ? "KITCHEN" : "RECEPTIONIST";
                userStmt.setString(1, emp.getUsername());
                userStmt.setString(2, password);
                userStmt.setString(3, dbRole);
                userStmt.executeUpdate();
                
                ResultSet rs = userStmt.getGeneratedKeys();
                if (rs.next()) {
                    userId = rs.getInt(1);
                }
            }
            
            try (PreparedStatement empStmt = conn.prepareStatement(insertEmpSql)) {
                empStmt.setInt(1, userId);
                empStmt.setString(2, emp.getName());
                empStmt.setString(3, emp.getPhone());
                empStmt.setString(4, emp.getRole());
                empStmt.setDouble(5, emp.getSalary());
                empStmt.executeUpdate();
            }
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try { 
                    conn.setAutoCommit(true);
                    conn.close(); 
                } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT e.*, u.username FROM employees e JOIN users u ON e.user_id = u.user_id";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                String role = rs.getString("role");
                if (role.equals("RECEPTIONIST")) {
                    list.add(new Receptionist(rs.getInt("emp_id"), rs.getString("name"), rs.getString("phone"), rs.getDouble("salary"), rs.getString("username")));
                } else if (role.equals("CHEF")) {
                    list.add(new Chef(rs.getInt("emp_id"), rs.getString("name"), rs.getString("phone"), rs.getDouble("salary"), rs.getString("username")));
                } else if (role.equals("MANAGER")) {
                    list.add(new Manager(rs.getInt("emp_id"), rs.getString("name"), rs.getString("phone"), rs.getDouble("salary"), rs.getString("username")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}