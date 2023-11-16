package iteration1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Student extends Person implements IDisplayMenu {

    //All attributes
    private Advisor advisor;
    private Transcript transcript;
    private byte gradeLevel;
    private boolean hasRequest;
    private List<Course> draft;
    private List<Course> availableCourses;

    //Implement Constructor
    public Student(int studentID, String name, String surname, String userName, String password, byte gradeLevel) {
        super(studentID, name, surname, userName, password);
        this.draft = new ArrayList<>();
        this.availableCourses = new ArrayList<>();
        this.gradeLevel = gradeLevel;
    }

    //Check if there is already a request awaiting approval
    //Check if the draft is empty
    /* Create a new Registration object and initialize it with the current
    student object (this) and draft information */
    public void sendRequest() {
        if (hasRequest) {
            System.out.println("\nYou already have a request waiting for approval");
        } else if (draft.isEmpty()) {
            System.out.println("\nYour draft is empty!");
        } else {
            Registration registration = new Registration(this, draft);
            registration.addRequest(advisor);
        }
    }

    //Implements the printMenu method from the IDisplayMenu interface.
    //Prints a menu based on the specified menu type.
    /* After determining whether the user is a student or an advisor
    based on the String value, print the relevant menu. */
    @Override
    public void printMenu(String menuType) {
        switch (menuType) {
            case "studentMenu":
                System.out.println("\nStudent Menu");
                System.out.println("Welcome " + this.getName() + " " + this.getSurname() + "!");
                System.out.println("Please select from the following options:");
                System.out.println("0. Exit");
                System.out.println("1. Course Selection Menu");
                System.out.println("2. View Transcript");
                System.out.println("3. Log out");
                System.out.print("Enter your choice: ");
                break;
            case "courseSelectionMenu":
                System.out.println("\nCourse Selection Menu");
                System.out.println("Please select from the following options:");
                System.out.println("0. Back");
                System.out.println("1. Course Status Check");
                System.out.println("2. Add Course");
                System.out.println("3. Drop Course");
                System.out.println("4. Send Request");
                System.out.println("5. Show request status");
                System.out.println("6. Log out");
                System.out.print("Enter your choice: ");
        }
    }

    public void addCourse() {
        // Add course logic
    }

    public void dropCourse() {
        // Drop course logic
    }

    //Checks if the student has a registration request waiting for advisor approval
    //and prints an appropriate message to the console.
    public void showRequestStatus() {
        if (hasRequest) {
            System.out.println("\nYour request is waiting for advisor approval");
        } else System.out.println("\nThere is no waiting request");
    }

    public List<Course> getAvailableCourses() {
        return availableCourses;
    }

    public ArrayList<Course> viewAvailableCourses() {
        //Create a function that captures the logic for viewing available courses based on specific conditions.
    }

    //Checks if the provided username and password match the student's credentials.
    //Overrides the abstract login method in the Person class.
    @Override
    boolean login(String userName, String password) {
        return this.getUserName().equals(userName) && this.getPassword().equals(password);
    }

    public String getDepartmentName() {
        return this.getDepartment().getDepartmentName();
    }

    public void setPassword(String password) {
        super.setPassword(password);
    }

    // Override the setUserName method to set the username by
    // calling the setUserName method from the inherited Person class.
    @Override
    public void setUserName(String userName) {
        super.setUserName(userName);
    }

    public Advisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }

    public byte getGradeLevel() {
        return gradeLevel;
    }

    public void setHasRequest(boolean hasRequest) {
        this.hasRequest = hasRequest;
    }

    public List<Course> getDraft() {
        return draft;
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public void setTranscript(Transcript transcript) {
        this.transcript = transcript;
    }

    public void setDraft(List<Course> draft) {
        this.draft = draft;
    }
}
