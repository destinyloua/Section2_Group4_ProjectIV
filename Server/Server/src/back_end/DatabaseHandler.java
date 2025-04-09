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
            e.getStackTrace();
            return false;
        }
	}
	
	public static Boolean CheckConnection() {
	    try {
	        if (connection == null || connection.isClosed()) {
	            System.out.println("Database is NOT connected.");
				FileHandler.SaveLog("Server failed to connect to database.");
	            return false;
	        } else {
	        	FileHandler.SaveLog("Server connected to database.");
	            System.out.println("Database is connected.");
	            return true;
	        }
	    } catch (SQLException e) {
			FileHandler.SaveLog("Server failed to connect to database.");
	        System.out.println("Error checking connection: " + e.getMessage());
	        return false;
	    }
	}
	
	public static Boolean UpdateAccount(Account a) {
		String query = "UPDATE Accounts SET fName = ?, lName = ?, email = ?, password = ? WHERE id = ?";
		try {
			pstm = connection.prepareStatement(query);
			pstm.setString(1, a.GetFName());
			pstm.setString(2, a.GetLName());
			pstm.setString(3, a.GetEmail());
			pstm.setInt(4, a.GetPassword());
			pstm.setInt(5, a.GetId());
			
			int rowsUpdated = pstm.executeUpdate(); // Correct method for UPDATE
			System.out.println("rowsUpdated: "+ rowsUpdated);
			FileHandler.SaveLog("Account #"+ a.GetId() + " updated.");
			return true;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
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
				FileHandler.SaveLog("Plant "+p.GetName() + " created.");
				return true;
			}
		}
		catch(Exception e) {
			FileHandler.SaveLog("Server failed to create plant.");
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
				FileHandler.SaveLog("Account " + a.GetEmail() + " created.");
				return true;
			}
		}
		catch(Exception e) {
			FileHandler.SaveLog("Server failed to create account.");
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
				FileHandler.SaveLog("Account " + account.GetEmail() + " is authenticated.");
				return true;
			}
			else {
				FileHandler.SaveLog("Account " + account.GetEmail() + " is not authenticated.");
				return false;
			}
		}
		catch(Exception e) {
			FileHandler.SaveLog("Server failed to authenticate account.");
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
	
	public static Vector<Order> FetchOrdersList(){
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
	
	public static Order FetchOrderById(int id){
		String query1 = "Select * from Orders Where id = ?";
		int oId;
		int aId;
		float totalPrice;
		int status;
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
		Vector<Integer> pId = new Vector<>();
		Vector<Integer> quantity = new Vector<>();
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
            if(rowsAffected > 0) {
                FileHandler.SaveLog("Order #" + orderId + "'s status is updated."); 
            	return true;
            }
            else {
            	FileHandler.SaveLog("Order #" + orderId + "'s status is not updated."); 
            	return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            FileHandler.SaveLog("Server failed to update order's status."); 
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
            if(rowsAffected > 0) {
                FileHandler.SaveLog("Order #" + id + " is deleted."); 
            	return true;
            }
            else {
            	FileHandler.SaveLog("Order #" + id + " is deleted."); 
            	return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            FileHandler.SaveLog("Server failed to delete order."); 
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
	
	public static Vector<Order> FetchOrdersListByAId(int aId){
		Vector<Order> ordersList = new Vector<>();
		String query = "Select * from Orders Where aId = ?";
		try {
			pstm = connection.prepareStatement(query);
			pstm.setInt(1, aId);
			resultSet = pstm.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt(1);
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
	
//	public static Vector<Order> FetchOrdersListByAId(int accountId) {
//		Vector<Order> ordersList = new Vector<>();
//		String query = "Select * from Orders where aId = ?";
//		try {
//			pstm = connection.prepareStatement(query);
//			pstm.setInt(1, accountId);
//			resultSet = pstm.executeQuery();
//			while(resultSet.next()) {
//				int id = resultSet.getInt(1);
//				int aId = resultSet.getInt(2);
//				float total = resultSet.getFloat(3);
//				int status = resultSet.getInt(4);
//				System.out.println("Order ID: " + id);
//				Vector<Integer> pId = new Vector<>();
//				Vector<Integer> quantity = new Vector<>();
//				String query2 = "SELECT p.id, oi.quantity FROM Order_items oi JOIN Plants p ON oi.pId = p.id WHERE oi.oId = ?";
//				try {
//					pstm = connection.prepareStatement(query2);
//					pstm.setInt(1, id);
//					resultSet = pstm.executeQuery();
//					while(resultSet.next()) {
//						pId.add(resultSet.getInt(1));
//						quantity.add(resultSet.getInt(1));
//					}
//				}
//				catch(Exception e) {
//					System.out.println("Error: " + e.getMessage());
//					return null;
//				}
//				Order o = new Order(id, aId, total, status, pId, quantity);
//				ordersList.add(o);
//			}
//		}
//		catch(Exception e) {
//			System.out.println("Error: " + e.getMessage());
//		}
//		return ordersList;
//	}
	
//	public static Boolean InsertNewOrder(Order o) {
//		String query = "INSERT INTO Orders (aID, totalPrice, status) VALUES (?,?,?)";
//		try {
//			pstm = connection.prepareStatement(query);
//			pstm.setInt(1, o.GetAId());
//			pstm.setFloat(2, o.GetTotalPrice());
//			pstm.setInt(3, o.GetStatus());
//			
//			int rowInserted = pstm.executeUpdate();
//			
//			if(rowInserted > 0 && InsertItems(o)) {
//				System.out.println("Inserted successfully");
//				return true;
//			}
//		}
//		catch(Exception e) {
//			e.getStackTrace();
//		}
//		return false;
//	}
	
	public static Boolean InsertNewOrder(Order o) {
	    String query = "INSERT INTO Orders (aID, totalPrice, status) VALUES (?,?,?)";
	    try {
	        pstm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        pstm.setInt(1, o.GetAId());
	        pstm.setFloat(2, o.GetTotalPrice());
	        pstm.setInt(3, o.GetStatus());

	        int rowInserted = pstm.executeUpdate();
	        
	        if (rowInserted > 0) {
	            ResultSet rs = pstm.getGeneratedKeys();
	            if (rs.next()) {
	                int generatedId = rs.getInt(1);
	                o.SetId(generatedId);  // Ensure your Order class has SetId() method
	                System.out.println(o.GetId());
	            }

	            if (InsertItems(o)) {
	                FileHandler.SaveLog("New order created"); 
	                System.out.println("Inserted successfully");
	                return true;
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
            FileHandler.SaveLog("Server failed to create new order."); 

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

