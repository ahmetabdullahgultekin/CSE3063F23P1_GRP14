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
        Person person;
        String userName, password;

        System.out.println("\nLogin Page");
        System.out.print("Please enter your username: ");
        userName = input.next();
        System.out.print("Enter your password: ");
        password = input.next();

        person = department.getUserNamePersonMap().get(userName);
        if (person != null) { //Check if there is such user
            if (person.login(userName, password)) {
                switch (person.getClass().getSimpleName()) {
                    case "Student":
                        studentMenu((Student) person);
                        break;
                    case "Advisor":
                        advisorMenu((Advisor) person);
                        break;
                }
            }
        } else {
            System.out.println("There is no such user. Please try again!");
            loginMenu(department);
        }
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

