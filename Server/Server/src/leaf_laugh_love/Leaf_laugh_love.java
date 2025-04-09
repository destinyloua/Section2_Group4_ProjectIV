package leaf_laugh_love;

import java.awt.*;
import java.util.*;

import objects.*;

import javax.swing.*;

import back_end.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;



public class Leaf_laugh_love extends JFrame {

	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Leaf_laugh_love frame = new Leaf_laugh_love();
					frame.setVisible(true);
					FileHandler.CreateFile("Log.txt");
					String logMessage = FileHandler.GetTimeStamp() + ": Server started";
					FileHandler.WriteToFile("Log.txt", logMessage);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            StartServer();
                        }
                    }).start();
                    
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Leaf_laugh_love() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				String logMessage = FileHandler.GetTimeStamp() + ": Server stopped";
				FileHandler.WriteToFile("Log.txt", logMessage);
			}
		});
		String url = "jdbc:mysql://localhost:3306/project4";
		//Change these!!
        String user = "root";
        String password = "sqlPFM00*";
		setTitle("Leaf, Laugh, Love Dashboard");
		DatabaseHandler.SetUpConnection(url, user, password);
		DatabaseHandler.ConnectDatabase();
		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 840, 700);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Initialize the CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        contentPane.add(mainPanel, BorderLayout.CENTER);
        //Connect to server
        if(DatabaseHandler.CheckConnection()) {
        	mainPanel.add(new Dashboard_page(mainPanel, cardLayout), "Dashboard");
        }
        else {
        	mainPanel.add(new Error_page(mainPanel, cardLayout), "Error");
        }
	}
	
	public static void StartServer() {		
	    while (true) {  // Keep listening for new connections
	        try {
	            SocketHandler.MakeConnection(27000);  // Wait for a client connection
	            
	            Thread requestHandlingThread = new Thread(new RequestHandler());
	            requestHandlingThread.start();
	            
	            System.out.println("Client connected, handling requests...");
	            // Wait for the client to disconnect
	            requestHandlingThread.join();  
	            
	            System.out.println("Client disconnected, waiting for new connection...");

	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("Error in connection. Restarting server...");
	        }
	    }
	}
}
