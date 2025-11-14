package library;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class IssueBookPage extends JFrame {

    private JTextField bookIdField, studentIdField, issueDateField, dueDateField;

    public IssueBookPage() {
        setTitle("Issue Book");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Book ID:"), gbc);
        gbc.gridx = 1; bookIdField = new JTextField(); add(bookIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1; studentIdField = new JTextField(); add(studentIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Issue Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; issueDateField = new JTextField(); add(issueDateField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Due Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; dueDateField = new JTextField(); add(dueDateField, gbc);

        JButton issue = new JButton("Issue Book");
        gbc.gridx = 1; gbc.gridy = 4; add(issue, gbc);

        issue.addActionListener(e -> issueBook());
    }

    private void showToast(String message, Color bg) {
        JDialog dialog = new JDialog();
        dialog.setUndecorated(true);

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(bg);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 17));
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(20, 40, 20, 40)
        ));

        dialog.add(label);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        Timer timer = new Timer(2000, e -> dialog.dispose());
        timer.setRepeats(false);
        timer.start();

        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    private void issueBook() {
        String bookId = bookIdField.getText().trim();
        String studentId = studentIdField.getText().trim();
        String issueDate = issueDateField.getText().trim();
        String dueDate = dueDateField.getText().trim();

        if (bookId.isEmpty() || studentId.isEmpty() ||
                issueDate.isEmpty() || dueDate.isEmpty()) {
            showToast("Fill all fields!", Color.RED);
            return;
        }

        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO issued_books(book_id, student_id, issue_date, due_date) VALUES (?, ?, ?, ?)"
            );

            ps.setInt(1, Integer.parseInt(bookId));
            ps.setInt(2, Integer.parseInt(studentId));
            ps.setString(3, issueDate);
            ps.setString(4, dueDate);
            ps.executeUpdate();

            showToast("Book Issued!", new Color(46, 204, 113));

            dispose();

        } catch (Exception e) {
            showToast("Error: " + e.getMessage(), Color.RED);
        }
    }
}
