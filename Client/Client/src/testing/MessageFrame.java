package testing;

import javax.swing.*;
import java.awt.*;

public class MessageFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public MessageFrame(String message) {
        setTitle("Message Window");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Title label (Static)
        JLabel titleLabel = new JLabel("Number List", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Scrollable content panel (list of JPanels)
        JPanel scrollablePanel = new JPanel();
        scrollablePanel.setLayout(new BoxLayout(scrollablePanel, BoxLayout.Y_AXIS));

        // Add numbered panels to the scrollable area
        for (int i = 1; i <= 20; i++) { // You can change the range
            JPanel numberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            numberPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Optional border
            numberPanel.setPreferredSize(new Dimension(350, 40)); // Set panel height

            JLabel numberLabel = new JLabel("Number: " + i);
            numberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            numberPanel.add(numberLabel);
            scrollablePanel.add(numberPanel);
        }

        // Make the scrollablePanel scrollable
        JScrollPane scrollPane = new JScrollPane(scrollablePanel);
        scrollPane.setPreferredSize(new Dimension(350, 150)); // Adjust height of the scrollable zone
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add scrollable area to the center
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Static close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        mainPanel.add(closeButton, BorderLayout.SOUTH);

        add(mainPanel);
    }
}
