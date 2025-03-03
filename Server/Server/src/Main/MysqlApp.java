package Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;

public class MysqlApp extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JScrollPane scrollPane;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MysqlApp frame = new MysqlApp();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MysqlApp() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Add a JTable to display the user data
        table = new JTable();
        scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Load data when the window is opened
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                connectToDatabase();
                loadData();
            }
        });
    }

    // Connect to the MySQL database
    public void connectToDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/project4"; // Replace with your DB info
            String user = "root"; // Replace with your DB username
            String password = "$quillyChonk4bear!"; // Replace with your DB password
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch users from MySQL and display them in JTable
    public void loadData() {
        try {
            String query = "SELECT * FROM users";
            resultSet = statement.executeQuery(query);

            // Get metadata of the result set
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Create column names for the table
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }

            // Fetch all data rows
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                model.addRow(row);
            }

            // Set the table model
            table.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    // Close the database connection
    public void closeConnection() {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
