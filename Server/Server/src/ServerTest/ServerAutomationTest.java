package ServerTest;

import objects.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import back_end.DatabaseHandler;
import back_end.SocketHandler;

public class ServerAutomationTest {
	@BeforeEach
    public void setUp() {
		String url = "jdbc:mysql://localhost:3306/project4";
		//Change these!!
        String user = "root";
        String password = "Tyler123";
		DatabaseHandler.SetUpConnection(url, user, password);
    }

	@Test
    public void TEST_SVR_001() {
		assertTrue(DatabaseHandler.ConnectDatabase(), "The function shall return true indicating server is connected to database");
		Account a = new Account("Tyler", "Dao", "tyler@gmail.com", "Tyler");

		assertTrue(DatabaseHandler.InsertNewAccount(a), "The function shall return true indicating account is inserted");
    }
	
	@Test
    public void TEST_SVR_014() {

		assertTrue(DatabaseHandler.ConnectDatabase(), "The function shall return true indicating server is connected to database");
		Account a = new Account("Tyler", "Dao", "svr004@gmail.com", "Tyler");
		assertTrue(DatabaseHandler.InsertNewAccount(a), "The function shall return true indicating account is inserted");
		Account result = DatabaseHandler.FetchAccountByEmail("svr004@gmail.com");
		assertEquals(a.GetEmail(), result.GetEmail());
		
    }
	
	@Test
    public void SVR_007() {
    	SocketHandler.MakeConnection(27000);
        assertTrue(SocketHandler.CheckConnection(), "Connection should be established.");
        SocketHandler.Disconnect();
    }
	
	@Test
    public void SVR_011() {
		//Serialize
    }
	
	@Test
    public void SVR_012() {
		//De-serialize
    }
    
}
