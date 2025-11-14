package library;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ReturnBookPage extends JFrame {

    private JTextField issueIdField;

    public ReturnBookPage() {
        setTitle("Return Book");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 2, 10, 10));

        add(new JLabel("Issue ID:"));
        issueIdField = new JTextField();
        add(issueIdField);

        JButton returnBtn = new JButton("Return");
        JButton cancelBtn = new JButton("Cancel");

        returnBtn.addActionListener(e -> returnBook());
        cancelBtn.addActionListener(e -> dispose());

        add(returnBtn);
        add(cancelBtn);
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

    private void returnBook() {
        String idText = issueIdField.getText().trim();

        if (idText.isEmpty()) {
            showToast("Enter Issue ID!", Color.RED);
            return;
        }

        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM issued_books WHERE id = ?"
            );
            ps.setInt(1, Integer.parseInt(idText));

            int rows = ps.executeUpdate();

            if (rows == 0) {
                showToast("No such issue ID!", Color.RED);
            } else {
                showToast("Book Returned!", new Color(39, 174, 96));
                dispose();
            }

        } catch (Exception e) {
            showToast("Error: " + e.getMessage(), Color.RED);
        }
    }
}
