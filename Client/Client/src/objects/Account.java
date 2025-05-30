package objects;

import java.nio.*;

import back_end.Objects;
import back_end.SocketHandler;

public class Account implements Objects{
	private int id;
	private String fName;
	private String lName;
	private String email;
	private int password;
	private byte[] data;
	
	public Account() {

	}
	
	public Account(String fName, String lName, String email, String password) {
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.password = password.hashCode();
	}
	
	public Account(String email, String password) {
		this.fName = "Null";
		this.lName = "Null";
		this.email = email;
		this.password = password.hashCode();
	}
	
	public Account(byte[] data) {
		ByteBuffer read = ByteBuffer.wrap(data);
		this.id = read.getInt();
		this.password = read.getInt();
		byte[] buffer = new byte[read.remaining()];
		read.get(buffer);
	    String combined = new String(buffer);
	    String[] splitArray = combined.split("#", 3);
	    this.fName = splitArray[0];
	    this.lName = splitArray[1];
	    this.email = splitArray[2];
	}
	
	public int GetId() {
		return id;
	}
	
	public void SetId(int id) {
		this.id=id;
	}
	
	public void SetAccount(String fName, String lName, String email, String password) {
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.password = password.hashCode();
	}
	
	public void UpdateAccount(String fName, String lName, String email, String password) {
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.password = password.hashCode();
	}
	public void UpdateAccount(String fName, String lName, String email) {
		this.fName = fName;
		this.lName = lName;
		this.email = email;
	}
	
	public void SetAccount(String fName, String lName, String email, int password) {
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.password = password;
	}
	
	public void SetAccount(String email, String password) {
		this.fName = "Null";
		this.lName = "Null";
		this.email = email;
		this.password = password.hashCode();
	}
	
//	public Boolean Authenticate() {
//		SocketHandler.SendData(Serialize());
//		byte[] receivedData = SocketHandler.ReceiveData();
//		ByteBuffer read = ByteBuffer.wrap(receivedData);
//		int response;
//		response = read.getInt();
//		if(response == 1) {
//			return true;
//		}
//		else {
//			return false;
//		}
//	}
	
	public String GetFName() {
		return fName;
	}
	
	public String GetLName() {
		return lName;
	}
	
	public String GetEmail() {
		return email;
	}
	
	public int GetPassword() {
		return password;
	}
	
	public Integer id() {
		return id;
	}
	
	public byte[] Serialize() {
		byte[] idByte = ByteBuffer.allocate(4).putInt(id).array();
		byte[] passwordByte = ByteBuffer.allocate(4).putInt(password).array();
		String combined = fName + "#" + lName + "#" + email;
		this.data = new byte[8+combined.length()];
		System.arraycopy(idByte, 0, data, 0, 4);
		System.arraycopy(passwordByte, 0, data, 4, 4);
		System.arraycopy(combined.getBytes(), 0, data, 8, combined.length());
		return data;
	}
	
	
}
