import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
public class Student extends User implements Serializable {
    @Serial
    private static final long serialVersionUID = -8975606175870630255L;
    double balance;
    private boolean isAided = false;
    int currCreditHrs;
    private int totalCreditHrs;
    private boolean isReadyForSemester;
    public final HashMap<String, Character> currCourses = new HashMap<>();
    public final HashMap<String, Character> allCourses = new HashMap<>();


    public Student() {
        super();
        isProcessed = false;
        currCreditHrs = 0;
        isReadyForSemester = false;
    }

    public void setReadyForSemester(boolean readyForSemester) {
        isReadyForSemester = readyForSemester;
    }

    public void setAided(boolean aided) {
        isAided = aided;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public HashMap<String, Character> getCurrCourses() {
        return currCourses;
    }



    public double getBalance() {
        return balance;
    }

    public boolean isAided() {
        return isAided;
    }


    public void setCurrCreditHrs(int creditHrs) {
        currCreditHrs = creditHrs;
    }
    public int getCurrCreditHrs()
    {
        return currCreditHrs;
    }

    public void setTotalCreditHrs(int totalCreditHrs) {
        this.totalCreditHrs = totalCreditHrs;
    }

    public int getTotalCreditHrs() {
        return totalCreditHrs;
    }

    public boolean getReadyForSemester() {
        return isReadyForSemester;
    }


    @Override
    public String toString() {
        return "Student{" +
                "balance=" + balance +
                ", isAided=" + isAided +
                ", currCreditHrs=" + currCreditHrs +
                ", isReadyForSemester=" + isReadyForSemester +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isProcessed=" + isProcessed +
                '}';
    }
}
