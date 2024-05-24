package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StudentData extends Remote {
    void registerStudent(int studentID, String name, int age, String address, String contactNum) 
            throws RemoteException;
}