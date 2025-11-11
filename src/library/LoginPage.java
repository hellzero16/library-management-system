package library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame {
    JTextField userField;
    JPasswordField passField;
    JButton loginBtn;

    public LoginPage() {
        setTitle("Library Management System - Login");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ✅ Initialize database when the application starts
        DatabaseConnection.initializeDatabase();

        // === Background ===
        JLabel bg = new JLabel(new ImageIcon("library.jpg"));
        bg.setLayout(new GridBagLayout());
        add(bg);

        // === Login Panel ===
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setOpaque(false);

        panel.add(new JLabel("Username:", SwingConstants.RIGHT));
        userField = new JTextField();
        panel.add(userField);

        panel.add(new JLabel("Password:", SwingConstants.RIGHT));
        passField = new JPasswordField();
        panel.add(passField);

        loginBtn = new JButton("Login");
        panel.add(new JLabel());
        panel.add(loginBtn);

        bg.add(panel);

        // === Button Action ===
        loginBtn.addActionListener(e -> login());
    }

    void login() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword()).trim();

        if (user.equals("admin") && pass.equals("123")) {
            dispose();
            new HomePage().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "❌ Invalid credentials", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // ✅ First connect and create all required tables
        DatabaseConnection.initializeDatabase();

        // ✅ Then start the UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }
}
