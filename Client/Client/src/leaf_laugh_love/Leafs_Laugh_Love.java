package leaf_laugh_love;

import java.awt.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import back_end.SocketHandler;
import objects.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.ByteBuffer;


public class Leafs_Laugh_Love extends JFrame {

	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Account a = new Account();
    private Order o = new Order();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Leafs_Laugh_Love frame = new Leafs_Laugh_Love();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Leafs_Laugh_Love() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				byte[] frame = ByteBuffer.allocate(4).putInt(-1).array();
				SocketHandler.SendData(frame);
				SocketHandler.CloseConnection();
				System.out.println("Closed");
				System.exit(1);
			}
		});
		setTitle("Leaf, Laugh, Love shop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 840, 700);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Initialize the CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        contentPane.add(mainPanel, BorderLayout.CENTER);
//    	mainPanel.add(new Home_page(mainPanel, cardLayout), "Home");

        //Connect to server
        if(SocketHandler.MakeConnection()) {
<<<<<<< HEAD
            mainPanel.add(new Log_in_page(mainPanel, cardLayout,a,o), "Log In");
=======
            mainPanel.add(new Log_in_page(mainPanel, cardLayout), "Log In");
            mainPanel.add(new Sign_up_page(mainPanel, cardLayout), "Sign Up");
            mainPanel.add(new Sign_up_success_page(mainPanel, cardLayout), "Sign Up Success");
        	mainPanel.add(new Error_page(mainPanel, cardLayout), "Error");
>>>>>>> parent of e694fdc (Update)
        }
        else {
        	mainPanel.add(new Error_page(mainPanel, cardLayout, a, o), "Error");
        }

	}

}


