package testing;

import java.awt.CardLayout;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import back_end.SocketHandler;
import leaf_laugh_love.Cart_page;
import objects.Order;

public class ViewCart {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SocketHandler s = new SocketHandler(); 
		JPanel mainPanel = new JPanel(); 
		CardLayout cardLayout = new CardLayout();
		mainPanel.setLayout(cardLayout);
		
		Vector<Integer> plants = new Vector<Integer>();
		plants.add(1);
		plants.add(2);
		plants.add(3);
		
		Vector<Integer> qtys = new Vector<Integer>();
		qtys.add(1);
		qtys.add(1);
		qtys.add(1);

		Order o = new Order(1, 1, 0, 1, plants, qtys);
		
		Cart_page c = new Cart_page(mainPanel, cardLayout, o);
		
		JFrame f = new JFrame("Cart"); 
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(600, 400);
		f.add(c);
		f.setVisible(true);
	}

}
