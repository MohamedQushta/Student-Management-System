import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class FileHandler {
    static void loadAllData()throws IOException {
        loadUserData();
        loadCourseData();
        loadRequestsData();
    }
    static void updateAllData() throws IOException{
        updateUserData();
        updateCourseData();
        updateRequestsData();
    }

    static void loadUserData() throws IOException {
        FileInputStream fis;
        ObjectInputStream ois;
        try {
            fis =  new FileInputStream("D:\\Work\\Other\\Java Notes\\Student-Management-System\\Files\\Users.ser");
            ois = new ObjectInputStream(fis);
            Engine.allUsers = (HashMap<String, User>) ois.readObject();
            fis.close();
            ois.close();
        } catch (EOFException e) {
            System.out.println("File is still empty");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    static void updateUserData() throws IOException {

        FileOutputStream fos = new FileOutputStream("D:\\Work\\Other\\Java Notes\\Student-Management-System\\Files\\Users.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        if(Engine.allUsers.isEmpty())
            return;
        else {
            oos.writeObject(Engine.allUsers);
        }
        oos.close();
        fos.close();
    }

    private static void loadCourseData() throws IOException {
        FileInputStream fis;
        ObjectInputStream ois;
        try {
            fis =  new FileInputStream("D:\\Work\\Other\\Java Notes\\Student-Management-System\\Files\\Courses.ser");
            ois = new ObjectInputStream(fis);
            Engine.allCourses = (HashMap<String, Course>) ois.readObject();
            fis.close();
            ois.close();
        } catch (EOFException e) {
            System.out.println("File is still empty");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private static void updateCourseData() throws IOException
    {
        FileOutputStream fos = new FileOutputStream("D:\\Work\\Other\\Java Notes\\Student-Management-System\\Files\\Courses.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        if(Engine.allCourses.isEmpty())
            return;
        else {
            oos.writeObject(Engine.allCourses);
        }
        oos.close();
        fos.close();
    }
    private static void loadRequestsData() throws IOException {
        FileInputStream fis;
        ObjectInputStream ois;
        try {
            fis =  new FileInputStream("D:\\Work\\Other\\Java Notes\\Student-Management-System\\Files\\Requests.ser");
            ois = new ObjectInputStream(fis);
            Engine.pendingRequests = (ArrayList<Request>) ois.readObject();
            fis.close();
            ois.close();
        } catch (EOFException e) {
            System.out.println("File is still empty");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private static void updateRequestsData() throws IOException
    {
        FileOutputStream fos = new FileOutputStream("D:\\Work\\Other\\Java Notes\\Student-Management-System\\Files\\Requests.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        if(Engine.pendingRequests.isEmpty())
            return;
        else {
            oos.writeObject(Engine.pendingRequests);
        }
        oos.close();
        fos.close();
    }
}
