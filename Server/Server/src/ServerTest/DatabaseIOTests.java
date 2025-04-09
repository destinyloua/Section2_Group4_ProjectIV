package ServerTest;

import objects.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import back_end.DatabaseHandler;
import back_end.SocketHandler;

public class DatabaseIOTests {
	@BeforeEach
    public void setUp() {
		String url = "jdbc:mysql://localhost:3306/project4";
		//Change these!!
        String user = "root";
        String password = "Lolecksdee2/";
		DatabaseHandler.SetUpConnection(url, user, password);
    }

	@Test
    public void TEST_SVR_002_Valid_Parameters_Passed() {
		assertTrue(DatabaseHandler.ConnectDatabase(), "The function shall return true indicating server is connected to database");
		Vector<Integer> pids = new Vector<Integer>();
		pids.add(3);
		Vector<Integer> quantity = new Vector<Integer>();
		quantity.add(2);
		
		Order testOrder = new Order(6,1,300,-1,pids,quantity);
		
		assertTrue(DatabaseHandler.InsertNewOrder(testOrder), "The function shall return true indicating that the order record has been successfully added to the database");
    }

	@Test
    public void TEST_SVR_002_Invalid_AccountID() {
		assertTrue(DatabaseHandler.ConnectDatabase(), "The function shall return true indicating server is connected to database");
		Vector<Integer> pids = new Vector<Integer>();
		pids.add(3);
		Vector<Integer> quantity = new Vector<Integer>();
		quantity.add(2);
		
		Order testOrder = new Order(10,9,12,2,pids,quantity);
		
		assertFalse(DatabaseHandler.InsertNewOrder(testOrder), "The function shall return false indicating that no order has been placed under a non-existent account ID");
    }
	
	@Test
    public void TEST_SVR_002_Invalid_PlantID() {
		assertTrue(DatabaseHandler.ConnectDatabase(), "The function shall return true indicating server is connected to database");
		Vector<Integer> pids = new Vector<Integer>();
		pids.add(20);
		Vector<Integer> quantity = new Vector<Integer>();
		quantity.add(2);
		
		Order testOrder = new Order(20,3,2,0,pids,quantity);
		
		assertFalse(DatabaseHandler.InsertNewOrder(testOrder), "The function shall return false indicating that no order has been placed under a non-existent plant ID");
    }
	
	@Test
    public void TEST_SVR_003() {
		assertTrue(DatabaseHandler.ConnectDatabase(), "The function shall return true indicating server is connected to database");

		Plant testPlant = new Plant(6, "Test plant", (float)50.3, 200, "fakeImagepath.jpg");
		
		assertTrue(DatabaseHandler.InsertNewPlant(testPlant), "The function shall return true indicating that a new plant has successfully been added to the database");
	}
	
	//This test tries to add a plant to the database with an already existing plant ID, expected outcome is InsertNewPlant will return true indicating insertion has occured
	//however, not with the intended plant ID
	@Test
    public void TEST_SVR_003_Existing_PlantID() {
		assertTrue(DatabaseHandler.ConnectDatabase(), "The function shall return true indicating server is connected to database");

		Plant testPlant = new Plant(1, "Failing Test Plant", (float)3.22, 10, "failingFakeImagePath.jpg");
		
		assertTrue(DatabaseHandler.InsertNewPlant(testPlant), "The function shall return true indicating that a new plant has not been added to the database");
	}	
	
	@Test
    public void TEST_SVR_005_ValidID_1() {
		assertTrue(DatabaseHandler.ConnectDatabase(), "The function shall return true indicating server is connected to database");

		Order testOrder = DatabaseHandler.FetchOrderById(1);
		Vector<Integer> pids = new Vector<Integer>();
		pids.add(1);
		pids.add(3);
		Vector<Integer> quantity = new Vector<Integer>();
		quantity.add(2);
		quantity.add(1);
		assertNotNull(testOrder);
		Vector<Integer> actualquantity = testOrder.GetQuantity();
		Vector<Integer> actualpids = testOrder.GetPId();

		assertEquals(1,testOrder.GetId());
		assertEquals(1,testOrder.GetAId());
		assertEquals((float)99.99,testOrder.GetTotalPrice());
		assertEquals(0,testOrder.GetStatus());
		assertEquals(pids, testOrder.GetPId());
		assertEquals(quantity, testOrder.GetQuantity());
		}	
	
	@Test
    public void TEST_SVR_005_ValidID_2() {
		assertTrue(DatabaseHandler.ConnectDatabase(), "The function shall return true indicating server is connected to database");

		Order testOrder = DatabaseHandler.FetchOrderById(2);
		Vector<Integer> pids = new Vector<Integer>();
		pids.add(2);
		pids.add(4);
		Vector<Integer> quantity = new Vector<Integer>();
		quantity.add(1);
		quantity.add(2);
		assertNotNull(testOrder);
		Vector<Integer> actualquantity = testOrder.GetQuantity();
		Vector<Integer> actualpids = testOrder.GetPId();

		assertEquals(2,testOrder.GetId());
		assertEquals(2,testOrder.GetAId());
		assertEquals((float)99.99,testOrder.GetTotalPrice());
		assertEquals(1,testOrder.GetStatus());
		assertEquals(pids, testOrder.GetPId());
		assertEquals(quantity, testOrder.GetQuantity());
		}	
	
	@Test
    public void TEST_SVR_005_Invalid_OrderID() {
		assertTrue(DatabaseHandler.ConnectDatabase(), "The function shall return true indicating server is connected to database");

		Order testOrder = DatabaseHandler.FetchOrderById(130);

		assertNull(testOrder);
		}	

	@Test
    public void TEST_SVR_006_Valid_PlantID_1() {
		assertTrue(DatabaseHandler.ConnectDatabase(), "The function shall return true indicating server is connected to database");

		Plant testPlant = DatabaseHandler.FetchPlant(1);

		assertNotNull(testPlant);
		assertEquals(1,testPlant.GetId());
		assertEquals("Catus",testPlant.GetName());
		assertEquals((float)20.99,testPlant.GetPrice());
		assertEquals(20,testPlant.GetQuantity());
		assertEquals("resources/images/plants/cactus.jpg",testPlant.GetImagePath());
		}	
	
	@Test
    public void TEST_SVR_006_Valid_PlantID_2() {
		assertTrue(DatabaseHandler.ConnectDatabase(), "The function shall return true indicating server is connected to database");

		Plant testPlant = DatabaseHandler.FetchPlant(2);

		assertNotNull(testPlant);
		assertEquals(2,testPlant.GetId());
		assertEquals("Cylinder Snake Plant",testPlant.GetName());
		assertEquals((float)14.99,testPlant.GetPrice());
		assertEquals(15,testPlant.GetQuantity());
		assertEquals("resources/images/plants/cylinder_snake_plant.jpg",testPlant.GetImagePath());
		}	
	
	@Test
    public void TEST_SVR_006_Invalid_PlantID() {
		assertTrue(DatabaseHandler.ConnectDatabase(), "The function shall return true indicating server is connected to database");

		Plant testPlant = DatabaseHandler.FetchPlant(52);

		assertNull(testPlant);		
		}	
}
