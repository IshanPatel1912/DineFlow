package model;

public class Manager extends Employee {
    
    public Manager(int id, String name, String phone, double salary, String username) {
        super(id, name, phone, "MANAGER", salary, username);
    }

    @Override
    public void displayProfile() {
        System.out.println("Manager Profile: " + this.name + " | Oversees Operations | Salary: $" + this.salary);
    }
}