package leaf_laugh_love;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import back_end.*;
import objects.*;

public class Dashboard_page extends JPanel {

    private static final long serialVersionUID = 1L;
    private JPanel orderList; // Declare orderList as an instance variable

    /**
     * Create the panel.
     */
    public Dashboard_page(JPanel mainPanel, CardLayout cardLayout) {
    	setBackground(new Color(255, 255, 255));
        setLayout(null);

        JLabel pageLabel = new JLabel("Dashboard | Orders Management");
        pageLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        pageLabel.setBounds(80, 28, 723, 43);
        add(pageLabel);

        orderList = new JPanel();
        orderList.setLayout(new GridBagLayout()); // Use GridBagLayout
        orderList.setBackground(new Color(217, 217, 217));

        JScrollPane scrollPane = new JScrollPane(orderList);
        scrollPane.setBounds(120, 237, 600, 270);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

        JButton reloadButton = new JButton("Reload");
        reloadButton.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
        reloadButton.setBounds(620, 517, 100, 40);
        add(reloadButton);
        
        JLabel logo = new JLabel("Logo");
        logo.setIcon(new ImageIcon("C:\\Users\\baona\\Desktop\\School\\Project 4\\Section2_Group4_ProjectIV\\Server\\Server\\resources\\images\\logo.png"));
        logo.setBounds(10, 20, 60, 60);
        add(logo);
        
        JButton btnSeePlants = new JButton("See plants");
        btnSeePlants.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
        btnSeePlants.setBounds(483, 517, 127, 40);
        add(btnSeePlants);
        btnSeePlants.addActionListener(e->{
        	new Plants_list_window();
        });
        
        JButton btnChat = new JButton("Chat");
        btnChat.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
        btnChat.setBounds(620, 567, 100, 40);
        //add(btnChat);
//        btnChat.addActionListener(e->{
//        	new Message_window();
//        });
        
        JLabel lblTotalCompleted = new JLabel("Total completed:" + DatabaseHandler.GetNumberOfOrder(2));
        lblTotalCompleted.setForeground(new Color(85, 169, 85));
        lblTotalCompleted.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
        lblTotalCompleted.setBounds(120, 507, 354, 27);
        add(lblTotalCompleted);
        
        JLabel lblTotalReady = new JLabel("Total ready: " + DatabaseHandler.GetNumberOfOrder(1));
        lblTotalReady.setForeground(new Color(184, 223, 255));
        lblTotalReady.setFont(new Font("Segoe UI Semibold", Font.BOLD, 18));
        lblTotalReady.setBounds(120, 534, 354, 27);
        add(lblTotalReady);
        
        JLabel lblTotalProcessing = new JLabel("Total processing: " + DatabaseHandler.GetNumberOfOrder(0));
        lblTotalProcessing.setForeground(new Color(170, 170, 170));
        lblTotalProcessing.setFont(new Font("Segoe UI Semibold", Font.BOLD, 18));
        lblTotalProcessing.setBounds(120, 561, 354, 27);
        add(lblTotalProcessing);
        
        JLabel lblTotalCancelled = new JLabel("Total cancelled: " + DatabaseHandler.GetNumberOfOrder(-1));
        lblTotalCancelled.setForeground(new Color(255, 72, 72));
        lblTotalCancelled.setFont(new Font("Segoe UI Semibold", Font.BOLD, 18));
        lblTotalCancelled.setBounds(120, 588, 354, 27);
        add(lblTotalCancelled);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(184, 223, 255));
        panel.setBounds(120, 143, 600, 84);
        add(panel);
        panel.setLayout(null);
        
        Vector<Order> orders = new Vector<>(DatabaseHandler.FetchOrdersList());
        
        JLabel lblNewLabel_1 = new JLabel("Total orders: " + DatabaseHandler.GetNumberOfOrder());
        lblNewLabel_1.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 24));
        lblNewLabel_1.setBounds(10, 10, 571, 32);
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
        panel.add(lblNewLabel_1);
        
        float revenue = 0;
        for(Order o:orders) {
        	revenue += o.GetTotalPrice();
        }
        
        JLabel lblNewLabel_1_2 = new JLabel("Total revenue: $" + revenue);
        lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_1_2.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 24));
        lblNewLabel_1_2.setBounds(10, 42, 571, 32);
        panel.add(lblNewLabel_1_2);
        
        // Initial load of orders
        loadOrders();

        reloadButton.addActionListener(e -> loadOrders()); // Reload orders on button click
    }

    /**
     * Fetch and display orders from the database.
     */
    private void loadOrders() {
        orderList.removeAll(); // Clear the existing orders
        Vector<Order> orders = new Vector<>(DatabaseHandler.FetchOrdersList()); // Fetch updated order list

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0); // Space between rows

        for (int i = orders.size() - 1; i >= 0; i--) {
            Order o = orders.get(i);
            JPanel orderCard = new JPanel();
            orderCard.setLayout(new GridLayout(2, 1));
            orderCard.setPreferredSize(new Dimension(290, 60)); // Fixed size

            // Set background color based on order status
            switch (o.GetStatus()) {
                case 0 -> orderCard.setBackground(new Color(170, 170, 170));
                case 1 -> orderCard.setBackground(new Color(184, 223, 255));
                case 2 -> orderCard.setBackground(new Color(85, 169, 85));
                case -1 -> orderCard.setBackground(new Color(255, 72, 72));
                default -> orderCard.setBackground(new Color(170, 170, 170));
            }

            JLabel orderId = new JLabel("Order #" + o.GetId());
            orderId.setFont(new Font("Segoe UI", Font.BOLD, 20));
            orderId.setPreferredSize(new Dimension(479, 27));

            JLabel totalPrice = new JLabel("$" + o.GetTotalPrice());
            totalPrice.setFont(new Font("Segoe UI", Font.PLAIN, 20));
            totalPrice.setPreferredSize(new Dimension(479, 27));

            JButton editButton = new JButton("Edit");
            JLabel orderStatus;
            if(o.GetStatus() == 0) {
            	orderStatus = new JLabel("Processing");
            	orderStatus.setFont(new Font("Segoe UI", Font.BOLD, 20));
            }
            else if(o.GetStatus() == 1) {
            	orderStatus = new JLabel("Ready to pick up");
            	orderStatus.setFont(new Font("Segoe UI", Font.BOLD, 20));
            }
            else if(o.GetStatus() == 2) {
            	orderStatus = new JLabel("Completed");
            	orderStatus.setFont(new Font("Segoe UI", Font.BOLD, 20));
            }
            else if(o.GetStatus() == -1) {
            	orderStatus = new JLabel("Cancelled");
            	orderStatus.setFont(new Font("Segoe UI", Font.BOLD, 20));
            }
            else {
            	orderStatus = new JLabel("Unknown");
            	orderStatus.setFont(new Font("Segoe UI", Font.BOLD, 20));
            }

            orderCard.add(orderId);
            orderCard.add(editButton);
            orderCard.add(totalPrice);
            orderCard.add(orderStatus);

            orderList.add(orderCard, gbc);
            gbc.gridy++; // Move to the next row

            editButton.addActionListener(e -> new Order_info_window(o.GetId()));
        }

        orderList.revalidate(); // Refresh layout
        orderList.repaint(); // Redraw components
    }
}
