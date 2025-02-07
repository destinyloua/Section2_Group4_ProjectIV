package Main;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class ChatServer {
    private JFrame frame;
    private JTextArea textArea;
    private JTextField textField;
    private JButton sendButton;
    private JButton endChatButton;
    
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ChatServer server = new ChatServer();
                server.frame.setVisible(true);
                server.startServer(12345);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ChatServer() {
        frame = new JFrame("Chat Server");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel panel = new JPanel(new BorderLayout());
        textField = new JTextField();
        sendButton = new JButton("Send");
        endChatButton = new JButton("End Chat");
        
        panel.add(textField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);
        panel.add(endChatButton, BorderLayout.SOUTH);

        frame.add(panel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> sendMessage());
        endChatButton.addActionListener(e -> closeConnection());
    }

    public void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            textArea.append("Waiting for a client...\n");
            while (true) {
                clientSocket = serverSocket.accept();
                textArea.append("Client connected.\n");

                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                new Thread(() -> receiveMessages()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                textArea.append("Client: " + message + "\n");
            }
        } catch (IOException e) {
            textArea.append("Client disconnected.\n");
        } finally {
            closeConnection();
        }
    }

    private void sendMessage() {
        String message = textField.getText();
        if (!message.isEmpty() && out != null) {
            out.println(message);
            textArea.append("Server: " + message + "\n");
            textField.setText("");
        }
    }

    private void closeConnection() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (clientSocket != null) clientSocket.close();
            textArea.append("Client disconnected. Waiting for new connection...\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
