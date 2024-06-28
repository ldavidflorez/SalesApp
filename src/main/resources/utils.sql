-- Stored procedure for insert new order
DELIMITER //

CREATE PROCEDURE CreateNewOrder(
    IN customerId BIGINT,
    IN orderType VARCHAR(255),
    IN orderStatus VARCHAR(255),
    IN initDate DATETIME,
    IN completeFees INT,
    IN remainingFees INT,
    IN timeToPayInDays INT,
    IN itemsJson TEXT,
    IN increment DECIMAL(10, 2),
    OUT orderIdGenerated INT
)
BEGIN
    DECLARE totalItems INT DEFAULT 0;
    DECLARE totalPrice DECIMAL(10, 2) DEFAULT 0.00;
    DECLARE item JSON;
    DECLARE itemCount INT DEFAULT 0;
    DECLARE productId BIGINT;
    DECLARE unitNumber INT;
    DECLARE unitPrice DECIMAL(10, 2);
    DECLARE discount DECIMAL(5, 2);
    DECLARE itemPrice DECIMAL(10, 2);
    DECLARE feeValue DECIMAL(10, 2);
    DECLARE endDate DATETIME;
    DECLARE nextCollectionDate DATETIME;
    DECLARE state BIT(1);

    -- Declare variables for error handling
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET orderIdGenerated = 0;
    END;

    -- Declare handler for custom signal
    DECLARE CONTINUE HANDLER FOR SQLSTATE '45000'
    BEGIN
        ROLLBACK;
        SET orderIdGenerated = 0;
    END;

    -- Start transaction
    START TRANSACTION;

    -- Parse items JSON
    SET itemCount = JSON_LENGTH(itemsJson);

    -- Loop through the items JSON array
    SET @i = 0;
    WHILE @i < itemCount DO
        SET item = JSON_EXTRACT(itemsJson, CONCAT('$[', @i, ']'));
        SET productId = JSON_UNQUOTE(JSON_EXTRACT(item, '$.productId'));
        SET unitNumber = JSON_UNQUOTE(JSON_EXTRACT(item, '$.unitNumber'));

        -- Check if the product exists and get its price, discount, and availability state
        SELECT base_price, per_discount, available
        INTO unitPrice, discount, state
        FROM products
        WHERE id = productId;

        -- Check the availability state and rollback if not available
        IF state = b'0' THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Product not available';
        END IF;

        -- Calculate the price for this item after discount
        SET itemPrice = unitPrice * unitNumber * (1 - discount / 100);

        -- Update total items and total price
        SET totalItems = totalItems + unitNumber;
        SET totalPrice = totalPrice + itemPrice;

        -- Increment loop counter
        SET @i = @i + 1;
    END WHILE;

    -- Calculate endDate
    SET endDate = DATE_ADD(initDate, INTERVAL timeToPayInDays DAY);

    -- Calculate nextCollectionDate and fee_value based on orderType
    IF orderType = 'FEE_15' THEN
        SET totalPrice = totalPrice * increment; -- Increment by increment%
        SET feeValue = totalPrice / (timeToPayInDays / 15);
        SET nextCollectionDate = DATE_ADD(initDate, INTERVAL 15 DAY);
    ELSEIF orderType = 'FEE_30' THEN
        SET totalPrice = totalPrice * increment; -- Increment by increment%
        SET feeValue = totalPrice / (timeToPayInDays / 30);
        SET nextCollectionDate = DATE_ADD(initDate, INTERVAL 30 DAY);
    ELSE
        SET feeValue = NULL; -- No fee for other types
        SET nextCollectionDate = NULL; -- No next collection date for other types
    END IF;

    -- Insert new order
    INSERT INTO orders (customer_id, created_at, type, total_items, total_price, status, init_date, end_date, complete_fees, remaining_fees, time_to_pay_in_days, fee_value, next_collection_date)
    VALUES (customerId, NOW(), orderType, totalItems, totalPrice, orderStatus, initDate, endDate, completeFees, remainingFees, timeToPayInDays, feeValue, nextCollectionDate);

    -- Get the last inserted order ID
    SET @orderId = LAST_INSERT_ID();

    -- Loop through the items JSON array again to insert into items table
    SET @i = 0;
    WHILE @i < itemCount DO
        SET item = JSON_EXTRACT(itemsJson, CONCAT('$[', @i, ']'));
        SET productId = JSON_UNQUOTE(JSON_EXTRACT(item, '$.productId'));
        SET unitNumber = JSON_UNQUOTE(JSON_EXTRACT(item, '$.unitNumber'));

        -- Check if the product exists and get its price
        SELECT base_price, per_discount
        INTO unitPrice, discount
        FROM products
        WHERE id = productId;

        -- Calculate the price for this item after discount plus fee increment
        SET itemPrice = (unitPrice * unitNumber * (1 - discount / 100)) * increment;

        -- Insert each item into the items table
        INSERT INTO items (order_id, customer_id, product_id, unit_price, unit_number, created_at)
        VALUES (@orderId, customerId, productId, itemPrice, unitNumber, NOW());

        -- Increment loop counter
        SET @i = @i + 1;
    END WHILE;

    -- Commit transaction
    COMMIT;

    -- Set id of generated order
    SET orderIdGenerated = @orderId;
