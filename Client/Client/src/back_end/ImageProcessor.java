package back_end;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class ImageProcessor {
	private static String filePath;
	
	public static ImageIcon Resize(String filePath, int w, int h) {
		ImageIcon icon = new ImageIcon(filePath);
		Image data = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
		icon = new ImageIcon(data);
		return icon;
	}
}
