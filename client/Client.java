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
        System.out.println("Client starting...");
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9100);

            System.out.println("Looking up services...");
            StudentData studentService = (StudentData) registry.lookup("StudentService");
            CourseData courseService = (CourseData) registry.lookup("CourseService");

            System.out.println("Sending student data...");
            sendStudentData(studentService);
            System.out.println("Sending course data...");
            sendCourseData(courseService);

        } catch (RemoteException e) { 
            System.err.println("Remote error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Client finished.");
    }

    private static void sendStudentData(StudentData studentService) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("./client/Student.xml");
            doc.getDocumentElement().normalize();

            NodeList studentNodes = doc.getElementsByTagName("Student");
            for (int i = 0; i < studentNodes.getLength(); i++) {
                Element studentElement = (Element) studentNodes.item(i);

                int studentID = Integer.parseInt(studentElement.getElementsByTagName("Student_ID").item(0).getTextContent());
                String name = studentElement.getElementsByTagName("Name").item(0).getTextContent();
                int age = Integer.parseInt(studentElement.getElementsByTagName("Age").item(0).getTextContent());
                String address = studentElement.getElementsByTagName("Address").item(0).getTextContent();
                String contactNum = studentElement.getElementsByTagName("Contact_Number").item(0).getTextContent();

                studentService.registerStudent(studentID, name, age, address, contactNum);
                System.out.println("Registered student with ID: " + studentID);
            }

        } catch (Exception e) { 
            System.err.println("Error sending student data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void sendCourseData(CourseData courseService) { 
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("./client/Courses.xml");
            doc.getDocumentElement().normalize();

            NodeList courseNodes = doc.getElementsByTagName("Course");
            for (int i = 0; i < courseNodes.getLength(); i++) {
                Element courseElement = (Element) courseNodes.item(i);

                int courseID = Integer.parseInt(courseElement.getElementsByTagName("Course_ID").item(0).getTextContent());
                int studentID = Integer.parseInt(courseElement.getElementsByTagName("Student_ID").item(0).getTextContent());
                String courseTitle = courseElement.getElementsByTagName("Course_title").item(0).getTextContent();
                String courseDescription = courseElement.getElementsByTagName("Course_Description").item(0).getTextContent();

                courseService.enrollCourse(courseID, studentID, courseTitle, courseDescription);
                System.out.println("Enrolled course with ID: " + courseID + " for student ID: " + studentID);
            } 

        } catch (Exception e) {
            System.err.println("Error sending course data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

// java -cp ".;server\mysql-connector-j-8.4.0.jar" client.Client