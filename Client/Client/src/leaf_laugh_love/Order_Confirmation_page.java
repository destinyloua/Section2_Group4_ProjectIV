package leaf_laugh_love;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import back_end.FileHandler;
import objects.Account;
import objects.Order;

public class Order_Confirmation_page extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public Order_Confirmation_page(JPanel mainPanel, CardLayout cardLayout, Account a, Order o) {
		FileHandler.SaveLog("New order placed");
		setLayout(null);
		
		// message to thank client 
		JLabel thanks = new JLabel("Thanks " + a.GetFName() + "!");
		thanks.setFont(new Font("Segoe UI", Font.BOLD, 32));
		thanks.setBounds(110, 91, 620, 43);
		add(thanks);
		
		// message confirming order was placed 
		JLabel orderConfirmed = new JLabel("Your order has been placed.");
		orderConfirmed.setFont(new Font("Segoe UI", Font.BOLD, 32));
		orderConfirmed.setBounds(110, 145, 620, 43);
		add(orderConfirmed);
		
		// message to inform client where to find order details
		JLabel checkHistory = new JLabel("You can view your orders in your Order History. ");
		checkHistory.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
		checkHistory.setBounds(110, 243, 619, 27);
		add(checkHistory);
		
		
		// button to return home
		JButton homeBtn = new JButton("Home");
		homeBtn.setForeground(new Color(85, 169, 85));
		homeBtn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		homeBtn.setBounds(110, 361, 214, 40);
		add(homeBtn);
		homeBtn.addActionListener(e->{
			mainPanel.removeAll();
			mainPanel.add(new Home_page(mainPanel, cardLayout, a, o), "Home");
			mainPanel.repaint();
			mainPanel.revalidate();
			cardLayout.show(mainPanel, "Home");
		});
		
		// button to view order history 
		JButton orderHistoryBtn = new JButton("Order History");
		orderHistoryBtn.setForeground(new Color(85, 169, 85));
		orderHistoryBtn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		orderHistoryBtn.setBounds(373, 361, 214, 40);
		add(orderHistoryBtn);
		
		orderHistoryBtn.addActionListener(e->{
			mainPanel.removeAll();
			mainPanel.add(new Orders_history_page(mainPanel, cardLayout, a, o), "Order History");
			mainPanel.repaint();
			mainPanel.revalidate();
			cardLayout.show(mainPanel, "Order History");
		});
	}
}
