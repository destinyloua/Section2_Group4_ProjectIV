package test;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class SaveImage {
    public static void main(String[] args) {
        try {
        	// Load the image
            File inputFile = new File("image.jpg"); // Change this to your image path
            BufferedImage originalImage = ImageIO.read(inputFile);

            // Resize the image
            int newWidth = 100, newHeight = 100;
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
            g2d.dispose(); // Release resources

            // Save the resized image
            File outputFile = new File("resized_image.jpg");
            ImageIO.write(resizedImage, "jpg", outputFile); // Change "png" to "jpg" if needed

            System.out.println("Image resized and saved successfully!" + inputFile.getName());

            // Display in a JFrame
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Resized Image");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(300, 300);
                frame.setLayout(new FlowLayout());

                // Display resized image in JLabel
                ImageIcon resizedIcon = new ImageIcon(resizedImage);
                JLabel imageLabel = new JLabel(resizedIcon);
                frame.add(imageLabel);

                frame.setVisible(true);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
