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

        statsArea = new JTextArea();
        statsArea.setEditable(false);
        add(new JScrollPane(statsArea), BorderLayout.CENTER);

        showStats();
    }

    void showStats() {
        try (Connection con = DatabaseConnection.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs1 = st.executeQuery("SELECT COUNT(*) FROM books");
            rs1.next();
            int bookCount = rs1.getInt(1);

            ResultSet rs2 = st.executeQuery("SELECT COUNT(*) FROM students");
            rs2.next();
            int studentCount = rs2.getInt(1);

            statsArea.setText("📚 Total Books: " + bookCount + "\n👩‍🎓 Total Students: " + studentCount);
        } catch (Exception e) {
            statsArea.setText("No data yet!");
        }
    }
}
