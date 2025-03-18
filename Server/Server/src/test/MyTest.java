package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import back_end.SocketHandler;

class MyTest {
    @Test
    void testSocket() {
        boolean result = SocketHandler.CheckConnection();
        assertEquals(true, result);
    }
}