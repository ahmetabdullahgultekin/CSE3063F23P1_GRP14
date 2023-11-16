package iteration1;

import java.util.Scanner;


public class CourseRegistrationSystem implements IDisplayMenu {
    private Scanner input;
    private int choice;
    Department department;

    public void start()  {
        department = new Department("CSE");
        JSONReader jsonReader = new JSONReader();
        jsonReader.start(department);
        mainMenu(department);
    }


    /**
     * Displays the main menu and handles user input.
     *
     * @param department The department object to which the menu belongs.
     */
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

    /**
     * Displays the login menu and handles user authentication.
     *
     * @param department The department object to which the menu belongs.
     */
    public void loginMenu(Department department) {
        Person person;
        String userName, password;

        System.out.println("\nLogin Page");
        System.out.println("0. Back");
        System.out.print("Please enter your username: ");
        userName = input.next();
        if (userName.equals("0")) {
            mainMenu(department);
        }
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
            } else {
                System.out.println("Username or password is incorrect. Please try again!");
                loginMenu(department);
            }
        } else {
            System.out.println("There is no such user. Please try again!");
            loginMenu(department);
        }
    }

    /**
     * Displays the menu options for a student and handles user input.
     *
     * @param student The Student object for which the menu is being displayed.
     */
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

    /**
     * Displays the menu options for course selection and handles user input.
     *
     * @param student The Student object for which the menu is being displayed.
     */
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

    /**
     * Displays the menu options for the advisor and handles user input.
     *
     * @param advisor The Advisor object for which the menu is being displayed.
     */
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

    /**
     * Reads and returns user input as an integer.
     *
     * @return User input as an integer.
     */
    public int getInput() {
        try {
            input = new Scanner(System.in);
            choice = input.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid input, please enter a number");
            return -1;
        }
        return choice;
    }

    public void exitProgram() {
        JSONWriter jsonWriter = new JSONWriter();
        jsonWriter.start(department);
        input.close();
        System.exit(0);
    }

    /**
     * Prints the menu options for the Course Registration System.
     *
     * @param menuType The type of menu to be printed.
     */
    @Override
    public void printMenu(String menuType) {
        System.out.println("\nWelcome to the Course Registration System");
        System.out.println("Please select from the following options:");
        System.out.println("0. Exit");
        System.out.println("1. Login Page");
        System.out.print("Enter your choice: ");
    }
}
