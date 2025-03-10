package testing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import objects.Order;

import javax.swing.JButton;
import javax.swing.JCheckBox;

public class Order_info_window extends JFrame {

	private static final long serialVersionUID = 1L;
	public Order_info_window(Order order) {
		setTitle("Administrator Dashboard");
        setSize(840, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Header Section
        JLabel titleLabel = new JLabel("Administrator Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setBounds(80, 28, 400, 43);
        add(titleLabel);

        // Order Information Panel
        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(null);
        orderPanel.setBackground(new Color(184, 223, 255));
        orderPanel.setBounds(120, 180, 600, 100);
        add(orderPanel);

        JLabel orderLabel = new JLabel("Order #");
        orderLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        orderLabel.setBounds(10, 10, 150, 30);
        orderPanel.add(orderLabel);

        JLabel priceLabel = new JLabel("Total price: $");
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        priceLabel.setBounds(10, 50, 200, 30);
        orderPanel.add(priceLabel);

        // Scrollable Panel for Plant Cards
        JPanel plantListPanel = new JPanel();
        plantListPanel.setLayout(new GridLayout(0, 1, 10, 10)); // One column, vertical layout
        plantListPanel.setBackground(new Color(217, 217, 217));

        JScrollPane scrollPane = new JScrollPane(plantListPanel);
        scrollPane.setBounds(120, 300, 600, 270);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

        // Adding Example Plant Cards
        for (int i = 1; i <= 10; i++) {
            JPanel plantCard = new JPanel();
            plantCard.setLayout(new GridLayout(2, 1));
            plantCard.setPreferredSize(new Dimension(290, 60));
            plantCard.setBackground(new Color(184, 223, 255));

            JLabel plantName = new JLabel("Plant name " + i);
            plantName.setFont(new Font("Segoe UI", Font.BOLD, 20));

            JLabel plantPrice = new JLabel("$[Price] x [Quantity]");
            plantPrice.setFont(new Font("Segoe UI", Font.PLAIN, 18));

            plantCard.add(plantName);
            plantCard.add(plantPrice);
            plantListPanel.add(plantCard);
        }

        // Checkbox "Ready to pick up"
        JCheckBox readyCheckBox = new JCheckBox("Ready to pick up");
        readyCheckBox.setFont(new Font("Segoe UI", Font.BOLD, 20));
        readyCheckBox.setBackground(new Color(217, 217, 217));
        readyCheckBox.setBounds(120, 590, 200, 30);
        add(readyCheckBox);

        // Save Button
        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        saveButton.setBounds(624, 586, 100, 40);
        add(saveButton);

        // Simulating Button Action (TODO: Implement logic)
        saveButton.addActionListener(e -> {
        	JOptionPane.showMessageDialog(this, "Order status saved!");
        });
    }
}
