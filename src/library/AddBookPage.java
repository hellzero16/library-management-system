package library;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddBookPage extends JFrame {
    private JTextField nameField, publisherField, priceField, yearField;

    public AddBookPage() {
        setTitle("Add New Book");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));
        setResizable(false);

        add(new JLabel("Book Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Publisher:"));
        publisherField = new JTextField();
        add(publisherField);

        add(new JLabel("Price:"));
        priceField = new JTextField();
        add(priceField);

        add(new JLabel("Year:"));
        yearField = new JTextField();
        add(yearField);

        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        saveBtn.addActionListener(e -> saveBook());
        cancelBtn.addActionListener(e -> dispose());
        add(saveBtn);
        add(cancelBtn);
    }

    
    private void showToast(String message) {
    JDialog dialog = new JDialog();
    dialog.setUndecorated(true);

    JLabel label = new JLabel(message, SwingConstants.CENTER);
    label.setOpaque(true);
    label.setBackground(new Color(46, 204, 113)); 
    label.setForeground(Color.WHITE);
    label.setFont(new Font("Segoe UI", Font.BOLD, 15));
    label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            BorderFactory.createEmptyBorder(15, 25, 15, 25)
    ));

    dialog.add(label);
    dialog.pack();
    dialog.setLocationRelativeTo(null);

    Timer timer = new Timer(1500, e -> dialog.dispose());
    timer.setRepeats(false);
    timer.start();

    dialog.setVisible(true);
}

    private void saveBook() {
        String name = nameField.getText().trim();
        String publisher = publisherField.getText().trim();
        String priceText = priceField.getText().trim();
        String year = yearField.getText().trim();

        if (name.isEmpty() || publisher.isEmpty() || priceText.isEmpty() || year.isEmpty()) {
            showToast("Please fill all fields!");
            return;
        }

        try (Connection con = DatabaseConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO books(name, publisher, price, pub_year) VALUES(?, ?, ?, ?)"
            );

            ps.setString(1, name);
            ps.setString(2, publisher);
            ps.setInt(3, Integer.parseInt(priceText));
            ps.setString(4, year);
            ps.executeUpdate();

            showToast("Book added!");
            dispose();

        } catch (Exception e) {
            showToast("Error: " + e.getMessage());
        }
    }
}
