package iteration2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//import java.util.Scanner;

//Inherits from the Person class and implements the ILogin interface
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
        ConsoleColours.paintYellowMenu();
        if (hasRequest) {
            System.out.println("You already have a request waiting for approval.");
            ConsoleColours.resetColour();
            System.out.println();
        } else if (draft.isEmpty()) {
            System.out.println("Your draft is empty!");
            ConsoleColours.resetColour();
            System.out.println();
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
                ConsoleColours.paintBlueMenu();
                System.out.println("Welcome " + this.getName() + " " + this.getSurname() + "!");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                System.out.println("Please select from the following options:");
                System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
                System.out.println("                         Exit System -> 0");
                System.out.println("               Course Selection Menu -> 1");
                System.out.println("                     View Transcript -> 2");
                System.out.println("                             Log out -> 3");
                System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
                ConsoleColours.paintGreenMenu();
                System.out.print("Enter your choice: \n");
                break;
            case "courseSelectionMenu":
                ConsoleColours.paintBlueMenu();
                System.out.println("Course Selection Menu");
                System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨\n");
                System.out.println("Please select from the following options:");
                System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
                System.out.println("                   Back to Main Menu -> 0");
                System.out.println("                 Course Status Check -> 1");
                System.out.println("                 Add Course to Draft -> 2");
                System.out.println("            Delete Course from Draft -> 3");
                System.out.println("                 Show request status -> 4");
                System.out.println("                        Send Request -> 5");
                System.out.println("                             Log out -> 6");
                System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
                ConsoleColours.paintGreenMenu();
                System.out.print("Enter your choice: \n");
        }
    }

    // The 'addCourse' function displays all accessible courses and adds these courses to
    // the student's draft, which is a private attribute.
    public void addCourse() {
        CourseRegistrationSystem controller = new CourseRegistrationSystem();

        //If a request has been sent, terminate the function.
        if (hasRequest) {
            ConsoleColours.paintRedMenu();
            System.out.println("You can not add lecture because you have a request waiting for approval.");
            return;
        }
        //Call the calculateNumberOfCourses() function to limit with the maximum number of courses.
        int numberOfCourses = calculateNumberOfCourses();
        if (numberOfCourses >= this.getDepartment().getMaxCourseNumber()) {
            ConsoleColours.paintRedMenu();
            System.out.println("You can not add more lectures.");
            return;
        }

        //Call the viewAvailableCourses() function to see all available courses.
        ConsoleColours.paintBlueMenu();
        System.out.println("Add Course Menu");
        System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨\n");
        viewAvailableCourses();

        System.out.println("      Back -> 0");
        System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
        System.out.println("Here is the available courses:");
        System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
        ConsoleColours.paintPurpleMenu();

        //Print Available Courses
        for (int i = 0; i < this.getAvailableCourses().size(); i++) {
            System.out.println((i + 1) + ". " + this.getAvailableCourses().get(i).getCourseCode() +
                    " - " + this.getAvailableCourses().get(i).getCourseName());
            System.out.println("´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´");
        }


        //Take input from the user and make assignments to the courses.
        ConsoleColours.paintGreenMenu();
        System.out.print("Choose number between 1 to " + this.getAvailableCourses().size() + " to add course: \n");

        int userNumberInput = controller.getInput();
        //Back to Course Section Menu with return value

            //If incorrect input is entered, ask for input again.
        if (1 <= userNumberInput && userNumberInput <= this.getAvailableCourses().size()) {
            this.draft.add(this.getAvailableCourses().get(userNumberInput - 1));
            this.getAvailableCourses().remove(userNumberInput - 1);
            addCourse();
        } else if (userNumberInput < 0 || userNumberInput > this.getAvailableCourses().size()){
            ConsoleColours.paintRedMenu();
            System.out.println("Invalid input, please enter a valid number!");
            addCourse();
        }
    }

    // This method calculates the total number of courses, considering both the drafted courses
    // and the courses in the transcript for which the student has not received a grade yet.
    public int calculateNumberOfCourses() {
        int numberOfCourses = draft.size();
        for (Course course : this.getTranscript().getCourseGradeMap().keySet()) {
            if (this.getTranscript().getCourseGradeMap().get(course) == null) {
                numberOfCourses++;
            }
        }
        return numberOfCourses;
    }

    public void dropCourse() {
        CourseRegistrationSystem controller = new CourseRegistrationSystem();

        // Check if the student has already sent a request
        if (hasRequest) {
            ConsoleColours.paintRedMenu();
            System.out.println("You can not remove lecture because you have a request waiting for approval.");
            return;
        }
        // Check if the draft is empty.
        if (draft.isEmpty()) {
            ConsoleColours.paintYellowMenu();
            System.out.println("Your draft is empty!");
            ConsoleColours.resetColour();
            System.out.println();
        } else {
            ConsoleColours.paintBlueMenu();
            System.out.println("Remove Course from Draft");
            System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨\n");
            System.out.println("               Back -> 0");
            System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
            System.out.println("Select the course you want to remove:");
            System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
            ConsoleColours.paintPurpleMenu();

            // Manipulation for displaying and removing courses in the menu.
            for (int i = 0; i < draft.size(); i++) {
                System.out.println((i + 1) + ". " + draft.get(i).getCourseCode() +
                        " - " + draft.get(i).getCourseName());
                System.out.println("´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´");
            }
            // Remove input index element -1 from the draft list if the input is valid and in the appropriate range.
            ConsoleColours.paintGreenMenu();
            System.out.print("Choose number between 1 to " + this.draft.size() + " to remove course: \n");

            int userNumberInput = controller.getInput();

            if (userNumberInput <= draft.size() && userNumberInput >= 1){
                draft.remove(userNumberInput - 1);
                dropCourse();
            }
            else if(userNumberInput > draft.size() || userNumberInput < 0) {
                ConsoleColours.paintRedMenu();
                System.out.println("Invalid input, please enter a number");
                dropCourse();
            }
        }
    }

    //Checks if the student has a registration request waiting for advisor approval
    //and prints an appropriate message to the console.
    public void showRequestStatus() {
        ConsoleColours.paintYellowMenu();
        if (hasRequest) {
            System.out.println("Your request is waiting for advisor approval.");
        } else {
            System.out.println("There is no waiting request!");
            ConsoleColours.resetColour();
            System.out.println();
            return;
        }

        ConsoleColours.paintBlueMenu();
        System.out.println("Your draft: ");
        ConsoleColours.paintPurpleMenu();
        for (Course course : draft){
            System.out.println(course.getCourseCode() + "-" + course.getCourseName());
        }
        ConsoleColours.resetColour();
        System.out.println();
    }

    public List<Course> getAvailableCourses() {
        return availableCourses;
    }

    public void viewAvailableCourses() {
        /*
        Collect all course sections from the department, and for each course, store them in
        a map<course,grade> along with the grade value. Also, keep track of course sections
        with their respective courses.
        */
        ArrayList<Course> availableCourses = new ArrayList<>();
        List<CourseSection> allCourseSections = this.getDepartment().getCourseSections();
        Map<Course, Grade> mapGrade = this.getTranscript().getCourseGradeMap();
        Map<CourseSection, Course> courseSectionCourse = this.getDepartment().getSectionCourseMap();

        /*
         While iterating through all course sections, check if the grade level is sufficient
         and if the course is already in the draft. If it is, continue the loop to change the course section.
         */
        for (CourseSection courseSection : allCourseSections) {

            if (this.gradeLevel < courseSection.getGradeLevel()
                    || draft.contains(courseSectionCourse.get(courseSection)))
                continue;

        /*
        If the conditions are not problematic and there is no course in the transcript,
        enter the 'if' statement. If status is true, then the student can add.
        If status is false, the student is ineligible unless they have taken the course before
        and received a low success grade; in this case, they can retake the course.
        */
            Course course = courseSectionCourse.get(courseSection);

            if (!mapGrade.containsKey(course)) {
                boolean status = true;
                for (Course prerequisite : course.getPreRequisiteCourses()) {
                    if ((mapGrade.get(prerequisite) == null
                            || mapGrade.get(prerequisite).getLetterGrade().equals("FF")
                            || mapGrade.get(prerequisite).getLetterGrade().equals("FD"))) {
                        status = false;
                    }
                }
                if (status)
                    availableCourses.add(course);
            } else {
                // If the student has previously taken the course and has a grade lower than 'CC', include it
                if (mapGrade.get(course) != null && mapGrade.get(course).getLetterGrade().compareTo("CC") > 0) {
                    availableCourses.add(course);
                }
            }
        }
        this.availableCourses = availableCourses;
    }

    //Checks if the provided username and password match the student's credentials.
    //Overrides the abstract login method in the Person class.
    @Override
    public boolean login(String userName, String password) {
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

    public boolean isHasRequest() {
        return hasRequest;
    }

}
