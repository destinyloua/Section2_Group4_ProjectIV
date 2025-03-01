package leaf_laugh_love;
import javax.swing.*;

import back_end.DatabaseHandler;
import back_end.ImageProcessor;
import objects.Plant;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;

public class Add_plant_window extends JFrame {
    private JLabel imageLabel;
    private String imagePath; // Stores the selected image path
    private String imageFormat;
    File selectedFile;
    private JTextField nameInput;
    private JTextField priceInput;
    private JTextField quantityInput;

    public Add_plant_window() {
        setTitle("Image Uploader");
        setSize(840, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setVisible(true);
        
        // Image display area
        imageLabel = new JLabel("No Image Uploaded", SwingConstants.CENTER);
        imageLabel.setBounds(50, 111, 400, 400);
        imageLabel.setPreferredSize(new Dimension(300, 300));
        getContentPane().setLayout(null);
        getContentPane().add(imageLabel);
        
        nameInput = new JTextField();
        nameInput.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        nameInput.setBounds(490, 138, 300, 20);
        getContentPane().add(nameInput);
        nameInput.setColumns(10);
        
        JButton uploadButton = new JButton("Upload Image");
        uploadButton.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
        uploadButton.setBounds(50, 548, 140, 40);
        getContentPane().add(uploadButton);
        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int option = fileChooser.showOpenDialog(null);
                if (option == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    imagePath = selectedFile.getAbsolutePath(); // Store image path
                    imageFormat = ImageProcessor.GetFileExtension(selectedFile);
                    JOptionPane.showMessageDialog(null, "Image Selected: " + imagePath);
                }
            }
        });
        
        JButton loadButton = new JButton("Load Image");
        loadButton.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
        loadButton.setBounds(200, 548, 136, 40);
        getContentPane().add(loadButton);
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (imagePath != null) {
                    imageLabel.setIcon(ImageProcessor.Resize(imagePath, 400, 400));
                    imageLabel.setText(""); // Remove text after loading image
                } else {
                    JOptionPane.showMessageDialog(null, "Please upload an image first.");
                }
            }
        });
        
        
        JLabel lblplantName = new JLabel("Plant Name");
        lblplantName.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
        lblplantName.setBounds(490, 111, 300, 27);
        getContentPane().add(lblplantName);
        
        priceInput = new JTextField();
        priceInput.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        priceInput.setColumns(10);
        priceInput.setBounds(490, 185, 300, 20);
        getContentPane().add(priceInput);
        
        JLabel lblPrice = new JLabel("Price");
        lblPrice.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
        lblPrice.setBounds(490, 158, 300, 27);
        getContentPane().add(lblPrice);
        
        quantityInput = new JTextField();
        quantityInput.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        quantityInput.setColumns(10);
        quantityInput.setBounds(490, 232, 300, 20);
        getContentPane().add(quantityInput);
        
        JLabel lblQuantity = new JLabel("Quantity");
        lblQuantity.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
        lblQuantity.setBounds(490, 205, 300, 27);
        getContentPane().add(lblQuantity);
        
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 20));
        btnCancel.setBounds(554, 548, 113, 40);
        getContentPane().add(btnCancel);
        btnCancel.addActionListener(e->{
        	setVisible(false);
        });
        
        JButton btnAddPlant = new JButton("Add plant");
        btnAddPlant.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
        btnAddPlant.setBounds(677, 548, 113, 40);
        getContentPane().add(btnAddPlant);
        btnAddPlant.addActionListener(e->{
        	try {
        		String strQuantity = quantityInput.getText();
        		System.out.println(strQuantity);
        		int quantity = Integer.parseInt(strQuantity);
        		String strPrice = priceInput.getText();
        		System.out.println(strPrice);
        		float price = Float.parseFloat(strPrice);
        		String name = nameInput.getText();
        		System.out.println("Parsed info");
        		Plant p = new Plant(name, price, quantity, ImageProcessor.GetFileExtension(selectedFile));
        		System.out.println("Plant created");
        		String destinationFile = "resources/images/plants/" + p.GetImagePath();
        		ImageProcessor.CopyFile(imagePath, destinationFile);
        		System.out.println("File saved");
        		//TODO add to database
        		if(DatabaseHandler.InsertNewPlant(p)) {
        			JOptionPane.showMessageDialog(null, "Plant inserted successfully!");
        			setVisible(false);
        		}
        		else {
        			JOptionPane.showMessageDialog(null, "Failed to insert plant");
        			setVisible(false);
        		}
        	}
        	catch(Exception ex) {
        		JOptionPane.showMessageDialog(null, "Price and quantity must be a number!");
        	}
        });

        setVisible(true);
    }
}
