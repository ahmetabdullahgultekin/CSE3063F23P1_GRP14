package iteration2;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an adviser who can log in, reach request of the student, approve and reject request of the student
 * Inherits from the Person class and implements the IDisplayMenu interface.
 */
public class Advisor extends Lecturer implements IDisplayMenu {

    private List<Student> studentsAdvised;
    private List<Registration> requestList;
    private int requestNumber;
    private Notification notification;


    /**
     * Creates a new advisor object with the given parameters
     *
     * @param ID       the advisor's ID
     * @param name     the advisor's name
     * @param surname  the advisor's surname
     * @param userName the advisor's userName
     * @param password the advisor's password
     */
    public Advisor(int ID, String name, String surname, String userName, String password) {
        super(ID, name, surname, userName, password);
        studentsAdvised = new ArrayList<>();
        requestList = new ArrayList<>();
    }

    /**
     * The method prints the first five request of students
     * Then prompts to advisor choose one of them to evaluate
     * Then prints the detailed request of selected student
     * And calls replyRequest method
     */
    public void printRequests() {
        CourseRegistrationSystem controller = new CourseRegistrationSystem();
        ConsoleColours.paintBlueMenu();
        System.out.println("Request Approval Menu:");
        System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨\n");
        System.out.println("             Back -> 0");
        System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
        if (!requestList.isEmpty()) {
            System.out.println("Request(s) Listed Below:");
            System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");

            ConsoleColours.paintPurpleMenu();
            for (int i = 0; i < requestList.size(); i++) {
                System.out.println((i + 1) + ". " + requestList.get(i).getStudent().getName()
                        + " " + requestList.get(i).getStudent().getSurname());
                System.out.println("``````````````````````````````````````````````");
            }

            ConsoleColours.paintGreenMenu();
            System.out.println("Enter the request number you want to evaluate: ");
            requestNumber = controller.getInput();

            ConsoleColours.paintBlueMenu();
            if (requestNumber <= requestList.size() && requestNumber >= 1) {
                Registration request = requestList.get(requestNumber - 1);
                System.out.println(request.getStudent().getName() + " "
                        + request.getStudent().getSurname() + " wants to take these courses:");
                ConsoleColours.paintPurpleMenu();
                for (Course course : request.getCourses()) {
                    if (!(request.getStudent().getTranscript().getCourseGradeMap().get(course) != null
                            && request.getStudent().getTranscript().getCourseGradeMap().get(course).getLast() == null))
                        System.out.println(course.getCourseCode() + " " + course.getCourseName());
                }
                ConsoleColours.paintBlueMenu();
                System.out.println(request.getStudent().getName() + " "
                        + request.getStudent().getSurname() + " wants to drop these courses:");
                ConsoleColours.paintPurpleMenu();
                for (Course course : request.getCourses()) {
                    if ((request.getStudent().getTranscript().getCourseGradeMap().get(course) != null
                            && request.getStudent().getTranscript().getCourseGradeMap().get(course).getLast() == null))
                        System.out.println(course.getCourseCode() + " " + course.getCourseName());
                }
                replyRequests();
            } else if (requestNumber > requestList.size() || requestNumber < 0) {
                ConsoleColours.paintRedMenu();
                System.out.println("Invalid choice Please select again!");
                printRequests();
            }
        } else {
            ConsoleColours.paintYellowMenu();
            System.out.println("There is not a Waiting Request!");
            ConsoleColours.resetColour();
            System.out.println();
        }
    }

    /**
     * The method prompts to adviser select an option for request
     * Calls the appropriate methods from registration class
     */
    public void replyRequests() {
        CourseRegistrationSystem controller = new CourseRegistrationSystem();

        ConsoleColours.paintBlueMenu();
        System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
        System.out.println("               Back -> 0");
        System.out.println("    Approve Request -> 1");
        System.out.println("     Reject Request -> 2");
        System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
        ConsoleColours.paintGreenMenu();
        System.out.println("Please select an option:");

        int choice = controller.getInput();
        switch (choice) {
            case 1:
                requestList.get(requestNumber - 1).approveRequest();
                requestList.remove(requestNumber - 1);
                printRequests();
                break;
            case 2:
                requestList.get(requestNumber - 1).rejectRequest();
                requestList.remove(requestNumber - 1);
                printRequests();
                break;
            case 0:
                printRequests();
                break;
            default:
                ConsoleColours.paintRedMenu();
                System.out.println("Invalid choice. Please select again!");
                replyRequests();
                break;
        }
    }

    /**
     * Prints the advisor menu
     * Prompts to advisor select an action
     */
    @Override
    public void printMenu(String menuType) {
        ConsoleColours.paintBlueMenu();
        System.out.println("Welcome " + getName() + " " + getSurname() + "!");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        ConsoleColours.paintYellowMenu();
        String message = "You have " + requestList.size() + " request(s).";
        System.out.println(message);
        ConsoleColours.paintBlueMenu();

        System.out.println("Please select from the following options:");
        System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
        System.out.println("                                Exit -> 0");
        System.out.println("                       List requests -> 1");
        System.out.println("                             Log out -> 2");
        System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
        ConsoleColours.paintGreenMenu();
        System.out.print("Enter your choice: \n");
    }

    /**
     * The method to check whether the userName and password match
     */
    @Override
    public boolean login(String userName, String password) {
        return getUserName().equals(userName) && getPassword().equals(password);
    }

    public List<Student> getStudentsAdvised() {
        return studentsAdvised;
    }

    public List<Registration> getRequests() {
        return requestList;
    }

    public List<Registration> getRequestList() {
        return requestList;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
