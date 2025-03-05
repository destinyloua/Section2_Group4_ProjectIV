package leaf_laugh_love;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import back_end.DatabaseHandler;
import back_end.ImageProcessor;
import objects.Plant;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Plants_list_window extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel plantsList;
	public Plants_list_window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 840, 700);
		getContentPane().setLayout(null);
		setVisible(true);
		
		JLabel pageLabel = new JLabel("Dashboard | Plants");
        pageLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        pageLabel.setBounds(80, 28, 739, 43);
        getContentPane().add(pageLabel);
        
        JLabel logo = new JLabel("Logo");
        logo.setIcon(new ImageIcon("C:\\Users\\baona\\Desktop\\School\\Project 4\\Section2_Group4_ProjectIV\\Server\\Server\\resources\\images\\logo.png"));
        logo.setBounds(10, 20, 60, 60);
        getContentPane().add(logo);

        plantsList = new JPanel();
        plantsList.setVisible(true);
        plantsList.setLayout(new GridBagLayout()); // Use GridBagLayout
        plantsList.setBackground(new Color(217, 217, 217));

        JScrollPane scrollPane = new JScrollPane(plantsList);
        scrollPane.setBounds(120, 125, 600, 450);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scrollPane);

        JButton reloadButton = new JButton("Reload");
        reloadButton.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
        reloadButton.setBounds(620, 585, 100, 40);
        getContentPane().add(reloadButton);
        
        JButton btnAddPlant = new JButton("Add plant");
        getContentPane().add(btnAddPlant);
        btnAddPlant.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
        btnAddPlant.setBounds(481, 585, 129, 40);
        btnAddPlant.addActionListener(e->{
        	new Add_plant_window();
        });
        
        // Initial load of orders
        loadOrders();

        reloadButton.addActionListener(e -> loadOrders()); // Reload orders on button click
    }

    /**
     * Fetch and display orders from the database.
     */
    private void loadOrders() {
    	plantsList.removeAll(); // Clear the existing orders
        Vector<Plant> plants = new Vector<>(DatabaseHandler.FetchPlantsList()); // Fetch updated order list

        GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 0, 10, 0); // Space between rows

        for (int i = plants.size() - 1; i >= 0; i--) {
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
		    
			JButton editBttn = new JButton("Edit");
			editBttn.setForeground(new Color(0, 0, 0));
			editBttn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
			editBttn.setBounds(105, 63, 150, 30);
			
			JLabel image = new JLabel(ImageProcessor.Resize(plants.get(i).GetImagePath(), 100, 100));
			image.setBounds(0, 0, 100, 100);

		    plantCard.add(plantId);
		    plantCard.add(price);
		    plantCard.add(editBttn);
		    //plantCard.add(detailBttn);
		    plantCard.add(image);

            plantsList.add(plantCard, gbc);
            gbc.gridy++; // Move to the next row

            //TODO change to edit plant
            //editBttn.addActionListener(ev -> new Order_info_window(o.GetId()));
        }

        plantsList.revalidate(); // Refresh layout
        plantsList.repaint(); // Redraw components
    }
}
