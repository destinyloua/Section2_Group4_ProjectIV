package back_end;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;

public class ImageProcessor {
	private static String filePath;
	
	public static ImageIcon Resize(String filePath, int w, int h) {
		ImageIcon icon = new ImageIcon(filePath);
		Image data = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
		icon = new ImageIcon(data);
		return icon;
	}
	
	public static String GetFileExtension(File file) {
        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf(".");
        
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return ""; // No extension found
    }
	
	public static Boolean CopyFile(String source, String destination) {
		File sourceFile = new File(source);
        File destinationFile = new File(destination);

        try (FileInputStream fis = new FileInputStream(sourceFile);
             FileOutputStream fos = new FileOutputStream(destinationFile)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            System.out.println("File copied successfully!");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
	}
}
