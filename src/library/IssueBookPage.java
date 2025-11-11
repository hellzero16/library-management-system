package library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class IssueBookPage extends JFrame {
    private JTextField bookIdField, studentIdField, issueDateField;
    private JButton issueBtn;

    public IssueBookPage() {
        setTitle("Issue Book");
        setSize(400, 300);
        setLayout(new GridLayout(4, 2, 10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(new JLabel("Book ID:"));
        bookIdField = new JTextField();
        add(bookIdField);

        add(new JLabel("Student ID:"));
        studentIdField = new JTextField();
        add(studentIdField);

        add(new JLabel("Issue Date (YYYY-MM-DD):"));
        issueDateField = new JTextField();
        add(issueDateField);

        issueBtn = new JButton("Issue Book");
        add(new JLabel());
        add(issueBtn);

        issueBtn.addActionListener(e -> issueBook());
    }

    private void issueBook() {
        String bookId = bookIdField.getText();
        String studentId = studentIdField.getText();
        String issueDate = issueDateField.getText();

        if (bookId.isEmpty() || studentId.isEmpty() || issueDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        try (Connection con = DatabaseConnection.getConnection()) {
            Statement st = con.createStatement();
            // Create table if not exists
            st.execute("CREATE TABLE IF NOT EXISTS issued_books(" +
                    "id IDENTITY, book_id VARCHAR, student_id VARCHAR, issue_date DATE, return_date DATE)");

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO issued_books(book_id, student_id, issue_date) VALUES (?, ?, ?)");
            ps.setString(1, bookId);
            ps.setString(2, studentId);
            ps.setString(3, issueDate);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Book Issued Successfully!");
            bookIdField.setText("");
            studentIdField.setText("");
            issueDateField.setText("");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error issuing book: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new IssueBookPage().setVisible(true);
    }
}
