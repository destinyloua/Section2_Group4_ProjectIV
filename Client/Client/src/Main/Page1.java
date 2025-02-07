package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Page1 extends JPanel {
    public Page1(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());

        // Add components to Page 1
        JLabel label = new JLabel("This is Page 1");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        // Add button to go to Page 2
        JButton btnNext = new JButton("Go to Page 2");
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Page 2");
            }
        });
        add(btnNext, BorderLayout.SOUTH);
    }
}
