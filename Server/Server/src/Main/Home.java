package Main;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.CardLayout;
import java.util.*;

import javax.swing.JLabel;

public class Home extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Home(JPanel mainPanel, CardLayout cardLayout, Vector<Plant> plants) {
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);

	}

}
