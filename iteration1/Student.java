package iteration1;

import java.util.*;

public class Student extends Person implements IDisplayMenu {

    //All attributes
    private Advisor advisor;
    private Transcript transcript;
    private byte gradeLevel;
    private boolean hasRequest;
    private List<Course> selectedCourses;
    private List<Course> draft;
    private List<Course> availableCourses;
    private Map<Course, CourseSection> courseSections;

    //Implement Constructor
    public Student(int studentID, String name, String surname, String userName, String password, byte gradeLevel) {
        super(studentID, name, surname, userName, password);
        this.selectedCourses = new ArrayList<>();
        this.draft = new ArrayList<>();
        this.availableCourses = new ArrayList<>();
        this.courseSections = new HashMap<>();
        this.gradeLevel = gradeLevel;
    }

    public void sendRequest() {
        // Send request logic
    }

    //Implements the printMenu method from the IDisplayMenu interface.
    //Prints a menu based on the specified menu type.
    @Override
    public void printMenu(String menuType) {
        switch (menuType) {
            case "studentMenu":
                // Student menu printing logic
                break;
            case "courseSelectionMenu":
                // Course selection menu printing logic
                break;
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
            System.out.println("Your request is waiting for advisor approval");
        } else System.out.println("There is no waiting request");
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

    public boolean isHasRequest() {
        return hasRequest;
    }

    public void setHasRequest(boolean hasRequest) {
        this.hasRequest = hasRequest;
    }

    public List<Course> getSelectedCourses() {
        return selectedCourses;
    }

    public List<Course> getDraft() {
        return draft;
    }

    public Map<Course, CourseSection> getCourseSections() {
        return courseSections;
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public void setTranscript(Transcript transcript) {
        this.transcript = transcript;
    }
}
