import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class SystemAdmin extends User implements Serializable {
    @Serial
    private static final long serialVersionUID = -8975606175870630255L;
    public static double crHrCost;

    public SystemAdmin() {
        super();
        isProcessed = true;
    }




    public void showAndProcessRequests() {
        List<Request> unprocessedRequests = Engine.pendingRequests.stream().
                    filter(request -> !request.isProcessed).toList();

        if(unprocessedRequests.isEmpty())
        {
            System.out.println("There are no unprocessed requests yet");
            return;
        }
        for(int i = 0; i < Engine.pendingRequests.size(); i++)
        {
            if(Engine.pendingRequests.get(i).isProcessed)
                continue;
            System.out.println(Engine.pendingRequests.get(i));
            System.out.println("Press 1 to process the request, 2 to skip the request, 3 to exit");
            int choice = InputHandler.promptNumericInput(1,3);
            User currRequester;
            if(choice == 1)
            {
                if(Engine.pendingRequests.get(i).type == RequestType.FINANCIALAID){
                    System.out.println("Press 1 to accept the request, 2 to decline the request");
                    int c = InputHandler.promptNumericInput(1,2);
                    if(c == 1)
                    {
                        System.out.println("The request has been accepted");
                        currRequester = Engine.pendingRequests.get(i).requester;
                        ((Student) currRequester).setAided(true);
                    }
                    Engine.pendingRequests.get(i).isProcessed = true;

                }
                if(Engine.pendingRequests.get(i).type == RequestType.ACCOUNTCREATION)
                {
                    System.out.println("Press 1 to accept the request, 2 to decline the request");
                    int x = InputHandler.promptNumericInput(1,2);
                    if(x == 1)
                    {
                         currRequester = Engine.pendingRequests.get(i).requester;
                        currRequester.setProcessed(true);
                    }
                    Engine.pendingRequests.get(i).isProcessed = true;
                }
            }
            if(choice == 2)
                continue;
            if(choice == 3)
                break;
        }
    }
    public void addCourse()
    {
        Course currCourse;
        String choice;
        do {
            System.out.println("Course Name: ");
            String courseName;
            courseName = Engine.in.nextLine();

            System.out.println("Course ID: ");
            String courseId = Engine.in.nextLine();

            System.out.println("Course Specialization (case sensitive): ");
            String specialization = Engine.in.nextLine();

            System.out.println("Course max students: ");
            int maxStudents = Engine.in.nextInt();
            Engine.in.nextLine();

            System.out.println("Course credit hours: ");
            int crHrs = Engine.in.nextInt();
            Engine.in.nextLine();
            if(Engine.allCourses.containsKey(courseId))
                System.out.println("This course ID already exists in the list of courses");
            else
            {
               currCourse = new Course(courseName, courseId, specialization, maxStudents, crHrs);
               Engine.allCourses.put(courseId, currCourse);
                System.out.println("Course has been added successfully");
            }
            System.out.println("Do you want to add another course(y/n)");
            choice = InputHandler.promptBinaryInput();
        } while (!choice.equals("n"));

    }
    public void modifyInfo()
    {
        String answer;
        do {
            System.out.println("Whose info do you want to modify");
            System.out.println("1: Student");
            System.out.println("2: Teacher");
            System.out.println("3: Course");
            System.out.println("4: Exit");
            int choice = InputHandler.promptNumericInput(1,4);
            if(choice == 4)
                break;
            switch (choice) {
                case 1 -> modifyStudentInfo();
                case 2 -> modifyTeacherInfo();
                case 3 -> modifyCourseInfo();
            }
            System.out.println("Do you want to modify anything else (y/n)");
            answer = InputHandler.promptBinaryInput();
        } while (answer.equals("y"));
    }
    //Done
    private void modifyStudentInfo()
    {
        System.out.println("Enter the user's email");
        String email = Engine.in.nextLine();
        if(!Engine.allUsers.containsKey(email))
        {
            System.out.println("This email has not been found");
            return;
        }
        Student currStudent = (Student) Engine.allUsers.get(email);
        System.out.println(currStudent);
        String answer;
        do {
            System.out.println("What info do you want to change");
            System.out.println("1: Name");
            System.out.println("2: Password");
            System.out.println("3: Email");
            System.out.println("4: Balance");
            System.out.println("5: Exit");
            int choice = InputHandler.promptNumericInput(1,5);
            if(choice == 5)
                break;
            switch (choice) {
                case 1 -> {
                    System.out.println("What is the new name");
                    String newName = Engine.in.nextLine();
                    currStudent.setName(newName);
                }
                case 2 -> {
                    System.out.println("What is the new password");
                    String newPassword = Engine.in.nextLine();
                    currStudent.setPassword(newPassword);
                }
                case 3 -> {
                    System.out.println("What is the new email");
                    String newEmail = Engine.in.nextLine();
                    currStudent.setEmail(newEmail);
                }
                case 4 -> {
                    System.out.println("What is the new balance");
                    double newBalance = Engine.in.nextDouble();
                    Engine.in.nextLine();
                    currStudent.setBalance(newBalance);
                }
            }
            System.out.println("Do you want to change anything else for this student? (y/n)");
            answer = InputHandler.promptBinaryInput();
        }while(!Objects.equals(answer, "n"));
    }
    //Done
    private void modifyTeacherInfo() {
        System.out.println("Enter the teacher's email");
        String email = Engine.in.nextLine();
        if (!Engine.allUsers.containsKey(email)) {
            System.out.println("This email has not been found");
            return;
        }
        Teacher currTeacher = (Teacher) Engine.allUsers.get(email);
        System.out.println(currTeacher);
        String answer;
        do{
            System.out.println("What info do you want to change");
            System.out.println("1: Name");
            System.out.println("2: Password");
            System.out.println("3: Email");
            System.out.println("4: Specialization");
            System.out.println("5: Exit");
            int currChoice = InputHandler.promptNumericInput(1,5);
            if(currChoice == 5)
                break;
            switch (currChoice) {
                case 1 -> {
                    System.out.println("What is the new name");
                    String newName = Engine.in.nextLine();
                    currTeacher.setName(newName);
                }
                case 2 -> {
                    System.out.println("What is the new password");
                    String newPassword = Engine.in.nextLine();
                    currTeacher.setPassword(newPassword);
                }
                case 3 -> {
                    System.out.println("What is the new email");
                    String newEmail = Engine.in.nextLine();
                    currTeacher.setEmail(newEmail);
                }
                case 4 -> {
                    System.out.println("What is the new specialization");
                    String newSpecialization = Engine.in.nextLine();
                    currTeacher.setSpecialization(newSpecialization);
                }
            }
            System.out.println("Do you want to change anything else for this teacher? (y/n)");
            answer = InputHandler.promptBinaryInput();
        }while(!answer.equals("n"));
    }
    //Done
    private void modifyCourseInfo()
    {
        System.out.println("Enter the course ID");
        String courseId = Engine.in.nextLine();
        if(!Engine.allCourses.containsKey(courseId))
        {
            System.out.println("This course ID is not available");
            return;
        }
        Course currentCourse = Engine.allCourses.get(courseId);
        String answer;
        do {
            System.out.println("What is the info you want to change");
            System.out.println("1: Course Name");
            System.out.println("2: Course ID");
            System.out.println("3: Course current teacher");
            System.out.println("4: Course credit hours");
            System.out.println("5: Course max students");
            System.out.println("6: Course specialization");
            System.out.println("7: Exit");
            int choice  = InputHandler.promptNumericInput(1,7);
            if(choice == 7) break;
            switch (choice) {
                case 1 -> {
                    System.out.println("What is the new name: ");
                    String newName = Engine.in.nextLine();
                    currentCourse.setName(newName);
                }
                case 2 -> {
                    System.out.println("What is the new ID: ");
                    String newId = Engine.in.nextLine();
                    currentCourse.setId(newId);
                }
                case 3 -> {
                    System.out.println("What is the new teacher email: ");
                    String newTeacherEmail = Engine.in.nextLine();
                    Teacher currTeacher = (Teacher) Engine.allUsers.get(newTeacherEmail);
                    if (Engine.allUsers.containsKey(newTeacherEmail))
                    {
                        TeacherOptions teacherOptions = TeacherOptions.getOnlyInstance();
                        teacherOptions.addCourse(courseId, newTeacherEmail);
                    }
                    else
                        System.out.println("There is no teacher with this email");
                }
                case 4 -> {
                    System.out.println("What is the new credit hours: ");
                    int newCreditHours = Engine.in.nextInt();
                    currentCourse.setCreditHours(newCreditHours);
                }
                case 5 -> {
                    System.out.println("What is the new max students: ");
                    int maxStudents = Engine.in.nextInt();
                    currentCourse.setMaxStudents(maxStudents);
                }
                case 6 -> {
                    System.out.println("What is the new specialization: ");
                    String newSpecialization = Engine.in.nextLine();
                    currentCourse.setSpecialization(newSpecialization);
                }
            }
            System.out.println("Do you want to modify anything else for this course (y/n)");
            answer = InputHandler.promptBinaryInput();
        } while (answer.equals("y"));
    }
    //Done

    @Override
    public String toString() {
        return "SystemAdmin{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isProcessed=" + isProcessed +
                '}';
    }
}