END //

DELIMITER ;

CALL CreateNewOrder(
    1, -- customerId
    'CASH', -- orderType
    'Completed', -- orderStatus
    '2024-06-24 10:00:00', -- initDate
    1, -- completeFees
    0, -- remainingFees
    0, -- timeToPayInDays
    '[{"productId": 1, "unitNumber": 2}, {"productId": 2, "unitNumber": 1}]', -- items JSON
    1.00, -- increment factor
    @status
);
-- Check the value of @status
SELECT @status;

CALL CreateNewOrder(
    1, -- customerId
    'FEE_15', -- orderType
    'Pending', -- orderStatus
    '2024-06-24 10:00:00', -- initDate
    0, -- completeFees
    5, -- remainingFees
    75, -- timeToPayInDays
    '[{"productId": 1, "unitNumber": 2}, {"productId": 2, "unitNumber": 1}]', -- items JSON
    1.05, -- increment factor
    @status
);
-- Check the value of @status
SELECT @status;

CALL CreateNewOrder(
    1, -- customerId
    'FEE_30', -- orderType
    'Pending', -- orderStatus
    '2024-06-24 10:00:00', -- initDate
    0, -- completeFees
    2, -- remainingFees
    60, -- timeToPayInDays
    '[{"productId": 1, "unitNumber": 2}, {"productId": 2, "unitNumber": 1}]', -- items JSON
    1.10, -- increment factor
    @status
);
-- Check the value of @status
SELECT @status;

-- Stored procedure for insert new payment
DELIMITER //

CREATE PROCEDURE InsertNewPayment(
    IN p_orderId BIGINT,
    IN p_customerId BIGINT,
    IN p_payQuantity DECIMAL(10, 2),
    OUT paymentId BIGINT
)
BEGIN
    DECLARE completeFees INT;
    DECLARE remainingFees INT;
    DECLARE orderType VARCHAR(255);
    DECLARE initDate DATETIME;
    DECLARE nextCollectionDate DATETIME;
    DECLARE errorOccurred BOOLEAN DEFAULT FALSE;

    -- Start transaction
    START TRANSACTION;

    -- Insert new payment
    INSERT INTO payments (order_id, customer_id, pay_quantity, created_at)
    VALUES (p_orderId, p_customerId, p_payQuantity, NOW());

    -- Get the last inserted payment ID
    SET paymentId = LAST_INSERT_ID();

    -- Get the current complete_fees, remaining_fees, and order type
    SELECT complete_fees, remaining_fees, type, init_date
    INTO completeFees, remainingFees, orderType, initDate
    FROM orders
    WHERE id = p_orderId;

    -- Increment complete_fees and decrement remaining_fees
    SET completeFees = completeFees + 1;
    SET remainingFees = remainingFees - 1;

    -- Ensure complete_fees does not exceed the initial remaining_fees
    IF completeFees > (completeFees + remainingFees) THEN
        -- Set error flag
        SET errorOccurred = TRUE;
    ELSE
        -- Compute next_collection_date based on order type
        IF orderType = 'FEE_15' THEN
            SET nextCollectionDate = DATE_ADD(initDate, INTERVAL (1 + completeFees) * 15 DAY);
        ELSEIF orderType = 'FEE_30' THEN
            SET nextCollectionDate = DATE_ADD(initDate, INTERVAL (1 + completeFees) * 30 DAY);
        END IF;

        -- Update the order with the new fee counts and status if complete
        UPDATE orders
        SET complete_fees = completeFees,
            remaining_fees = remainingFees,
            status = IF(remainingFees = 0, 'Completed', status),
            next_collection_date = IF(remainingFees = 0, NULL, nextCollectionDate)
        WHERE id = p_orderId;
    END IF;

    -- Check for errors and rollback if any
    IF errorOccurred THEN
        ROLLBACK;
        SET paymentId = 0;
    ELSE
        COMMIT;
    END IF;

END //

DELIMITER ;

CALL InsertNewPayment(
    5, -- orderId
    2, -- customerId
    18.35, -- payQuantity
    @paymentId -- OUT parameter
);

SELECT @paymentId; -- Check the ID of the inserted payment

