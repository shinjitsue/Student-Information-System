package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Define the remote interface for the course data
public interface CourseData extends Remote {

    // Define the method to enroll a student in a course
    void enrollCourse(int courseID, int studentID, String courseTitle, String courseDescription) 
            throws RemoteException;
}