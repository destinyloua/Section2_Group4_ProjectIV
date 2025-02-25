package objects;

import back_end.Objects;

public class Message implements Objects{
	private int size;
	private String message;
	private byte[] data;
	
	public byte[] Serialize() {
		
		return data;
	}
}
