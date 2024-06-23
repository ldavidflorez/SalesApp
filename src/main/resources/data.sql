-- Insert dummy data into users table
INSERT INTO users (name, lastname, password, role, email, created_at) VALUES
('John', 'Doe', 'password123', 'admin', 'john.doe@example.com', NOW()),
('Jane', 'Smith', 'password456', 'user', 'jane.smith@example.com', NOW()),
('Alice', 'Johnson', 'password789', 'manager', 'alice.johnson@example.com', NOW()),
('Bob', 'Brown', 'password012', 'user', 'bob.brown@example.com', NOW()),
('Charlie', 'Davis', 'password345', 'admin', 'charlie.davis@example.com', NOW());

-- Insert dummy data into orders table
INSERT INTO orders (user_id, created_at, type, total_items, total_price, status, init_date, end_date, complete_fees, remaining_fees, time_to_pay_in_days, fee_value, next_collection_date)
VALUES
(1, NOW(), 'CASH', 1, 100.00, 'Completed', NOW(), NOW(), 1, 0, 0, NULL, NULL),
(2, NOW(), 'FEE_15', 2, 276.25, 'Completed', NOW(), DATE_ADD(NOW(), INTERVAL 150 DAY), 10, 0, 150, 27.63, NULL),
(1, NOW(), 'CASH', 1, 200.75, 'Completed', NOW(), NOW(), 1, 0, 0, NULL, NULL),
(3, NOW(), 'CASH', 3, 371.00, 'Completed', NOW(), NOW(), 1, 0, 0, NULL, NULL),
(2, NOW(), 'FEE_30', 2, 220.25, 'Pending', NOW(), DATE_ADD(NOW(), INTERVAL 360 DAY), 4, 8, 360, 18.35, DATE_ADD(NOW(), INTERVAL 20 DAY));

-- Insert dummy data into products table
INSERT INTO products (name, ref, description, base_price, per_discount, created_at) VALUES
('Product A', 'PROD001', 'Product A description', 100.00, 0.00, NOW()),
('Product B', 'PROD002', 'Product B description', 75.50, 0.00, NOW()),
('Product C', 'PROD003', 'Product C description', 120.25, 0.00, NOW()),
('Product D', 'PROD004', 'Product D description', 50.00, 0.00, NOW()),
('Product E', 'PROD005', 'Product E description', 200.75, 0.00, NOW());

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