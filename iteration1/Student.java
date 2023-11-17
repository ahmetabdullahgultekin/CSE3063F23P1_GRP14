package iteration1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

    // The 'addCourse' function displays all accessible courses and adds these courses to
    // the student's draft, which is a private attribute.
    public void addCourse() {
        //If a request has been sent, terminate the function.
        if (hasRequest) {
            System.out.println("\nYou can not add lecture because you have a request waiting for approval.");
            return;
        }
        //Call the calculateNumberOfCourses() function to limit with the maximum number of courses.
        int numberOfCourses = calculateNumberOfCourses();
        if (numberOfCourses >= this.getDepartment().getMaxCourseNumber()) {
            System.out.println("You can not add more lectures.");
            return;
        }

        //Call the viewAvailableCourses() function to see all available courses.
        System.out.println("\nAdd Course Menu");
        viewAvailableCourses();

        System.out.println("Here is the available courses:");
        System.out.println("0. Back");
        //Yazdırma işlemi için belli bir for döngüsü kullan
        for (int i = 0; i < this.getAvailableCourses().size(); i++) {
            System.out.println((i + 1) + ". " + this.getAvailableCourses().get(i).getCourseCode() +
                    " - " + this.getAvailableCourses().get(i).getCourseName());
        }
        //Check the controlGate variable for while loop manipulation.
        int controlGate = 1;
        while (controlGate == 1) {
            //Take input from the user and make assignments to the courses.
            Scanner scanner = new Scanner(System.in);
            System.out.print("Choose number between 1 to " + this.getAvailableCourses().size() + " to add course: ");
            int userNumberInput = 0;
            try{
                userNumberInput = scanner.nextInt();
            }
            catch (Exception e){
                System.out.println("Invalid input, please enter a number");
                addCourse();
            }
            //Back to Course Section Menu with return value
            if (userNumberInput == 0)
                return;
            //If incorrect input is entered, ask for input again.
            if (1 <= userNumberInput && userNumberInput <= this.getAvailableCourses().size()) {
                controlGate = 0;
                this.draft.add(this.getAvailableCourses().get(userNumberInput - 1));
                this.getAvailableCourses().remove(userNumberInput - 1);
                addCourse();
            } else {
                System.out.println("Invalid input, please enter a valid number");
            }
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
        // Check if the student has already sent a request
        if (hasRequest) {
            System.out.println("\nYou can not drop lecture because you have a request waiting for approval.");
            return;
        }

        System.out.println("\nDrop Course Menu");
        System.out.println("Select the course you want drop");

        // Check if the draft is empty.
        if (draft.isEmpty()) {
            System.out.println("Your draft is empty!");
        } else {
            System.out.println("0. Back");
            // Manipulation for displaying and dropping courses in the menu.
            for (int i = 0; i < draft.size(); i++) {
                System.out.println((i + 1) + ". " + draft.get(i).getCourseCode() +
                        " - " + draft.get(i).getCourseName());
            }
            // Remove input index element -1 from the draft list if the input is valid and in the appropriate range.
            Scanner scanner = new Scanner(System.in);
            System.out.print("Choose number between 1 to " + this.draft.size() + " to drop course: ");
            int userNumberInput = 0;
            try{
                userNumberInput = scanner.nextInt();
            }
            catch (Exception e){
                System.out.println("Invalid input, please enter a number");
                dropCourse();
            }

            if (userNumberInput == 0)
                return;
            draft.remove(userNumberInput - 1);
            dropCourse();
        }
    }

    //Checks if the student has a registration request waiting for advisor approval
    //and prints an appropriate message to the console.
    public void showRequestStatus() {
        if (hasRequest) {
            System.out.println("\nYour request is waiting for advisor approval");
        } else System.out.println("\nThere is no waiting request");
        System.out.println("Your draft: ");
        for (Course course : draft){
            System.out.println(course.getCourseCode() + "-" + course.getCourseName());
        }

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
        "While iterating through all course sections, check if the grade level is sufficient
         and if the course is already in the draft. If it is, continue the loop to change the course section.
         */
        for (CourseSection courseSection : allCourseSections) {
            if (this.gradeLevel < courseSection.getGradeLevel() || draft.contains(courseSectionCourse.get(courseSection)))
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
                    if ((mapGrade.get(prerequisite) == null || mapGrade.get(prerequisite).getLetterGrade().equals("FF") ||
                            mapGrade.get(prerequisite).getLetterGrade().equals("FD"))) {
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

