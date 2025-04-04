package leaf_laugh_love;

import javax.swing.JPanel;

import back_end.FileHandler;
import objects.*;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

import java.awt.CardLayout;
import java.awt.Color;

public class Sign_up_success_page extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Sign_up_success_page(JPanel mainPanel, CardLayout cardLayout, Account a, Order o) {
		FileHandler.SaveLog("New client account created");
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Congratulations! Your account is created!");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
		lblNewLabel.setBounds(110, 298, 620, 43);
		add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Back to log in");
		btnNewButton.setForeground(new Color(85, 169, 85));
		btnNewButton.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		btnNewButton.setBounds(220, 361, 400, 40);
		add(btnNewButton);
		btnNewButton.addActionListener(e->{
			mainPanel.removeAll();
			mainPanel.add(new Log_in_page(mainPanel, cardLayout, a, o), "Log in");
			mainPanel.repaint();
			mainPanel.revalidate();
			cardLayout.show(mainPanel, "Log in");
		});
		
		JLabel lblNewLabel_1 = new JLabel("After logging in, you can see your account information in the setting page");
		lblNewLabel_1.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(111, 411, 619, 27);
		add(lblNewLabel_1);

	}
}
