package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CourseData extends Remote {
    void enrollCourse(int courseID, int studentID, String courseTitle, String courseDescription) 
            throws RemoteException;
}