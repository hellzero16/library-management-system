package library;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class StudentPage extends JFrame {
    JTextField name, roll;
    JButton add;

    public StudentPage() {
        setTitle("Manage Students");
        setSize(400, 300);
        setLayout(new GridLayout(3, 2, 10, 10));
        setLocationRelativeTo(null);

        add(new JLabel("Student Name:"));
        name = new JTextField();
        add(name);

        add(new JLabel("Roll No:"));
        roll = new JTextField();
        add(roll);

        add = new JButton("Add Student");
        add(new JLabel());
        add(add);

        add.addActionListener(e -> addStudent());
    }

    void addStudent() {
        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS students(id IDENTITY, name VARCHAR, roll VARCHAR)");
            ps.execute();
            ps = con.prepareStatement("INSERT INTO students(name, roll) VALUES(?, ?)");
            ps.setString(1, name.getText());
            ps.setString(2, roll.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student Added!");
            name.setText("");
            roll.setText("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
