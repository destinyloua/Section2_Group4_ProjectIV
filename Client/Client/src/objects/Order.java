package objects;

import java.nio.*;
import java.util.*;

public class Order {
	//This is header
	private int id;
	private int aId;
	private float totalPrice;
	private int status;
	//This is body
	private Vector<Integer> pId = new Vector<>();
	private Vector<Integer> quantity = new Vector<>();
	//This is what we send
	private byte[] data;
	
	public Order() {
		
	}
	
	public Order(int id, int aId, float totalPrice, int status, Vector<Integer> pId, Vector<Integer> quantity) {
		this.id = id;
		this.aId = aId;
		this.totalPrice = totalPrice;
		this.status = status;
		this.pId = pId;
		this.quantity = quantity;
	}
	
	public Order(byte[] data) {
		ByteBuffer read = ByteBuffer.wrap(data, 0, data.length);
		read.order(ByteOrder.LITTLE_ENDIAN);
		this.id = read.getInt();
		System.out.println("oId: "+ id);
		this.aId = read.getInt();
		System.out.println("aId: "+ aId);
		this.totalPrice = read.getFloat();
		System.out.println("total: "+ totalPrice);
		this.status = read.getInt();
		System.out.println("Status: "+ status);
		while(read.hasRemaining()) {
			int pid = read.getInt();
			int qty = read.getInt();
			System.out.println("PlantID: "+ pid);
			System.out.println("Quantity: "+ qty);
			pId.add(pid);
			quantity.add(qty);
		}
	}
	
	public void SetQuantity(int pId, int quantity) {
		this.quantity.set(this.pId.indexOf(pId), this.quantity.get(this.pId.indexOf(pId)) + quantity);
	}
	
	public void SetAccountAssociated(Account a) {
		this.aId = a.GetId();
	}
	public void SetTotalPrice(float total) {
		this.totalPrice = total;
	}
	
	public void AddPlant(int pId, int quantity) {
		if(this.pId.indexOf(pId) != -1) {
			SetQuantity(pId, quantity);
		}
		else {
			this.pId.add(pId);
			this.quantity.add(quantity);
		}
	}
	
	public void ClearCart() {
		this.pId.clear();
		this.quantity.clear();
	}
	
	public int GetAId() {
		return aId;
	}
	
	public int GetId() {
		return id;
	}
	
	public Vector<Integer> GetPId() {
		return pId;
	}
	
	public Vector<Integer> GetQuantity() {
		return quantity;
	}
	
	public float GetTotalPrice() {
		return totalPrice; 
	}
	

	public byte[] Serialize() {
//		int size = 4 + 4 + 4 + 4 + (pId.size() * 4) + (quantity.size() * 4); 
//		ByteBuffer read = ByteBuffer.allocate(size);
//		read.order(ByteOrder.LITTLE_ENDIAN);
//		read.putInt(id);
//		read.putInt(aId);
//		read.putFloat(totalPrice);
//		read.putInt(status);
//		for(int i=0 ; i<pId.size();i++) {
//			read.putInt(pId.get(i));
//			read.putInt(quantity.get(i));
//		}
//		this.data = read.array();
//		return data;
		
	    int size = 4 + 4 + 4 + 4 + (pId.size() * 4) + (quantity.size() * 4);
	    ByteBuffer read = ByteBuffer.allocate(size);
	    read.order(ByteOrder.LITTLE_ENDIAN);
	    read.putInt(id);
	    read.putInt(aId);
	    read.putFloat(totalPrice);
	    read.putInt(status);

	    for (int i = 0; i < pId.size(); i++) {
	        read.putInt(pId.get(i));
	        read.putInt(quantity.get(i));
	    }

	    this.data = read.array();
	    return data;
	} 
}
