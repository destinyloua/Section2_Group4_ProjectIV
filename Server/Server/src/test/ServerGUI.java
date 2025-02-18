package test;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class ServerGUI {
    private JFrame frame;
    private JTextArea textArea;
    private ServerSocket serverSocket;

    public ServerGUI() {
        // Create GUI window
        frame = new JFrame("Server");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create a text area to show messages
        textArea = new JTextArea();
        textArea.setEditable(false);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        frame.setVisible(true);

        // Start the server on a separate thread
        new Thread(this::startServer).start();
    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(27000);
            appendText("Server started on port 27000...");

            while (true) {
                // Wait for client connections
                Socket clientSocket = serverSocket.accept();
                appendText("Client connected: " + clientSocket.getInetAddress());

                // Handle client communication in a new thread
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            appendText("Server error: " + e.getMessage());
        }
    }

    private void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String message;
            while ((message = in.readLine()) != null) { // Keep reading messages
                appendText("Client: " + message);
            }
        } catch (IOException e) {
            appendText("Client disconnected.");
        }
    }

    private void appendText(String text) {
        SwingUtilities.invokeLater(() -> textArea.append(text + "\n"));
    }

    public static void main(String[] args) {
        new ServerGUI();
    }
}
