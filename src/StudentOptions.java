import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class StudentOptions {
    static StudentOptions onlyInstance = new StudentOptions();
    private StudentOptions()
    {}
    public static StudentOptions getOnlyInstance(){
//            if(onlyInstance == null)
//                onlyInstance = new StudentOptions();
        return onlyInstance;
    }
    Student currStudent;
    CourseBehaviour courseBehaviour = new StudentCourseBehaviour();
    static Random randomizer = new Random();
    public static final Character[] allGradesAvailable ={'A' , 'B', 'C', 'D', 'F'};
    static void studentOptions(Student student) {
        StudentOptions studentOptions = StudentOptions.getOnlyInstance();
        String answer;
        do {
            System.out.println("1: Register Course");
            System.out.println("2: Show Courses");
            System.out.println("3: Show Grades of current semester");
            System.out.println("4: Show Grades of past semesters");
            System.out.println("5: Request Financial Aid");
            System.out.println("6: Show Information");
            System.out.println("7: Back to Sign in/ Sign Up");
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
        courseBehaviour.addCourse(courseId, studentEmail);
    }
    public void refundCourse(String courseId,String studentEmail)
    {
        currStudent = (Student) Engine.allUsers.get(studentEmail);
        if(Engine.allCourses.containsKey(courseId)) //Check if the course exists
        {
            Course courseToBeDeleted = Engine.allCourses.get(courseId);
            if(currStudent.currCourses.containsKey(courseId)) //Check if the student is taking this course
            {
                currStudent.currCourses.remove(courseId);
                currStudent.setBalance(currStudent.getBalance() + courseToBeDeleted.getCreditHrs() * SystemAdmin.crHrCost); ;
                currStudent.setCurrCreditHrs(currStudent.getTotalCreditHrs() - courseToBeDeleted.getCreditHrs()); ;
                System.out.println("Student: " + currStudent.getName() + " has been refunded" );
            }
            else
            {
                System.out.println("Student: " + currStudent.getName() + " is not taking this course");
            }
        }
        else
        {
            System.out.println("course ID: " + courseId +  " is not available");
        }
    }

    public void showCurrCourses(String studentEmail)
    {
        currStudent = (Student) Engine.allUsers.get(studentEmail);
        if(!currStudent.currCourses.isEmpty())
        {
            for(String courseId :currStudent.currCourses.keySet())
            {
                Course currCourse = Engine.allCourses.get(courseId);
                System.out.println(currCourse);
            }
        }
        else
            System.out.println("Student: " + currStudent.getName() + " has no courses for this semester yet");
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
    void showAvailableCourses(String studentEmail)
    {
        courseBehaviour.showAvailableCourses(studentEmail);
    }
}
