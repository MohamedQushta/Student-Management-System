import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class TeacherCourseStrategy implements CourseStrategy {

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

    @Override
    public void removeCourse(String teacherEmail, String courseID) {
        Teacher currTeacher = (Teacher) Engine.allUsers.get(teacherEmail);
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

    @Override
    public void viewCurrCourses(String teacherEmail) {
        Teacher currTeacher = (Teacher) Engine.allUsers.get(teacherEmail);
        if(currTeacher.currCourses.isEmpty())
            System.out.println("This teacher does not teach any course");
        for(String c : currTeacher.currCourses)
        {
            Course currCourse = Engine.allCourses.get(c);
            System.out.println(currCourse);
        }
    }
}
