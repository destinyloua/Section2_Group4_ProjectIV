package leaf_laugh_love;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import back_end.SocketHandler;
import objects.Account;
import objects.Order;

public class Account_Already_Exists_page extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Account_Already_Exists_page(JPanel mainPanel, CardLayout cardLayout, Account a, Order o) {
		setLayout(null);
		
		if(SocketHandler.CheckConnection()) {
			JLabel lblNewLabel = new JLabel("This account already exists!");
			lblNewLabel.setBounds(208, 298, 443, 44);
			lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
			add(lblNewLabel);
			JButton btnNewButton = new JButton("Back to log in");
			btnNewButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					mainPanel.removeAll();
					mainPanel.add(new Log_in_page(mainPanel, cardLayout, a, o), "Log in");
					mainPanel.repaint();
					mainPanel.revalidate();
					cardLayout.show(mainPanel, "Log in");
				}
			});
			btnNewButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			btnNewButton.setForeground(new Color(85, 169, 85));
			btnNewButton.setBounds(220, 361, 400, 40);
			add(btnNewButton);
		}
		else if(!SocketHandler.CheckConnection()) {
			JLabel connectionFailedLabel = new JLabel("Can not connect to the server right now :(");
			connectionFailedLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
			connectionFailedLabel.setForeground(Color.RED);
			connectionFailedLabel.setBounds(105, 298, 630, 43);
			add(connectionFailedLabel);
			JButton closeButton = new JButton("Close application");
			closeButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					System.exit(1);
				}
			});
			closeButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			closeButton.setForeground(Color.red);
			closeButton.setBounds(220, 361, 400, 40);
			add(closeButton);
		}
	}
}
