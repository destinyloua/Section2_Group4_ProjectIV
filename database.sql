create database project4;
use project4;
CREATE TABLE Accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fName VARCHAR(100),
    lName VARCHAR(100),
    email VARCHAR(100),
    password varchar(100)
);

Create table Plants(
	id INT auto_increment primary key,
    name varchar(100),
    price float,
    quantity int,
    imagePath varchar(100)
);

Create table Orders(
	id INT auto_increment primary key,
    aID int,
    totalPrice float,
    status varchar(100),
    foreign key(aID) references accounts(id)
);

Create table Order_items(
	oId int,
	pId int,
    quantity int,
    foreign key (oId) references Orders(id),
    foreign key (pId) references Plants(id)
);

Select*from Accounts;

Select*from Plants;

delete from accounts where id=4;

Select count(*) from Accounts where email = "tyler@gmail.com" and password = "Nam@326389";