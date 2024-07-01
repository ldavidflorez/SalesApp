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
    OUT spResult BIGINT,
    OUT spMessage VARCHAR(255)
)
`proc`:
BEGIN
    DECLARE totalItems INT DEFAULT 0;
    DECLARE totalPrice DECIMAL(10, 2) DEFAULT 0.00;
    DECLARE item JSON;
    DECLARE itemCount INT DEFAULT 0;
    DECLARE customerCount INT DEFAULT 0;
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
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        -- Unknown error
        SET spResult = -1;
        SET spMessage = 'Unknown error';
    END;

    -- Start transaction
    START TRANSACTION;

    -- Check if the customer exists
    SELECT COUNT(*)
    INTO customerCount
    FROM customers
    WHERE id = customerId;

    IF customerCount = 0 THEN
        COMMIT;
        -- Customer not found
        SET spResult = -2;
        SET spMessage = 'Customer not found';
        LEAVE `proc`;
    END IF;

    -- Parse items JSON
    SET itemCount = JSON_LENGTH(itemsJson);

    IF itemCount = 0 THEN
        COMMIT;
        -- Not items inserted
        SET spResult = -3;
        SET spMessage = 'No items inserted';
        LEAVE `proc`;
    END IF;

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

        -- Check if product exists
        IF unitPrice IS NULL THEN
            COMMIT;
            -- Product not found
            SET spResult = -4;
            SET spMessage = 'Product not found';
            LEAVE `proc`;
        END IF;

        -- Check the availability state and rollback if not available
        IF state = b'0' THEN
            COMMIT;
            -- Product not available
            SET spResult = -5;
            SET spMessage = 'Product not available';
            LEAVE `proc`;
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
    SET spResult = @orderId;
    SET spMessage = 'Order created successfully';
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
    0.00, -- increment percent
    @spResult,
    @spMessage
);
-- Check the value of @status
SELECT @spResult, @spMessage;

CALL CreateNewOrder(
    1, -- customerId
    'FEE_15', -- orderType
    'Pending', -- orderStatus
    '2024-06-24 10:00:00', -- initDate
    0, -- completeFees
    5, -- remainingFees
    75, -- timeToPayInDays
    '[{"productId": 1, "unitNumber": 2}, {"productId": 2, "unitNumber": 1}]', -- items JSON
    5.00, -- increment percent
    @spResult,
    @spMessage
);
-- Check the value of @status
SELECT @spResult, @spMessage;

CALL CreateNewOrder(
    1, -- customerId
    'FEE_30', -- orderType
    'Pending', -- orderStatus
    '2024-06-24 10:00:00', -- initDate
    0, -- completeFees
    2, -- remainingFees
    60, -- timeToPayInDays
    '[{"productId": 1, "unitNumber": 2}, {"productId": 2, "unitNumber": 1}]', -- items JSON
    10.00, -- increment percent
    @spResult,
    @spMessage
);
-- Check the value of @status
SELECT @spResult, @spMessage;

-- Stored procedure for insert new payment
DELIMITER //

CREATE PROCEDURE InsertNewPayment(
    IN p_orderId BIGINT,
    IN p_customerId BIGINT,
    IN p_payQuantity DECIMAL(10, 2),
    OUT spResult BIGINT,
    OUT spMessage VARCHAR(255)
)
`proc`:
BEGIN
    DECLARE completeFees INT;
    DECLARE remainingFees INT;
    DECLARE totalFees INT;
    DECLARE totalDaysToPay INT;
    DECLARE customerCount INT;
    DECLARE orderType VARCHAR(255);
    DECLARE initDate DATETIME;
    DECLARE nextCollectionDate DATETIME;
    DECLARE errorOccurred BOOLEAN DEFAULT FALSE;

    -- Declare variables for error handling
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        -- Unknown error
        SET spResult = -1;
        SET spMessage = 'Unknown error';
    END;

    -- Start transaction
    START TRANSACTION;

    -- Check if the customer exists
    SELECT COUNT(*)
    INTO customerCount
    FROM customers
    WHERE id = p_customerId;

    IF customerCount = 0 THEN
        COMMIT;
        -- Customer not found
        SET spResult = -2;
        SET spMessage = 'Customer not found';
        LEAVE `proc`;
    END IF;

    -- Get the current complete_fees, remaining_fees, and order type
    SELECT complete_fees, remaining_fees, type, init_date, time_to_pay_in_days
    INTO completeFees, remainingFees, orderType, initDate, totalDaysToPay
    FROM orders
    WHERE id = p_orderId;

    IF orderType IS NULL THEN
        COMMIT;
        -- Order not found
        SET spResult = -3;
        SET spMessage = 'Order not found';
        LEAVE `proc`;
    END IF;

    IF orderType = 'CASH' THEN
        COMMIT;
        -- Order type cannot be CASH
        SET spResult = -4;
        SET spMessage = 'Order type cannot be CASH';
        LEAVE `proc`;
    END IF;

    -- Insert new payment
    INSERT INTO payments (order_id, customer_id, pay_quantity, created_at)
    VALUES (p_orderId, p_customerId, p_payQuantity, NOW());

    -- Get the last inserted payment ID
    SET spResult = LAST_INSERT_ID();
    SET spMessage = 'Payment inserted successfully';

    -- Increment complete_fees and decrement remaining_fees
    SET completeFees = completeFees + 1;
    SET remainingFees = remainingFees - 1;

    -- Get total fees from database
    IF orderType = 'FEE_15' THEN
        SET totalFees = totalDaysToPay / 15;
    ELSEIF orderType = 'FEE_30' THEN
        SET totalFees = totalDaysToPay / 30;
    END IF;

    -- Ensure complete_fees does not exceed the initial remaining_fees
    IF completeFees > totalFees THEN
        ROLLBACK;
        -- Cannot pay more fees
        SET spResult = -5;
        SET spMessage = 'Cannot pay more fees';
        LEAVE `proc`;
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

    COMMIT;

