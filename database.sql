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
('Catus', 20.99, 20, 'cactus.jpg'),
('Cylinder Snake Plant', 14.99, 15, 'cylinder_snake_plant.jpg'),
('Rubber Plant', 55.50, 10, 'rubber_plant.jpg'),
('Sansevieria Snake Plant', 29.99, 25, 'sansevieria_snake_plant.jpg'),
('Succulent Plant', 7.50, 25, 'succulent_plant.jpg');

-- Step 4: Insert a Sample Account
INSERT INTO Accounts (fName, lName, email, password) VALUES
("Avrin", "Akbari", "avrin@gmail.com", 123456789),
("Destiny", "Louangsombath", "destiny@gmail.com", 123456789),
("Hui-Ying", "Huang", "ying@gmail.com", 123456789),
("Tyler", "Dao", "tyler@gmail.com", 123456789);

-- Step 5: Create Two Orders for the Account (Assume John has id=1)
INSERT INTO Orders (aID, totalPrice, status) VALUES
(1, 99.99, 0),  -- First Order
(2, 99.99, 1), -- Second Order
(3, 99.99, 2), -- Third Order
(4, 99.99, -1); -- Fourth Order

-- Step 6: Add Items to the Orders
-- First Order (ID: 1)
INSERT INTO Order_items (oId, pId, quantity) VALUES
(1, 1, 2), -- Aloe Vera x2
(1, 3, 1); -- Peace Lily x1

-- Second Order (ID: 2)
INSERT INTO Order_items (oId, pId, quantity) VALUES
(2, 2, 1),
(2, 4, 2);

-- Second Order (ID: 3)
INSERT INTO Order_items (oId, pId, quantity) VALUES
(3, 3, 1),
(3, 4, 1);

-- Second Order (ID: 4)
INSERT INTO Order_items (oId, pId, quantity) VALUES
(4, 2, 5),
(4, 3, 2);

-- Step 7: Retrieve Items from the First Order
SELECT p.id, oi.quantity FROM Order_items oi JOIN Plants p ON oi.pId = p.id WHERE oi.oId = 1;

SELECT p.id, oi.quantity, o.id FROM Order_items oi JOIN Plants p ON oi.pId = p.id JOIN Orders o ON o.id = oi.oId WHERE o.aId = 1;


Select * from Orders Where aId = 1;

-- Step 8: Verify Data
SELECT * FROM Orders;
SELECT * FROM Accounts;
SELECT * FROM Order_items;

SELECT * FROM Plants;