package back_end;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.text.*;
import java.util.*;

import leaf_laugh_love.Message_window;
import objects.*;

public class RequestHandler implements Runnable {
	private static int object;
	private static int action;
	private static ByteBuffer read;
	private static byte[] data;
	
	private static Packet packet = new Packet();;
	
	public static Boolean Processing() {
		while(SocketHandler.CheckConnection()) {
			data = SocketHandler.ReceiveData();
			System.out.println(data.length);
			System.out.println("Request received");
			read = ByteBuffer.wrap(data);
			object = read.getInt();
			if(object == -1) {
				System.out.println("Client disconnect");
				TerminateConnection();
				break;
			}
			else {
				action = read.getInt();
				if(read.remaining() > 0) {
					data = new byte[read.remaining()];
					read.get(data);
				}
				//Account
				if(object == 1) {
					if(action == 1) {
						CreateAccount();
					}
					else if(action == 2) {
						Authenticate();
					}
					else if(action == 3) {
						GetAccountByEmail();
					}
				}
				
				//Order
				else if (object == 2) {
					if(action == 1) {
						PlaceOrder();
					}
					else if(action ==2) {
						GetOrdersListByAId();
					}
				}
				
				//Plant
				else if(object == 3) {
					if(action == 1) {
						//Haven done NEED IMAGE
						GetPlantById();
					}
						//Haven done NEED IMAGE
					else if(action == 2) {
						GetPlantsList();
					}
				}
				
				//Message
				else if (object == 4) {
					if(action == 1) {      
			    		//TODO Thread make connection ok
						//TODO message window ok
						//TODO response back to client to connect ok
						
						//Open message window
			            packet.SetHeader(true);
			            packet.SetContent(0);
			            SocketHandler.SendData(packet);
			            System.out.println("Sent");
			            SocketHandler.MakeChatConnection(27001);
		            	System.out.println("Client chat connected");
			 
			            new Message_window();
					}
				}
			}
		}
		return true;
	}
	
	public static Boolean GetAccountByEmail() {
		Account a = DatabaseHandler.FetchAccountByEmail(new Account(data).GetEmail());
		if(a == null) {
			packet.SetHeader(false);
			packet.SetContent(false);
			SocketHandler.SendData(packet);
			
			FileHandler.SaveLog("Failed to send account data to client");
			return false;
		}
		else {
			packet.SetHeader(true);
			packet.SetContent(a);
			SocketHandler.SendData(packet);
			
			FileHandler.SaveLog("Account " + a.GetId() + " data sent to client");
			return true;
		}
	}
	
	public static Boolean GetOrdersListByAId() {
		read = ByteBuffer.wrap(data);
		System.out.println("Coppied: " + data.length);
		int aId = read.getInt();
		Vector<Order> orders = DatabaseHandler.FetchOrdersListByAId(aId);
		if(orders.isEmpty()) {
			packet.SetHeader(true);
			packet.SetContent(false);
			SocketHandler.SendData(packet);
			FileHandler.SaveLog("Orders list of account #"+ aId +" sent to client");
	        return false;
		}
		else {
			packet.SetHeader(true);
			packet.SetContent(orders.size());
			SocketHandler.SendData(packet);
			for(int i=0;i<orders.size();i++) {
				packet.SetHeader(true);
				packet.SetContent(orders.get(i));
				SocketHandler.SendData(packet);
			}
			FileHandler.SaveLog("Orders list of account #"+ aId +" sent to client");
	        return true;
		}
	}
	
	public static Boolean PlaceOrder() {
		Order o = new Order(data);
		if(DatabaseHandler.InsertNewOrder(o)) {
			packet.SetHeader(true);
			packet.SetContent(true);
			SocketHandler.SendData(packet);
			
	        FileHandler.SaveLog("New order placed");
	        return true;
		}
		else {
			packet.SetHeader(false);
			packet.SetContent(false);
			SocketHandler.SendData(packet);
			
	        FileHandler.SaveLog("Failed to place new order");
	        return false;
		}
	}
	
