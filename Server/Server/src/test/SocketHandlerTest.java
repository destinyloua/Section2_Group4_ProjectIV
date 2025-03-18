package test;

import back_end.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SocketHandlerTest {

    @BeforeEach
    public void setUp() {
        SocketHandler.MakeConnection(28000);
    }

    @Test
    public void testCheckConnection() {
        assertTrue(SocketHandler.CheckConnection(), "Connection should be established.");
    }

    @AfterEach
    public void tearDown() {
        SocketHandler.Disconnect();
    }
}
