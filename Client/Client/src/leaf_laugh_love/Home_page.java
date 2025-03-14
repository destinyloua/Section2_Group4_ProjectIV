package leaf_laugh_love;

import java.awt.*;
import java.util.Vector;

import javax.swing.*;
import back_end.*;
import objects.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home_page extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Home_page(JPanel mainPanel, CardLayout cardLayout, Account a, Order o) {
		//TODO add all the available page here
		
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
    	
    	JPanel plantsList = new JPanel();
        plantsList.setLayout(new GridBagLayout()); // Use GridBagLayout
        plantsList.setBackground(new Color(217, 217, 217));

		JScrollPane scrollPane = new JScrollPane(plantsList);
		scrollPane.setBounds(120, 105, 600, 450);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
		
		JButton accountBttn = new JButton("Account");
		accountBttn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		accountBttn.setBounds(99, 568, 153, 35);
		add(accountBttn);
		accountBttn.addActionListener(e->{
			mainPanel.removeAll();
			mainPanel.add(new Account_setting_page(mainPanel, cardLayout, a, o), "Account");
			mainPanel.repaint();
			mainPanel.revalidate();
			cardLayout.show(mainPanel, "Account");
		});
		
		JButton orderBttn = new JButton("Orders");
		orderBttn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		orderBttn.setBounds(262, 568, 153, 35);
		add(orderBttn);
		orderBttn.addActionListener(e->{	
			mainPanel.removeAll();
			mainPanel.add(new Orders_history_page(mainPanel, cardLayout, a, o), "Orders history");
			mainPanel.repaint();
			mainPanel.revalidate();
			cardLayout.show(mainPanel, "Orders history");
		});
		
		JButton helpBttn = new JButton("Help");
		helpBttn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		helpBttn.setBounds(425, 568, 153, 35);
		add(helpBttn);
		helpBttn.addActionListener(e->{
			ResponseHandler.StartChat();
			new Help_window(a);
		});
		
		JButton cartBttn = new JButton("View cart");
		cartBttn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		cartBttn.setBounds(588, 568, 153, 35);
		add(cartBttn);
		cartBttn.addActionListener(e->{
			mainPanel.removeAll();
			mainPanel.add(new View_cart_page(mainPanel, cardLayout, a, o), "Cart");
			mainPanel.repaint();
			mainPanel.revalidate();
			cardLayout.show(mainPanel, "Cart");
		});
		
		JButton logOutBttn = new JButton("Log out");
		
		logOutBttn.setBackground(UIManager.getColor("Button.shadow"));
		logOutBttn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		logOutBttn.setBounds(344, 613, 153, 35);
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
		Vector<Plant>plants = ResponseHandler.GetPlantsList();
		for (int i=0; i<plants.size();i++) {
			Plant p = plants.get(i);
		    JPanel plantCard = new JPanel();
		    plantCard.setLayout(null);
		    plantCard.setPreferredSize(new Dimension(600, 100)); // Fixed size
		    plantCard.setBackground(new Color(85, 169, 85));

		    JLabel plantId = new JLabel(p.GetName());
		    plantId.setFont(new Font("Segoe UI", Font.BOLD, 20));
		    plantId.setBounds(105, 9, 485, 27);
		    JLabel price = new JLabel("$"+ p.GetPrice());
		    price.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		    price.setBounds(105, 36, 485, 27);
		    
			JButton addBttn = new JButton("Add to cart");
			addBttn.setForeground(new Color(0, 0, 0));
			addBttn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
			addBttn.setBounds(105, 63, 150, 30);
			addBttn.addActionListener(e->{
				o.AddPlant(p.GetId(), 1);
				
			});
			
//			JButton detailBttn = new JButton("See details");
//			detailBttn.setForeground(new Color(0, 0, 0));
//			detailBttn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
//			detailBttn.setBounds(265, 63, 150, 30);
			
			JLabel image = new JLabel(ImageProcessor.Resize(plants.get(i).GetImagePath(), 100, 100));
			image.setBounds(0, 0, 100, 100);

		    plantCard.add(plantId);
		    plantCard.add(price);
		    plantCard.add(addBttn);
		    //plantCard.add(detailBttn);
		    plantCard.add(image);

		    plantsList.add(plantCard, gbc);
		    gbc.gridy++;   // Move to the next row
		}
       
    }
}
