package objects;

import java.nio.ByteBuffer;

import back_end.Objects;

public class Packet {
	private byte[] header;
	private byte[] content;
	
	public void SetHeader(int object, int action) {
		header = new byte[8];
		System.arraycopy(ByteBuffer.allocate(4).putInt(object).array(),0 , this.header, 0, 4);
		System.arraycopy(ByteBuffer.allocate(4).putInt(action).array(),0 , this.header, 4, 4);
	}
	
	public void SetHeader(int size) {
		header = new byte[4];
		System.arraycopy(ByteBuffer.allocate(4).putInt(size).array(),0 , this.header, 0, 4);
	}
	
	public void SetContent(Objects object) {
		this.content = object.Serialize();
	}
	
	public void SetContent(Boolean content) {
		if(content) {
			this.content = ByteBuffer.allocate(4).putInt(1).array();
		}
		else {
			this.content = ByteBuffer.allocate(4).putInt(0).array();
		}
	}
	
	public void SetContent(int value) {
		this.content = ByteBuffer.allocate(4).putInt(value).array();
	}
	
	public void SetContent(String message) {
		this.content = new byte[message.length()];
		this.content = message.getBytes();
	}
	
	public byte[] GetPacket() {
		byte[] packet = new byte[header.length+content.length];
		System.arraycopy(header, 0, packet, 0, header.length);
		System.arraycopy(content, 0, packet, header.length, content.length);
		return packet;
	}
}
