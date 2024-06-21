--  Create database and use
CREATE DATABASE IF NOT EXISTS salesapp;
USE salesapp;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL DEFAULT 'user',
    email VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Insert dummy data into users table
INSERT INTO users (name, lastname, password, role, email, created_at) VALUES
('John', 'Doe', 'password123', 'admin', 'john.doe@example.com', NOW()),
('Jane', 'Smith', 'password456', 'user', 'jane.smith@example.com', NOW()),
('Alice', 'Johnson', 'password789', 'manager', 'alice.johnson@example.com', NOW()),
('Bob', 'Brown', 'password012', 'user', 'bob.brown@example.com', NOW()),
('Charlie', 'Davis', 'password345', 'admin', 'charlie.davis@example.com', NOW());

-- Create orders table
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    type VARCHAR(255) NOT NULL,
    total_items INT NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    status VARCHAR(255) NOT NULL,
    init_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    complete_fees INT NOT NULL,
    remaining_fees INT NOT NULL,
    time_to_pay_in_days INT NOT NULL,
    fee_value DECIMAL(10, 2),
    next_collection_date TIMESTAMP,
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);

-- Insert dummy data into orders table
INSERT INTO orders (user_id, created_at, type, total_items, total_price, status, init_date, end_date, complete_fees, remaining_fees, time_to_pay_in_days, fee_value, next_collection_date)
VALUES
(1, NOW(), 'CASH', 1, 100.00, 'Completed', NOW(), NOW(), 1, 0, 0, NULL, NULL),
(2, NOW(), 'INSTALLAMENT_15', 2, 276.25, 'Completed', NOW(), DATE_ADD(NOW(), INTERVAL 150 DAY), 10, 0, 150, 27.63, NULL),
(1, NOW(), 'CASH', 1, 200.75, 'Completed', NOW(), NOW(), 1, 0, 0, NULL, NULL),
(3, NOW(), 'CASH', 3, 371.00, 'Completed', NOW(), NOW(), 1, 0, 0, NULL, NULL),
(2, NOW(), 'INSTALLAMENT_30', 2, 220.25, 'Pending', NOW(), DATE_ADD(NOW(), INTERVAL 360 DAY), 4, 8, 360, 18.35, DATE_ADD(NOW(), INTERVAL 20 DAY));

-- Create products table
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    ref VARCHAR(50) NOT NULL,
    base_price DECIMAL(10, 2) NOT NULL,
    per_discount DECIMAL(5, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Insert dummy data into products table
INSERT INTO products (name, ref, base_price, per_discount, created_at) VALUES
('Product A', 'PROD001', 100.00, 0.00, NOW()),
('Product B', 'PROD002', 75.50, 0.00, NOW()),
('Product C', 'PROD003', 120.25, 0.00, NOW()),
('Product D', 'PROD004', 50.00, 0.00, NOW()),
('Product E', 'PROD005', 200.75, 0.00, NOW());

-- Create items table (shopping cart)
CREATE TABLE IF NOT EXISTS items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    unit_number INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_order
        FOREIGN KEY (order_id)
        REFERENCES orders(id),
    CONSTRAINT fk_user_item
        FOREIGN KEY (user_id)
        REFERENCES users(id),
    CONSTRAINT fk_product
        FOREIGN KEY (product_id)
        REFERENCES products(id)
);

-- Insert dummy data into items table
INSERT INTO items (order_id, user_id, product_id, unit_price, unit_number, created_at) VALUES
(1, 1, 1, 100.00, 1, NOW()),
(2, 2, 3, 120.25, 1, NOW()),
(2, 2, 5, 200.75, 1, NOW()),
(3, 1, 5, 270.50, 1, NOW()),
(4, 3, 3, 120.25, 1, NOW()),
(4, 3, 4, 50.00, 1, NOW()),
(4, 3, 5, 200.75, 1, NOW()),
(5, 2, 1, 100.00, 1, NOW()),
(5, 2, 3, 120.25, 1, NOW());