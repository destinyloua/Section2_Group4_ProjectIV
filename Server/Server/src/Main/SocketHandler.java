package Main;

import java.net.*;
import java.io.*;

public class SocketHandler {
	protected String buffer;
	protected Socket clientSocket;
	protected ServerSocket serverSocket;
	
	public Boolean MakeConnection(int port) {
		try {
			serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);

            // Accept client connection
            clientSocket = serverSocket.accept();
            System.out.println("Client connected!");

            // Create input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Receive message
            String message = in.readLine();
            System.out.println("Received: " + message);

            // Send response
            out.println("Message received: " + message);
            
            // Close connections
            clientSocket.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public Boolean SendData(byte[] data) {
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
	
	public byte[] ReceiveData() {
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
	
	public Boolean CloseServer() {
		try {
			serverSocket.close();
			return true;
		}
		catch (Exception e){
			System.out.println("Error: "+e.getMessage());
			return false;
		}
	}
}
