package leaf_laugh_love;

import javax.swing.JPanel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;

import back_end.*;
import objects.*;

import javax.swing.JTextField;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.ByteBuffer;

import javax.swing.JPasswordField;

public class Account_setting_page extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField fName;
	private JTextField lName;
	private JPasswordField password;

	/**
	 * Create the panel.
	 */
	public Account_setting_page(JPanel mainPanel, CardLayout cardLayout, Account a, Order o) {
		setLayout(null);
		
    	JLabel lblNewLabel = new JLabel("New label");
    	lblNewLabel.setIcon(new ImageIcon("resources\\images\\logo.png"));
    	lblNewLabel.setBounds(10, 20, 60, 60);
    	add(lblNewLabel);
    	
    	JLabel TitleLabel = new JLabel("Leaf, Laugh, Love | Account setting");
    	TitleLabel.setForeground(new Color(85, 169, 85));
    	TitleLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 32));
    	TitleLabel.setBounds(75, 28, 736, 39);
    	add(TitleLabel);
		
		fName = new JTextField();
		fName.setBounds(new Rectangle(0, 0, 400, 40));
		fName.setBounds(220, 201, 400, 40);
		add(fName);
		fName.setColumns(10);
		fName.setText(a.GetFName());
		
		lName = new JTextField();
		lName.setColumns(10);
		lName.setBounds(new Rectangle(0, 0, 400, 40));
		lName.setBounds(220, 270, 400, 40);
		add(lName);
		lName.setText(a.GetLName());
		
		password = new JPasswordField();
		password.setBounds(220, 339, 400, 40);
		add(password);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.setForeground(new Color(85, 169, 85));
		btnNewButton.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		btnNewButton.setBounds(220, 478, 400, 40);
		add(btnNewButton);
		btnNewButton.addActionListener(e -> {
			if(password.getText().length()==0) {
			}
			else {
			}
			if(ResponseHandler.UpdateAccount(a)) {
				JOptionPane.showMessageDialog(this, "Account information is updated!");
				FileHandler.SaveLog("Client updated account information");
			}
			else {
				JOptionPane.showMessageDialog(this, "Account information is not updated!");
			}
			mainPanel.removeAll();
			mainPanel.add(new Home_page(mainPanel, cardLayout, a, o), "Home");
			mainPanel.repaint();
			mainPanel.revalidate();
			cardLayout.show(mainPanel, "Home");
        });
		
		JButton btnBackToLog = new JButton("Back to home page");
		btnBackToLog.setForeground(new Color(85, 169, 85));
		btnBackToLog.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		btnBackToLog.setBounds(220, 528, 400, 40);
		add(btnBackToLog);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblFirstName.setBounds(220, 174, 400, 27);
		add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblLastName.setBounds(220, 244, 400, 27);
		add(lblLastName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblPassword.setBounds(220, 313, 400, 27);
		add(lblPassword);
		btnBackToLog.addActionListener(e ->{
			mainPanel.removeAll();
			mainPanel.add(new Home_page(mainPanel, cardLayout, a, o), "Home");
			mainPanel.repaint();
			mainPanel.revalidate();
			cardLayout.show(mainPanel, "Home");
		});
	}
}
