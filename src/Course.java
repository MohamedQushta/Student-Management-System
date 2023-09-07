import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
public class Course implements Serializable{
    @Serial
    private static final long serialVersionUID = 3848013544502160153L;
    private String name;
    private String id;

    private Teacher currTeacher;
    public ArrayList<String> studentsEmails;
    private int maxStudents;
    private int currStudentsNum;
    private int creditHrs;
    private String specialization;

    public Course(String courseName,String courseId,  String specialization, int maxStudents, int crHrs) {
        name = courseName;
        id = courseId;
        this.specialization = specialization;
        this.maxStudents = maxStudents;
        this.creditHrs = crHrs;
        currStudentsNum = 0;
        studentsEmails = new ArrayList<>();
    }


    public ArrayList<String> getStudentIds() {
        return studentsEmails;
    }
    public String getSpecialization()
    {
        return specialization;
    }
    public int getCreditHrs()
    {
        return creditHrs;
    }
    public String getName()
    {
        return name;
}
    public int getMaxStudents()
    {
        return maxStudents;
    }
    public int getCurrStudentsNum()
    {
        return currStudentsNum;
    }
    public Teacher getCurrTeacher() {return currTeacher;}
    public String getId() {
        return id;
    }

    public void setName(String newName) {
        name = newName;
    }
    public void setCurrTeacher(Teacher teacher)
    {
        this.currTeacher = teacher;
    }
    public void setCreditHours(int creditHours) {
        creditHrs = creditHours;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }



    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", ID=" + id +
                ", currTeacher=" +( (currTeacher != null)? currTeacher.getName() : "No Teacher" )+
                ", maxStudents=" + maxStudents +
                ", currStudents=" + studentsEmails.size() +
                ", creditHrs=" + creditHrs +
                ", specialization='" + specialization + '\'' +
                '}';
    }
    public void refundAllStudents() {
        StudentOptions studentOptions = new StudentOptions();
        for(String studentEmail : studentsEmails)
        {
           Student currStudent = (Student) Engine.allUsers.get(studentEmail);
            studentOptions.refundCourse(id, studentEmail);
        }
        studentsEmails.clear();
    }
    public void addStudent(String email) {
        studentsEmails.add(email);
    }

    public void removeStudent(String email) {
        studentsEmails.remove(email);
    }


    public void setCurrStudents(int currStudentsNum) {
        this.currStudentsNum = currStudentsNum;
    }
}
