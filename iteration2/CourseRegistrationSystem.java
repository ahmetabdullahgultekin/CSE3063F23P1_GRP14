package iteration2;

import java.util.Scanner;

/**
 * The CourseRegistrationSystem class implements the IDisplayMenu interface and
 * serves as the main driver for the Course Registration System.
 * It contains methods for displaying various menus and handling user input,
 * as well as methods for starting and exiting the system.
 * <p>
 * The class contains the following private fields:
 * - Scanner input: A Scanner object used for reading user input from the console.
 * - int choice: An integer used for storing the user's menu choice.
 */
public class CourseRegistrationSystem implements IDisplayMenu {
    private Scanner input;
    private int choice;
    private University university;

    /**
     * This method is the entry point for the Course Registration System.
     * It then creates a new JSONReader object and calls its start method to read data from JSON files and
     * populate the department object.
     * Finally, it displays the main menu of the system by calling the mainMenu method.
     */
    public void start() {
        university = new University("Marmara University");
        JSONReader jsonReader = new JSONReader();
        jsonReader.readDepartments(university);
        for (Department department : university.getDepartments()) {
            jsonReader.start(department);
        }
        mainMenu();
    }


    /**
     * This method displays the main menu of the Course Registration System and handles user input.
     * The main menu provides two options:
     * - 0: Exit the system.
     * - 1: Navigate to the login page.
     * <p>
     * If the user enters an invalid choice, they are prompted to select again.
     * If the user chooses to exit, the system is terminated.
     * If the user chooses to navigate to the login page, the loginMenu method is called.
     */
    public void mainMenu() {
        // Print the main menu options.
        printMenu("mainMenu");

        // Get the user's choice.
        choice = getInput();
        // If the user's input is invalid, display the main menu again.
        // Handle the user's choice.
        switch (choice) {
            case 0:
                // If the user chooses to exit, print a message and terminate the program.
                exitProgram();
                break;
            case 1:
                // If the user chooses to navigate to the login page, call the loginMenu method.
                loginMenu();
                break;
            case -1:
                // If the user enters string or -1 give error.
                ConsoleColours.paintRedMenu();
                System.out.println("Please enter valid number!");
                mainMenu();
                break;
            default:
                // If the user's choice is not recognized, print a message and display the main menu again.
                ConsoleColours.paintRedMenu();
                System.out.println("Invalid choice. Please select again!");
                mainMenu();
                break;
        }
    }

    /**
     * This method displays the login menu of the Course Registration System and handles user authentication.
     * The login menu prompts the user to enter their username and password.
     * If the username is "0", the user is navigated back to the main menu.
     * If the username exists in the university's userNamePersonMap, the method attempts to authenticate the user.
     * If the authentication is successful, the user is navigated to the appropriate menu
     * based on their role (student or advisor).
     * If the authentication fails, the user is informed that the username or password is incorrect and
     * the login menu is displayed again.
     * If the username does not exist in the university's userNamePersonMap,
     * the user is informed that there is no such user and the login menu will be displayed again.
     */
    public void loginMenu() {
        Person person;
        String userName, password;

        ConsoleColours.paintBlueMenu();
        System.out.println("Login Page");
        System.out.println("¨¨¨¨¨¨¨¨¨¨\n");
        System.out.println(" Back -> 0");
        System.out.println("¨¨¨¨¨¨¨¨¨¨");

        ConsoleColours.paintGreenMenu();
        System.out.print("Please enter your username: \n");
        userName = input.next();

        //User selected to go back
        if (userName.equals("0")) {
            mainMenu();
            return;
        }

        System.out.print("Enter your password: \n");
        password = input.next();
        ConsoleColours.paintBlueMenu();

        person = university.getUserNamePersonMap().get(userName);
        if (person != null) { //Check if there is such user
            if (person.login(userName, password)) {
                if (person instanceof Student) {
                    studentMenu((Student) person);
                } else if (person instanceof Advisor) {
                    advisorMenu((Advisor) person);
                }
            } else {
                ConsoleColours.paintRedMenu();
                System.out.println("Username or password is incorrect. Please try again!");
                loginMenu();
            }
        } else {
            ConsoleColours.paintRedMenu();
            System.out.println("There is no such user. Please try again!");
            loginMenu();
        }
    }

