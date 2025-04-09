package Test;

import objects.Account;
import objects.Packet;
import back_end.SocketHandler;
import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testacc {

	@Test
	public void testDuplicateAccountCreation() {
	    // Simulate an account that already exists
	    Account duplicateAccount = new Account("Jane", "Doe", "jane@example.com", "password123");

	    // Serialize the account data
	    byte[] accountData = duplicateAccount.Serialize();

	    // Create the packet to simulate the account creation request
	    Packet packet = new Packet();
	    packet.SetHeader(1, 1); // object = 1 for Account, action = 1 for Create
	    packet.SetContent(duplicateAccount);

	    // Send the packet
	    SocketHandler.SendData(packet.GetPacket());

	    // Simulate backend response (0 = failure to create due to duplication)
	    byte[] response = SocketHandler.ReceiveData();
	    ByteBuffer buffer = ByteBuffer.wrap(response);
	    int result = buffer.getInt();

	    // Assert that the account was not created
	    assertEquals(0, result);
	}

	@Test
	public void testSuccessfulLogin() {
	    // Simulate login with correct credentials
	    Account loginAccount = new Account("jane@example.com", "password123");

	    Packet packet = new Packet();
	    packet.SetHeader(1, 2); // object = 1 for Account, action = 2 for Login
	    packet.SetContent(loginAccount);

	    SocketHandler.SendData(packet.GetPacket());

	    byte[] response = SocketHandler.ReceiveData();
	    ByteBuffer buffer = ByteBuffer.wrap(response);
	    int result = buffer.getInt();

	    // Assert successful login
	    assertEquals(1, result);
	}

	@Test
	public void testFailedLoginWithInvalidCredentials() {
	    // Simulate login attempt with invalid credentials
	    Account loginAccount = new Account("jane@example.com", "wrongpassword");

	    Packet packet = new Packet();
	    packet.SetHeader(1, 2); // object = 1 for Account, action = 2 for Login
	    packet.SetContent(loginAccount);

	    SocketHandler.SendData(packet.GetPacket());

	    byte[] response = SocketHandler.ReceiveData();
	    ByteBuffer buffer = ByteBuffer.wrap(response);
	    int result = buffer.getInt();

	    // Assert login failure
	    assertEquals(0, result);
	}


}
