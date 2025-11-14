package library;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class StudentPage extends JFrame {

    private JTextField nameField, courseField, branchField;

    public StudentPage() {
        setTitle("Add Student");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Course:"));
        courseField = new JTextField();
        add(courseField);

        add(new JLabel("Branch:"));
        branchField = new JTextField();
        add(branchField);

        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");

        save.addActionListener(e -> saveStudent());
        cancel.addActionListener(e -> dispose());

        add(save);
        add(cancel);
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

    private void saveStudent() {
        String name = nameField.getText().trim();
        String course = courseField.getText().trim();
        String branch = branchField.getText().trim();

        if (name.isEmpty() || course.isEmpty() || branch.isEmpty()) {
            showToast("Fill all fields!", Color.RED);
            return;
        }

        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO students(name, course, branch) VALUES (?, ?, ?)"
            );
            ps.setString(1, name);
            ps.setString(2, course);
            ps.setString(3, branch);
            ps.executeUpdate();

            showToast("Student added!", new Color(46, 204, 113));
            dispose();

        } catch (Exception e) {
            showToast("Error: " + e.getMessage(), Color.RED);
        }
    }
}
