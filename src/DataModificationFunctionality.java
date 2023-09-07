import java.io.IOException;

public class DataModificationFunctionality implements Runnable{
    private static boolean exit;
    Thread thread;
    DataModificationFunctionality()
    {
        thread = new Thread(this);
        exit = false;
    }

    @Override
    public void run() {
        while (!exit) {
            User currUser;
            System.out.println("Do you want to sign up(1) or sign in(2)");
            int choice = InputHandler.promptNumericInput(1,2);
            switch (choice) {
                case 1 -> {
                    currUser = InputHandler.signUp();
                    Engine.allUsers.put(currUser.getEmail(), currUser);
                    System.out.println("user has been added successfully");
                    if (currUser.userType != UserType.SYSTEMADMIN)
                        Engine.pendingRequests.add(new Request(RequestType.ACCOUNTCREATION, currUser));
                }
                case 2 -> {
                    currUser = InputHandler.signIn();
                    if (!currUser.isProcessed())
                        System.out.println("This user has not yet been processed by an admin");
                    else {
                        System.out.println("You have signed in and these are your options:");
                        switch (currUser.userType) {
                            case STUDENT -> StudentOptions.studentOptions((Student) currUser);
                            case TEACHER -> Engine.teacherOptions((Teacher) currUser);
                            case SYSTEMADMIN -> Engine.systemAdminOptions((SystemAdmin) currUser);
                        }
                    }
                }
            }
            try {
                FileHandler.updateAllData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void stop()
    {
        exit = true;
    }
}
