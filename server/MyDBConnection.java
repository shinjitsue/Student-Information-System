package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/student-management-system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "joey89"; 

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC driver not found: " + e.getMessage());
            throw new SQLException("MySQL JDBC driver not found", e);
        }
    }
}