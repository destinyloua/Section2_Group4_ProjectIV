package leaf_laugh_love;

import java.awt.*;
import java.util.Vector;

import javax.swing.*;

import back_end.DatabaseHandler;
import back_end.FileHandler;
import objects.*;

public class Order_info_window extends JFrame {

	private static final long serialVersionUID = 1L;
	public Order_info_window(int orderId) {
		Order order = DatabaseHandler.FecthOrder(orderId);
		Account account = DatabaseHandler.FetchAccount(order.GetAId());
		setTitle("Order #" + orderId);
        setSize(840, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        setVisible(true);
        
        JPanel deleteConfirmPanel = new JPanel();
        deleteConfirmPanel.setBackground(new Color(192, 192, 192));
        deleteConfirmPanel.setBounds(220, 272, 400, 156);
        getContentPane().add(deleteConfirmPanel);
        deleteConfirmPanel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("You are deleting");
        lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        lblNewLabel.setBounds(10, 10, 246, 43);
        deleteConfirmPanel.add(lblNewLabel);
        
        JLabel lblNewLabel1 = new JLabel("Order #"+order.GetId());
        lblNewLabel1.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblNewLabel1.setBounds(10, 53, 246, 43);
        deleteConfirmPanel.add(lblNewLabel1);
        
        JButton confirmYes = new JButton("Yes");
        confirmYes.setBackground(new Color(255, 72, 72));
        confirmYes.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
        confirmYes.setBounds(10, 106, 100, 40);
        deleteConfirmPanel.add(confirmYes);
        confirmYes.addActionListener(e -> {
        	DatabaseHandler.DeleteOrder(orderId);
        	JOptionPane.showMessageDialog(this, "Order deleted!");
        	setVisible(false);
        });
        
        JButton confirmNo = new JButton("No");
        confirmNo.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
        confirmNo.setBackground(new Color(85, 255, 85));
        confirmNo.setBounds(120, 106, 100, 40);
        deleteConfirmPanel.add(confirmNo);
        confirmNo.addActionListener(e -> {
        	setVisible(false);
        });
        
        deleteConfirmPanel.setVisible(false);
        
        
        
        JPanel cancelledConfirmPanel = new JPanel();
        cancelledConfirmPanel.setBackground(new Color(192, 192, 192));
        cancelledConfirmPanel.setBounds(220, 272, 400, 156);
        getContentPane().add(cancelledConfirmPanel);
        cancelledConfirmPanel.setLayout(null);
        
        JLabel cancelledPanelLabel = new JLabel("You are cancelling");
        cancelledPanelLabel.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        cancelledPanelLabel.setBounds(10, 10, 255, 43);
        cancelledConfirmPanel.add(cancelledPanelLabel);
        
        JLabel cancelledPanelOIdLabel = new JLabel("Order #"+order.GetId());
        cancelledPanelOIdLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        cancelledPanelOIdLabel.setBounds(10, 53, 246, 43);
        cancelledConfirmPanel.add(cancelledPanelOIdLabel);
        
        JButton cancelledPanelconfirmYes = new JButton("Yes");
        cancelledPanelconfirmYes.setBackground(new Color(255, 72, 72));
        cancelledPanelconfirmYes.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
        cancelledPanelconfirmYes.setBounds(10, 106, 100, 40);
        cancelledConfirmPanel.add(cancelledPanelconfirmYes);
        cancelledPanelconfirmYes.addActionListener(e -> {
        	DatabaseHandler.UpdateOrderStatus(orderId, -1);
        	String logMessage = FileHandler.GetTimeStamp() + ": Order #" + order.GetId() + " is cancelled";
        	FileHandler.WriteToFile("Log.txt", logMessage);
        	JOptionPane.showMessageDialog(this, "Order is cancelled!");
        	setVisible(false);
        });
        
        JButton cancelledPanelconfirmNo = new JButton("No");
        cancelledPanelconfirmNo.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
        cancelledPanelconfirmNo.setBackground(new Color(85, 255, 85));
        cancelledPanelconfirmNo.setBounds(120, 106, 100, 40);
        cancelledConfirmPanel.add(cancelledPanelconfirmNo);
        cancelledPanelconfirmNo.addActionListener(e -> {
        	setVisible(false);
        });
        
        cancelledConfirmPanel.setVisible(false);

        // Header Section
        JLabel titleLabel = new JLabel();
        if(order.GetStatus() >=2 || order.GetStatus() == -1) {
        	titleLabel.setText("Order #" + order.GetId() +" (Archieved)");
        }
        else {
        	titleLabel.setText("Order #" + order.GetId());
        }
        
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setBounds(80, 28, 400, 43);
        getContentPane().add(titleLabel);
        
        // Account Information Panel
        JPanel accountPanel = new JPanel();
        accountPanel.setLayout(null);
        accountPanel.setBackground(new Color(184, 223, 255));
        accountPanel.setBounds(120, 88, 600, 84);
        getContentPane().add(accountPanel);

        JLabel accountNameLabel = new JLabel("Customer name: " + account.GetFName() + " " + account.GetLName());
        accountNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        accountNameLabel.setBounds(10, 10, 580, 30);
        accountPanel.add(accountNameLabel);

        JLabel accountIdLabel = new JLabel("Account ID: "+ account.GetId());
        accountIdLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        accountIdLabel.setBounds(10, 50, 580, 30);
        accountPanel.add(accountIdLabel);

        // Order Information Panel
        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(null);
        orderPanel.setBackground(new Color(184, 223, 255));
        orderPanel.setBounds(120, 182, 600, 84);
        getContentPane().add(orderPanel);

        JLabel orderLabel = new JLabel("Order #" + order.GetId());
        orderLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        orderLabel.setBounds(10, 10, 580, 30);
        orderPanel.add(orderLabel);

        JLabel priceLabel = new JLabel("Total price: $" + order.GetTotalPrice());
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        priceLabel.setBounds(10, 50, 580, 30);
        orderPanel.add(priceLabel);
        
        JPanel plantsList = new JPanel();
        plantsList.setLayout(new GridBagLayout()); // Use GridBagLayout
        plantsList.setBackground(new Color(217, 217, 217));

		JScrollPane scrollPane = new JScrollPane(plantsList);
		scrollPane.setBounds(120, 286, 600, 270);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		getContentPane().add(scrollPane);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 0, 10, 0); // Space between rows

		for (int i=0; i<order.GetPId().size();i++) {
			Plant p = DatabaseHandler.FetchPlant(order.GetPId().get(i));
		    JPanel plantCard = new JPanel();
		    plantCard.setLayout(new GridLayout(2, 1));
		    plantCard.setPreferredSize(new Dimension(290, 60)); // Fixed size
		    plantCard.setBackground(new Color(170, 170, 170));

		    JLabel plantId = new JLabel(p.GetName());
		    plantId.setFont(new Font("Segoe UI", Font.BOLD, 20));
		    float total = p.GetPrice() * order.GetQuantity().get(i);
		    JLabel price = new JLabel("$" + p.GetPrice() + " x " + order.GetQuantity().get(i) + " = " + "$" + total);
		    price.setFont(new Font("Segoe UI", Font.PLAIN, 18));

		    plantCard.add(plantId);
		    plantCard.add(price);
		    
		    plantsList.add(plantCard, gbc);
		    gbc.gridy++;   // Move to the next row
		}
		
        // Checkbox "Ready to pick up"
        JCheckBox readyCheck = new JCheckBox("Ready to pick up");
        readyCheck.setFont(new Font("Segoe UI", Font.BOLD, 20));
        readyCheck.setBackground(new Color(184, 223, 255));
        readyCheck.setBounds(120, 577, 200, 30);
        getContentPane().add(readyCheck);

        JCheckBox completedCheck = new JCheckBox("Order completed");
        completedCheck.setFont(new Font("Segoe UI", Font.BOLD, 20));
        completedCheck.setBackground(new Color(85, 169, 85));
        completedCheck.setBounds(120, 617, 200, 30);
        getContentPane().add(completedCheck);

        
        if(order.GetStatus() == -1) {
        	readyCheck.setSelected(false);
        	completedCheck.setSelected(false);
        	
        	readyCheck.setEnabled(false);
        	completedCheck.setEnabled(false);
        }
        else if(order.GetStatus() == 0) {
        	readyCheck.setSelected(false);
        	completedCheck.setSelected(false);
        	
        	readyCheck.setEnabled(true);
        	completedCheck.setEnabled(false);
        }
        else if(order.GetStatus() == 1) {
        	readyCheck.setSelected(true);
        	completedCheck.setSelected(false);
        	
        	readyCheck.setEnabled(true);
        	completedCheck.setEnabled(true);
        }
        else if(order.GetStatus() == 2) {
        	readyCheck.setSelected(true);
        	completedCheck.setSelected(true);
        	
        	readyCheck.setEnabled(false);
        	completedCheck.setEnabled(false);
        }
        
        // Save Button
        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        saveButton.setBackground(new Color(85, 169, 85));
        saveButton.setBounds(624, 572, 100, 40);
        getContentPane().add(saveButton);
        // Simulating Button Action (TODO: Implement logic)
        saveButton.addActionListener(e -> {
        	if(readyCheck.isSelected()) {
        		if (completedCheck.isSelected()) {
        			String logMessage = FileHandler.GetTimeStamp()+": Order #" + order.GetId() + "'s state changed to Completed";
        			FileHandler.WriteToFile("Log.txt", logMessage);
        			DatabaseHandler.UpdateOrderStatus(orderId, 2);
        		}
        		else {
        			String logMessage = FileHandler.GetTimeStamp()+": Order #" + order.GetId() + "'s state changed to Ready";
        			FileHandler.WriteToFile("Log.txt", logMessage);
        			DatabaseHandler.UpdateOrderStatus(orderId, 1);
        		}
        	}
        	else if(!readyCheck.isSelected()) {
        		if (completedCheck.isSelected()) {
        			String logMessage = FileHandler.GetTimeStamp()+": Order #" + order.GetId() + "'s state changed to Ready";
        			FileHandler.WriteToFile("Log.txt", logMessage);
        			DatabaseHandler.UpdateOrderStatus(orderId, 1);
        		}
        		else {
        			String logMessage = FileHandler.GetTimeStamp()+ ": Order #" + order.GetId() + "'s state changed to Processing";
        			FileHandler.WriteToFile("Log.txt", logMessage);
        			DatabaseHandler.UpdateOrderStatus(orderId, 0);
        		}
        	}
        	JOptionPane.showMessageDialog(this, "Order's state changed!");
        	setVisible(false);
        });
        
        // Delete Button
        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        deleteButton.setBackground(new Color(255, 72, 72));
        deleteButton.setBounds(514, 572, 100, 40);
        getContentPane().add(deleteButton);
        // Simulating Button Action (TODO: Implement logic)
        deleteButton.addActionListener(e -> {
        	deleteConfirmPanel.setVisible(true);
        });
        
        // Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        cancelButton.setBackground(new Color(217, 217, 217));
        cancelButton.setBounds(404, 572, 100, 40);
        getContentPane().add(cancelButton);
        cancelButton.addActionListener(e -> {
        	setVisible(false);
        });
        
        JButton cancelledButton = new JButton("Cancelled Order");
        cancelledButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        cancelledButton.setBackground(new Color(255, 72, 72));
        cancelledButton.setBounds(514, 617, 210, 40);
        getContentPane().add(cancelledButton);
        if(order.GetStatus() == -1) {
        	cancelledButton.setVisible(false);
        }
        // Simulating Button Action (TODO: Implement logic)
        cancelledButton.addActionListener(e -> {
        	cancelledConfirmPanel.setVisible(true);
        });

    }
}
