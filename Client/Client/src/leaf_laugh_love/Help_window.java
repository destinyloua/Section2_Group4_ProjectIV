package leaf_laugh_love;

import javax.swing.*;

import back_end.*;
import objects.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class Help_window {
	 private JFrame frame;
	    private JPanel chatArea;
	    private JTextField messageField, serverField, portField;
	    private JButton sendButton, connectButton;
	    private JButton endBttn;
	    private Account a;
	    
	    public Help_window(Account a) {
	    	this.a = a;
	        frame = new JFrame("Chat Client");
	        frame.setSize(400, 400);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.getContentPane().setLayout(new BorderLayout());
	        
	        chatArea = new JPanel();
	        chatArea.setLayout(new BoxLayout(chatArea, BoxLayout.Y_AXIS));
	        JScrollPane scrollPane = new JScrollPane(chatArea);
	        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
	        
	        JPanel inputPanel = new JPanel(new BorderLayout());
	        messageField = new JTextField();
	        sendButton = new JButton("Send");
	        inputPanel.add(messageField, BorderLayout.CENTER);
	        inputPanel.add(sendButton, BorderLayout.EAST);
	        frame.getContentPane().add(inputPanel, BorderLayout.SOUTH);
	        
	        endBttn = new JButton("End chat");
	        inputPanel.add(endBttn, BorderLayout.SOUTH);
	        endBttn.addActionListener(e->{
	        	//TODO send termination signal to server
	        	Terminate();
	        	FileHandler.SaveLog("Client ended chat");
	        	frame.setVisible(false);
	        });
	        
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
	    	FileHandler.SaveLog("Client is requesting help via chat");
	    	//new Thread(this::receiveMessages).start();
	        if (SocketHandler.MakeChatConnecion()) {
	            //chatArea.append("Connected to server\n");
	        	displaySystemMessage("Thanks for connecting with Customer Service. How can we help?");
	            new Thread(this::receiveMessages).start();
	        } else {
	        	displaySystemMessage("Failed to connect to Customer Service");
	        }
	    }
	    
	    private void sendMessage() {
	        String message = messageField.getText();
	        if (!message.isEmpty()) {
		        Packet p = new Packet();
		        p.SetHeader(message.length());
		        p.SetContent(message);
	            if (SocketHandler.SendMessage(p)) {
	            	displayClientMessage(a.GetFName()+": " + message);
	                messageField.setText("");
	                FileHandler.SaveLog("Message sent to server");
	            } else {
		        	displaySystemMessage("Failed to send message");
	            }
	        }
	    }
	    
	    private void receiveMessages() {
	        while (true) {
	            byte[] data = SocketHandler.ReceiveMessage();
	            System.out.println(data.length);
	            ByteBuffer read = ByteBuffer.wrap(data);
	            System.out.println("Data received");
	            if (read.getInt() == -1) {
	            	SocketHandler.CloseChatConnection();
                	frame.setVisible(false);
	            }
                else {
	            	byte[] messageData = new byte[read.remaining()];
	            	read.get(messageData);
	            	String m = new String(messageData);
                    SwingUtilities.invokeLater(() -> displaySystemMessage("Customer Service: " + m));
	            	FileHandler.SaveLog("Message received from server");
                }
	        }
	    }
	    
	    private void Terminate() {
	    	Packet p = new Packet();
	    	p.SetHeader(-1);
	    	p.SetContent(0);
	    	SocketHandler.SendMessage(p);
	    	messageField.setText("");
	    }
	    
	    // display the client message with the sent indicator together
	    private void displayClientMessage(String message) {
	    	JPanel msgPanel = new JPanel(); 
	    	msgPanel.setLayout(new BoxLayout(msgPanel, BoxLayout.Y_AXIS));
	    	
	    	JLabel msgLabel = new JLabel(message);
	    	JLabel sentLabel = new JLabel("Sent"); // REQ-LOG-050
	    	
	    	sentLabel.setFont(new Font("SansSerif", Font.ITALIC, 10));
	    	sentLabel.setForeground(Color.LIGHT_GRAY);
	    	
	    	msgPanel.add(msgLabel); 
	    	msgPanel.add(sentLabel); 
	    	
	    	chatArea.add(msgPanel); 
	    	chatArea.revalidate();
	    	chatArea.repaint(); 
	    	
	    }
	    
	    // for displaying additional messages to the client
	    private void displaySystemMessage(String message) {
	    	JLabel msgLabel = new JLabel(message); 
	    	chatArea.add(msgLabel);
	    	chatArea.revalidate();
	    	chatArea.repaint(); 
	    }
	    
}

