package main;

import model.*;
import service.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class DineFlowApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final AuthService authService = new AuthService();
    private static final MenuService menuService = new MenuService();
    private static final OrderService orderService = new OrderService();
    private static final BillingService billingService = new BillingService();
    private static final InventoryService inventoryService = new InventoryService();
    private static final ReportService reportService = new ReportService();
    private static final CustomerService customerService = new CustomerService();
    private static final ReservationService reservationService = new ReservationService();
    private static final FeedbackService feedbackService = new FeedbackService();
    private static final EmployeeService employeeService = new EmployeeService();

    private static User currentUser = null;

    public static void main(String[] args) {
        System.out.println("Initializing DineFlow System...");
        while (true) {
            if (currentUser == null) {
                showAuthMenu();
            } else {
                routeUser();
            }
        }
    }

    private static void pause() {
        System.out.print("\nPress [ENTER] to continue...");
        scanner.nextLine();
    }

    private static void printHeader(String title) {
        System.out.println("\n=============================================");
        int spaces = (43 - title.length()) / 2;
        System.out.printf("%" + (spaces + title.length()) + "s\n", title);
        System.out.println("=============================================");
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid decimal number.");
            }
        }
    }

    private static void showAuthMenu() {
        printHeader("DINEFLOW - SMART RESTAURANT PLATFORM");
        System.out.println("1. Login");
        System.out.println("2. Signup (Customer)");
        System.out.println("3. Exit Application");
        System.out.println("---------------------------------------------");
        
        int choice = readInt("Select an option: ");

        switch (choice) {
            case 1: handleLogin(); break;
            case 2: handleSignup(); break;
            case 3: System.out.println("Shutting down DineFlow. Goodbye!"); System.exit(0); break;
            default: System.out.println("Invalid choice. Please try again."); pause();
        }
    }

    private static void handleLogin() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        currentUser = authService.login(username, password);

        if (currentUser != null) {
            System.out.println("\n>> Login Successful! Welcome, " + currentUser.getUsername() + ".");
        } else {
            System.out.println("\n>> Error: Invalid credentials.");
        }
        pause();
    }

    private static void handleSignup() {
        System.out.print("Choose Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Choose Password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Full Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Phone Number: ");
        String phone = scanner.nextLine().trim();
        
        boolean success = authService.signup(username, password, "CUSTOMER");
        if (success) {
            User newUser = authService.login(username, password);
            if (newUser != null) {
                customerService.registerCustomer(newUser.getUserId(), name, phone, "");
            }
            System.out.println("\n>> Signup successful! Profile created. You can now log in.");
        } else {
            System.out.println("\n>> Error: Signup failed. Username might be taken.");
        }
        pause();
    }

    private static void routeUser() {
        switch (currentUser.getRole()) {
            case "ADMIN": showAdminMenu(); break;
            case "MANAGER": showManagerMenu(); break;
            case "RECEPTIONIST": showReceptionistMenu(); break;
            case "KITCHEN": showKitchenMenu(); break;
            case "CUSTOMER": showCustomerMenu(); break;
            default: System.out.println("Role not recognized. Logging out."); currentUser = null;
        }
    }

    private static void showAdminMenu() {
        printHeader("ADMIN DASHBOARD");
        System.out.println("1. View Financial Analytics");
        System.out.println("2. View Customer Feedback");
        System.out.println("3. Hire New Employee");
        System.out.println("4. Logout");
        System.out.println("---------------------------------------------");

        int choice = readInt("Select an option: ");
        switch (choice) {
            case 1: displayReports(); pause(); break;
            case 2: displayFeedback(); pause(); break;
            case 3: hireEmployee(); pause(); break;
            case 4: currentUser = null; break;
            default: System.out.println("Invalid option."); pause();
        }
    }
    
    private static void showManagerMenu() {
        printHeader("MANAGER DASHBOARD");
        System.out.println("1. Manage Inventory Stock");
        System.out.println("2. Manage Digital Menu");
        System.out.println("3. Logout");
        System.out.println("---------------------------------------------");

        int choice = readInt("Select an option: ");
        switch (choice) {
            case 1: manageInventory(); break;
            case 2: manageMenu(); break;
            case 3: currentUser = null; break;
            default: System.out.println("Invalid option."); pause();
        }
    }

    private static void showReceptionistMenu() {
        printHeader("RECEPTION DASHBOARD");
        System.out.println("1. Manage Customers");
        System.out.println("2. Manage Table Reservations");
        System.out.println("3. Generate Bill & Invoice");
        System.out.println("4. Logout");
        System.out.println("---------------------------------------------");

        int choice = readInt("Select an option: ");
        switch (choice) {
            case 1: manageCustomers(); break;
            case 2: manageReservations(); break;
            case 3: handleBilling(); pause(); break;
            case 4: currentUser = null; break;
            default: System.out.println("Invalid option."); pause();
        }
    }

    private static void showKitchenMenu() {
        printHeader("KITCHEN DASHBOARD");
        System.out.println("1. View Pending Orders & Items");
        System.out.println("2. Update Order Status");
        System.out.println("3. Logout");
        System.out.println("---------------------------------------------");

        int choice = readInt("Select an option: ");
        switch (choice) {
            case 1: displayPendingOrders(); pause(); break;
            case 2: updateOrderStatus(); pause(); break;
            case 3: currentUser = null; break;
            default: System.out.println("Invalid option."); pause();
        }
    }

    private static void showCustomerMenu() {
        printHeader("CUSTOMER DASHBOARD");
        System.out.println("1. View Digital Menu");
        System.out.println("2. Place Order (Scan QR/Table ID)");
        System.out.println("3. Submit Feedback");
        System.out.println("4. Logout");
        System.out.println("---------------------------------------------");

        int choice = readInt("Select an option: ");
        switch (choice) {
            case 1: displayMenu(); pause(); break;
            case 2: placeOrder(); pause(); break;
            case 3: submitFeedback(); pause(); break;
            case 4: currentUser = null; break;
            default: System.out.println("Invalid option."); pause();
        }
    }

    // ==========================================
    // MODULE: MENU MANAGEMENT
    // ==========================================
    
    private static void manageMenu() {
        while(true) {
            printHeader("MENU MANAGEMENT");
            System.out.println("1. View Menu");
            System.out.println("2. Add New Menu Item");
            System.out.println("3. Edit Existing Item");
            System.out.println("4. Go Back");
            int choice = readInt("Select: ");
            
            if (choice == 1) {
                displayMenu();
                pause();
            } else if (choice == 2) {
                System.out.print("Item Name: ");
                String name = scanner.nextLine().trim();
                System.out.print("Description: ");
                String desc = scanner.nextLine().trim();
                double price = readDouble("Price: ₹");
                System.out.print("Category (STARTER/MAIN_COURSE/DESSERT/BEVERAGE): ");
                String cat = scanner.nextLine().toUpperCase().trim();
                
                if (menuService.createMenuItem(name, desc, price, cat)) System.out.println(">> Menu item added!");
                else System.out.println(">> Error adding item.");
                pause();
            } else if (choice == 3) {
                displayMenu();
                int id = readInt("Enter Item ID to edit: ");
                System.out.print("New Name: ");
                String name = scanner.nextLine().trim();
                System.out.print("New Description: ");
                String desc = scanner.nextLine().trim();
                double price = readDouble("New Price: ₹");
                System.out.print("New Category: ");
                String cat = scanner.nextLine().toUpperCase().trim();
                
                if (menuService.editMenuItem(id, name, desc, price, cat, true)) System.out.println(">> Menu item updated!");
                else System.out.println(">> Error updating item.");
                pause();
            } else break;
        }
    }

    private static void displayMenu() {
        printHeader("DIGITAL MENU");
        List<MenuItem> menu = menuService.viewMenu();
        if (menu.isEmpty()) System.out.println("The menu is currently empty.");
        else {
            for (MenuItem item : menu) {
                System.out.printf("[%d] %-20s - ₹%.2f (%s)\n", item.getItemId(), item.getName(), item.getPrice(), item.getCategory());
            }
        }
    }

    // ==========================================
    // MODULE: CUSTOMER MANAGEMENT
    // ==========================================

    private static void manageCustomers() {
        while(true) {
            printHeader("CUSTOMER MANAGEMENT");
            System.out.println("1. View All Customers");
            System.out.println("2. Edit Customer Details");
            System.out.println("3. Go Back");
            int choice = readInt("Select: ");
            
            if (choice == 1) {
                displayCustomers();
                pause();
            } else if (choice == 2) {
                displayCustomers();
                int id = readInt("Enter Customer ID to edit: ");
                System.out.print("New Full Name: ");
                String name = scanner.nextLine().trim();
                System.out.print("New Phone Number: ");
                String phone = scanner.nextLine().trim();
                System.out.print("New Email (or leave blank): ");
                String email = scanner.nextLine().trim();
                
                if (customerService.editCustomer(id, name, phone, email)) System.out.println(">> Customer details updated successfully.");
                else System.out.println(">> Error updating customer.");
                pause();
            } else break;
        }
    }

    private static void displayCustomers() {
        printHeader("REGISTERED CUSTOMERS");
        List<Customer> customers = customerService.viewAllCustomers();
        if (customers.isEmpty()) System.out.println("No customers found.");
        else {
            for (Customer c : customers) {
                System.out.printf("ID: %d | Name: %-15s | Phone: %s\n", c.getId(), c.getName(), c.getPhone());
            }
        }
    }

    // ==========================================
    // MODULE: KITCHEN & ORDERS
    // ==========================================

    private static void displayPendingOrders() {
        printHeader("KITCHEN QUEUE");
        Queue<Order> queue = orderService.getKitchenQueue();
        Map<Integer, MenuItem> menuMap = menuService.getMenuAsMap();

        if (queue.isEmpty()) System.out.println("No pending orders. Kitchen is clear!");
        else {
            for (Order o : queue) {
                System.out.printf("\n[ORDER #%d] | Table: %d | Status: %s\n", o.getOrderId(), o.getTableId(), o.getOrderStatus());
                System.out.println("Items to prepare:");
                
                List<OrderItem> items = orderService.getItemsForOrder(o.getOrderId());
                for (OrderItem item : items) {
                    String itemName = menuMap.containsKey(item.getItemId()) ? menuMap.get(item.getItemId()).getName() : "Unknown Item";
                    System.out.printf("  - %s (Qty: %d)\n", itemName, item.getQuantity());
                }
                System.out.println("---------------------------------------------");
            }
        }
    }

    private static void updateOrderStatus() {
        displayPendingOrders();
        System.out.println();
        int orderId = readInt("Enter Order ID to update (or 0 to cancel): ");
        if (orderId == 0) return;

        System.out.print("New Status (PREPARING / READY / SERVED): ");
        String status = scanner.nextLine().toUpperCase().trim();
        
        if (orderService.updateOrderStatus(orderId, status)) System.out.println(">> Status updated successfully.");
        else System.out.println(">> Error: Failed to update status. Check Order ID.");
    }

    private static void placeOrder() {
        displayMenu();
        Map<Integer, MenuItem> menuMap = menuService.getMenuAsMap();
        List<OrderItem> cart = new ArrayList<>();
        
        System.out.println("\n--- CART CREATION ---");
        int tableId = readInt("Enter Table ID (Simulation of QR Scan): ");
        
        while (true) {
            int itemId = readInt("Enter Menu Item ID to add (or 0 to finish): ");
            if (itemId == 0) break;
            
            if (menuMap.containsKey(itemId)) {
                int qty = readInt("Enter Quantity: ");
                if (qty > 0) {
                    MenuItem selected = menuMap.get(itemId);
                    double subtotal = selected.getPrice() * qty;
                    cart.add(new OrderItem(0, 0, itemId, qty, subtotal));
                    System.out.println(">> Added: " + selected.getName() + " x" + qty + " (₹" + subtotal + ")");
                } else System.out.println("Quantity must be greater than 0.");
            } else System.out.println("Invalid Menu Item ID.");
        }
        
        if (!cart.isEmpty()) {
            Customer c = customerService.getCustomerByUserId(currentUser.getUserId());
            Integer customerId = (c != null) ? c.getId() : null; 
            
            if (orderService.placeOrder(tableId, customerId, cart)) {
                System.out.println("\n>> Success: Order placed and sent to the kitchen queue!");
            } else System.out.println("\n>> Error: Failed to place order. Ensure Table ID is correct.");
        } else System.out.println("Order cancelled (Cart is empty).");
    }

    // ==========================================
    // MODULE: INVENTORY
    // ==========================================

    private static void manageInventory() {
        while(true) {
            printHeader("INVENTORY MANAGEMENT");
            System.out.println("1. View Inventory Status");
            System.out.println("2. Update Stock Quantity");
            System.out.println("3. Go Back");
            int choice = readInt("Select: ");
            
            if (choice == 1) {
                displayInventory();
                pause();
            } else if (choice == 2) {
                displayInventory();
                System.out.println();
                int id = readInt("Enter Ingredient ID to update (or 0 to cancel): ");
                if (id == 0) continue;
                
                double newQty = readDouble("Enter new total stock quantity: ");
                if (inventoryService.updateIngredientStock(id, newQty)) {
                    System.out.println(">> Stock successfully updated.");
                } else {
                    System.out.println(">> Error: Failed to update stock.");
                }
                pause();
            } else break;
        }
    }

    private static void displayInventory() {
        List<InventoryItem> items = inventoryService.viewFullInventory();
        for (InventoryItem item : items) {
            System.out.printf("[%d] %-15s - %.2f %s\n", item.getIngredientId(), item.getIngredientName(), item.getStockQuantity(), item.getUnit());
        }
        System.out.println("\n--- LOW STOCK ALERTS ---");
        List<InventoryItem> alerts = inventoryService.checkLowStockAlerts();
        if (alerts.isEmpty()) System.out.println("All stock levels are healthy.");
        else {
            for (InventoryItem alert : alerts) {
                System.out.println("! WARNING: " + alert.getIngredientName() + " is below minimum threshold.");
            }
        }
    }

    // ==========================================
    // REMAINDER OF MODULES (Billing, Reports, etc.)
    // ==========================================

    private static void displayReports() {
        printHeader("FINANCIAL ANALYTICS");
        System.out.println("1. Daily Profit Report");
        System.out.println("2. Weekly Profit Report");
        System.out.println("3. Yearly Profit Report");
        int choice = readInt("Select Period: ");
        
        FinancialReport report = null;
        if (choice == 1) report = reportService.generateFinancialReport("DAILY");
        else if (choice == 2) report = reportService.generateFinancialReport("WEEKLY");
        else if (choice == 3) report = reportService.generateFinancialReport("YEARLY");
        else System.out.println("Invalid option.");
        
        if (report != null) report.printReport();
    }

    private static void displayFeedback() {
        printHeader("CUSTOMER FEEDBACK");
        List<Feedback> list = feedbackService.viewAllFeedback();
        if (list.isEmpty()) System.out.println("No feedback available.");
        else {
            for (Feedback f : list) {
                System.out.printf("Rating: %d/5 | Cust ID: %d | Date: %s\nComments: %s\n------------------------\n", 
                    f.getRating(), f.getCustomerId(), f.getFeedbackDate().toString().substring(0,10), f.getComments());
            }
        }
    }

    private static void handleBilling() {
        int orderId = readInt("Enter Order ID for checkout: ");
        Order order = orderService.getOrderDetails(orderId);
        
        if (order == null || !order.getOrderStatus().equals("SERVED")) {
            System.out.println(">> Error: Order not found or food is not yet 'SERVED'.");
            return;
        }

        System.out.printf("Order Subtotal: ₹%.2f\n", order.getTotalAmount());
        double discount = readDouble("Enter Discount Amount: ₹");
        
        System.out.print("Payment Method (CASH/CARD/UPI): ");
        String method = scanner.nextLine().toUpperCase().trim();
        
        Bill bill = billingService.generateBill(orderId, order.getTotalAmount(), discount, method);
        if (bill != null) {
            System.out.println("\n>> Success: Bill generated!");
            System.out.println(">> Invoice saved to /invoices/ folder.");
            orderService.updateOrderStatus(orderId, "COMPLETED");
        } else {
            System.out.println("\n>> Error: Failed to generate bill. Check details.");
        }
    }
    
    private static void submitFeedback() {
        Customer c = customerService.getCustomerByUserId(currentUser.getUserId());
        if (c == null) {
            System.out.println(">> Notice: You must complete your customer profile at the reception before leaving feedback.");
            return;
        }
        
        int rating = readInt("Enter Rating (1-5): ");
        System.out.print("Enter your comments: ");
        String comments = scanner.nextLine().trim();
        
        if (feedbackService.submitFeedback(c.getId(), rating, comments)) {
            System.out.println(">> Thank you! Your feedback has been recorded.");
        } else {
            System.out.println(">> Error: Failed to submit feedback (Rating must be 1-5).");
        }
    }

    private static void manageReservations() {
        while(true) {
            printHeader("RESERVATIONS");
            System.out.println("1. Book a Table");
            System.out.println("2. View Pending Reservations");
            System.out.println("3. Confirm Reservation");
            System.out.println("4. Go Back");
            
            int choice = readInt("Select: ");
            switch(choice) {
                case 1:
                    int customerId = readInt("Enter Customer ID: ");
                    int tableId = readInt("Enter Table ID to reserve: ");
                    Timestamp time = new Timestamp(System.currentTimeMillis() + 86400000); // Books for tomorrow
                    if(reservationService.bookTable(customerId, tableId, time)) {
                        System.out.println("Table reserved successfully for tomorrow.");
                    } else System.out.println("Failed to reserve table.");
                    pause();
                    break;
                case 2:
                    PriorityQueue<Reservation> queue = reservationService.viewUpcomingReservations();
                    if(queue.isEmpty()) System.out.println("No pending reservations.");
                    for(Reservation r : queue) {
                        System.out.printf("Res ID: %d | Cust ID: %d | Table: %d | Status: %s\n", 
                            r.getReservationId(), r.getCustomerId(), r.getTableId(), r.getStatus());
                    }
                    pause();
                    break;
                case 3:
                    int resId = readInt("Enter Reservation ID to confirm: ");
                    if(reservationService.confirmReservation(resId)) System.out.println("Reservation Confirmed.");
                    else System.out.println("Failed to confirm.");
                    pause();
                    break;
                case 4:
                    return; 
            }
        }
    }

    private static void hireEmployee() {
        printHeader("HIRE EMPLOYEE");
        System.out.print("Full Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Phone Number: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Role (RECEPTIONIST / CHEF / MANAGER): ");
        String role = scanner.nextLine().toUpperCase().trim();
        double salary = readDouble("Monthly Salary: ₹");
        
        System.out.println("--- Setup Login Credentials ---");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        if (employeeService.hireEmployee(name, phone, role, salary, username, password)) {
            System.out.println("\n>> Success: Employee hired and login account created!");
        } else {
            System.out.println("\n>> Error: Failed to hire employee. Username might exist or invalid role.");
        }
    }
}