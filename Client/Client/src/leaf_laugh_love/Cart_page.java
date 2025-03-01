package leaf_laugh_love;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;

import objects.Order;
import objects.CartItem; 

public class Cart_page extends JPanel {

	private static final long serialVersionUID = 1L;
	private Order o;
	private Vector<CartItem> cartItems; 
	private JLabel cartTotal; 
	private JPanel cart; 
	private JPanel mainPanel; 
	private CardLayout cardLayout; 
	
	
	// cart page setup 
	// construct + initialize page 
	public Cart_page(JPanel mainPanel, CardLayout cardLayout, Order o) {
		this.mainPanel = mainPanel; 
		this.cardLayout = cardLayout; 
		this.o = o; 
		this.cartItems = new Vector<>(); 
		
		setLayout(new BorderLayout()); 
		cart = new JPanel(); 
		cart.setLayout(new GridLayout(o.GetPId().size() + 1, 4, 10, 10));
		
		initCart(); 
	}
	 
	private void initCart() {
		addHeaders(); 
		createCartItems(); 
		add(new JScrollPane(cart), BorderLayout.CENTER);
		displayCartTotal(); 
		addCartButtons(); 
	}
	
	
	// helper functions for retrieving information
	
	private void updateTotal(float price) {
		cartTotal.setText("Cart Total: $" + price);
	}

	
	// button creation 
	// template for creating buttons 
	private JButton createBtn(String text, ActionListener a) {
		JButton btn = new JButton(text);
		btn.addActionListener(a);
		return btn; 
	}
	
	// add buttons to cart: place order, continue shopping, remove an item 
	private void addCartButtons() {
		JPanel cartBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JButton placeOrder = createBtn("Place Order", this::handlePlaceOrder); 
		JButton continueShopping = createBtn("Continue Shopping", this::handleContinueShopping);
		JButton removeItem = createBtn("Remove", this::handleRemoveItem); 
		
		cartBtns.add(placeOrder);
		cartBtns.add(continueShopping);
		cartBtns.add(removeItem);
		
		add(cartBtns, BorderLayout.SOUTH);
	}
	
	// button handlers 
	// place order --> order confirmation 
	private void handlePlaceOrder(ActionEvent e) {
		
	}
	
	// continue shopping --> back to home page
	private void handleContinueShopping(ActionEvent e) {
		cardLayout.show(mainPanel,  "Home");
	}
	
	// remove item --> delete from cart, update total
	private void handleRemoveItem(ActionEvent e) {
		
	}

	
	// item row creation 
	// headers for displaying item information 
	public void addHeaders() {
		cart.add(new JLabel("Plant Name:"));
		cart.add(new JLabel("Price:"));
		cart.add(new JLabel("Qty:"));
		cart.add(new JLabel("Subtotal:"));

	}
	
	// create a row for each item in the cart  
	public void createCartItems() {
		float total = 0; 
		Vector<Integer> pIds = o.GetPId();
		Vector<Integer> qtys = o.GetQuantity();
		
		for(int i = 0; i < pIds.size(); i++) {
			CartItem item = new CartItem(pIds.get(i), qtys.get(i));
			cartItems.add(item);
			cart.add(item.createItemRow());
			total += item.getSubtotal();
		}
		
		updateTotal(total);
	}
	
	// bottom of cart page 
	// display cart total 
	private void displayCartTotal() {
		cartTotal = new JLabel("Cart Total: $0.00", SwingConstants.RIGHT); 
		cartTotal.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		add(cartTotal, BorderLayout.SOUTH); 
	}
	
	
	
}
