package model;

public abstract class Person {
    protected int id;
    protected String name;
    protected String phone;

    public Person() {}

    public Person(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    // Abstract method: Every child class must define how they display their profile
    public abstract void displayProfile(); 
}