END //

DELIMITER ;

CALL InsertNewPayment(
    5, -- orderId
    2, -- customerId
    18.35, -- payQuantity
    @spResult, -- OUT parameter
    @spMessage -- OUT parameter
);

SELECT @spResult, @spMessage; -- Check the ID of the inserted payment

-- Store procedure for insert new delay
DELIMITER //

CREATE PROCEDURE SaveNewDelay(
    IN customerId BIGINT,
    IN orderId BIGINT,
    IN surchargePercent DECIMAL(5, 2),
    IN wayDays INT,
    OUT spResult BIGINT,
    OUT spMessage VARCHAR(255)
)
`proc`:
BEGIN
    DECLARE nextCollectionDate DATETIME;
    DECLARE promisedDate DATETIME;
    DECLARE daysOfDelay INT;
    DECLARE customerCount INT;
    DECLARE currentFeeValue DECIMAL(10, 2);
    DECLARE surchargeAmount DECIMAL(10, 2);
    DECLARE orderType VARCHAR(255);

    -- Declare variables for error handling
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        -- Unknown error
        SET spResult = -1;
        SET spMessage = 'Unknown error';
    END;

    -- Start transaction
    START TRANSACTION;

    -- Check if the customer exists
    SELECT COUNT(*)
    INTO customerCount
    FROM customers
    WHERE id = customerId;

    IF customerCount = 0 THEN
        COMMIT;
        -- Customer not found
        SET spResult = -2;
        SET spMessage = 'Customer not found';
        LEAVE `proc`;
    END IF;

    -- Get order type, next collection date, and current fee value from orders table
    SELECT type, next_collection_date, fee_value
    INTO orderType, nextCollectionDate, currentFeeValue
    FROM orders
    WHERE id = orderId;

    -- Check if order exists
    IF orderType IS NULL THEN
        COMMIT;
        -- order not found
        SET spResult = -3;
        SET spMessage = 'Order not found';
        LEAVE `proc`;
    END IF;

    -- Check if order type is CASH
    IF orderType = 'CASH' THEN
        COMMIT;
        -- This procedure is not suitable for CASH type orders
        SET spResult = -4;
        SET spMessage = 'Procedure not suitable for CASH type orders';
        LEAVE `proc`;
    ELSE
        -- Calculate promised date and days of delay
        SET promisedDate = nextCollectionDate;
        SET daysOfDelay = DATEDIFF(NOW(), nextCollectionDate);

        -- Calculate surcharge amount based on percentage
        SET surchargeAmount = currentFeeValue * (surchargePercent / 100);

        -- Insert new record into delays table
        INSERT INTO delays (customer_id, order_id, promised_date, days_of_delay, created_at)
        VALUES (customerId, orderId, promisedDate, daysOfDelay, NOW());

        -- Update fee_value in orders table with surcharge amount and new promised date
        UPDATE orders
        SET fee_value = fee_value + surchargeAmount,
            next_collection_date = DATE_ADD(NOW(), INTERVAL wayDays DAY)
        WHERE id = orderId;

        -- Commit transaction
        COMMIT;

        -- Set result code to last inserted delay ID
        SET spResult = LAST_INSERT_ID();
        SET spMessage = 'Delay saved successfully';
    END IF;
END //

DELIMITER ;

-- Declare a variable to hold the result
SET @spResult = 0;
SET @spMessage = '';

-- Call the stored procedure with a 10% surcharge
CALL SaveNewDelay(2, 5, 10.00, 3, @spResult, @spMessage);

-- Output the result
SELECT @spResult, @spMessage;