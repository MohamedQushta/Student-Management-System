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
    //DONE


    //Done

    //Done


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

    public String getSpecialization() {
        return specialization;
    }
}
