package server;

import shared.StudentData;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentServiceImpl extends UnicastRemoteObject implements StudentData {

    public StudentServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public void registerStudent(int studentID, String name, int age, String address, String contactNum) 
            throws RemoteException {

        try (Connection conn = MyDBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO students (id, name, age, address, contact_number) " +
                     "VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE " + 
                     "name=?, age=?, address=?, contact_number=?"
             )) {

            stmt.setInt(1, studentID);
            stmt.setString(2, name);
            stmt.setInt(3, age);
            stmt.setString(4, address);
            stmt.setString(5, contactNum);
            // For ON DUPLICATE KEY UPDATE
            stmt.setString(6, name); 
            stmt.setInt(7, age);
            stmt.setString(8, address);
            stmt.setString(9, contactNum); 

            stmt.executeUpdate();
            System.out.println("Successfully registered/updated student with ID: " + studentID);

        } catch (SQLException e) {
            System.err.println("Error in registerStudent: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
            throw new RemoteException("Database error during registration", e); // Re-throw
        }
    }
}