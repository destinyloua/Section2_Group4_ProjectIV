package objects;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageObject{
	private File file;
	private Image imageData;
	private ImageIcon icon;
	private byte[] data;
	
	public ImageObject(byte[] data) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            BufferedImage bufferedImage = ImageIO.read(bais);
            this.icon = new ImageIcon(bufferedImage);
            this.imageData = icon.getImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public ImageObject(String filePath) {
		this.icon = new ImageIcon(filePath);
		this.imageData = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		this.icon = new ImageIcon(imageData);
	}
	
	public void SetFileName(String filePath) {
		this.file = new File(filePath);
	}
	
	public Boolean ResizeImage(int w, int h) {
		try {
			this.imageData = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
			return true;
		}
		catch (Exception e){
			System.out.println("Error: " + e.getMessage());
			return false;
		}
	}
	
	public Boolean SaveImage(String newName) {
		try {
			// Load the image
            File inputFile = new File("image.jpg"); // Change this to your image path
            BufferedImage imageBuffer = ImageIO.read(inputFile);

            // Save the resized image
            File outputFile = new File(newName);
            ImageIO.write(imageBuffer, "jpg", outputFile); // Change "png" to "jpg" if needed
            return true;
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		}
	}
	
	public byte[] Serialize() {
        // Convert ImageIcon to Image
        this.imageData = icon.getImage();

        // Create a BufferedImage with transparency support
        BufferedImage bufferedImage = new BufferedImage(imageData.getWidth(null), imageData.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the Image into the BufferedImage
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(imageData, 0, 0, null);
        g2d.dispose();

        // Convert BufferedImage to byte array
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "jpg", baos);
            this.data = baos.toByteArray();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}
}