    /**
     * This method displays the student menu of the Course Registration System and handles user input.
     * The student menu provides the following options:
     * - 0: Exit the system.
     * - 1: Navigate to the course selection menu.
     * - 2: Show the student's transcript.
     * - 3: Log out and navigate back to the login page.
     * <p>
     * If the user enters an invalid choice, they are prompted to select again.
     * If the user chooses to exit, the system is terminated.
     * If the user chooses to navigate to the course selection menu, the courseSelectionMenu method is called.
     * If the user chooses to show the transcript, the student's transcript is displayed and
     * the student menu is displayed again.
     * If the user chooses to log out, the loginMenu method will be called.
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
                    loginMenu();
                    break;
                default:
                    ConsoleColours.paintRedMenu();
                    System.out.println("Invalid choice Please select again!");
                    studentMenu(student);
                    break;
            }
        }
    }

    /**
     * This method displays the course selection menu of the Course Registration System and handles user input.
     * The course selection menu provides the following options:
     * - 0: Navigate back to the student menu.
     * - 1: Check the status of the student's courses.
     * - 2: Add a course to the student's draft.
     * - 3: Drop a course from the student's draft.
     * - 4: Send a course registration request.
     * - 5: Show the status of the student's registration request.
     * - 6: Log out and navigate back to the login page.
     * <p>
     * If the user enters an invalid choice, they are prompted to select again.
     * If the user chooses to navigate back to the student menu, the studentMenu method is called.
     * If the user chooses to check the status of their courses,
     * the courseStatusCheck method of the student's transcript is called.
     * If the user chooses to add a course, the addCourse method of the student is called.
     * If the user chooses to drop a course, the dropCourse method of the student is called.
     * If the user chooses to send a request, the sendRequest method of the student will be called.
     * If the user chooses to show the request status, the showRequestStatus method of the student will be called.
     * If the user chooses to log out, the loginMenu method is called.
     *
     * @param student The Student object for which the menu is being displayed.
     */
    public void courseSelectionMenu(Student student) {
        student.printMenu("courseSelectionMenu");
        choice = getInput();

        switch (choice) {
            case 0:
                studentMenu(student);
                break;
            case 1:
                student.getTranscript().courseStatusCheck();
                courseSelectionMenu(student);
                break;
            case 2:
                student.addCourseToDraft();
                courseSelectionMenu(student);
                break;
            case 3:
                student.removeCourseFromDraft();
                courseSelectionMenu(student);
                break;
            case 4:
                student.showRequestStatus();
                courseSelectionMenu(student);
                break;
            case 5:
                student.sendRequest();
                courseSelectionMenu(student);
                break;
            case 6:
                loginMenu();
                break;
            case -1:
                // If the user enters string or -1 give error.
                ConsoleColours.paintRedMenu();
                System.out.println("Please enter valid number!");
                courseSelectionMenu(student);
                break;
            default:
                ConsoleColours.paintRedMenu();
                System.out.println("Invalid choice Please select again!");
                courseSelectionMenu(student);
                break;
        }
    }

    /**
     * This method displays the advisor menu of the Course Registration System and handles user input.
     * The advisor menu provides the following options:
     * - 0: Exit the system.
     * - 1: Print the advisor's requests.
     * - 2: Log out and navigate back to the login page.
     * <p>
     * If the user enters an invalid choice, they are prompted to select again.
     * If the user chooses to exit, the system is terminated.
     * If the user chooses to print the advisor's requests,
     * the printRequests method of the advisor is called and the advisor menu is displayed again.
     * If the user chooses to log out, the loginMenu method is called.
     *
     * @param advisor The Advisor object for which the menu is being displayed.
     */
    public void advisorMenu(Advisor advisor) {
        advisor.printMenu("advisorMenu");
        choice = getInput();

        switch (choice) {
            case 0:
                exitProgram();
                break;
            case 1:
                advisor.printRequests();
                advisorMenu(advisor);
                break;
            case 2:
                loginMenu();
                break;
            case -1:
                // If the user enters string or -1 give error.
                ConsoleColours.paintRedMenu();
                System.out.println("Please enter valid number!");
                advisorMenu(advisor);
                break;
            default:
                ConsoleColours.paintRedMenu();
                System.out.println("Invalid choice!");
                advisorMenu(advisor);
                break;
        }
    }

    /**
     * This method reads user input from the console and returns it as an integer.
     * If the user enters a non-integer input, an error message is printed and the method returns -1.
     *
     * @return The user's input as an integer, or -1 if the input is invalid.
     */
    public int getInput() {
        try {
            input = new Scanner(System.in);
            choice = input.nextInt();
        } catch (Exception e) {
            ConsoleColours.paintRedMenu();
            System.out.println("Invalid input, please do not enter a nonnumeric input!");
            return -1;
        }
        return choice;
    }

    /**
     * This method is used to exit the Course Registration System.
     * Before exiting, it writes the current state of the system to JSON files using the JSONWriter class.
     * It then closes the Scanner object used for reading user input and terminates the program.
     */
    public void exitProgram() {
        //Print Message
        ConsoleColours.paintYellowMenu();
        System.out.println("Exiting from system...");
        // Create a new JSONWriter instance.
        JSONWriter jsonWriter = new JSONWriter();
        // Write the current state of the university to JSON files.
        jsonWriter.start(university);
        // Close the Scanner object.
        input.close();
        // Terminate the program.
        System.exit(0);
    }

    /**
     * This method prints the main menu options for the Course Registration System.
     * The main menu provides two options:
     * - 0: Exit the system.
     * - 1: Navigate to the login page.
     * The user is then prompted to enter their choice.
     *
     * @param menuType The type of menu to be printed. This parameter is currently not used in the method.
     */
    @Override
    public void printMenu(String menuType) {
        ConsoleColours.paintBlueMenu();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("  Marmara University Course Registration System  ");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        System.out.println("Please select from the following options:");
        System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
        System.out.println("                                Exit -> 0");
        System.out.println("                          Login Page -> 1");
        System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
        ConsoleColours.paintGreenMenu();
        System.out.print("Enter your choice: \n");
    }


    public void addToSchedule(Course course, Student student) {
        int i = 0, j = 0;
        switch (course.getDay()) {
            case "Monday" -> i = 0;
            case "Tuesday" -> i = 1;
            case "Wednesday" -> i = 2;
            case "Thursday" -> i = 3;
            case "Friday" -> i = 4;
        }
        switch (course.getHour()) {
            case 8 -> j = 0;
            case 9 -> j = 1;
            case 10 -> j = 2;
            case 11 -> j = 3;
            case 13 -> j = 4;
            case 14 -> j = 5;
            case 15 -> j = 6;
            case 16 -> j = 7;
        }
        student.getSchedule()[i][j] = course;
    }

    /*
        Menu patterns
        System.out.println("||||||||||||||||||||||||||||||||||||||||||");
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println("");
        System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("``````````````````````````````````````````");
        System.out.println("´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´");
        System.out.println("------------------------------------------");
        System.out.println("''''''''''''''''''''''''''''''''''''''''''");
        System.out.println("__________________________________________");
     */
}
