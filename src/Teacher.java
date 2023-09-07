import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class Teacher extends User implements Serializable {
    @Serial
    private static final long serialVersionUID = -8975606175870630255L;
    private String specialization;
    public final ArrayList<String> currCourses = new ArrayList<>();

    public Teacher() {
        super();
        isProcessed = false;
//        currCourses = new ArrayList<>();
    }

    //Done
    public void addCourse() {
        System.out.println("Enter the course id: ");
        String courseID = Engine.in.nextLine();
        if(Engine.allCourses.containsKey(courseID))
        {
            Course courseToBeAdded = Engine.allCourses.get(courseID);
            for (String currCourse : currCourses) {
                if (courseID.equals(currCourse)) {
                    System.out.println("This teacher already teaches this course");
                    return;
                }
            }
            if (currCourses.size() > 2)
            {
                System.out.println("This teacher already teaches three courses");
            }
            else if(specialization.equals(courseToBeAdded.getSpecialization()) )
            {
                currCourses.add(courseID);
                courseToBeAdded.setCurrTeacher(this);
                System.out.println("This course has been assigned to this teacher successfully");
            }
            else
            {
                System.out.println("This teacher's specialization isn't compatible with this course");
            }
        }
        else
        {
            System.out.println("This course ID is not available");
        }

    }
    //DONE
    public void viewAvailableCourses()
    {
        Collection<Course> allCourses = Engine.allCourses.values();
        Stream< Course > allCoursesStream = allCourses.stream();
        Stream <Course> filteredCourses = allCoursesStream.filter(
                course -> course.getSpecialization().equals(specialization) &&
                course.getCurrTeacher() == null);
        List<Course> availableCourses = filteredCourses.toList();
        if(availableCourses.isEmpty())
            System.out.println("This teacher has no courses available");
        else {
            for (Course c : availableCourses) {
                System.out.println(c);
            }
        }

    }
    public void withdrawCourse() {
        System.out.println("Enter the course id");
        String courseID = Engine.in.nextLine();
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
        for (String currentCourseId : currCourses) {
            Course currCourse = Engine.allCourses.get(currentCourseId);
            idToBeWithdrawn = null;
            if (courseToBeWithdrawn.getId().equals(currCourse.getId())) {
                idToBeWithdrawn = currCourse.getId();
            }
        }
        if (idToBeWithdrawn != null) {
            currCourses.remove(idToBeWithdrawn);
            courseToBeWithdrawn.refundAllStudents();
            courseToBeWithdrawn.setCurrTeacher(null);
        }
        else
        {
            System.out.println("This teacher does not teach this course");
        }
    }
    //Done
    public void viewRegisteredStudents()
    {
        if(currCourses.isEmpty())
        {
            System.out.println("This teacher is teaches no courses currently");
            return;
        }
        for(String courseId : currCourses)
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
    //Done
    public void viewCurrCourses()
    {
        if(currCourses.isEmpty())
            System.out.println("This teacher does not teach any course");
        for(String c : currCourses)
        {
            Course currCourse = Engine.allCourses.get(c);
            System.out.println(currCourse);
        }
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", isProcessed=" + isProcessed +
                '}';
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
