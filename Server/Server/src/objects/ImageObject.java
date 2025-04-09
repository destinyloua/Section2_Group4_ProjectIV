package objects;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageObject {
	private String fileName;
	private String filePath;
	private Image imageData;
	private ImageIcon icon;
	private byte[] imageByteData;
	
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
            this.imageByteData = baos.toByteArray();
            return imageByteData;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
