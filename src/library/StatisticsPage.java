package library;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class StatisticsPage extends JFrame {
    JTextArea statsArea;

    public StatisticsPage() {
        setTitle("Library Statistics");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        statsArea = new JTextArea();
        statsArea.setEditable(false);
        statsArea.setFont(new Font("Consolas", Font.PLAIN, 16));

        add(new JScrollPane(statsArea), BorderLayout.CENTER);

        showStats();
    }

    void showStats() {
        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement()) {

            // ✅ Query counts safely
            ResultSet rs1 = st.executeQuery("SELECT COUNT(*) FROM books");
            rs1.next();
            int bookCount = rs1.getInt(1);

            ResultSet rs2 = st.executeQuery("SELECT COUNT(*) FROM students");
            rs2.next();
            int studentCount = rs2.getInt(1);

            ResultSet rs3 = st.executeQuery("SELECT COUNT(*) FROM issued_books");
            rs3.next();
            int issueCount = rs3.getInt(1);

            
            statsArea.setText(
                "📚 Total Books: " + bookCount +
                "\n🎓 Total Students: " + studentCount +
                "\n📖 Books Issued: " + issueCount
            );

        } catch (SQLException e) {
            e.printStackTrace();
            statsArea.setText("❌ Error fetching statistics.\n" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StatisticsPage().setVisible(true));
    }
}
