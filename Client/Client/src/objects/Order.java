package objects;

import java.nio.*;
import java.util.*;

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
	
	public Order(byte[] data) {
		ByteBuffer read = ByteBuffer.wrap(data, 0, data.length);
		this.id = read.getInt();
		this.aId = read.getInt();
//		this.plantsQuantity = read.getInt();
		this.totalPrice = read.getFloat();
		this.status = read.getInt();
		while(read.hasRemaining()) {
			pId.add(read.getInt());
			quantity.add(read.getInt());
		}
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
		int size = (pId.size()*4) + (quantity.size()*4)+16;
		ByteBuffer read = ByteBuffer.allocate(size);
		read.putInt(id);
		read.putInt(aId);
//		read.putInt(plantsQuantity);
		read.putFloat(totalPrice);
		read.putInt(status);
		for(int i=0 ; i<pId.size();i++) {
			read.putInt(pId.get(i));
			read.putInt(quantity.get(i));
		}
		this.data = read.array();
		return data;
	} 
}
