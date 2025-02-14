package Main;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Log_in extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField login_email;
	private JPasswordField login_password;

	/**
	 * Create the panel.
	 */
	public Log_in(JPanel mainPanel, CardLayout cardLayout) {
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
				Database db = new Database();
				String dbUrl = "jdbc:mysql://localhost:3306/project4"; // Replace with your DB info
	            String dbUser = "root"; // Replace with your DB username
	            String dbPassword = "Nam@326389"; // Replace with your DB password
	            
	            String email = login_email.getText();
	            String password = Integer.toString(login_password.getText().hashCode());
				db.SetUpConnection(dbUrl, dbUser, dbPassword);
				db.ConnectDatabase();
				Boolean auth = db.AuthenticateLogin(email, password);
				if(auth) {
					System.out.println("Accept");
				}
				else {
					System.out.println("Deny");
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
