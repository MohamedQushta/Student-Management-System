import java.io.IOException;

public class SemesterTimelineFunctionality implements Runnable {
    private final int currentSemester;
    private int currentWeek;
    Thread dataModificationThread;
    public static boolean isRunning;
    SemesterTimelineFunctionality(int currentSemester)
    {
        this.currentSemester = currentSemester;
        currentWeek = 1;
    }
    @Override
    public void run() {

        System.out.println("Semester " + currentSemester + " has now been started");
        while (currentWeek <= 4) {
            System.out.println("Week " + currentWeek + " has just started");
            System.out.println("Do you want to modify anything? (y/n)");
            checkIfUserWantsToModify();
            try {
                //TODO IMPLEMENT USING SCHEDULER
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            currentWeek++;
        }
        try {
            StudentOptions.randomizeAllStudentsGrades();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Semester " + currentSemester + " Has been finished, would you like to view the data? (y,n)");
        checkIfUserWantsToModify();
        Engine.clearSemesterData();
    }

    private void checkIfUserWantsToModify() {
        String ans = InputHandler.promptBinaryInput();
        if(ans.equals("y"))
        {
            DataModificationFunctionality dataModificationFunctionality = new DataModificationFunctionality();
            dataModificationThread = new Thread(dataModificationFunctionality);
            dataModificationThread.start();
            try {
                dataModificationThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

