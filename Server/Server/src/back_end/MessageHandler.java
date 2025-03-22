package back_end;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import objects.Message;
import objects.Packet;

public class MessageHandler{	

	public static String ReceiveMessage() {
		byte[] data = SocketHandler.ReceiveData();
		System.out.println("New message");
		ByteBuffer read = ByteBuffer.wrap(data);
		int object = read.getInt();
		int action = read.getInt();
		//Termination
		if(action == -1) {
			return "\0";
		}
		
		//Message received
		else if(action == 2) {
			byte[] messageBytes = new byte[read.remaining()];
			read.get(messageBytes);
			Message m = new Message(messageBytes);
			return m.GetMessage();
		}
		return null;
	}
}
