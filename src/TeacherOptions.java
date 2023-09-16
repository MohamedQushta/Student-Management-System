import java.util.ArrayList;
public class TeacherOptions {
    Teacher currTeacher;
    static TeacherOptions onlyInstance = new TeacherOptions();
    CourseStrategy courseStrategy = new TeacherCourseStrategy();
    private TeacherOptions()
    {}
    public static TeacherOptions getOnlyInstance()
    {
        return onlyInstance;
    }
    static void teacherOptions(Teacher currTeacher) {
        TeacherOptions teacherOptions = TeacherOptions.getOnlyInstance();
        String answer;
        do {
            displayTeacherOptions();
            int choice = InputHandler.promptNumericInput(1,6);
            if(choice == 6 ) break;
            switch (choice) {
                case 1 -> {
                    System.out.println("Enter the course id: ");
                    String courseID = Engine.in.nextLine();
                    teacherOptions.addCourse(courseID, currTeacher.getEmail());
                }
                case 2 -> teacherOptions.showAvailableCourses(currTeacher.getEmail());
                case 3 -> {
                    System.out.println("Enter the course id: ");
                    String courseID = Engine.in.nextLine();
                    teacherOptions.withdrawCourse(courseID, currTeacher.getEmail());
                }
                case 4 -> teacherOptions.viewRegisteredStudents(currTeacher.getEmail());
                case 5 -> teacherOptions.viewCurrCourses(currTeacher.getEmail());
            }
            System.out.println("Do you want to do anything else (y/n)");
            answer = InputHandler.promptBinaryInput();
        } while (answer.equals("y"));
    }

    private static void displayTeacherOptions() {
        System.out.println("1: Get assigned to a course");
        System.out.println("2: View available courses");
        System.out.println("3: Withdraw course");
        System.out.println("4: View registered students");
        System.out.println("5: View current courses");
        System.out.println("6: Back to Sign in/ Sign Up");
    }

    public void showAvailableCourses(String teacherEmail)
    {
        courseStrategy.showAvailableCourses(teacherEmail);
    }
    public void addCourse(String courseId, String teacherEmail) {
        courseStrategy.addCourse(courseId, teacherEmail);
    }
    public void withdrawCourse(String teacherEmail, String courseID) {courseStrategy.removeCourse(teacherEmail, courseID);}
    public void viewCurrCourses(String teacherEmail)  {courseStrategy.viewCurrCourses(teacherEmail);}
    public void viewRegisteredStudents(String teacherEmail)
    {
        currTeacher = (Teacher) Engine.allUsers.get(teacherEmail);
        if(currTeacher.currCourses.isEmpty())
        {
            System.out.println("This teacher is teaches no courses currently");
            return;
        }
        for(String courseId : currTeacher.currCourses)
        {
            System.out.println("Students assigned to this course: " + courseId);
            Course currCourse = Engine.allCourses.get(courseId);
            ArrayList<String> thisCourseStudents = currCourse.getStudentIds();
            if(thisCourseStudents.isEmpty())
            {
                System.out.println("This course has no students");
                return;
            }
            for(String studentEmail : thisCourseStudents)
            {
                Student currStudent = (Student) Engine.allUsers.get(studentEmail);
                System.out.println("Student{name=" + currStudent.getName() + ", email=" + currStudent.getEmail() +"}" );
            }
        }
    }

}
