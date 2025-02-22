CREATE DATABASE project4;
USE project4;

-- Step 2: Create Tables
CREATE TABLE Accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fName VARCHAR(100),
    lName VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password int
);

CREATE TABLE Plants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    price FLOAT,
    quantity INT,
    imagePath VARCHAR(100)
);

CREATE TABLE Orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    aID INT,
    totalPrice FLOAT,
    status INT,
    FOREIGN KEY (aID) REFERENCES Accounts(id) ON DELETE CASCADE
);

CREATE TABLE Order_items (
    oId INT,
    pId INT,
    quantity INT,
    PRIMARY KEY (oId, pId),
    FOREIGN KEY (oId) REFERENCES Orders(id) ON DELETE CASCADE,
    FOREIGN KEY (pId) REFERENCES Plants(id) ON DELETE CASCADE
);

-- Step 3: Insert Sample Data into Plants
INSERT INTO Plants (name, price, quantity, imagePath) VALUES
('Aloe Vera', 5.99, 20, 'images/aloe_vera.jpg'),
('Snake Plant', 12.49, 15, 'images/snake_plant.jpg'),
('Peace Lily', 8.99, 10, 'images/peace_lily.jpg'),
('Spider Plant', 7.50, 25, 'images/spider_plant.jpg');

-- Step 4: Insert a Sample Account
INSERT INTO Accounts (fName, lName, email, password) VALUES
('John', 'Doe', 'john.doe@example.com', 1353467532);

-- Get the account ID
SELECT id FROM Accounts WHERE email = 'john.doe@example.com';

-- Step 5: Create Two Orders for the Account (Assume John has id=1)
INSERT INTO Orders (aID, totalPrice, status) VALUES
(1, 26.98, 0),  -- First Order
(1, 34.99, 1), -- Second Order
(1, 34.99, 2), -- Third Order
(1, 34.99, -1); -- Fourth Order

-- Get order IDs
SELECT id FROM Orders WHERE aID = 1 ORDER BY id ASC;

-- Step 6: Add Items to the Orders
-- First Order (ID: 1)
INSERT INTO Order_items (oId, pId, quantity) VALUES
(1, 1, 2), -- Aloe Vera x2
(1, 3, 1); -- Peace Lily x1

-- Second Order (ID: 2)
INSERT INTO Order_items (oId, pId, quantity) VALUES
(2, 2, 1), -- Snake Plant x1
(2, 4, 2); -- Spider Plant x2

-- Step 7: Retrieve Items from the First Order
SELECT p.id, oi.quantity FROM Order_items oi JOIN Plants p ON oi.pId = p.id WHERE oi.oId = 1;

Select * from Orders Where id = 1;

-- Step 8: Verify Data
SELECT * FROM Orders;
SELECT * FROM Accounts;
SELECT * FROM Order_items;

SELECT * FROM Plants WHERE id = 1;