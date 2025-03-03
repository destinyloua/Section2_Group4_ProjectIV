package leaf_laugh_love;

import javax.swing.JPanel;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import back_end.*;
import objects.*;

import javax.swing.JTextField;
import java.awt.Rectangle;
import javax.swing.JButton;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.ByteBuffer;

import javax.swing.JPasswordField;

public class Sign_up_page extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField fName;
	private JTextField lName;
	private JTextField email;
	private JPasswordField password;

	/**
	 * Create the panel.
	 */
	public Sign_up_page(JPanel mainPanel, CardLayout cardLayout, Account a, Order o) {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sign Up");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
		lblNewLabel.setBounds(10, 132, 820, 43);
		add(lblNewLabel);
		
		fName = new JTextField();
		fName.setBounds(new Rectangle(0, 0, 400, 40));
		fName.setBounds(220, 201, 400, 40);
		add(fName);
		fName.setColumns(10);
		
		lName = new JTextField();
		lName.setColumns(10);
		lName.setBounds(new Rectangle(0, 0, 400, 40));
		lName.setBounds(220, 270, 400, 40);
		add(lName);
		
		email = new JTextField();
		email.setColumns(10);
		email.setBounds(new Rectangle(0, 0, 400, 40));
		email.setBounds(220, 339, 400, 40);
		add(email);
		
		password = new JPasswordField();
		password.setBounds(220, 408, 400, 40);
		add(password);
		
		JButton btnNewButton = new JButton("Sign up");
		btnNewButton.setForeground(new Color(85, 169, 85));
		btnNewButton.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		btnNewButton.setBounds(220, 478, 400, 40);
		add(btnNewButton);
		btnNewButton.addActionListener(e -> {
			a.SetAccount(fName.getText(), lName.getText(), email.getText().toLowerCase(), password.getText());
			if(ResponseHandler.CreateAccount(a)) {
				mainPanel.removeAll();
				mainPanel.add(new Sign_up_success_page(mainPanel, cardLayout, a, o), "Sign up success");
				mainPanel.repaint();
				mainPanel.revalidate();
				cardLayout.show(mainPanel, "Sign up success");
			}
			else {
				mainPanel.removeAll();
				mainPanel.add(new Error_page(mainPanel, cardLayout, a, o), "Error");
				mainPanel.repaint();
				mainPanel.revalidate();
				cardLayout.show(mainPanel, "Error");
			}
        });
		
		JButton btnBackToLog = new JButton("Back to log in page");
		btnBackToLog.setForeground(new Color(85, 169, 85));
		btnBackToLog.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		btnBackToLog.setBounds(220, 528, 400, 40);
		add(btnBackToLog);
		btnBackToLog.addActionListener(e ->{
			mainPanel.removeAll();
			mainPanel.add(new Log_in_page(mainPanel, cardLayout, a, o), "Log in");
			mainPanel.repaint();
			mainPanel.revalidate();
			cardLayout.show(mainPanel, "Log in");
		});
	}
}
