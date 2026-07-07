package model;

public interface Payment {
    boolean processPayment(double amount, String method);
}