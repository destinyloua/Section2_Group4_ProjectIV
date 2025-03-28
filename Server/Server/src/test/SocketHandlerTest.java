package test;

import back_end.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SocketHandlerTest {

    @Test
    public void testCheckConnection() {
    	SocketHandler.MakeConnection(27000);
        assertTrue(SocketHandler.CheckConnection(), "Connection should be established.");
        SocketHandler.Disconnect();
    }

}
