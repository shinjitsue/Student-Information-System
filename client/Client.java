package client;

import shared.StudentData;
import shared.CourseData;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException; 

public class Client {
    public static void main(String[] args) {
        // Print a message to indicate that the client is starting
        System.out.println("Client starting...");

        try {
            // Get the RMI registry on the localhost at port 9100
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9100);

            // Look up the remote services
            System.out.println("Looking up services...");
            StudentData studentService = (StudentData) registry.lookup("StudentService");
            CourseData courseService = (CourseData) registry.lookup("CourseService");

            // Send student data to the server
            System.out.println("Sending student data...");
            sendStudentData(studentService);

            // Send course data to the server
            System.out.println("Sending course data...");
            sendCourseData(courseService);

        } catch (RemoteException e) {
            // Handle remote exceptions
            System.err.println("Remote error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Handle other exceptions
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }

        // Print a message to indicate that the client has finished
        System.out.println("Client finished.");
    }

    private static void sendStudentData(StudentData studentService) {
        try {
            // Parse the XML file containing student data
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("./client/Student.xml");
            doc.getDocumentElement().normalize();

            // Get the list of student nodes
            NodeList studentNodes = doc.getElementsByTagName("Student");

            // Iterate over each student node
            for (int i = 0; i < studentNodes.getLength(); i++) {
                Element studentElement = (Element) studentNodes.item(i);

                // Extract student information from the XML
                int studentID = Integer.parseInt(studentElement.getElementsByTagName("Student_ID").item(0).getTextContent());
                String name = studentElement.getElementsByTagName("Name").item(0).getTextContent();
                int age = Integer.parseInt(studentElement.getElementsByTagName("Age").item(0).getTextContent());
                String address = studentElement.getElementsByTagName("Address").item(0).getTextContent();
                String contactNum = studentElement.getElementsByTagName("Contact_Number").item(0).getTextContent();

                // Register the student with the server
                studentService.registerStudent(studentID, name, age, address, contactNum);
                System.out.println("Registered student with ID: " + studentID);
            }

        } catch (Exception e) {
            // Handle exceptions while sending student data
            System.err.println("Error sending student data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void sendCourseData(CourseData courseService) {
        try {
            // Parse the XML file containing course data
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("./client/Courses.xml");
            doc.getDocumentElement().normalize();

            // Get the list of course nodes
            NodeList courseNodes = doc.getElementsByTagName("Course");

            // Iterate over each course node
            for (int i = 0; i < courseNodes.getLength(); i++) {
                Element courseElement = (Element) courseNodes.item(i);

                // Extract course information from the XML
                int courseID = Integer.parseInt(courseElement.getElementsByTagName("Course_ID").item(0).getTextContent());
                int studentID = Integer.parseInt(courseElement.getElementsByTagName("Student_ID").item(0).getTextContent());
                String courseTitle = courseElement.getElementsByTagName("Course_title").item(0).getTextContent();
                String courseDescription = courseElement.getElementsByTagName("Course_Description").item(0).getTextContent();

                // Enroll the student in the course
                courseService.enrollCourse(courseID, studentID, courseTitle, courseDescription);
                System.out.println("Enrolled course with ID: " + courseID + " for student ID: " + studentID);
            }

        } catch (Exception e) {
            // Handle exceptions while sending course data
            System.err.println("Error sending course data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

// java -cp ".;server\mysql-connector-j-8.4.0.jar" client.Client