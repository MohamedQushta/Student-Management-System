import java.util.ArrayList;
public class TeacherOptions {
    Teacher currTeacher;
    static TeacherOptions onlyInstance = new TeacherOptions();
    CourseBehaviour courseBehaviour = new TeacherCourseBehaviour();
    private TeacherOptions()
    {}
    public static TeacherOptions getOnlyInstance()
    {
//        if(onlyInstance == null)
//            onlyInstance = new TeacherOptions();
        return onlyInstance;
    }
    static void teacherOptions(Teacher currTeacher) {
        TeacherOptions teacherOptions = TeacherOptions.getOnlyInstance();
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

    public void showAvailableCourses(String teacherEmail)
    {
        courseBehaviour.showAvailableCourses(teacherEmail);
    }
    public void addCourse(String courseId, String teacherEmail) {
        courseBehaviour.addCourse(courseId, teacherEmail);
    }
    public void withdrawCourse(String courseID, String teacherEmail) {
        currTeacher = (Teacher) Engine.allUsers.get(teacherEmail);
        Course courseToBeWithdrawn;
        if(SemesterTimelineFunctionality.isRunning == true)
        {
            System.out.println("Teachers can't withdraw courses while the semester is running");
            return;
        }
        if (Engine.allCourses.containsKey(courseID))
            courseToBeWithdrawn = Engine.allCourses.get(courseID);
        else {
            System.out.println("This course ID is not available");
            return;
        }
        String idToBeWithdrawn = null;
        for (String currentCourseId : currTeacher.currCourses) {
            Course currCourse = Engine.allCourses.get(currentCourseId);
            idToBeWithdrawn = null;
            if (courseToBeWithdrawn.getId().equals(currCourse.getId())) {
                idToBeWithdrawn = currCourse.getId();
            }
        }
        if (idToBeWithdrawn != null) {
            currTeacher.currCourses.remove(idToBeWithdrawn);
            courseToBeWithdrawn.refundAllStudents();
            courseToBeWithdrawn.setCurrTeacher(null);
        }
        else
        {
            System.out.println("This teacher does not teach this course");
        }
    }
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
    public void viewCurrCourses(String teacherEmail)
    {
        currTeacher = (Teacher) Engine.allUsers.get(teacherEmail);
        if(currTeacher.currCourses.isEmpty())
            System.out.println("This teacher does not teach any course");
        for(String c : currTeacher.currCourses)
        {
            Course currCourse = Engine.allCourses.get(c);
            System.out.println(currCourse);
        }
    }
}
