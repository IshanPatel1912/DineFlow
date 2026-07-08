package main;

import service.AuthService;
import service.InventoryService;
import java.util.Scanner;

public class SetupWizard {
    private static final Scanner scanner = new Scanner(System.in);
    private AuthService authService;
    private InventoryService inventoryService;

    public SetupWizard(AuthService authService, InventoryService inventoryService) {
        this.authService = authService;
        this.inventoryService = inventoryService;
    }

    public void run() {
        printHeader("FIRST TIME SETUP WIZARD");
        System.out.println("No Admin account detected. Welcome to your new restaurant!");
        
        System.out.print("Create Admin Username: ");
        String user = scanner.nextLine().trim();
        System.out.print("Create Admin Password: ");
        String pass = scanner.nextLine().trim();
        authService.signup(user, pass, "ADMIN");
        System.out.println("\n>> Admin created successfully!\n");
        
        System.out.println("--- INVENTORY SETUP ---");
        System.out.println("Let's add your starting ingredients. You can add as many as you need.");
        
        while (true) {
            System.out.print("\nIngredient Name (e.g., Rice): ");
            String name = scanner.nextLine().trim();
            double qty = readDouble("Starting Quantity: ");
            System.out.print("Unit (e.g., kg, liters, pieces): ");
            String unit = scanner.nextLine().trim();
            double price = readDouble("Purchase Price per unit: ₹");
            double threshold = readDouble("Minimum Alert Threshold: ");
            
            if (inventoryService.addNewIngredient(name, qty, unit, price, threshold)) {
                System.out.println(">> " + name + " added to inventory!");
            } else {
                System.out.println(">> Error adding ingredient. Please check values.");
            }
            
            System.out.print("\nAdd another ingredient? (Y/N): ");
            String choice = scanner.nextLine().trim().toUpperCase();
            if (choice.equals("N")) break;
        }
        
        System.out.println("\n>> Setup Complete! Your database is ready. Please log in as Admin.");
        pause();
    }

    private void pause() {
        System.out.print("\nPress [ENTER] to continue...");
        scanner.nextLine();
    }

    private void printHeader(String title) {
        System.out.println("\n=============================================");
        int spaces = (43 - title.length()) / 2;
        System.out.printf("%" + (spaces + title.length()) + "s\n", title);
        System.out.println("=============================================");
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Double.parseDouble(scanner.nextLine().trim()); } 
            catch (NumberFormatException e) { System.out.println("Invalid input. Please enter a decimal."); }
        }
    }
}