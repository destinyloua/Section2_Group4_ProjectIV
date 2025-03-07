# Section2_Group4_ProjectIV
# Leaf Laugh Love 🪴

## Overview
This project is a server-client application designed for an e-commerce system. The **server** handles interactions with the database, manages orders and plants, and processes client requests. The **client** is responsible for placing orders for products, which are then processed by the server.

## Features
- **Server:**
  - Manages the database and stores orders and plant data.
  - Handles client requests and processes orders.
  - Provides responses to the client with order status updates.
  
- **Client:**
  - Allows users to view available plants and place orders.
  - Displays order confirmation and updates from the server.

## Technology Stack
- **Frontend:** Java Swing (for the client-side interface)  
- **Backend:** Java (server-side logic)  
- **Database:** MySQL (or any other database system you're using)  
- **Communication:** Socket programming (for server-client interaction)

## Installation & Setup  

### Prerequisites:
1. **Java Development Kit (JDK 23)**  
2. **MySQL Database**

### Steps to Set Up the Database:
1. Open MySQL and log into your MySQL server.
3. Run the SQL file `database.sql` to create the required tables and schema in your database.

### Note
We recommend you to re-add the Database Connector library again to avoid any issue. The external library file is named `mysql-connector-j-9.2.0.jar` (Included in this repository).
#### To add external library in Eclipse
1. Rught-click to the project folder's icon
2. Click to Build Path -> Config Build Path
4. Choose Libraries tab, click Add external JARs
5. Choose `mysql-connector-j-9.2.0.jar file, then click apply

## Authors
1. [Tyler Dao](https://github.com/TylerDdao)
2. [Destiny Loungsombath](https://github.com/destinyloua)
3. Arvin Akbari
4. Hui-Ying Huang
