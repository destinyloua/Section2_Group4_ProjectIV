package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Page3 extends JPanel {
    public Page3(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());

        // Add components to Page 3
        JLabel label = new JLabel("This is Page 3");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        // Add button to go back to Page 2
        JButton btnBack = new JButton("Go Back to Page 2");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Page 2");
            }
        });
        add(btnBack, BorderLayout.SOUTH);
    }
}
