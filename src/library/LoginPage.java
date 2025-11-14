package library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPage extends JFrame {
    JTextField userField;
    JPasswordField passField;
    JButton loginBtn;

    public LoginPage() {
        setTitle("Library Management System - Login");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        DatabaseConnection.initializeDatabase();

        // === Background ===
        JLabel bg = new JLabel(new ImageIcon(getClass().getResource("/library/library.jpg")));
        bg.setLayout(new GridBagLayout());
        add(bg);

        // === Login Panel ===
        JPanel panel = new JPanel(new GridLayout(3, 2, 12, 12));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(350, 150));

        // === Styled Labels ===
        JLabel userLabel = new JLabel("Username:", SwingConstants.RIGHT);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        userLabel.setForeground(Color.WHITE);
        userLabel.setOpaque(false);

        JLabel passLabel = new JLabel("Password:", SwingConstants.RIGHT);
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        passLabel.setForeground(Color.WHITE);
        passLabel.setOpaque(false);

        // === Input Fields ===
        userField = new JTextField();
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        passField = new JPasswordField();
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // === Button ===
        loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(45, 140, 240));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setFocusPainted(false);

        // Add elements
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(new JLabel());
        panel.add(loginBtn);

        bg.add(panel);

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
        DatabaseConnection.initializeDatabase();
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }
}
