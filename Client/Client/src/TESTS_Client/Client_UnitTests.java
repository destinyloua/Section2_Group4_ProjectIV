package TESTS_Client;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Vector;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import objects.Account;
import objects.Order;

class Client_UnitTests {

	private Account a; 
	private Order o; 
	private static final Vector<Integer> pID = new Vector<>(Arrays.asList(1, 2, 3));
	private static final Vector<Integer> qty = new Vector<>(Arrays.asList(3, 2, 1));

	
	@BeforeEach
	void setUp() throws Exception {
		a = new Account ("Destiny", "Cactus", "plants@green.com", "123");
		o = new Order(1, 1, 50, 0, pID, qty); 
		
	}

	// TEST-CLT-001 (DL)
	// Verify client account is successfully created when providing a valid string username and password. 
	@Test
	void TEST001_Account_Creation() {
		assertEquals("Destiny", a.GetFName());
		assertEquals("Cactus", a.GetLName());
		assertEquals("plants@green.com", a.GetEmail());
		assertTrue(a.GetPassword() != 0);
	}
	
	// TEST-CLT-002 (DL)
	// Verify that an account is not created when receiving account data that already exists. 
	
	// TEST-CLT-003 (HH)
	// Verify client can log in successfully when providing an existing username and password. 
	
	// TEST-CLT-004 (HH)
	// Verify that login is not successful when invalid credentials are provided. 
	
	// TEST-CLT-005 (HH)
	// Verify plant menu is visible to the client after successful login. 
	
	// TEST-CLT-006 (HH)
	// Verify "add to cart" button successfully adds plant item to cart. 
	
	// TEST-CLT-007 (DL)
	// Verify "place order" button redirects client to "order confirmation" page. 
	
	// TEST-CLT-008 (DL)
	// Verify cart items are accurately displayed when selecting "view cart."
	
	// TEST-CLT-009 (DL)
	// Verify client message is displayed in the chat box to confirm delivery to server. 
	
	// TEST-CLT-010 (HH)
	// Verify client can connect to the server, indicated by "connected" indicator. 
	
	// TEST-CLT-011 (HH)
	// Verify client is able to terminate connection to server by logging out. 
	
	// TEST-CLT-012 (DL)
	// Verify order contents are accurately constructed into a packet to be transmitted to the server. 
	
	// TEST-CLT-013 (DL)
	// Verify that a visual indicator is present to notify client of message delivery to server. 

}
