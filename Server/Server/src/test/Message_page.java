package test;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class Message_page implements Runnable {
    private static ServerSocket serverSocket;

    public static void StartServer() {
        try {
            serverSocket = new ServerSocket(27000);
            System.out.println("Server started on port 27000...");

            while (true) {
                // Wait for client connections
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Handle client communication in a new thread
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String message;
            while ((message = in.readLine()) != null) { // Keep reading messages
                System.out.println("Client: " + message);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected.");
        }
    }
    
    @Override
    public void run() {
    	StartServer();
    }
}