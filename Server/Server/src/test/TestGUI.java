package test;
//Adding more text when button clicked

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;

public class TestGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestGUI frame = new TestGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		int y = 0;
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(225, 63, 89, 23);
		btnNewButton.addActionListener(e->{
			JLabel label = new JLabel("<html>This is a very long sentence that will automatically break into multiple lines.</html>");
			contentPane.setLayout(null);
			
			JTextArea textArea = new JTextArea("This is a long sentence that will wrap automatically.");
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			textArea.setEditable(false);
			textArea.setOpaque(false);
			textArea.setBounds(0, y*textArea.getPreferredSize().height, textArea.getPreferredSize().width, textArea.getPreferredSize().height);
			
			Dimension d = textArea.getPreferredSize();
			System.out.println(d.height);
			
			contentPane.add(textArea);
			contentPane.revalidate();
			contentPane.repaint();
			y++;
		});
		contentPane.add(btnNewButton);
	}

}
