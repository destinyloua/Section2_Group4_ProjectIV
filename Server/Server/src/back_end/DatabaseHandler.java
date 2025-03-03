package back_end;

import java.sql.*;
import java.util.*;
import objects.*;

public class DatabaseHandler {
	private static String url;
	private static String user;
	private static String password;
	
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private static PreparedStatement pstm;

	public static void SetUpConnection(String urlInput, String userInput, String passwordInput) {
		url = urlInput;
		user = userInput;
		password = passwordInput;
	}
	
	public static Boolean ConnectDatabase() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
	}
	
	public static Boolean CheckConnection() {
	    try {
	        if (connection == null || connection.isClosed()) {
	            System.out.println("Database is NOT connected.");
	            return false;
	        } else {
	            System.out.println("Database is connected.");
	            return true;
	        }
	    } catch (SQLException e) {
	        System.out.println("Error checking connection: " + e.getMessage());
	        return false;
	    }
	}
	
	public static int GetNumberOfOrder() {
		String query = "Select count(*) from Orders";
		try {
			pstm = connection.prepareStatement(query);

			resultSet = pstm.executeQuery();
			resultSet.next();
			int rowReturned = resultSet.getInt(1);
			return rowReturned;
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return 0;
	}
	
	public static int GetNumberOfOrder(int status) {
		String query = "Select count(*) from Orders WHERE status = ?";
		try {
			pstm = connection.prepareStatement(query);
			pstm.setInt(1, status);
			
			resultSet = pstm.executeQuery();
			resultSet.next();
			int rowReturned = resultSet.getInt(1);
			return rowReturned;
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return 0;
	}
	
	public static Boolean CheckDuplicatedEmail(String email) {
		String query = "Select count(*) from Accounts where email = ?";
		try {
			pstm = connection.prepareStatement(query);
			pstm.setString(1, email);
			
			resultSet = pstm.executeQuery();
			resultSet.next();
			int rowReturned = resultSet.getInt(1);
			if(rowReturned == 0) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return false;
	}
	
	public static Boolean InsertNewPlant(Plant p) {
		String query = "INSERT INTO Plants (name, price, quantity, imagePath) VALUES (?, ?, ?, ?)";
		try {
			pstm=connection.prepareStatement(query);
			pstm.setString(1, p.GetName());
			pstm.setFloat(2, p.GetPrice());
			pstm.setInt(3, p.GetQuantity());
			pstm.setString(4, p.GetImagePath());
			int rowInserted = pstm.executeUpdate();
			if(rowInserted>0) {
				System.out.println("New plant inserted");
				return true;
			}
		}
		catch(Exception e) {
			e.getStackTrace();
		}
		return false;
	}
	
	public static Boolean InsertNewAccount(Account a) {
		String query = "Insert into Accounts (fName, lName, email, password) values (?,?,?,?)";
		try {
			if(!CheckDuplicatedEmail(a.GetEmail())) {
				System.out.println("Email is already used");
				return false;
			}
			pstm = connection.prepareStatement(query);
			pstm.setString(1, a.GetFName());
			pstm.setString(2, a.GetLName());
			pstm.setString(3, a.GetEmail());
			pstm.setInt(4, a.GetPassword());
			
			int rowInserted = pstm.executeUpdate();
			if(rowInserted > 0) {
				System.out.println("Inserted successfully");
				return true;
			}
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return false;
	}
	
	public static Boolean AuthenticateLogin(Account account) {
		String query = "Select count(*) from Accounts where email = ? and password = ?";
		try {
			pstm = connection.prepareStatement(query);
			pstm.setString(1, account.GetEmail());
			pstm.setInt(2, account.GetPassword());
			
			resultSet = pstm.executeQuery();
			resultSet.next();
			int rowReturned = resultSet.getInt(1);
			if(rowReturned > 0) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return false;
	}
	
	public static Vector<Plant> FetchPlantsList(){
		Vector<Plant> plantsList = new Vector<>();
		String query = "Select * from Plants ORDER BY name ASC";
		try {
			pstm = connection.prepareStatement(query);
			resultSet = pstm.executeQuery();
			while(resultSet.next()) {
			//resultSet.next();
			int id = resultSet.getInt(1);
			String name = resultSet.getString(2);
			float price = resultSet.getFloat(3);
			int quantity = resultSet.getInt(4);
			String imagePath = resultSet.getString(5);
			System.out.println("ID: " + id);
			System.out.println("Name: " + name);
			System.out.println("Price: " + price);
			System.out.println("Quantity: " + quantity);
			System.out.println("Image Path: " + imagePath);
			System.out.println("---------------");
			Plant newPlant = new Plant(id, name, price, quantity, imagePath);
			plantsList.add(newPlant);
			}
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return plantsList;
	}
	
	public static Vector<Order> FecthOrdersList(){
		Vector<Order> ordersList = new Vector<>();
		String query = "Select * from Orders";
		try {
			pstm = connection.prepareStatement(query);
			resultSet = pstm.executeQuery();
			while(resultSet.next()) {
			int id = resultSet.getInt(1);
			int aId = resultSet.getInt(2);
			float totalPrice = resultSet.getFloat(3);
			int status = resultSet.getInt(4);
			Order newOrder = new Order(id, aId, totalPrice, status);
			ordersList.add(newOrder);
			}
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return ordersList;
	}
	
	public static Order FecthOrder(int id){
		String query1 = "Select * from Orders Where id = ?";
		int oId;
		int aId;
		float totalPrice;
		int status;
		Vector<Integer> pId = new Vector<>();
		Vector<Integer> quantity = new Vector<>();
		try {
			pstm = connection.prepareStatement(query1);
			pstm.setInt(1, id);
			resultSet = pstm.executeQuery();
			resultSet.next();
			oId = resultSet.getInt(1);
			aId = resultSet.getInt(2);
			totalPrice = resultSet.getFloat(3);
			status = resultSet.getInt(4);
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}
		String query2 = "SELECT p.id, oi.quantity FROM Order_items oi JOIN Plants p ON oi.pId = p.id WHERE oi.oId = ?";
		try {
			pstm = connection.prepareStatement(query2);
			pstm.setInt(1, id);
			resultSet = pstm.executeQuery();
			while(resultSet.next()) {
				pId.add(resultSet.getInt(1));
				quantity.add(resultSet.getInt(1));
			}
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}
		Order order = new Order(oId, aId, totalPrice, status, pId, quantity);
		return order;
	}
	
	public static Boolean UpdateOrderStatus(int orderId, int newStatus) {
        // SQL query to update the order status
        String query = "UPDATE orders SET status = ? WHERE id = ?";
        try{
        	pstm = connection.prepareStatement(query);
            // Set the parameters for the query
        	pstm.setInt(1, newStatus);  // Status (new value)
        	pstm.setInt(2, orderId);    // Order ID

            // Execute the update query
            int rowsAffected = pstm.executeUpdate();

            // Return true if one or more rows were updated
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	public static Boolean DeleteOrder(int id) {
        // SQL query to update the order status
        String query = "DELETE FROM orders WHERE id = ?";
        try{
        	pstm = connection.prepareStatement(query);
            // Set the parameters for the query
        	pstm.setInt(1, id);  // Status (new value)

            // Execute the update query
            int rowsAffected = pstm.executeUpdate();

            // Return true if one or more rows were updated
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public static Account FetchAccount(int id){
		String query1 = "SELECT * from Accounts WHERE id = ?";
		int aId;
		String fName;
		String lName;
		String email;
		int password;
		try {
			pstm = connection.prepareStatement(query1);
			pstm.setInt(1, id);
			resultSet = pstm.executeQuery();
			resultSet.next();
			aId = resultSet.getInt(1);
			fName = resultSet.getString(2);
			lName = resultSet.getString(3);
			email = resultSet.getString(4);
			password = resultSet.getInt(5);
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}
		Account a = new Account(aId, fName, lName, email, password);
		return a;
	}
	
	public static Account FetchAccountByEmail(String email){
		String query1 = "SELECT * from Accounts WHERE email = ?";
		int aId;
		String fName;
		String lName;
		int password;
		try {
			pstm = connection.prepareStatement(query1);
			pstm.setString(1, email);
			resultSet = pstm.executeQuery();
			resultSet.next();
			aId = resultSet.getInt(1);
			fName = resultSet.getString(2);
			lName = resultSet.getString(3);
			password = resultSet.getInt(5);
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}
		Account a = new Account(aId, fName, lName, email, password);
		return a;
	}
	
	public static Plant FetchPlant(int id) {
		String query = "SELECT * FROM Plants WHERE id = ?";
		try {
			pstm = connection.prepareStatement(query);
			pstm.setInt(1, id);
			resultSet = pstm.executeQuery();
			resultSet.next();
			String name = resultSet.getString(2);
			float price = resultSet.getFloat(3);
			int quantity = resultSet.getInt(4);
			String imagePath = resultSet.getString(5);
			Plant plant = new Plant(id, name, price, quantity, imagePath);
			return plant;
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}
	}
	
	public static Order FetchOrderByAId(int accountId) {
		String query1 = "Select * from Orders Where aId = ?";
		int oId;
		int aId;
		float totalPrice;
		int status;
		Vector<Integer> pId = new Vector<>();
		Vector<Integer> quantity = new Vector<>();
		try {
			pstm = connection.prepareStatement(query1);
			pstm.setInt(1, accountId);
			resultSet = pstm.executeQuery();
			resultSet.next();
			oId = resultSet.getInt(1);
			aId = resultSet.getInt(2);
			totalPrice = resultSet.getFloat(3);
			status = resultSet.getInt(4);
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}
		String query2 = "SELECT p.id, oi.quantity, o.id FROM Order_items oi JOIN Plants p ON oi.pId = p.id JOIN Orders o ON o.id = oi.oId WHERE o.aId = ?";
		try {
			pstm = connection.prepareStatement(query2);
			pstm.setInt(1, accountId);
			resultSet = pstm.executeQuery();
			while(resultSet.next()) {
				pId.add(resultSet.getInt(1));
				quantity.add(resultSet.getInt(1));
			}
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}
		Order order = new Order(oId, aId, totalPrice, status, pId, quantity);
		return order;
	}
	
	public static Boolean InsertNewOrder(Order o) {
		String query = "INSERT INTO Orders (aID, totalPrice, status) VALUES (?,?,?)";
		try {
			pstm = connection.prepareStatement(query);
			pstm.setInt(1, o.GetAId());
			pstm.setFloat(2, o.GetTotalPrice());
			pstm.setInt(3, o.GetStatus());
			
			int rowInserted = pstm.executeUpdate();
			
			if(rowInserted > 0 && InsertItems(o)) {
				System.out.println("Inserted successfully");
				return true;
			}
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return false;
	}
	
	public static Boolean InsertItems(Order o) {
		String query = "INSERT INTO Order_items (oId, pId, quantity) VALUES (?,?,?)";
		try {
			pstm = connection.prepareStatement(query);
			Vector<Integer> pId = o.GetPId();
			Vector<Integer> quantity = o.GetQuantity();
			
			for(int i =0; i < pId.size(); i++) {
				pstm.setInt(1, o.GetId());
				pstm.setInt(2, pId.get(i));
				pstm.setInt(3, quantity.get(i));
				int rowInserted = pstm.executeUpdate();
				if(rowInserted <=0) {
					return false;
				}
			}
			System.out.println("Inserted successfully");
			return true;
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return false;
	}
	
}

