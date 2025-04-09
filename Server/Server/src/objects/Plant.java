package objects;

import java.nio.*;

public class Plant {
	private int id;
	private float price;
	private int quantity;
	private String name;
	private String imagePath;
	private byte[] data;
	
	public Plant() {
		
	}
	
	public Plant(int id, String name, float price, int quantity, String imagePath) {
		this.id = id;
		this.price = price;
		this.quantity = quantity;
		this.name = name;
		this.imagePath = imagePath;
	}
	
	public Plant(int id, String name, float price, int quantity) {
		this.id = id;
		this.price = price;
		this.quantity = quantity;
<<<<<<< HEAD
		this.imagePath = name.toLowerCase().replace(" ", "_") + "."+ imgFormat;
=======
		this.name = name;
		this.imagePath = "null";
>>>>>>> parent of e694fdc (Update)
	}
	
	public Plant(byte[] data) {
		ByteBuffer read = ByteBuffer.wrap(data);
		this.id = read.getInt();
		this.price = read.getFloat();
		this.quantity = read.getInt();
		byte[] buffer = new byte[read.remaining()];
		read.get(buffer);
		this.name = new String(buffer);
	}
	
	public void SetImage(String path) {
		this.imagePath = path;
	}
	
	public int GetId() {
		return id;
	}
	
	public void SetQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String GetName() {
		return name;
	}
	
	public float GetPrice() {
		return price;
	}
	
	public int GetQuantity() {
		return quantity;
	}
	
	public String GetImagePath() {
		return imagePath;
	}
	
	public byte[] Serialize() {
		int size = name.length()+(4*3);
		ByteBuffer read = ByteBuffer.allocate(size);
		read.putInt(id);
		read.putFloat(price);
		read.putInt(quantity);
		this.data = new byte[size];
		this.data = read.array();
		System.arraycopy(name.getBytes(), 0, data, 12, name.length());
		return data;
	}
	
	public void Display() {
		System.out.println("ID: " +id);
		System.out.println("Price: " +price);
		System.out.println("Quantity: " +quantity);
		System.out.println("Name: " +name);
	}
}
