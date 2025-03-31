package leaf_laugh_love;

import javax.swing.*;
import back_end.FileHandler;
import back_end.SocketHandler;
import objects.Packet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.ByteBuffer;
import java.util.concurrent.*;

public class Message_window {
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private JButton endBttn;

    private static String fileName = "ChatHistory.txt"; // REQ-LOG-040
    private static String msgContent;

    private ScheduledExecutorService scheduler;
    private Runnable terminateTask;

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
        endBttn.addActionListener(e -> {
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
        initializeTimer();
    }

    private void initializeTimer() {
        scheduler = Executors.newScheduledThreadPool(1);
        terminateTask = () -> {
            System.out.println("2 minutes passed with no activity. Terminating chat.");
            Terminate();
            frame.setVisible(false);
        };
        resetTimer();
    }

    private void resetTimer() {
        scheduler.shutdownNow();
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(terminateTask, 2, TimeUnit.MINUTES);
    }

    private void startServer() {
        chatArea.append("Client connected\n");
        new Thread(this::ReceiveMessage).start();
    }

    public void ReceiveMessage() {
        while (SocketHandler.CheckChatConnection()) {
            ByteBuffer read = ByteBuffer.wrap(SocketHandler.ReceiveMessage());
            if (read.getInt() == -1) {
                frame.setVisible(false);
                SocketHandler.EndChat();
            } else {
                byte[] messageData = new byte[read.remaining()];
                read.get(messageData);
                String m = new String(messageData);
                chatArea.append("Client: " + m + "\n");

                // Log chat history
                msgContent = "From Client: " + m + "\n";
                FileHandler.SaveChatHistory(fileName, msgContent);

                // Reset the timer on receiving a message
                resetTimer();
            }
        }
    }

    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            Packet p = new Packet();
            p.SetHeader(message.length());
            p.SetContent(message);
            chatArea.append("Server: " + message + "\n");
            SocketHandler.SendMessage(p);
            System.out.println("Message sent");
            messageField.setText("");

            // Log chat history
            msgContent = "From Server: " + message + "\n";
            FileHandler.SaveChatHistory(fileName, msgContent);

            // Reset the timer on sending a message
            resetTimer();
        }
    }

    private void Terminate() {
        Packet p = new Packet();
        p.SetHeader(-1);
        p.SetContent(0);
        SocketHandler.SendMessage(p);
        messageField.setText("");
        System.out.println("Chat terminated.");
    }
}
