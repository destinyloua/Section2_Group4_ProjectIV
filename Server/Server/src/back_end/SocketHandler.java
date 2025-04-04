package back_end;

import java.net.*;
import objects.Packet;

import java.io.*;

public class SocketHandler {
	protected static String buffer;
	protected static Socket clientSocket;
	protected static Socket chatSocket;
	protected static ServerSocket serverSocket;
	protected static ServerSocket serverChatSocket;
	
	public static Boolean MakeChatConnection(int port) {
		try {
			serverChatSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);

            // Accept client connection
            chatSocket = serverChatSocket.accept();
            System.out.println("Client chat connected!");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public static Boolean SendMessage(Packet p) {
        // Create DataOutputStream to send raw data (binary data)
		try {
	        DataOutputStream out = new DataOutputStream(chatSocket.getOutputStream());
	        byte[] data = p.GetPacket();
	        // Send the length of the data first
	        out.writeInt(data.length);  // Write the length of the byte array

	        // Send the actual raw byte data
	        out.write(data);  // Write the byte array data	
	        return true;
		}
		catch(Exception e) {
			e.getStackTrace();
			return false;
		}
	}
	
	public static byte[] ReceiveMessage() {
		try {
            // Create DataInputStream to read raw data (binary data)
            DataInputStream inputStream = new DataInputStream(chatSocket.getInputStream());

            // Read the length of the data
            int dataLength = inputStream.readInt();  // Read the length of the incoming data (e.g., 1024 bytes)
            byte[] data = new byte[dataLength];

            // Read the raw byte data from the stream
            inputStream.readFully(data);  // Reads the specified number of bytes into the array
            return data;
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
			return null;
		}
	}
	
	public static Boolean MakeConnection(int port) {
		try {
			serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);

            // Accept client connection
            clientSocket = serverSocket.accept();
            System.out.println("Client connected!");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public static Boolean SendData(byte[] data) {
        // Create DataOutputStream to send raw data (binary data)
		try {
	        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

	        // Send the length of the data first
	        out.writeInt(data.length);  // Write the length of the byte array

	        // Send the actual raw byte data
	        out.write(data);  // Write the byte array data	
	        return true;
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		}
	}
	
	public static Boolean SendData(Objects object) {
        // Create DataOutputStream to send raw data (binary data)
		try {
	        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
	        byte[] data = object.Serialize();
	        // Send the length of the data first
	        out.writeInt(data.length);  // Write the length of the byte array

	        // Send the actual raw byte data
	        out.write(data);  // Write the byte array data	
	        return true;
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		}
	}
	
	public static Boolean SendData(Packet packet) {
        // Create DataOutputStream to send raw data (binary data)
		try {
	        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

	        // Send the length of the data first
	        out.writeInt(packet.GetPacket().length);  // Write the length of the byte array

	        // Send the actual raw byte data
	        out.write(packet.GetPacket());  // Write the byte array data	
	        return true;
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		}
	}
	

	
	public static byte[] ReceiveData() {
		try {
            // Create DataInputStream to read raw data (binary data)
            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());

            // Read the length of the data
            int dataLength = inputStream.readInt();  // Read the length of the incoming data (e.g., 1024 bytes)
            byte[] data = new byte[dataLength];

            // Read the raw byte data from the stream
            inputStream.readFully(data);  // Reads the specified number of bytes into the array
            return data;
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
			return null;
		}
	}
	
	public static Boolean CheckConnection() {
		if(clientSocket != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static Boolean CheckChatConnection() {
		if(chatSocket != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static Boolean Disconnect() {
		try {
	        if (clientSocket != null) {
//	            // Close the input and output streams
//	            clientSocket.getInputStream().close();  // Close the input stream
//	            clientSocket.getOutputStream().close(); // Close the output stream

	            // Finally, close the client socket itself
	            clientSocket.close();
	            System.out.println("Client connection closed.");
	            return true;
	        } else {
	            System.out.println("No client connection to close.");
	            return false;
	        }
	    } catch (IOException e) {
	        System.out.println("Error while disconnecting: " + e.getMessage());
	        return false;
	    }
	}
	
	public static Boolean EndChat() {
	    try {
	        if (chatSocket != null && !chatSocket.isClosed()) {
	        	serverChatSocket.close();
	            chatSocket.close();
	            System.out.println("Chat socket on port 27001 closed.");
	        }
	        chatSocket = null; // Ensure it is completely released
	        return true;
	    } catch (IOException e) {
	        System.out.println("Error while closing chat connection: " + e.getMessage());
	        return false;
	    }
	}
	
	public static Boolean CloseServer() {
		try {
			serverSocket.close();
			serverChatSocket.close();
			return true;
		}
		catch (Exception e){
			System.out.println("Error: "+e.getMessage());
			return false;
		}
	}
}
