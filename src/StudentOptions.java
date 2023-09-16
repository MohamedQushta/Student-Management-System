import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class StudentOptions {
    static StudentOptions onlyInstance = new StudentOptions();
    private StudentOptions()
    {}
    public static StudentOptions getOnlyInstance(){
        return onlyInstance;
    }
    Student currStudent;
    CourseStrategy courseStrategy = new StudentCourseStrategy();
    static Random randomizer = new Random();
    public static final Character[] allGradesAvailable ={'A' , 'B', 'C', 'D', 'F'};
    static void studentOptions(Student student) {
        StudentOptions studentOptions = StudentOptions.getOnlyInstance();
        String answer;
        do {
            displayStudentOptions();
            int choice = InputHandler.promptNumericInput(1,7);
            if(choice == 7) break;
            switch (choice) {
                case 1 -> {
                    String curr;
                    do {
                        System.out.println("These are all the courses available for this student, if you want to add any of them press 1, to exit press 2");
                        studentOptions.showAvailableCourses(student.getEmail());
                        int c = InputHandler.promptNumericInput(1,2);
                        if (c == 1) {
                            System.out.println("Enter Course ID");
                            String id = Engine.in.nextLine();
                            Course currCourse;
                            if (!Engine.allCourses.containsKey(id)) {
                                System.out.println("There is no course with this ID");
                                return;
                            }
                            currCourse = Engine.allCourses.get(id);
                            studentOptions.addCourse(currCourse.getId(), student.getEmail());
                        } else if (c == 2) {
                            break;
                        }
                        System.out.println("Do you want to add another course? (y/n)");
                        curr = InputHandler.promptBinaryInput();
                    } while (!curr.equals("n"));
                }
                case 2 -> studentOptions.showCurrCourses(student.getEmail());
                case 3 -> studentOptions.showGrades(student.getEmail());
                case 4 -> studentOptions.showPastGrades(student.getEmail());
                case 5 -> studentOptions.requestAid(student.getEmail());
                case 6 -> studentOptions.showStudentInfo(student.getEmail());
            }
            System.out.println("Do you want to do anything else (y/n)");
            answer = InputHandler.promptBinaryInput();
        } while (answer.equals("y"));
    }

    public  void addCourse(String courseId, String studentEmail)
    {
        courseStrategy.addCourse(courseId, studentEmail);
    }
    public void refundCourse(String studentEmail,String courseId) { courseStrategy.removeCourse(studentEmail, courseId); }

    public void showCurrCourses(String studentEmail)
    {
        courseStrategy.viewCurrCourses(studentEmail);
    }
    void showAvailableCourses(String studentEmail)
    {
        courseStrategy.showAvailableCourses(studentEmail);
    }

    public void showGrades(String studentEmail) {
        currStudent = (Student) Engine.allUsers.get(studentEmail);
        if(!currStudent.currCourses.isEmpty())
        {
            currStudent.currCourses.forEach( (courseId, grade) ->
                    System.out.println("Course name: " +
                            (Engine.allCourses.get(courseId).getName()) +
                            ", Grade: " +
                            currStudent.currCourses.get(courseId)));
        }
        else
            System.out.println("Student: " + currStudent.name + " has no courses for this semester yet");
    }
    public void showPastGrades(String studentEmail)
    {
        currStudent = (Student) Engine.allUsers.get(studentEmail);
        if(!currStudent.allCourses.isEmpty())
        {
            currStudent.allCourses.forEach( (courseId, grade) ->
                    System.out.println("Course name: " +
                            (Engine.allCourses.get(courseId).getName()) +
                            " Grade: " +
                            currStudent.allCourses.get(courseId)));
        }
        else
            System.out.println("Student: " + currStudent.name + " has no courses added yet");
    }
    public void requestAid(String studentEmail)
    {
        currStudent = (Student) Engine.allUsers.get(studentEmail);
        Request r = new Request(RequestType.FINANCIALAID ,currStudent);
        Engine.pendingRequests.add(r);
        System.out.println("Request has been sent successfully");
    }
    public static void randomizeAllStudentsGrades() throws IOException {
        List<User> allStudents = Engine.allUsers.values().stream().
                filter((user -> user.userType == UserType.STUDENT)).toList();
        for(User currStudent : allStudents)
        {
            randomizeGrade(currStudent.getEmail());
        }
    }
    public static void randomizeGrade(String studentEmail){
        Student currStudent = (Student) Engine.allUsers.get(studentEmail);
        for(Map.Entry<String, Character> entry : currStudent.currCourses.entrySet())
        {
            Character gradeToBePut = allGradesAvailable[randomizer.nextInt(allGradesAvailable.length)];
            entry.setValue(gradeToBePut);
            currStudent.allCourses.put(entry.getKey(), gradeToBePut);
        }
    }

    void showStudentInfo(String studentEmail)
    {
        Student currStudent = (Student) Engine.allUsers.get(studentEmail);
        System.out.println(currStudent);
    }
    private static void displayStudentOptions() {
        System.out.println("1: Register Course");
        System.out.println("2: Show Courses");
        System.out.println("3: Show Grades of current semester");
        System.out.println("4: Show Grades of past semesters");
        System.out.println("5: Request Financial Aid");
        System.out.println("6: Show Information");
        System.out.println("7: Back to Sign in/ Sign Up");
    }

}
