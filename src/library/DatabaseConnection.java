package library;

import java.sql.*;

public class DatabaseConnection {

    private static Connection con;

    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                Class.forName("org.h2.Driver");
                con = DriverManager.getConnection("jdbc:h2:./libraryDB", "sa", "");
                System.out.println("Connected to H2 Database");

                java.io.File f = new java.io.File("libraryDB.mv.db");
                System.out.println("DATABASE FILE PATH = " + f.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void initializeDatabase() {
        try (Connection con = getConnection(); Statement st = con.createStatement()) {

            st.execute(
                "CREATE TABLE IF NOT EXISTS books (" +
                "book_id IDENTITY PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "publisher VARCHAR(255), " +
                "price INT, " +
                "pub_year VARCHAR(50))"
            );

            st.execute(
                "CREATE TABLE IF NOT EXISTS students (" +
                "student_id IDENTITY PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "course VARCHAR(255), " +
                "branch VARCHAR(255))"
            );

            st.execute(
                "CREATE TABLE IF NOT EXISTS issued_books (" +
                "id IDENTITY PRIMARY KEY, " +
                "book_id INT, " +
                "student_id INT, " +
                "issue_date DATE, " +
                "due_date DATE)"
            );

            System.out.println("All tables verified/created.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