	public static Boolean GetPlantsList() {
		Vector<Plant> plants;
		plants = DatabaseHandler.FetchPlantsList();
		if(plants.isEmpty()) {
			packet.SetHeader(false);
			packet.SetContent(false);
			SocketHandler.SendData(packet);
			
			FileHandler.SaveLog("Failed to send plants list data to client");
			return false;
		}
		else {
			packet.SetHeader(true);
			packet.SetContent(plants.size());
			SocketHandler.SendData(packet);
			//TODO SEND PLANTS
			for(int i=0;i<plants.size();i++) {
				SocketHandler.SendData(plants.get(i));
				System.out.println("Sent " + plants.get(i).GetName());
				System.out.println("Sending image...");
				
				//Send filePath
				SocketHandler.SendData(plants.get(i).GetImagePath().getBytes());
				//TODO SEND IMAGE
				SendImage(plants.get(i).GetImagePath());
				System.out.println();
			}
			return true;
		}
			
	}
	
	public static void SendImage(String filePath) {
		try {			
			byte[] imgData = Files.readAllBytes(new File(filePath).toPath());
			int totalSize = imgData.length;
			int chunkSize = 32768;
			int dataSize = chunkSize -4;
			int totalChunk = (int) Math.ceil((double) totalSize/dataSize);
			
			//Send size
			SocketHandler.SendData(ByteBuffer.allocate(4).putInt(totalSize).array());
			System.out.println("Size sent: "+totalSize +" bytes");
			
			for(int i=0; i<totalChunk;i++) {
				int start = i*dataSize;
				int end = Math.min((start+dataSize), totalSize);
				int sentChunkSize = end - start; //For last chunk (data less than chunk size case)
				
				byte[] chunk = new byte[chunkSize];
				
				//Copy chunk number
				System.arraycopy(ByteBuffer.allocate(4).putInt(i).array(),0, chunk, 0, 4);
				
				//Copy img data to chunk;
				System.arraycopy(imgData, start, chunk, 4, sentChunkSize);
				
				//Send chunk
				SocketHandler.SendData(chunk);
				System.out.println("Chunk " + i+": " +sentChunkSize+" bytes sent");
			}
			System.out.println("Image " + filePath + "is sent: " + totalSize + " bytes sent");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Haven done NEED IMAGE
	public static Boolean GetPlantById() {
		int id = read.getInt();
		Plant p = DatabaseHandler.FetchPlant(id);
		if(p == null) {
			packet.SetHeader(false);
			packet.SetContent(false);
			SocketHandler.SendData(packet);
			
	        FileHandler.SaveLog("Failed to send plant data to client");
	        return false;
		}
		else {
			packet.SetHeader(true);
			packet.SetContent(p);
			SocketHandler.SendData(packet);
			
	        FileHandler.SaveLog("Plant " + p.GetId() + "data sent to client");
	        return true;
		}
	}
	
	public static Boolean Authenticate() {
		Account a = new Account(data);
		object =0;
		action =0;
		if(DatabaseHandler.AuthenticateLogin(a)) {
			packet.SetHeader(true);
			packet.SetContent(true);
			SocketHandler.SendData(packet);
			
	        FileHandler.SaveLog("Authentication success");
	        return true;
		}
		else {
			packet.SetHeader(false);
			packet.SetContent(false);
			SocketHandler.SendData(packet);
			
	        FileHandler.SaveLog("Failed to authenticate");
	        return false;
		}
	}
	
	public static Boolean CreateAccount() {
		Account a = new Account(data);
		object =0;
		action = 0;
		if(DatabaseHandler.InsertNewAccount(a)) {
			packet.SetHeader(true);
			packet.SetContent(true);
			SocketHandler.SendData(packet);
			
	        FileHandler.SaveLog("New account created");
	        return true;
		}
		else {
			packet.SetHeader(false);
			packet.SetContent(false);
			SocketHandler.SendData(packet);
			
	        FileHandler.SaveLog("Failed to create new account");
	        return false;
		}
	}
	
	public static Boolean TerminateConnection() {
		packet.SetHeader(true);
		packet.SetContent(0);
		SocketHandler.SendData(packet);
		SocketHandler.Disconnect();
		SocketHandler.CloseServer();
		return true;
	}
	
	@Override
    public void run() {
		Processing();
    }
}
