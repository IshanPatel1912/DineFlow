CREATE DATABASE IF NOT EXISTS dineflow_db;
USE dineflow_db;


CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL, 
    role ENUM('ADMIN', 'RECEPTIONIST', 'KITCHEN', 'CUSTOMER','MANAGER') NOT NULL
);


CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE, 
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(15) UNIQUE,
    email VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);


CREATE TABLE restaurant_tables (
    table_id INT AUTO_INCREMENT PRIMARY KEY,
    table_number INT UNIQUE NOT NULL,
    capacity INT NOT NULL,
    status ENUM('AVAILABLE', 'OCCUPIED', 'RESERVED') DEFAULT 'AVAILABLE',
    qr_code_hash VARCHAR(255) UNIQUE 
);


CREATE TABLE menu_items (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    category ENUM('STARTER', 'MAIN_COURSE', 'DESSERT', 'BEVERAGE') NOT NULL,
    is_available BOOLEAN DEFAULT TRUE
);


CREATE TABLE inventory (
    ingredient_id INT AUTO_INCREMENT PRIMARY KEY,
    ingredient_name VARCHAR(100) UNIQUE NOT NULL,
    stock_quantity DECIMAL(10, 2) NOT NULL,
    unit VARCHAR(20) NOT NULL, 
    minimum_threshold DECIMAL(10, 2) NOT NULL 
);


CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    table_id INT NOT NULL,
    customer_id INT, 
    order_status ENUM('PENDING', 'PREPARING', 'SERVED', 'COMPLETED') DEFAULT 'PENDING',
    order_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) DEFAULT 0.00,
    FOREIGN KEY (table_id) REFERENCES restaurant_tables(table_id),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);


CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    item_id INT NOT NULL,
    quantity INT NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES menu_items(item_id)
);

CREATE TABLE reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    table_id INT NOT NULL,
    reservation_time DATETIME NOT NULL,
    status ENUM('PENDING', 'CONFIRMED', 'CANCELLED') DEFAULT 'PENDING',
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (table_id) REFERENCES restaurant_tables(table_id)
);

CREATE TABLE bills (
    bill_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    tax DECIMAL(10, 2) NOT NULL,
    discount DECIMAL(10, 2) DEFAULT 0.00,
    final_amount DECIMAL(10, 2) NOT NULL,
    payment_method ENUM('CASH', 'CARD', 'UPI') DEFAULT 'CASH',
    bill_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE
);

CREATE TABLE feedback (
    feedback_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comments TEXT,
    feedback_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);

CREATE TABLE employees (
    emp_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    role ENUM('RECEPTIONIST', 'CHEF', 'MANAGER') NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);