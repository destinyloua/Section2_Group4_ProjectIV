package objects;

import java.nio.ByteBuffer;

import back_end.Objects;

public class Packet {
	private byte[] header;
	private byte[] content;
	
	public void SetHeader(Boolean status) {
		if(status) {
			this.header = ByteBuffer.allocate(4).putInt(1).array();
		}
		else {
			this.header = ByteBuffer.allocate(4).putInt(0).array();
		}
	}
	
	public void SetHeader(int size) {
		this.header = ByteBuffer.allocate(4).putInt(size).array();

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
	
	public void SetContent(String message) {
		this.content = message.getBytes();
	}
	
	public void SetContent(int value) {
		this.content = ByteBuffer.allocate(4).putInt(value).array();
	}
	
	public byte[] GetPacket() {
		byte[] packet = new byte[header.length+content.length];
		System.arraycopy(header, 0, packet, 0, header.length);
		System.arraycopy(content, 0, packet, header.length, content.length);
		return packet;
	}
}
