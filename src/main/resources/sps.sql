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

    -- Declare variables for error handling
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
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

        -- Check if the product exists and get its price and discount
        SELECT base_price, per_discount
        INTO unitPrice, discount
        FROM products
        WHERE id = productId;

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
        SET totalPrice = totalPrice * 1.025; -- Increment by 2.5%
        SET feeValue = totalPrice / (timeToPayInDays / 15);
        SET nextCollectionDate = DATE_ADD(initDate, INTERVAL 15 DAY);
    ELSEIF orderType = 'FEE_30' THEN
        SET totalPrice = totalPrice * 1.055; -- Increment by 5.5%
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
        SELECT base_price
        INTO unitPrice
        FROM products
        WHERE id = productId;

        -- Insert each item into the items table
        INSERT INTO items (order_id, customer_id, product_id, unit_price, unit_number, created_at)
        VALUES (@orderId, customerId, productId, unitPrice, unitNumber, NOW());

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
    '[{"productId": 1, "unitNumber": 2}, {"productId": 2, "unitNumber": 1}]' -- items JSON
);

CALL CreateNewOrder(
    1, -- customerId
    'FEE_15', -- orderType
    'Pending', -- orderStatus
    '2024-06-24 10:00:00', -- initDate
    0, -- completeFees
    5, -- remainingFees
    75, -- timeToPayInDays
    '[{"productId": 1, "unitNumber": 2}, {"productId": 2, "unitNumber": 1}]' -- items JSON
);

CALL CreateNewOrder(
    1, -- customerId
    'FEE_30', -- orderType
    'Pending', -- orderStatus
    '2024-06-24 10:00:00', -- initDate
    0, -- completeFees
    2, -- remainingFees
    60, -- timeToPayInDays
    '[{"productId": 1, "unitNumber": 2}, {"productId": 2, "unitNumber": 1}]' -- items JSON
);