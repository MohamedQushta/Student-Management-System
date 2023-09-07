import java.io.Serial;
import java.io.Serializable;
public abstract class User implements Serializable {
    @Serial
    private static final long serialVersionUID = -8975606175870630255L;
    protected String name;
    protected String password;
    protected String email;
    protected boolean isProcessed;
    public UserType userType;


    public User() {
        name = "";
        password = "";
        email = "";
        boolean isProcessed = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean isProcessed() {
        return isProcessed;
    }


    public void setUserType(UserType userType) {
        this.userType = userType;
    }


}
