package leaf_laugh_love;

import javax.swing.*;

import back_end.SocketHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class Message_window {
	 private JFrame frame;
	    private JTextArea chatArea;
	    private JTextField messageField;
	    private JButton sendButton;
	  
	    public Message_window() {
	        frame = new JFrame("Chat Client");
	        //frame.setSize(400, 400);
	        frame.setBounds(400, 0, 400, 400);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setLayout(new BorderLayout());
	        
	        chatArea = new JTextArea();
	        chatArea.setEditable(false);
	        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);
	        
	        JPanel inputPanel = new JPanel(new BorderLayout());
	        messageField = new JTextField();
	        sendButton = new JButton("Send");
	        inputPanel.add(messageField, BorderLayout.CENTER);
	        inputPanel.add(sendButton, BorderLayout.EAST);
	        frame.add(inputPanel, BorderLayout.SOUTH);
	        
	        sendButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                sendMessage();
	            }
	        });
	        
	        frame.setVisible(true);
	        
	        connectToServer(); // Automatically connect on startup
	    }
	    
	    private void connectToServer() {
	        if (SocketHandler.MakeConnection(27000, "localhost")) {
	            chatArea.append("Connected to server\n");
	            new Thread(this::receiveMessages).start();
	        } else {
	            chatArea.append("Failed to connect\n");
	        }
	    }
	    
	    private void sendMessage() {
	        String message = messageField.getText();
	        if (!message.isEmpty()) {
	            if (SocketHandler.SendData(message.getBytes())) {
	                chatArea.append("Me: " + message + "\n");
	                messageField.setText("");
	            } else {
	                chatArea.append("Failed to send message\n");
	            }
	        }
	    }
	    
	    private void receiveMessages() {
	        while (SocketHandler.CheckConnection()) {
	            byte[] data = SocketHandler.ReceiveData();
	            if (data != null) {
	                String receivedMessage = new String(data);
	                chatArea.append("Server: " + receivedMessage + "\n");
	            }
	        }
	    }
	    
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(Message_window::new);
	    }
	    
	    
}


