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
        student.printMenu("studentMenu");
        choice = getInput();
        if (choice == -1) {
            studentMenu(student);
        } else {
            switch (choice) {
                case 0:
                    System.out.println("Exiting from system");
                    exitProgram();
                    break;
                case 1:
                    courseSelectionMenu(student);
                    break;
                case 2:
                    student.getTranscript().showTranscript();
                    studentMenu(student);
                    break;
                case 3:
                    loginMenu(student.getDepartment());
                    break;
                default:
                    System.out.println("Invalid choice Please select again");
                    studentMenu(student);
                    break;
            }
        }
    }

    public void courseSelectionMenu(Student student) {
        student.printMenu("courseSelectionMenu");

        choice = getInput();
        if (choice == -1) {
            courseSelectionMenu(student);
        } else {
            switch (choice) {
                case 0:
                    studentMenu(student);
                    break;
                case 1:
                    student.getTranscript().courseStatusCheck();
                    courseSelectionMenu(student);
                    break;
                case 2:
                    student.addCourse();
                    courseSelectionMenu(student);
                    break;
                case 3:
                    student.dropCourse();
                    courseSelectionMenu(student);
                    break;
                case 4:
                    student.sendRequest();
                    courseSelectionMenu(student);
                    break;
                case 5:
                    student.showRequestStatus();
                    courseSelectionMenu(student);
                    break;
                case 6:
                    loginMenu(student.getDepartment());
                    break;
                default:
                    System.out.println("Invalid choice Please select again");
                    courseSelectionMenu(student);
                    break;
            }
        }
    }

    public void advisorMenu(Advisor advisor) {
        advisor.printMenu("advisorMenu");
        choice = getInput();
        if (choice == -1) {
            advisorMenu(advisor);
        } else {
            switch (choice) {
                case 0:
                    System.out.println("Exiting from system");
                    exitProgram();
                    break;
                case 1:
                    advisor.printRequests();
                    advisorMenu(advisor);
                    break;
                case 2:
                    loginMenu(advisor.getDepartment());
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

    public int getInput() {
     
    }

    public void exitProgram() {
    }

    public void printMenu(String menuType) {
   
    }
}

