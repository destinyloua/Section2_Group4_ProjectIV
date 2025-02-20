package test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ImageUploader extends JFrame {
    private JLabel imageLabel;
    private String imagePath; // Stores the selected image path

    public ImageUploader() {
        setTitle("Image Uploader");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        
        JButton uploadButton = new JButton("Upload Image");
        JButton loadButton = new JButton("Load Image");

        buttonPanel.add(uploadButton);
        buttonPanel.add(loadButton);
        
        // Image display area
        imageLabel = new JLabel("No Image Uploaded", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(300, 300));

        add(buttonPanel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);

        // Upload button action
        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int option = fileChooser.showOpenDialog(null);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    imagePath = selectedFile.getAbsolutePath(); // Store image path
                    JOptionPane.showMessageDialog(null, "Image Selected: " + imagePath);
                }
            }
        });

        // Load button action
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (imagePath != null) {
                    ImageIcon icon = new ImageIcon(imagePath);
                    Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(img));
                    imageLabel.setText(""); // Remove text after loading image
                } else {
                    JOptionPane.showMessageDialog(null, "Please upload an image first.");
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ImageUploader());
    }
}
