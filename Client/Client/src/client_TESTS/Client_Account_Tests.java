package client_TESTS;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import back_end.*; 
import objects.Account; 
import leaf_laugh_love.*; 




class Client_Account_Tests {

	private Leafs_Laugh_Love app; 
	private Account testAccount; 
	
	
	@BeforeEach
	void setUp(){
		app = new Leafs_Laugh_Love(); 
		testAccount = new Account("test", "user", "test@user.com", "password");
		SocketHandler.MakeConnection(27000); 
		boolean connected = SocketHandler.CheckConnection();
		assertTrue(connected); 
		
	}

	@Test
	void Account_Creation_Success() {
		boolean result = ResponseHandler.CreateAccount(testAccount); 
		assertTrue(result, "Account should be created successfully"); 
	}
	
	@Test
	void Account_Login_Success() {
		
	}
	
	@AfterEach
	void tearDown() {
		SocketHandler.CloseChatConnection();
		
	}
	
	

}
