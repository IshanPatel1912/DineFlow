package model;

public class Receptionist extends Employee {
    
    public Receptionist(int id, String name, String phone, double salary, String username) {
        super(id, name, phone, "RECEPTIONIST", salary, username);
    }

    @Override
    public void displayProfile() {
        System.out.println("Receptionist Profile: " + this.name + " | Phone: " + this.phone + " | Salary: $" + this.salary);
    }
}