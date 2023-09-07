import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Engine implements Serializable{

    public static HashMap<String, Course> allCourses = new HashMap<>();
    public static HashMap<String, User> allUsers = new HashMap<>();
    public static ArrayList<Request> pendingRequests = new ArrayList<>();
    StudentOptions studentOptions = new StudentOptions();
    public static final Scanner in = new Scanner(System.in);
    public void go() throws IOException, InterruptedException {
        FileHandler.loadAllData();
        int currentSemester = 1;
        while(currentSemester <= 4) {
            System.out.println("Welcome to semester " + currentSemester);

            DataModificationFunctionality dataModificationFunctionality = new DataModificationFunctionality();
            Thread dataModificationThread = new Thread(dataModificationFunctionality, "Data modification thread");

            dataModificationThread.start();
            dataModificationThread.join();
            studentOptions.handleRefundedStudents();
            SemesterTimelineFunctionality semesterTimelineFunctionality = new SemesterTimelineFunctionality(currentSemester);
            Thread semesterTimelineThread = new Thread(semesterTimelineFunctionality);
            SemesterTimelineFunctionality.isRunning = true;
            semesterTimelineThread.start();
            semesterTimelineThread.join();
            SemesterTimelineFunctionality.isRunning = false;
            currentSemester++;
        }
        System.out.println("Academic year has now been finished");
    }
    static void systemAdminOptions(SystemAdmin currUser){
        String answer;
        do {
            System.out.println("1: Add a new Course");
            System.out.println("2: Show and process requests");
            System.out.println("3: Modify info");
            System.out.println("4: Back to Sign in/ Sign Up");
            System.out.println("5: Start/Continue the semester");
            int choice = InputHandler.promptNumericInput(1,5);
            if(choice == 4) break;
            switch (choice) {
                case 1 -> currUser.addCourse();
                case 2 -> currUser.showAndProcessRequests();
                case 3 -> currUser.modifyInfo();
                case 5 -> {
                    DataModificationFunctionality.stop();
                    return;
                }
            }
            System.out.println("Do you want to do anything else (y/n)");
            answer = InputHandler.promptBinaryInput();
        } while (answer.equals("y"));
    }

    static void teacherOptions(Teacher currTeacher) {
        String answer;
        do {
            System.out.println("1: Get assigned to a course");
            System.out.println("2: View available courses");
            System.out.println("3: Withdraw course");
            System.out.println("4: View registered students");
            System.out.println("5: View current courses");
            System.out.println("6: Back to Sign in/ Sign Up");
            int choice = InputHandler.promptNumericInput(1,6);
            if(choice == 6 ) break;
            switch (choice) {
                case 1 -> currTeacher.addCourse();
                case 2 -> currTeacher.viewAvailableCourses();
                case 3 -> currTeacher.withdrawCourse();
                case 4 -> currTeacher.viewRegisteredStudents();
                case 5 -> currTeacher.viewCurrCourses();
            }
            System.out.println("Do you want to do anything else (y/n)");
            answer = InputHandler.promptBinaryInput();
        } while (answer.equals("y"));
    }

    static void clearSemesterData()
    {
        clearCoursesData();
        clearUsersData();
    }
    static void clearUsersData()
    {
        for(User user : allUsers.values())
        {
            if(user.userType == UserType.STUDENT)
            {
                ((Student) user).currCourses.clear();
                ((Student) user).setCurrCreditHrs(0);
                ((Student) user).setReadyForSemester(false);
            }
            if(user.userType == UserType.TEACHER)
            {
                ((Teacher) user).currCourses.clear();
            }
        }
    }
    static void clearCoursesData()
    {
        for(Course course : allCourses.values())
        {
            course.setCurrTeacher(null);
            course.setCurrStudents(0);
            course.studentsEmails.clear();
        }
    }

}




