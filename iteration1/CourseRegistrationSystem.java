package iteration1;



public class CourseRegistrationSystem implements IDisplayMenu {
    private Scanner input;
    private int choice;

    public void start() throws IOException {
       
    }

    public void mainMenu(Department department) {
        printMenu("mainMenu");

        choice = getInput();
        if (choice == -1) {
            mainMenu(department);
        } else {
            switch (choice) {
                case 0:
                    System.out.println("Exiting from system");
                    exitProgram();
                    break;
                case 1:
                    loginMenu(department);
                    break;
                default:
                    System.out.println("Invalid choice Please select again");
                    mainMenu(department);
                    break;
            }
        }
    }

    public void loginMenu(Department department) {
     
    }

    public void studentMenu(Student student) {
      
    }

    public void courseSelectionMenu(Student student) {
     
    }

    public void advisorMenu(Advisor advisor) {
      
    }

    public int getInput() {
     
    }

    public void exitProgram() {
    }

    public void printMenu(String menuType) {
   
    }
}

