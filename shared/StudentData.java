package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Define the remote interface for the student data
public interface StudentData extends Remote {

    // Define the method to register a student
    void registerStudent(int studentID, String name, int age, String address, String contactNum) 
            throws RemoteException;
}