package library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomePage extends JFrame {

    public HomePage() {
        setTitle("Library Management System - Home");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        ImageIcon bgIcon = null;
        try {
            bgIcon = new ImageIcon(getClass().getResource("/library/library.jpg"));
        } catch (Exception e) {
            System.out.println("⚠️ Background image not found, using plain background.");
        }

        JLabel background;
        if (bgIcon != null) {
            Image img = bgIcon.getImage().getScaledInstance(950, 600, Image.SCALE_SMOOTH);
            background = new JLabel(new ImageIcon(img));
        } else {
            background = new JLabel();
            background.setBackground(new Color(45, 45, 60));
            background.setOpaque(true);
        }

        background.setLayout(new BorderLayout());
        add(background);

        JLabel title = new JLabel("📖 Library Management System", SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        background.add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new GridLayout(4, 2, 25, 25));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 180, 70, 180));

        JButton bookBtn = createGlassButton("📘 Add New Book");
        JButton studentBtn = createGlassButton("👩‍🎓 Manage Students");
        JButton issueBtn = createGlassButton("📖 Issue Book");
        JButton returnBtn = createGlassButton("🔁 Return Book");
        JButton statsBtn = createGlassButton("📊 View Statistics");
        JButton recordsBtn = createGlassButton("📋 View Records");
        JButton logoutBtn = createGlassButton("🚪 Logout");

        panel.add(bookBtn);
        panel.add(studentBtn);
        panel.add(issueBtn);
        panel.add(returnBtn);
        panel.add(statsBtn);
        panel.add(recordsBtn);
        panel.add(logoutBtn);

        background.add(panel, BorderLayout.CENTER);

        bookBtn.addActionListener(e -> new AddBookPage().setVisible(true));
        studentBtn.addActionListener(e -> new StudentPage().setVisible(true));
        issueBtn.addActionListener(e -> new IssueBookPage().setVisible(true));
        returnBtn.addActionListener(e -> new ReturnBookPage().setVisible(true));
        statsBtn.addActionListener(e -> new StatisticsPage().setVisible(true));
        recordsBtn.addActionListener(e -> new ViewRecordsPage().setVisible(true));
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginPage().setVisible(true);
        });
    }

    private JButton createGlassButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 17));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(0, 0, 0, 150));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2, true));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(180, 60));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(30, 144, 255, 180));
                btn.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 180), 2, true));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(0, 0, 0, 150));
                btn.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2, true));
            }
        });
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomePage().setVisible(true));
    }
}
