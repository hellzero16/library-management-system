package library;

import java.sql.*;

public class DatabaseConnection {
    private static Connection con;

    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                Class.forName("org.h2.Driver");
                con = DriverManager.getConnection("jdbc:h2:./libraryDB", "sa", "");
                System.out.println("✅ Connected to H2 Database");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void initializeDatabase() {
        try (Connection con = getConnection();
             Statement st = con.createStatement()) {

            st.execute("CREATE TABLE IF NOT EXISTS books(" +
                       "id IDENTITY PRIMARY KEY, " +
                       "title VARCHAR(255), " +
                       "author VARCHAR(255))");

            st.execute("CREATE TABLE IF NOT EXISTS students(" +
                       "id IDENTITY PRIMARY KEY, " +
                       "name VARCHAR(255), " +
                       "roll VARCHAR(255))");

            st.execute("CREATE TABLE IF NOT EXISTS issued_books(" +
                       "issue_id IDENTITY PRIMARY KEY, " +
                       "book_id INT, " +
                       "student_id INT, " +
                       "issue_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

            System.out.println("📘 All tables verified/created.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

