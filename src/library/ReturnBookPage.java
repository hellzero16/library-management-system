package library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ReturnBookPage extends JFrame {
    private JTextField issueIdField, returnDateField;
    private JButton returnBtn;

    public ReturnBookPage() {
        setTitle("Return Book");
        setSize(400, 250);
        setLayout(new GridLayout(3, 2, 10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(new JLabel("Issue ID:"));
        issueIdField = new JTextField();
        add(issueIdField);

        add(new JLabel("Return Date (YYYY-MM-DD):"));
        returnDateField = new JTextField();
        add(returnDateField);

        returnBtn = new JButton("Return Book");
        add(new JLabel());
        add(returnBtn);

        returnBtn.addActionListener(e -> returnBook());
    }

    private void returnBook() {
        String issueId = issueIdField.getText();
        String returnDate = returnDateField.getText();

        if (issueId.isEmpty() || returnDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE issued_books SET return_date = ? WHERE id = ?");
            ps.setString(1, returnDate);
            ps.setString(2, issueId);
            int updated = ps.executeUpdate();

            if (updated > 0)
                JOptionPane.showMessageDialog(this, "✅ Book Returned Successfully!");
            else
                JOptionPane.showMessageDialog(this, "⚠️ No matching issue ID found.");

            issueIdField.setText("");
            returnDateField.setText("");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error returning book: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ReturnBookPage().setVisible(true);
    }
}
