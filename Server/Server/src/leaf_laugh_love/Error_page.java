package leaf_laugh_love;

import javax.swing.JPanel;

import back_end.*;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Error_page extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Error_page(JPanel mainPanel, CardLayout cardLayout) {
		setLayout(null);
		
		if(DatabaseHandler.CheckConnection()) {
			JLabel lblNewLabel = new JLabel("Oops, something is wrong :(");
			lblNewLabel.setBounds(208, 298, 443, 44);
			lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
			add(lblNewLabel);
			JButton btnNewButton = new JButton("Back to dashboard");
			btnNewButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					cardLayout.show(mainPanel, "Dashboard");
				}
			});
			btnNewButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			btnNewButton.setForeground(new Color(85, 169, 85));
			btnNewButton.setBounds(220, 361, 400, 40);
			add(btnNewButton);
		}
		else if(!DatabaseHandler.CheckConnection()) {
			//Log unsuccessful connection to the database
			FileHandler.SaveLog("Client failed to connect to database");
			JLabel connectionFailedLabel = new JLabel("Can not connect to the database right now :(");
			connectionFailedLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
			connectionFailedLabel.setForeground(Color.RED);
			connectionFailedLabel.setBounds(84, 298, 672, 43);
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
