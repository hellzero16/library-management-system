package library;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomePage extends JFrame {

    public HomePage() {
        setTitle("Library Management System - Home");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // === Background Image ===
        ImageIcon bgIcon = new ImageIcon("library.jpg");
        Image img = bgIcon.getImage().getScaledInstance(900, 550, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(img));
        background.setLayout(new BorderLayout());
        add(background);

        // === Transparent Panel ===
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new GridLayout(3, 2, 25, 25));
        panel.setBorder(BorderFactory.createEmptyBorder(80, 150, 80, 150));

        // === Buttons ===
        JButton bookBtn = createButton("📚 Add New Book");
        JButton studentBtn = createButton("👩‍🎓 Manage Students");
        JButton issueBtn = createButton("📖 Issue Book");
        JButton returnBtn = createButton("🔁 Return Book");
        JButton statsBtn = createButton("📈 View Statistics");
        JButton logoutBtn = createButton("🚪 Logout");

        // === Add Buttons ===
        panel.add(bookBtn);
        panel.add(studentBtn);
        panel.add(issueBtn);
        panel.add(returnBtn);
        panel.add(statsBtn);
        panel.add(logoutBtn);

        background.add(panel, BorderLayout.CENTER);

        // === Button Actions ===
        bookBtn.addActionListener(e -> new AddBookPage().setVisible(true));
        studentBtn.addActionListener(e -> new StudentPage().setVisible(true));
        issueBtn.addActionListener(e -> new IssueBookPage().setVisible(true));
        returnBtn.addActionListener(e -> new ReturnBookPage().setVisible(true));
        statsBtn.addActionListener(e -> new StatisticsPage().setVisible(true));

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginPage().setVisible(true);
        });
    }

    // === Helper: Create styled buttons ===
    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(new Color(255, 255, 255, 180));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(230, 230, 255, 220));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(255, 255, 255, 180));
            }
        });
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomePage().setVisible(true));
    }
}
