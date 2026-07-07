package model;

public class Chef extends Employee {
    
    public Chef(int id, String name, String phone, double salary, String username) {
        super(id, name, phone, "CHEF", salary, username);
    }

    @Override
    public void displayProfile() {
        System.out.println("Chef Profile: " + this.name + " | Kitchen Station | Salary: $" + this.salary);
    }
}