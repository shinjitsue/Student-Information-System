package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(9100);
            System.out.println("Server has been started...");
            System.out.println("RMI Server ready on port 9100..");

            StudentServiceImpl studentService = new StudentServiceImpl();
            CourseServiceImpl courseService = new CourseServiceImpl();

            registry.rebind("StudentService", studentService);
            registry.rebind("CourseService", courseService); 

            System.out.println("Exporting and binding of Objects has been completed...");

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

// javac -cp .;mysql-connector-j-8.4.0.jar *.java
// java -cp .;server\mysql-connector-j-8.4.0.jar server.Server
// javac -cp .;server\mysql-connector-j-8.4.0.jar shared/*.java server/*.java client/*.java