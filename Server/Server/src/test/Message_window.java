// Server GUI
package test;

import back_end.SocketHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;

public class Message_window {
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;

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

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);

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
        if (SocketHandler.MakeConnection(12345)) {
            chatArea.append("Server started. Waiting for client...\n");
            listenForMessages();
        } else {
            chatArea.append("Failed to start server.\n");
        }
    }

    private void listenForMessages() {
        new Thread(() -> {
            while (true) {
                byte[] data = SocketHandler.ReceiveData();
                if (data != null) {
                    String message = new String(data, StandardCharsets.UTF_8);
                    chatArea.append("Client: " + message + "\n");
                }
            }
        }).start();
    }

    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            chatArea.append("Server: " + message + "\n");
            SocketHandler.SendData(message.getBytes(StandardCharsets.UTF_8));
            messageField.setText("");
        }
    }
}
