package model;

public abstract class Employee extends Person {
    protected String role;
    protected double salary;
    protected String username;

    public Employee(int id, String name, String phone, String role, double salary, String username) {
        super(id, name, phone); // Calls the Person constructor
        this.role = role;
        this.salary = salary;
        this.username = username;
    }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}