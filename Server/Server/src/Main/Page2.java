package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Page2 extends JPanel {
    public Page2(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());

        // Add components to Page 2
        JLabel label = new JLabel("This is Page 2");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        // Add button to go back to Page 1
        JButton btnBack = new JButton("Go Back to Page 1");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Page 1");
            }
        });
        add(btnBack, BorderLayout.SOUTH);

        // Add button to go to Page 3
        JButton btnNext = new JButton("Go to Page 3");
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Page 3");
            }
        });
        add(btnNext, BorderLayout.NORTH);
    }
}
