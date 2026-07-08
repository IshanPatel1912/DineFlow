package model;

public class Customer extends Person {
    private int userId;
    private String email;

    public Customer() {}

    public Customer(int customerId, int userId, String fullName, String phone, String email) {
        super(customerId, fullName, phone); // Passes data up to the Person class
        this.userId = userId;
        this.email = email;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public void displayProfile() {
        System.out.println("Customer Profile: " + this.name + " | Phone: " + this.phone + " | Email: " + this.email);
    }
}