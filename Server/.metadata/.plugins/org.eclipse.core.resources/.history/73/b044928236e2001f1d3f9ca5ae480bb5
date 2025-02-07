package Main;
import java.nio.*;
public class Message extends SocketHandler {
	private int length;
	private String message;
	
	private byte[] rawData;
	
	public Message(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		this.length = buffer.getInt();
		byte[] byteBuffer = new byte[length];
		buffer.get(byteBuffer);
		this.message = new String(byteBuffer);
	}
	
	public void SerializeData(String message) {
        byte[] length = ByteBuffer.allocate(4).putInt( message.length()).array();
        byte[] stringBytes = message.getBytes();        
        byte[] byteArray = new byte[4 + stringBytes.length];
        System.arraycopy(length, 0, byteArray, 0, 4);
        System.arraycopy(stringBytes, 0, byteArray, 4, stringBytes.length);
	}
	
	public byte[] GetRawData() {
		return rawData;
	}
	
	public String GetMessage() {
		return message;
	}
	
}
