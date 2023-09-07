import java.util.InputMismatchException;

public class InputHandler {
    public static int promptNumericInput(int start, int end)
    {
        int choice = 0;
        boolean flag;
        do {
            flag = false;
            try {
                choice = Engine.in.nextInt();
                Engine.in.nextLine();
                if (choice < start || choice > end) {
                    System.out.println("Please enter a number from " + start + " to " + end);
                } else
                    flag = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number from " + start + " to " + end);
                Engine.in.nextLine();
            }
        } while (!flag);
        return choice;
    }
    public static String promptBinaryInput()
    {
        String answer = Engine.in.nextLine();

        do {
            if(answer.equals("y") || answer.equals("n"))
            {
                return answer;
            }
            else
            {
                System.out.println("please enter \'y\' or \'n\'");
                answer = Engine.in.nextLine();
            }
        } while (true);
    }
    static void promptBasicInfo(User currUser) {

        System.out.println("Name: ");
        String name = Engine.in.nextLine();
        currUser.setName(name);

        System.out.println("Email: ");
        String email = Engine.in.nextLine();
        currUser.setEmail(email);
        boolean flag;
        do {
            if(Engine.allUsers.containsKey(email))
            {
                flag = false;
                System.out.println("This email already exists");
                System.out.println("Please enter another email: ");
                email = Engine.in.nextLine();
                continue;
            }
            flag = true;
        } while (!flag);

        System.out.println("Password: ");
        String password = Engine.in.nextLine();
        currUser.setPassword(password);
    }
    static User signUp() {
        do {
            System.out.println("Are you a student(1), teacher(2), or a system admin(3)?");
            int choice = InputHandler.promptNumericInput(1, 3);
            switch (choice) {
                case 1 -> {
                    Student currUser = new Student();
                    InputHandler.promptBasicInfo(currUser);
                    System.out.println("Balance: ");
                    double balance = Engine.in.nextDouble();
                    Engine.in.nextLine();
                    currUser.setBalance(balance);

                    currUser.setUserType(UserType.STUDENT);
                    Engine.allUsers.put(currUser.getEmail(), currUser);
                    return currUser;
                }
                case 2 -> {
                    Teacher currUser = new Teacher();
                    InputHandler.promptBasicInfo(currUser);
                    currUser.setUserType(UserType.TEACHER);
                    System.out.println("Specialization: ");
                    String specialization = Engine.in.nextLine();
                    currUser.setSpecialization(specialization);
                    if (Engine.allUsers.containsKey(currUser.getEmail())) {
                        System.out.println("This email already exists");
                        System.out.println("Please enter another email");
                        continue;
                    }
                    Engine.allUsers.put(currUser.getEmail(), currUser);
                    return currUser;
                }
                case 3 -> {
                    User currUser = new SystemAdmin();
                    InputHandler.promptBasicInfo(currUser);
                    if (Engine.allUsers.containsKey(currUser.getEmail())) {
                        System.out.println("This email already exists");
                        System.out.println("Please enter another email");
                        continue;
                    }
                    currUser.setUserType(UserType.SYSTEMADMIN);
                    return currUser;
                }
            }
        } while (true);
    }
    static User signIn() {
        do {
            User currUser;
            System.out.println("Enter your email: ");
            String email = Engine.in.nextLine();
            System.out.println("Enter your password: ");
            String password = Engine.in.nextLine();

            if (!Engine.allUsers.containsKey(email)) {
                System.out.println("There is no user with this email");
                System.out.println("Please enter another email and password");
            } else {
                if (Engine.allUsers.get(email).getPassword().equals(password)) {
                    currUser = Engine.allUsers.get(email);
                    if(currUser instanceof Student && ((Student) currUser).getReadyForSemester() == false &&
                            SemesterTimelineFunctionality.isRunning == true)
                    {
                        System.out.println("Sorry but this user's email has started the semester" +
                                "  with less than 9 credit hours and has been refunded for this semester");
                        System.out.println("Please enter another email and password");
                        continue;
                    }
                    return currUser;
                } else {
                    System.out.println("The password is incorrect, please try again");
                }
            }
        } while (true);
    }
}
