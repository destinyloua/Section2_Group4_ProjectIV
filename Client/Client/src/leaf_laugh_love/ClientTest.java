package leaf_laugh_love;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

	public class ClientTest {
	    
		   // A dummy DatabaseHandler class for testing login functionality
	    static class DummyDatabaseHandler {
	        public Boolean authenticateLogin(String username, String password) {
	            return "validUser".equals(username) && "validPassword".equals(password);
	        }
	    }

	    @Test
	    public void testLoginWithValidCredentials() {
	        // Arrange
	        String validUsername = "validUser";
	        String validPassword = "validPassword";

	        // Create the client with a dummy DatabaseHandler (no server interaction)
	        DummyDatabaseHandler dummyDatabaseHandler = new DummyDatabaseHandler();
	        Client client = new Client(dummyDatabaseHandler);

	        // Act
	        boolean loginResult = client.login(validUsername, validPassword);

	        // Assert
	        assertTrue(loginResult, "Login should succeed with valid credentials");
	    }
	    
	    @Test
	    public void testLoginWithInvalidCredentials() {
	        // Arrange
	        String invalidUsername = "invalidUser";
	        String invalidPassword = "invalidPassword";

	        // Create the client with a dummy DatabaseHandler (no server interaction)
	        DummyDatabaseHandler dummyDatabaseHandler = new DummyDatabaseHandler();
	        Client client = new Client(dummyDatabaseHandler);

	        // Act
	        boolean loginResult = client.login(invalidUsername, invalidPassword);

	        // Assert
	        assertFalse(loginResult, "Login should fail with invalid credentials");
	    }

	    @Test
	    public void testViewMenuAfterLogin() {
	        // Arrange
	        String validUsername = "validUser";
	        String validPassword = "validPassword";

	        // Create the client with a dummy DatabaseHandler (no server interaction)
	        DummyDatabaseHandler dummyDatabaseHandler = new DummyDatabaseHandler();
	        Client client = new Client(dummyDatabaseHandler);

	        // Act
	        boolean loginResult = client.login(validUsername, validPassword);
	        client.viewMenu();  // Simulate viewing the menu after login

	        // Assert
	        assertTrue(loginResult, "Login should succeed before viewing the menu");
	        // Here we can check if the menu is displayed properly, depending on your implementation
	    }

	    @Test
	    public void testAddItemToCart() {
	        // Arrange
	        String validUsername = "validUser";
	        String validPassword = "validPassword";
	        Plant mockPlant = new Plant("Rose", 10);  // Dummy plant object

	        // Create the client with a dummy DatabaseHandler (no server interaction)
	        DummyDatabaseHandler dummyDatabaseHandler = new DummyDatabaseHandler();
	        Client client = new Client(dummyDatabaseHandler);

	        // Act
	        boolean loginResult = client.login(validUsername, validPassword);
	        client.addItemToCart(mockPlant);  // Simulate adding item to the cart

	        // Assert
	        assertTrue(loginResult, "Login should succeed before adding to the cart");
	        assertTrue(client.getCart().contains(mockPlant), "The cart should contain the added item");
	    }

	    @Test
	    public void testClientConnectedToServer() {
	        // Arrange
	        DummyDatabaseHandler dummyDatabaseHandler = new DummyDatabaseHandler();
	        
	        // Create the client with dummy handlers (no server interaction)
	        Client client = new Client(dummyDatabaseHandler);

	        // Act
	        boolean isConnected = client.isConnected();  // Simulate the connection check

	        // Assert
	        assertTrue(isConnected, "Client should be 'connected' in this simulation");
	    }

	    @Test
	    public void testClientConnectionTermination() {
	        // Arrange
	        DummyDatabaseHandler dummyDatabaseHandler = new DummyDatabaseHandler();

	        // Create the client with dummy handlers (no server interaction)
	        Client client = new Client(dummyDatabaseHandler);

	        // Act
	        client.terminateConnection();  // Simulate connection termination

	        // Assert
	        assertFalse(client.isConnected(), "Client should not be 'connected' after termination");
	    }

	    
	}
	
	