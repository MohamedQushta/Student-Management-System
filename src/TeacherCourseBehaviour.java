import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class TeacherCourseBehaviour implements CourseBehaviour {

    @Override
    public void addCourse(String courseID, String teacherEmail) {
        Teacher currTeacher = (Teacher) Engine.allUsers.get(teacherEmail);
        if(Engine.allCourses.containsKey(courseID))
        {
            Course courseToBeAdded = Engine.allCourses.get(courseID);
            for (String currCourse : currTeacher.currCourses) {
                if (courseID.equals(currCourse)) {
                    System.out.println("This teacher already teaches this course");
                    return;
                }
            }
            if (currTeacher.currCourses.size() > 2)
            {
                System.out.println("This teacher already teaches three courses");
            }
            else if(currTeacher.getSpecialization().equals(courseToBeAdded.getSpecialization()) )
            {
                currTeacher.currCourses.add(courseID);
                courseToBeAdded.setCurrTeacher(currTeacher);
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

    @Override
    public void showAvailableCourses(String teacherEmail) {
        Teacher currTeacher = (Teacher) Engine.allUsers.get(teacherEmail);
        Collection<Course> allCourses = Engine.allCourses.values();
        Stream<Course> allCoursesStream = allCourses.stream();
        Stream <Course> filteredCourses = allCoursesStream.filter(
                course -> course.getSpecialization().equals(currTeacher.getSpecialization()) &&
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
}
