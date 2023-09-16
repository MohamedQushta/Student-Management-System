import java.io.IOException;

public class DataModificationFunctionality implements Runnable{
    private static boolean exit;
    Thread thread;
    StudentOptions studentOptions =StudentOptions.getOnlyInstance();
    DataModificationFunctionality()
    {
        thread = new Thread(this);
        exit = false;
    }

    @Override
    public void run() {
        while (!exit) {
            System.out.println("Do you want to sign up(1) or sign in(2)");
            int choice = InputHandler.promptNumericInput(1,2);
            switch (choice) {
                case 1 -> handlingSignUp();
                case 2 -> handlingSignIn();
            }
            try {
                FileHandler.updateAllData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handlingSignIn() {
        User currUser;
        currUser = InputHandler.signIn();
        if (!currUser.isProcessed())
            System.out.println("This user has not yet been processed by an admin");
        else {
            System.out.println("You have signed in and these are your options:");
            switch (currUser.userType) {
                case STUDENT -> StudentOptions.studentOptions((Student) currUser);
                case TEACHER -> TeacherOptions.teacherOptions((Teacher) currUser);
                case SYSTEMADMIN -> Engine.systemAdminOptions((SystemAdmin) currUser);
            }
        }
    }

    private void handlingSignUp() {
        User currUser;
        currUser = InputHandler.signUp();
        Engine.allUsers.put(currUser.getEmail(), currUser);
        System.out.println("user has been added successfully");
        if (currUser.userType != UserType.SYSTEMADMIN)
            Engine.pendingRequests.add(new Request(RequestType.ACCOUNTCREATION, currUser));
    }

    public static void stop()
    {
        exit = true;
    }
}
