package server;

import shared.CourseData;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseServiceImpl extends UnicastRemoteObject implements CourseData {

    public CourseServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public void enrollCourse(int courseID, int studentID, String courseTitle, String courseDescription) 
            throws RemoteException {
        
        try (Connection conn = MyDBConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            try (PreparedStatement checkCourseStmt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM courses WHERE id = ?"
            )) {
                checkCourseStmt.setInt(1, courseID);
                try (ResultSet rs = checkCourseStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        try (PreparedStatement insertCourseStmt = conn.prepareStatement(
                                "INSERT INTO courses (id, title, description) VALUES (?, ?, ?)"
                        )) {
                            insertCourseStmt.setInt(1, courseID);
                            insertCourseStmt.setString(2, courseTitle);
                            insertCourseStmt.setString(3, courseDescription);
                            insertCourseStmt.executeUpdate();
                        }
                    }
                }
            }

            // Check if student exists
            try (PreparedStatement checkStudentStmt = conn.prepareStatement(
                         "SELECT COUNT(*) FROM students WHERE id = ?"
                 )) {
                 checkStudentStmt.setInt(1, studentID);
                 try (ResultSet rs = checkStudentStmt.executeQuery()) {
                     if (rs.next() && rs.getInt(1) == 0) {
                         throw new RemoteException("Student with ID " + studentID + " does not exist.");
                     }
                 }
             }

            // Insert/Update student-course relationship
            try (PreparedStatement insertRelationshipStmt = conn.prepareStatement(
                    "INSERT INTO student_courses (student_id, course_id) VALUES (?, ?)" +
                    " ON DUPLICATE KEY UPDATE student_id=student_id"
            )) {
                insertRelationshipStmt.setInt(1, studentID);
                insertRelationshipStmt.setInt(2, courseID);
                insertRelationshipStmt.executeUpdate();
            }

            conn.commit(); // Commit transaction
            System.out.println("Enrolled/updated course with ID: " + courseID + 
                               " for student ID: " + studentID);

        } catch (SQLException e) {
            System.err.println("Error in enrollCourse: " + e.getMessage());
            e.printStackTrace();
            throw new RemoteException("Database error during enrollment", e); 
        }
    }
}