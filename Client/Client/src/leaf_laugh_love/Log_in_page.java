package leaf_laugh_love;

import javax.swing.*;

import objects.Account;

import java.awt.*;
import java.awt.event.*;

public class Log_in_page extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField login_email;
	private JPasswordField login_password;

	/**
	 * Create the panel.
	 */
	public Log_in_page(JPanel mainPanel, CardLayout cardLayout) {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Please log in to continue");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 32));
		lblNewLabel.setBounds(10, 178, 820, 43);
		add(lblNewLabel);
		
		login_email = new JTextField();
		login_email.setBounds(220, 276, 400, 40);
		add(login_email);
		login_email.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Email");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1.setLabelFor(login_email);
		lblNewLabel_1.setBounds(220, 249, 47, 27);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password");
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(220, 327, 82, 27);
		add(lblNewLabel_1_1);
		
		login_password = new JPasswordField();
		login_password.setBounds(220, 354, 400, 40);
		add(login_password);
		
		JButton logIn = new JButton("Log in");
		logIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Send data to server
				Account newAccount = new Account(login_email.getText(), login_password.getText());
				if(newAccount.Authenticate()) {
					
				}
			}
		});
		logIn.setForeground(new Color(85, 169, 85));
		logIn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		logIn.setBounds(220, 423, 400, 40);
		add(logIn);
		
		JButton signUp = new JButton("Sign Up");
		signUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(mainPanel, "Sign Up");
			}
		});
		signUp.setForeground(new Color(85, 169, 85));
		signUp.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		signUp.setBounds(220, 474, 400, 40);
		add(signUp);

	}
}
