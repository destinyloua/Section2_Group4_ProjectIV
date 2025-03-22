package objects;

import java.nio.*;
import java.util.*;

import back_end.FileHandler;
import back_end.Objects;

public class Order implements Objects{
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
	
	public Order(int id, int aId, float totalPrice, int status) {
		this.id = id;
		this.aId = aId;
		this.totalPrice = totalPrice;
		this.status = status;
//		this.pId = null;
//		this.quantity = null;
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
	
	public void SetId(int id) {
		this.id = id;
	}
	
	public void SetQuantity(int pId, int quantity) {
		this.quantity.set(this.pId.indexOf(pId), this.quantity.get(this.pId.indexOf(pId)) + quantity);
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
	
	public void SetItems(Vector<Integer> plants, Vector<Integer> qty) {
		this.pId = plants;
		this.quantity = qty;	
	}
	
	public int GetId() {
		return id;
	}
	
	public int GetAId() {
		return aId;
	}
	
	public int GetStatus() {
		return status;
	}
	
	public float GetTotalPrice() {
		return totalPrice;
	}
	
	public Vector<Integer> GetPId() {
		return pId;
	}
	
	public Vector<Integer> GetQuantity() {
		return quantity;
	}
	

	public byte[] Serialize() {
//		int size = (pId.size()*4) + (quantity.size()*4)+16;
//		ByteBuffer read = ByteBuffer.allocate(size);
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
	    read.order(ByteOrder.LITTLE_ENDIAN); // Ensure byte order consistency

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
	
	//TEST PURPOSE, DELETE AFTER
	public void Display() {
		System.out.println("Order #"+id);
		System.out.println("Account ID: "+aId);
		System.out.println("Total price: $"+totalPrice);
		System.out.println("Status: "+status);
		for(int p : pId) {
			System.out.println(p);
		}
		System.out.println("----------");
	}
	
}
