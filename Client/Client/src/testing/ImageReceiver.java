package testing;

import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import back_end.SocketHandler;

public class ImageReceiver {
	
	public void Receive(String savedFile) {
	    try {
	        // Get image size
	        byte[] receivedImgSize = SocketHandler.ReceiveData();
	        ByteBuffer read = ByteBuffer.wrap(receivedImgSize);
	        int imageSize = read.getInt();
	        System.out.println("Image received: " + imageSize + " bytes");

	        int chunkSize = 32768;
	        int dataSize = chunkSize - 4;
	        int totalChunk = (imageSize + dataSize - 1) / dataSize; // Correct chunk calculation

	        byte[] imgData = new byte[imageSize];

	        for (int i = 0; i < totalChunk; i++) {
	            byte[] receivedChunk = SocketHandler.ReceiveData();
	            read = ByteBuffer.wrap(receivedChunk);
	            int chunkNumber = read.getInt(); // Read the chunk number

	            System.out.println("Chunk #" + chunkNumber + ": " + receivedChunk.length + " bytes");

	            // Corrected index calculation
	            int start = chunkNumber * dataSize;
	            int length = Math.min(receivedChunk.length - 4, imageSize - start); // Prevent overflow

	            System.arraycopy(receivedChunk, 4, imgData, start, length);
	            System.out.println(length + " bytes copied into imgData");
	        }

	        // Save received image
	        Path imagePath = Paths.get(savedFile);
	        Files.write(imagePath, imgData);
	        System.out.println("Image is saved at " + savedFile);
	    } catch (Exception e) {
	        e.printStackTrace(); // Print error properly
	    }
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SocketHandler.MakeConnection(27000);
		ImageReceiver receiver = new ImageReceiver();
		receiver.Receive("images/Test.jpg");
	}

}
