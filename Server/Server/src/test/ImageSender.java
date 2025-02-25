package test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;

import back_end.SocketHandler;
import objects.Packet;

public class ImageSender {
	private Packet packet = new Packet();
	
	public void Send(String filePath) {
		try {
			byte[] imgData = Files.readAllBytes(new File(filePath).toPath());
			int totalSize = imgData.length;
			int chunkSize = 32768;
			int dataSize = chunkSize -4;
			int totalChunk = (int) Math.ceil((double) totalSize/dataSize);
			
			//Send size
			SocketHandler.SendData(ByteBuffer.allocate(4).putInt(totalSize).array());
			System.out.println("Size sent: "+totalSize +" bytes");
			
			for(int i=0; i<totalChunk;i++) {
				int start = i*dataSize;
				int end = Math.min((start+dataSize), totalSize);
				int sentChunkSize = end - start; //For last chunk (data less than chunk size case)
				
				byte[] chunk = new byte[chunkSize];
				
				//Copy chunk number
				System.arraycopy(ByteBuffer.allocate(4).putInt(i).array(),0, chunk, 0, 4);
				
				//Copy img data to chunk;
				System.arraycopy(imgData, start, chunk, 4, sentChunkSize);
				
				//Send chunk
				SocketHandler.SendData(chunk);
				System.out.println("Chunk " + i+": " +sentChunkSize+" bytes sent");
			}
			System.out.println("Image " + filePath + "is sent: " + totalSize + " bytes sent");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SocketHandler.MakeConnection(27000);
		ImageSender sender = new ImageSender();
		sender.Send("images/aloe_vera.jpg");
	}

}
