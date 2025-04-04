package back_end;

import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

import leaf_laugh_love.Help_window;
import objects.*;

public class ResponseHandler {	
	private static Packet packet = new Packet();
	private static ByteBuffer read;
	private static byte[] data;
	
	public static void ChatTerminate() {
		Message m = new Message("");
		SocketHandler.SendMessage(m);
		SocketHandler.CloseChatConnection();
	}
	
	public static Boolean UpdateAccount(Account a) {
		packet.SetHeader(1, 4);
		packet.SetContent(a);
		SocketHandler.SendData(packet);
		System.out.println("Sent request");
		
		read = ByteBuffer.wrap(SocketHandler.ReceiveData());
		System.out.println("Received resposne");
		if(read.getInt() == 0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public static Boolean StartChat() {
		//TODO send request to chat
		//TODO connect to chat
		packet.SetHeader(4, 1);
		packet.SetContent(0);
		SocketHandler.SendData(packet);
		
		read = ByteBuffer.wrap(SocketHandler.ReceiveData());
		System.out.println("Waiting for connecting from server");
		if(read.getInt() == 1) {
			try {
				Thread.sleep(500);
			}
			catch (Exception e){
				
			}
//			Message m = SocketHandler.ReceiveMessage();
//			System.out.println(m.GetMessage());
			
			return true;
		}
		else {
			return false;
		}
	}
	
	public static Boolean SendMessage(String message) {
		Message m = new Message (message);
		SocketHandler.SendMessage(m);
		return true;
	}
	
	public static Account GetAccount(Account a) {
		packet.SetHeader(1, 3);
		packet.SetContent(a);
		SocketHandler.SendData(packet);
		read = ByteBuffer.wrap(SocketHandler.ReceiveData());
		if(read.getInt()==0) {
			return null;
		}
		else {
			byte[] accountData = new byte[read.remaining()];
			read.get(accountData);
			a = new Account(accountData);
			System.out.println(a.GetEmail());
			return a;
		}
	}
	
	public static Vector<Order> GetOrdersList(Account a){
		packet.SetHeader(2, 2);
		packet.SetContent(a);
		SocketHandler.SendData(packet);
		read = ByteBuffer.wrap(SocketHandler.ReceiveData());
		Vector<Order> ordersList = new Vector<>();
		if(read.getInt() == 0) {
			return ordersList;
		}
		else {
			int numberOfOrders = read.getInt();
			System.out.println("Receiving " + numberOfOrders + " orders");
			for(int i=0; i<numberOfOrders;i++) {
				Order o = new Order(SocketHandler.ReceiveData());
				ordersList.add(o);
			}
			return ordersList;
		}
	}
	
	public static Vector<Plant> GetPlantsList(){
		packet.SetHeader(3, 2);
		packet.SetContent(0);
		SocketHandler.SendData(packet);
		Vector<Plant> plantsList = new Vector<>();
		read = ByteBuffer.wrap(SocketHandler.ReceiveData());
		if(read.getInt() == 0) {
			return plantsList;
		}
		else {
			int numberOfPlants = read.getInt();
			System.out.println("Receiving " + numberOfPlants + " plants");
			for(int i=0;i<numberOfPlants;i++) {
				Plant p = new Plant(SocketHandler.ReceiveData());
				//Set ImageName
				p.SetImage(new String(SocketHandler.ReceiveData()));
				ReceiveImage(p.GetImagePath());
				System.out.println(p.GetName() + " received with image");
				plantsList.add(p);
				System.out.println();
			}
			return plantsList;
		}
		
		//TODO receive plant into / make a class to process image
	}
	
	public static Plant GetPlant(int id) {
		packet.SetHeader(3, 1);
		packet.SetContent(id);
		SocketHandler.SendData(packet);
		read = ByteBuffer.wrap(SocketHandler.ReceiveData());
		if(read.getInt() == 0) {
			return null;
		}
		else {
			byte[] plantData = new byte[read.remaining()];
			read.get(plantData);
			Plant p = new Plant(plantData);
			//Set ImageName
			p.SetImage(new String(SocketHandler.ReceiveData()));
			ReceiveImage(p.GetImagePath());
			System.out.println(p.GetName() + " received with image");
			return p;	
		}
	}
	
	public static void ReceiveImage(String savedFile) {
	    try {
	        // Get image size
	        byte[] receivedImgSize = SocketHandler.ReceiveData();
	        ByteBuffer read = ByteBuffer.wrap(receivedImgSize);
	        int imageSize = read.getInt();
	        System.out.println("Image will be sent: " + imageSize + " bytes");

	        int chunkSize = 32768;
	        int dataSize = chunkSize - 4;
	        int totalChunk = (imageSize + dataSize - 1) / dataSize; // Correct chunk calculation

	        byte[] imgData = new byte[imageSize];

	        for (int i = 0; i < totalChunk; i++) {
	            byte[] receivedChunk = SocketHandler.ReceiveData();
	            read = ByteBuffer.wrap(receivedChunk);
	            int chunkNumber = read.getInt(); // Read the chunk number

	            // Corrected index calculation
	            int start = chunkNumber * dataSize;
	            int length = Math.min(receivedChunk.length - 4, imageSize - start); // Prevent overflow

	            System.arraycopy(receivedChunk, 4, imgData, start, length);
	        }

	        // Save received image
	        Path imagePath = Paths.get(savedFile);
	        Files.write(imagePath, imgData);
	        System.out.println("Image received: " + imageSize + " bytes");
	        System.out.println("Image is saved at " + savedFile);
	    } catch (Exception e) {
	        e.printStackTrace(); // Print error properly
	    }
	}
	public static Boolean CreateAccount(Account a){
		packet.SetHeader(1, 1);
		packet.SetContent(a);
		SocketHandler.SendData(packet);
		
		data = SocketHandler.ReceiveData();
		ByteBuffer read = ByteBuffer.wrap(data);
		int response;
		response = read.getInt();
		if(response == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static Boolean PlaceOrder(Order o) {
		float totalPrice =0;
		for(int i=0;i<o.GetPId().size();i++) {
			Plant p = GetPlant(o.GetPId().get(i));
			totalPrice += p.GetPrice() * o.GetQuantity().get(i);
		}
		o.SetTotalPrice(totalPrice);
		System.out.println(o.GetTotalPrice());
		packet.SetHeader(2, 1);
		packet.SetContent(o);
		SocketHandler.SendData(packet);
		
		read = ByteBuffer.wrap(SocketHandler.ReceiveData());
		if(read.getInt()==1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static Boolean AuthenticateLogin(Account a) {
		packet.SetHeader(1, 2);
		packet.SetContent(a);
		SocketHandler.SendData(packet);

		data = SocketHandler.ReceiveData();
		ByteBuffer read = ByteBuffer.wrap(data);
		int header;
		header = read.getInt();
		if(header == 1) {
			int content = read.getInt();
			if(content == 1) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	public static Boolean TerminateConnection() {
		packet.SetHeader(-1, -1);
		packet.SetContent(0);
		SocketHandler.SendData(packet);
		data = SocketHandler.ReceiveData();
		return true;
	}
	
}
