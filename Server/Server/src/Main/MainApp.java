package Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainApp extends JFrame {

    private JPanel contentPane;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainApp frame = new MainApp();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainApp() {
        setTitle("Multi-page Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Initialize the CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        contentPane.add(mainPanel, BorderLayout.CENTER);

        // Add pages to the CardLayout
        mainPanel.add(new Page1(mainPanel, cardLayout), "Page 1");
        mainPanel.add(new Page2(mainPanel, cardLayout), "Page 2");
        mainPanel.add(new Page3(mainPanel, cardLayout), "Page 3");
    }
}
