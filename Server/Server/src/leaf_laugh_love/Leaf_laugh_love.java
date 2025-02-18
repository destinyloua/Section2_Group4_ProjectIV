package leaf_laugh_love;

import java.awt.*;
import java.util.*;

import objects.*;

import javax.swing.*;

import back_end.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        String user = "root";
        String password = "Nam@326389";
		setTitle("Leaf, Laugh, Love Dashboard");
		DatabaseHandler.SetUpConnection(url, user, password);
		DatabaseHandler.ConnectDatabase();
//		
//		
//		Vector<Order> list = new Vector<>();
//		list = DatabaseHandler.FecthOrdersList();
//		
//		for(int i=0; i<list.size();i++) {
//			list.get(i).Display();
//		}
		
		Order o1 = DatabaseHandler.FecthOrder(1);
		o1.Display();
		
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
        
//      Thread socketThread = new Thread(new Message_page());
//      socketThread.start();
	}
	
	public static void StartServer() {
		SocketHandler.MakeConnection(27000);
        Thread requestHandlingThread = new Thread(new RequestHandler());
        requestHandlingThread.start();
	}
}
