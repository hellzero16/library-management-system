package library;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddBookPage extends JFrame {
    JTextField title, author;
    JButton save;

    public AddBookPage() {
        setTitle("Add New Book");
        setSize(400, 250);
        setLayout(new GridLayout(3, 2, 10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // closes only this window, not entire app

        // === UI Elements ===
        add(new JLabel("Book Title:"));
        title = new JTextField();
        add(title);

        add(new JLabel("Author:"));
        author = new JTextField();
        add(author);

        save = new JButton("Save Book");
        add(new JLabel()); // filler cell
        add(save);

        // === Button Action ===
        save.addActionListener(e -> saveBook());
    }

    // === Save book method ===
    void saveBook() {
        String bookTitle = title.getText().trim();
        String bookAuthor = author.getText().trim();

        // --- Validation ---
        if (bookTitle.isEmpty() || bookAuthor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Please fill in both fields!", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = DatabaseConnection.getConnection()) {

            // --- Create table if not exists (with proper types and sizes) ---
            PreparedStatement ps = con.prepareStatement(
                "CREATE TABLE IF NOT EXISTS books (" +
                "id IDENTITY PRIMARY KEY, " +
                "title VARCHAR(255), " +
                "author VARCHAR(255))"
            );
            ps.execute();

            // --- Insert book record ---
            ps = con.prepareStatement("INSERT INTO books(title, author) VALUES(?, ?)");
            ps.setString(1, bookTitle);
            ps.setString(2, bookAuthor);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // --- Reset fields ---
            title.setText("");
            author.setText("");

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "❌ Error adding book: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- To test this page standalone ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddBookPage().setVisible(true));
    }
}
