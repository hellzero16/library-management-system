package library;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;

public class ViewRecordsPage extends JFrame {
    private JTable tableBooks, tableStudents, tableIssued;

    public ViewRecordsPage() {
        setTitle("Library Records");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        tableBooks = createTable();
        tableStudents = createTable();
        tableIssued = createTable();

        tabs.addTab("Books", new JScrollPane(tableBooks));
        tabs.addTab("Students", new JScrollPane(tableStudents));
        tabs.addTab("Issued Books", new JScrollPane(tableIssued));

        add(tabs);

        loadBooks();
        loadStudents();
        loadIssuedBooks();
    }

    private JTable createTable() {
        JTable t = new JTable();
        t.setRowHeight(28);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTableHeader h = t.getTableHeader();
        h.setFont(new Font("Segoe UI", Font.BOLD, 15));
        return t;
    }

    private void loadBooks() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Book ID", "Name", "Publisher", "Price", "Year"});

        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM books")) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("book_id"),
                        rs.getString("name"),
                        rs.getString("publisher"),
                        rs.getInt("price"),
                        rs.getString("pub_year")
                });
            }

            tableBooks.setModel(model);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadStudents() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Student ID", "Name", "Course", "Branch"});

        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM students")) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getString("course"),
                        rs.getString("branch")
                });
            }

            tableStudents.setModel(model);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadIssuedBooks() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Book ID", "Book Name", "Student Name", "Issue Date", "Due Date"
        });

        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(
                     "SELECT i.book_id, b.name AS book_name, s.name AS student_name, " +
                             "i.issue_date, i.due_date " +
                             "FROM issued_books i " +
                             "JOIN books b ON i.book_id = b.book_id " +
                             "JOIN students s ON i.student_id = s.student_id")) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("book_id"),
                        rs.getString("book_name"),
                        rs.getString("student_name"),
                        rs.getString("issue_date"),
                        rs.getString("due_date")
                });
            }

            tableIssued.setModel(model);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
