import java.util.List;

public class StudentCourseStrategy implements CourseStrategy {

    @Override
    public void addCourse(String courseId, String studentEmail) {
        Student currStudent = (Student) Engine.allUsers.get(studentEmail);
        if(Engine.allCourses.containsKey(courseId)) //Check if the course exists
        {
            Course courseToBeAdded = Engine.allCourses.get(courseId);
            if(courseToBeAdded.getCurrStudentsNum() + 1 > courseToBeAdded.getMaxStudents())
            {
                System.out.println("Sorry, this course is full");
                return;
            }
            if(currStudent.allCourses.containsKey(courseId) && !currStudent.allCourses.get(courseId).equals('F'))
            {
                System.out.println("Student: " + currStudent.getName() + " has already taken this course and passed it");
                return;
            }
            if(currStudent.getCurrCourses().containsKey(courseId)) //Check if the student already has this course added
            {
                System.out.println("Student: " + currStudent.getName() + " already has this course registered");
            }

            else
            {
                double courseCost = courseToBeAdded.getCreditHrs() * SystemAdmin.crHrCost;
                if(currStudent.getBalance() >= courseCost || currStudent.isAided())
                {
                    currStudent.currCourses.put(courseId, 'I');
                    currStudent.allCourses.put(courseId, 'I');
                    double studentCurrBalance = currStudent.getBalance();
                    if(!currStudent.isAided())
                    {
                        double newBalance = studentCurrBalance - courseCost;
                        currStudent.setBalance(newBalance);
                    }
                    courseToBeAdded.addStudent(currStudent.email);
                    currStudent.setCurrCreditHrs(currStudent.getCurrCreditHrs() + courseToBeAdded.getCreditHrs());
                    if(currStudent.getCurrCreditHrs() >= 3)
                        currStudent.setReadyForSemester(true);
                    currStudent.setTotalCreditHrs(currStudent.getTotalCreditHrs() + courseToBeAdded.getCreditHrs());
                    System.out.println("Course: " + courseToBeAdded.getName() +  " has been added successfully to this student");
                }
                else
                {
                    System.out.println("Student: " + currStudent.getName() + " Has insufficient balance to purchase course " + courseToBeAdded);
                }
            }
        }
        else
        {
            System.out.println("This course ID is not available");
        }
    }

    @Override
    public void showAvailableCourses(String studentEmail) {
        Student currStudent = (Student) Engine.allUsers.get(studentEmail);
        List<Course> availableCourses = Engine.allCourses.values().stream().
                filter(course -> !currStudent.currCourses.containsKey(course.getId()) && course.getCurrTeacher()!=null)
                .toList();
        if(availableCourses.isEmpty())
        {
            System.out.println("There are no available courses for this student now");
            return;
        }
        for (Course course : availableCourses)
        {
            System.out.println(course);
        }
    }

    @Override
    public void removeCourse(String studentEmail, String courseId) {
        Student currStudent = (Student) Engine.allUsers.get(studentEmail);
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

    @Override
    public void viewCurrCourses(String studentEmail) {
        Student currStudent = (Student) Engine.allUsers.get(studentEmail);
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
}
