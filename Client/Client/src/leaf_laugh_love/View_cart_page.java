package leaf_laugh_love;

import java.awt.*;
import java.util.Vector;

import javax.swing.*;
import back_end.*;
import objects.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class View_cart_page extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public View_cart_page(JPanel mainPanel, CardLayout cardLayout, Account a, Order o) {
		//TODO Get plant by pID
		setLayout(null);
    	JLabel lblNewLabel = new JLabel("New label");
    	lblNewLabel.setIcon(new ImageIcon("resources\\images\\logo.png"));
    	lblNewLabel.setBounds(10, 20, 60, 60);
    	add(lblNewLabel);
    	
    	JLabel TitleLabel = new JLabel("Leaf, Laugh, Love | Hi " + a.GetFName());
    	TitleLabel.setForeground(new Color(85, 169, 85));
    	TitleLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 32));
    	TitleLabel.setBounds(75, 28, 736, 39);
    	add(TitleLabel);
    	
    	JPanel ordersList = new JPanel();
    	ordersList.setLayout(new GridBagLayout()); // Use GridBagLayout
    	ordersList.setBackground(new Color(217, 217, 217));

		JScrollPane scrollPane = new JScrollPane(ordersList);
		
		JLabel noOrdersLabel = new JLabel("No item in cart");
		noOrdersLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 32));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 1;
		scrollPane.setBounds(120, 105, 600, 450);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
		
		JButton homeBttn = new JButton("Home");
		homeBttn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		homeBttn.setBounds(120, 568, 153, 35);
		add(homeBttn);
		homeBttn.addActionListener(e->{
			mainPanel.removeAll();
			mainPanel.add(new Home_page(mainPanel, cardLayout, a, o), "Home");
			mainPanel.repaint();
			mainPanel.revalidate();
			cardLayout.show(mainPanel, "Home");
		});
		
		JButton helpBttn = new JButton("Help");
		helpBttn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		helpBttn.setBounds(283, 568, 153, 35);
		add(helpBttn);
		helpBttn.addActionListener(e->{
			ResponseHandler.StartChat();
			new Help_window(a);
		});
		
		JButton logOutBttn = new JButton("Log out");
		
		logOutBttn.setBackground(UIManager.getColor("Button.shadow"));
		logOutBttn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		logOutBttn.setBounds(567, 568, 153, 35);
		add(logOutBttn);
		logOutBttn.addActionListener(e->{
			ResponseHandler.TerminateConnection();
			System.exit(0);
		});
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 0, 10, 0); // Space between rows
		if(o.GetPId().size()==0) {
			ordersList.add(noOrdersLabel, gbc_lblNewLabel_1);
		}
		else {
			Vector<Integer> pId = o.GetPId();
			Vector<Integer> quantity = o.GetQuantity();
			for (int i=0; i<o.GetPId().size();i++) {
				Plant p = ResponseHandler.GetPlant(pId.get(i));
			    JPanel orderCard = new JPanel();
			    orderCard.setLayout(null);
			    orderCard.setPreferredSize(new Dimension(600, 100)); // Fixed size
			    orderCard.setBackground(new Color(85, 169, 85));
			    JLabel plantId = new JLabel("Plant " + p.GetName());
			    plantId.setFont(new Font("Segoe UI", Font.BOLD, 20));
			    plantId.setBounds(10, 9, 485, 27);
			    JLabel price = new JLabel("Quantity: " + quantity.get(i));
			    price.setFont(new Font("Segoe UI", Font.PLAIN, 18));
			    price.setBounds(10, 36, 485, 27);

			    orderCard.add(plantId);
			    orderCard.add(price);
			    
			    ordersList.add(orderCard, gbc);
			    gbc.gridy++;   // Move to the next row
			}
		}
    }
}
