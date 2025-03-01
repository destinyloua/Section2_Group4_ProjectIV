package objects;

import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

import back_end.ResponseHandler;

public class CartItem {

	private Plant p; 
	private int qty; 
	private float subtotal; 
	
	public CartItem(int pId, int qty) {
		this.p = getPlantById(pId); 
		this.qty = qty; 
		this.subtotal = p.GetPrice() * qty; 
	}
	
	public Plant getPlantById(int id) {
		Vector<Plant> plantList = ResponseHandler.GetPlantsList();
		return plantList.stream().filter(plant -> plant.GetId() == id).findFirst().orElse(null);
	}
	public Plant getPlant() {
		return p; 
	}
	
	public int getQty() {
		return qty; 
	}
	
	public float getSubtotal() {
		return subtotal; 
	}
	
	public JPanel createItemRow() {
		JPanel row = new JPanel(); 
		row.setLayout(new GridLayout(1, 4));
		
		if(p == null) {
			row.add(new JLabel(""));
			row.add(new JLabel(""));
			row.add(new JLabel(""));
			row.add(new JLabel(""));
		}
		
		row.add(new JLabel(p.GetName()));
		row.add(new JLabel("$" + p.GetPrice()));
		row.add(new JLabel(String.valueOf(qty)));
		row.add(new JLabel("$" + subtotal));
		
		return row; 

	}
}
