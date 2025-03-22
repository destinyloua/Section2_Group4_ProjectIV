package leaf_laugh_love;

import javax.swing.*;

import back_end.FileHandler;
import back_end.MessageHandler;
import back_end.SocketHandler;
import objects.Message;
import objects.Packet;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Message_window {
	private JFrame frame;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private JButton endBttn;
    
	private static String fileName = "ChatHistory.txt"; // REQ-LOG-040
	private static String msgContent;

    public Message_window() {
        frame = new JFrame("Server Chat");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        messageField = new JTextField();
        sendButton = new JButton("Send");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(messageField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        endBttn = new JButton("End chat");
        endBttn.addActionListener(e->{
        	Terminate();
        	frame.setVisible(false);
        	SocketHandler.EndChat();
        });
        scrollPane.setColumnHeaderView(endBttn);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        frame.setVisible(true);
        startServer();
    }

    private void startServer() {
    	chatArea.append("Client connected\n");
    	new Thread(this::ReceiveMessage).start();
    }
    
    public void ReceiveMessage() {
    	while(SocketHandler.CheckChatConnection()) {
    		ByteBuffer read = ByteBuffer.wrap(SocketHandler.ReceiveMessage());
    		System.out.println(read.remaining());
    		if(read.getInt() == -1) {
    			frame.setVisible(false);
    			SocketHandler.EndChat();
    		}
    		else {
    			byte[] messageData = new byte[read.remaining()];
    			System.out.println(read.remaining());
    			read.get(messageData);
    			String m = new String(messageData);
    			chatArea.append("Client: "+ m + "\n");
    			
    			// Log chat history
    			msgContent = "From Client: " + m + "\n";
    			FileHandler.SaveChatHistory(fileName, msgContent);
    		}
    	}
	}

    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
        	Packet p = new Packet();
        	p.SetHeader(message.length());
        	p.SetContent(message);
            chatArea.append("Server: " + message+ "\n");
            SocketHandler.SendMessage(p);
            System.out.println("Message sent");
            messageField.setText("");
            
            //Log chat history
            msgContent = "From Server: " + message + "\n";
			FileHandler.SaveChatHistory(fileName, msgContent);
        }
    }
    
    private void Terminate() {
    	Packet p = new Packet();
    	p.SetHeader(-1);
    	p.SetContent(0);
    	SocketHandler.SendMessage(p);
    	messageField.setText("");
    }
}
