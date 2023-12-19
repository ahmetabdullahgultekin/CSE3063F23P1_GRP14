package iteration2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Inherits from the Person class and implements the ILogin interface
public class Student extends Person implements IDisplayMenu {

    private final Logger logger = LogManager.getLogger(Student.class);
    //All attributes
    private Advisor advisor;
    private Transcript transcript;
    private byte semester;
    private boolean hasRequest;
    private Notification notification;
    private List<Course> draft;
    private List<Course> availableCoursesToAdd;
    private List<Course> availableCoursesToDrop;
    private List<LaboratorySection> labSections;
    private Course[][] schedule;

    //Implement Constructor
    public Student(int studentID, String name, String surname, String userName, String password, byte semester) {
        super(studentID, name, surname, userName, password);
        this.draft = new ArrayList<>();
        this.availableCoursesToAdd = new ArrayList<>();
        this.availableCoursesToDrop = new ArrayList<>();
        this.labSections = new ArrayList<>();
        this.semester = semester;
        this.schedule = new Course[5][8];
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
            logger.warn("Student " + this.getID() + " already has a request waiting for " + advisor.getID() + "'s approval.");
            System.out.println();
        } else if (draft.isEmpty()) {
            System.out.println("Your draft is empty!");
            ConsoleColours.resetColour();
            System.out.println();
        } else {
            Registration registration = new Registration(this, draft);
            registration.addRequest(advisor);
            logger.info("Student " + this.getID() + " sent a request to advisor " + advisor.getID() + ".");
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
                System.out.println("Welcome " + getName() + " " + getSurname() + "!");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                if (notification != null) {
                    ConsoleColours.paintYellowMenu();
                    System.out.println(notification.getMessage());
                    notification = null;
                    ConsoleColours.paintBlueMenu();
                }

                System.out.println("Please select from the following options:");
                System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
                System.out.println("                         Exit System -> 0");
                System.out.println("               Course Selection Menu -> 1");
                System.out.println("                     View Transcript -> 2");
                System.out.println("                             Log out -> 3");
                System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
                ConsoleColours.paintGreenMenu();
                System.out.println("Enter your choice: ");
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
                System.out.println("Enter your choice: ");
                break;
            case "innerCourseSelectionMenu":
                ConsoleColours.paintBlueMenu();
                System.out.println("Course Selection Menu");
                System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨\n");
                System.out.println("Please select from the following options:");
                System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
                System.out.println("       Back to Course Selection Menu -> 0");
                System.out.println("                       Add Mandatory -> 1");
                System.out.println("              Add Technical Elective -> 2");
                System.out.println("          Add Non-Technical Elective -> 3");
                System.out.println("      Add Faculty Technical Elective -> 4");
                System.out.println("                         Drop Course -> 5");
                System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
                ConsoleColours.paintGreenMenu();
                System.out.println("Enter your choice: ");
                break;
            case "addCourse":
                ConsoleColours.paintBlueMenu();
                System.out.println("Add Course Menu");
                System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨\n");
                System.out.println("      Back -> 0");
                System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
                System.out.println("Here is the available courses to add:");
                System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
                ConsoleColours.paintPurpleMenu();
                break;
            case "dropCourse":
                ConsoleColours.paintBlueMenu();
                System.out.println("Drop Course Menu");
                System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨\n");
                System.out.println("       Back -> 0");
                System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
                System.out.println("Here is the available courses to drop:");
                System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
                ConsoleColours.paintPurpleMenu();
                break;
        }
    }

    // The 'addCourse' function displays all accessible courses and adds these courses to
    // the student's draft, which is a private attribute.
    public void addCourseToDraft() {
        //If a request has been sent, terminate the function.
        if (hasRequest) {
            ConsoleColours.paintRedMenu();
            logger.warn("Student " + this.getID() + " can not add lecture because he/she has a request waiting for " + advisor.getID() + "'s approval.");
            System.out.println("You can not add lecture because you have a request waiting for approval.");
            return;
        }
        //Call the viewAvailableCourses() function to see all available courses.
        ConsoleColours.paintBlueMenu();
        printMenu("innerCourseSelectionMenu");
        switch ((new CourseRegistrationSystem()).getInput()) {
            case 0:
                return;
            case 1:
                addMandatoryCourse();
                break;
            case 2:
                addTechnicalElective();
                break;
            case 3:
                addNonTechnicalElective();
                break;
            case 4:
                addFacultyTechnicalElective();
                break;
            case 5:
                addCourseToDrop();
                break;
            case -1:
                // If the user enters string or -1 give error.
                ConsoleColours.paintRedMenu();
                System.out.println("Please enter valid number!");
                break;
            default:
                ConsoleColours.paintRedMenu();
                System.out.println("Invalid input, please enter a valid number!");
        }
        addCourseToDraft();
    }

    private void addMandatoryCourse() {
        if (maxCoursesReached()) return;
        computeAvailableMandatoryCourses();

        if (availableCoursesToAdd.isEmpty()) {
            ConsoleColours.paintRedMenu();
            System.out.println("You do not have any addable course!");
        } else {
            this.printMenu("addCourse");

            for (int i = 0; i < availableCoursesToAdd.size(); i++) {
                Course course = availableCoursesToAdd.get(i);
                System.out.println((i + 1) + ". " + course.getCourseCode() + " - " + course.getCourseName());
                System.out.println("´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´");
            }

            //Take input from the user and make assignments to the courses.
            ConsoleColours.paintGreenMenu();
            System.out.print("Choose number between 1 to " + availableCoursesToAdd.size() + " to add course: \n");
            int userNumberInput1 = (new CourseRegistrationSystem()).getInput();

            if (userNumberInput1 <= availableCoursesToAdd.size() && userNumberInput1 >= 1) {
                chooseLabSection(availableCoursesToAdd.get(userNumberInput1 - 1));
                logger.info("Student " + this.getID() + " added " + availableCoursesToAdd.get(userNumberInput1 - 1).getCourseCode() + " to draft.");
                draft.add(availableCoursesToAdd.get(userNumberInput1 - 1));
                availableCoursesToAdd.remove(userNumberInput1 - 1);
                if (!availableCoursesToAdd.isEmpty()) addMandatoryCourse();
            } else if (userNumberInput1 > availableCoursesToAdd.size() || userNumberInput1 < 0) {
                ConsoleColours.paintRedMenu();
                logger.warn("Student " + this.getID() + " entered invalid input.");
                System.out.println("Invalid input, please enter a valid number");
                addMandatoryCourse();
            } else return;
        }
    }

    private void chooseLabSection(Course course) {
        if (course.getLaboratorySections().isEmpty()) return;
        List<LaboratorySection> availableLabSections = new ArrayList<>();
        ConsoleColours.paintBlueMenu();
        System.out.println("Here is the available laboratory sections to add:");
        ConsoleColours.paintPurpleMenu();
        for (LaboratorySection labSection : course.getLaboratorySections()) {
            Course labCourse = new Course(null, labSection.getLaboratorySectionCode(), null, 0, (byte) 0, 0, labSection.getHour(), labSection.getDay());
            if (hasCourseOverlap(labCourse, true)
                    || labSection.getCapacity() <= labSection.getNumberOfStudents()) {
                continue;
            } else {
                availableLabSections.add(labSection);
            }
        }
        for (int i = 0; i < availableLabSections.size(); i++) {
            LaboratorySection labSection = availableLabSections.get(i);
            System.out.println((i + 1) + ". " + labSection.getLaboratorySectionCode());
            System.out.println("´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´");
        }
        System.out.print("Choose number between 1 to " + availableLabSections.size() + " to add laboratory section: \n");
        int userNumberInput = (new CourseRegistrationSystem()).getInput();

        if (userNumberInput <= availableLabSections.size() && userNumberInput >= 1) {
            this.labSections.add(availableLabSections.get(userNumberInput - 1));
            availableLabSections.get(userNumberInput - 1).setNumberOfStudents(availableLabSections.get(userNumberInput - 1).getNumberOfStudents() + 1);
        } else if (userNumberInput > availableLabSections.size() || userNumberInput <= 0) {
            ConsoleColours.paintRedMenu();
            System.out.println("Invalid input, please enter a valid number");
            ConsoleColours.resetColour();
            chooseLabSection(course);
        } else return;

    }

    private void addTechnicalElective() {
        if (maxCoursesReached()) return;

        computeAvailableTEAndFTECourses("TE");

        if (availableCoursesToAdd.isEmpty()) {
            ConsoleColours.paintRedMenu();
            System.out.println("You do not have any addable course!");
        } else {
            this.printMenu("addCourse");

            for (int i = 0; i < availableCoursesToAdd.size(); i++) {
                Course course = availableCoursesToAdd.get(i);
                System.out.println((i + 1) + ". " + course.getCourseCode() + " - " + course.getCourseName());
                System.out.println("´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´");
            }

            //Take input from the user and make assignments to the courses.
            ConsoleColours.paintGreenMenu();
            System.out.print("Choose number between 1 to " + availableCoursesToAdd.size() + " to add course: \n");
            int userNumberInput1 = (new CourseRegistrationSystem()).getInput();

            if (userNumberInput1 <= availableCoursesToAdd.size() && userNumberInput1 >= 1) {
                draft.add(availableCoursesToAdd.get(userNumberInput1 - 1));
                logger.info("Student " + this.getID() + " added " + availableCoursesToAdd.get(userNumberInput1 - 1).getCourseCode() + " to draft.");
                availableCoursesToAdd.remove(userNumberInput1 - 1);
                if (!availableCoursesToAdd.isEmpty()) addTechnicalElective();
            } else if (userNumberInput1 > availableCoursesToAdd.size() || userNumberInput1 < 0) {
                ConsoleColours.paintRedMenu();
                System.out.println("Invalid input, please enter a valid number");
                addTechnicalElective();
            } else return;
        }
    }


    private void computeAvailableTEAndFTECourses(String courseType) {
        availableCoursesToAdd.clear();
        int sum = 0;
        Map<Course, List<Grade>> mapGrade = transcript.getCourseGradeMap();

        for (Course course : transcript.getStudentCourses()) {
            if (course.semester() > 6
                    || course.getCourseType().equals("NTE")
                    || mapGrade.get(course).getLast() == null
                    || mapGrade.get(course).getLast().getLetterGrade().compareTo("DD") > 0) {
                continue;
            }
            sum += course.getCourseCredit();
        }

        if (sum >= 155) {
            for (Course course : this.getDepartment().getCourses()) {
                if (!course.getCourseType().equals(courseType)
                        || !course.hasCapacity()
                        || hasCourseOverlap(course, false)
                        || (semester < course.semester() && transcript.getCgpa() < 3)
                        || draft.contains(course)
                        || !checkPrerequisiteAndCourseThatWasTaken(course)
                        || (course.semester() % 2 != this.semester % 2)) {
                    continue;
                } else {
                    availableCoursesToAdd.add(course);
                }
            }
        }
    }

    private void addFacultyTechnicalElective() {
        if (maxCoursesReached()) return;
        computeAvailableTEAndFTECourses("FTE");

        if (availableCoursesToAdd.isEmpty()) {
            ConsoleColours.paintRedMenu();
            System.out.println("You do not have any addable course!");
        } else {
            this.printMenu("addCourse");

            for (int i = 0; i < availableCoursesToAdd.size(); i++) {
                Course course = availableCoursesToAdd.get(i);
                System.out.println((i + 1) + ". " + course.getCourseCode() + " - " + course.getCourseName());
                System.out.println("´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´");
            }

            //Take input from the user and make assignments to the courses.
            ConsoleColours.paintGreenMenu();
            System.out.print("Choose number between 1 to " + availableCoursesToAdd.size() + " to add course: \n");
            int userNumberInput1 = (new CourseRegistrationSystem()).getInput();

            if (userNumberInput1 <= availableCoursesToAdd.size() && userNumberInput1 >= 1) {
                draft.add(availableCoursesToAdd.get(userNumberInput1 - 1));
                logger.info("Student " + this.getID() + " added " + availableCoursesToAdd.get(userNumberInput1 - 1).getCourseCode() + " to draft.");
                availableCoursesToAdd.remove(userNumberInput1 - 1);
                if (!availableCoursesToAdd.isEmpty()) addFacultyTechnicalElective();
            } else if (userNumberInput1 > availableCoursesToAdd.size() || userNumberInput1 < 0) {
                ConsoleColours.paintRedMenu();
                System.out.println("Invalid input, please enter a valid number");
                addFacultyTechnicalElective();
            } else return;
        }

    }

    private void addNonTechnicalElective() {
        if (maxCoursesReached()) return;
        computeAvailableNTECourses();

        if (availableCoursesToAdd.isEmpty()) {
            ConsoleColours.paintRedMenu();
            System.out.println("You do not have any addable course!");
        } else {
            this.printMenu("addCourse");

            for (int i = 0; i < availableCoursesToAdd.size(); i++) {
                Course course = availableCoursesToAdd.get(i);
                System.out.println((i + 1) + ". " + course.getCourseCode() + " - " + course.getCourseName());
                System.out.println("´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´");
            }

            //Take input from the user and make assignments to the courses.
            ConsoleColours.paintGreenMenu();
            System.out.print("Choose number between 1 to " + availableCoursesToAdd.size() + " to add course: \n");
            int userNumberInput1 = (new CourseRegistrationSystem()).getInput();

            if (userNumberInput1 <= availableCoursesToAdd.size() && userNumberInput1 >= 1) {
                draft.add(availableCoursesToAdd.get(userNumberInput1 - 1));
                logger.info("Student " + this.getID() + " added " + availableCoursesToAdd.get(userNumberInput1 - 1).getCourseCode() + " to draft.");
                availableCoursesToAdd.remove(userNumberInput1 - 1);
                if (!availableCoursesToAdd.isEmpty()) addNonTechnicalElective();
            } else if (userNumberInput1 > availableCoursesToAdd.size() || userNumberInput1 < 0) {
                ConsoleColours.paintRedMenu();
                System.out.println("Invalid input, please enter a valid number");
                addNonTechnicalElective();
            } else return;
        }


    }

    private void computeAvailableNTECourses() {
        availableCoursesToAdd.clear();

        for (Course course : this.getDepartment().getCourses()) {
            if (!course.getCourseType().equals("NTE")
                    || !course.hasCapacity()
                    || hasCourseOverlap(course, false)
                    || (semester < course.semester() && transcript.getCgpa() < 3)
                    || draft.contains(course)
                    || !checkPrerequisiteAndCourseThatWasTaken(course)
                    || (course.semester() % 2 != this.semester % 2)) {
                continue;
            } else {
                availableCoursesToAdd.add(course);
            }
        }
    }

    private void computeAvailableMandatoryCourses() {
        availableCoursesToAdd.clear();

        //If no capacity, has overlap, (student's semester is less than course's semester and student has CGPA that is
        //less than 3) or course is in draft; continue.
        for (Course course : this.getDepartment().getCourses()) {
            if (!course.getCourseType().equals("Mandatory")
                    || !course.hasCapacity()
                    || hasCourseOverlap(course, false)
                    || (semester < course.semester() && transcript.getCgpa() < 3)
                    || draft.contains(course)
                    || !checkPrerequisiteAndCourseThatWasTaken(course)
                    || (course.semester() % 2 != this.semester % 2)) {
                continue;
            } else {
                availableCoursesToAdd.add(course);
            }
        }
    }

    private void addCourseToDrop() {
        computeAvailableCoursesToDrop();

        if (availableCoursesToDrop.isEmpty()) {
            ConsoleColours.paintRedMenu();
            System.out.println("You do not have any droppable course!");
        } else {
            this.printMenu("dropCourse");

            for (int i = 0; i < availableCoursesToDrop.size(); i++) {
                Course course = availableCoursesToDrop.get(i);
                System.out.println((i + 1) + ". " + course.getCourseCode() + " - " + course.getCourseName());
                System.out.println("´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´");
            }

            //Take input from the user and make assignments to the courses.
            ConsoleColours.paintGreenMenu();
            System.out.print("Choose number between 1 to " + availableCoursesToDrop.size() + " to add course: \n");
            int userNumberInput = (new CourseRegistrationSystem()).getInput();

            if (userNumberInput <= availableCoursesToDrop.size() && userNumberInput >= 1) {
                draft.add(availableCoursesToDrop.get(userNumberInput - 1));
                logger.info("Student " + this.getID() + " added " + availableCoursesToDrop.get(userNumberInput - 1).getCourseCode() + " to draft.");
                availableCoursesToDrop.remove(userNumberInput - 1);
                if (!availableCoursesToDrop.isEmpty()) addCourseToDrop();
            } else if (userNumberInput > availableCoursesToDrop.size() || userNumberInput < 0) {
                ConsoleColours.paintRedMenu();
                System.out.println("Invalid input, please enter a valid number");
                addCourseToDrop();
            }

        }
    }

    private boolean checkPrerequisiteAndCourseThatWasTaken(Course course) {
        Map<Course, List<Grade>> mapGrade = transcript.getCourseGradeMap();
        //If student take the course for the first time, prerequisites is checked.
        if (!mapGrade.containsKey(course)) {
            for (Course prerequisite : course.getPreRequisiteCourses()) {
                if ((!transcript.getStudentCourses().contains(prerequisite)
                        || mapGrade.get(prerequisite).getLast() == null
                        || mapGrade.get(prerequisite).getLast().getLetterGrade().equals("FF")
                        || mapGrade.get(prerequisite).getLast().getLetterGrade().equals("FD"))) {
                    return false;
                }
            }
            // If the student has  previously taken the course and has a grade bigger than 'CC',return false.
        } else if (!(mapGrade.get(course).getLast() != null && mapGrade.get(course).getLast().getLetterGrade().compareTo("CC") > 0)) {
            return false;
        }
        return true;
    }

    public boolean hasCourseOverlap(Course courseToAdd, boolean isLab) {
        int i = 0, j = 0;
        switch (courseToAdd.getDay()) {
            case "Monday" -> i = 0;
            case "Tuesday" -> i = 1;
            case "Wednesday" -> i = 2;
            case "Thursday" -> i = 3;
            case "Friday" -> i = 4;
        }
        switch (courseToAdd.getHour()) {
            case 8 -> j = 0;
            case 9 -> j = 1;
            case 10 -> j = 2;
            case 11 -> j = 3;
            case 13 -> j = 4;
            case 14 -> j = 5;
            case 15 -> j = 6;
            case 16 -> j = 7;
        }
        Course courseInSchedule = schedule[i][j];

        if (isLab) {
            if (courseInSchedule != null) {
                List<Grade> scheduleCourseGrades = transcript.getCourseGradeMap().get(courseInSchedule);
                if (scheduleCourseGrades.size() == 1 && scheduleCourseGrades.getLast() == null) {
                    return true;
                }
                if (scheduleCourseGrades.size() >= 2 && scheduleCourseGrades.get(scheduleCourseGrades.size() - 2).getLetterGrade().equals("DZ")) {
                    return true;
                }
            }
            for (Course course : draft) {
                if (transcript.getCourseGradeMap().get(course) != null && transcript.getCourseGradeMap().get(course).getLast() == null)
                    continue;
                if (course.getDay().equals(courseToAdd.getDay()) && course.getHour() == courseToAdd.getHour()) {
                    if (transcript.getCourseGradeMap().get(course) != null
                            && transcript.getCourseGradeMap().get(course).getLast().getLetterGrade().equals("DZ")) {
                        return true;
                    }
                }
            }

            for (LaboratorySection labSection : labSections) {
                if (labSection.getDay().equals(courseToAdd.getDay()) && labSection.getHour() == courseToAdd.getHour()) {
                    return true;
                }
            }


        } else {
            if (courseInSchedule != null) {
                List<Grade> scheduleCourseGrades = transcript.getCourseGradeMap().get(courseInSchedule);
                if (scheduleCourseGrades != null
                        && scheduleCourseGrades.size() >= 2
                        && scheduleCourseGrades.get(scheduleCourseGrades.size() - 2).getLetterGrade().equals("DZ")) {
                    if (!transcript.getStudentCourses().contains(courseToAdd)
                            || (transcript.getCourseGradeMap().get(courseToAdd).getLast() != null && transcript.getCourseGradeMap().get(courseToAdd).getLast().getLetterGrade().equals("DZ"))) {
                        return true;
                    }
                }
            }

            for (Course course : draft) {
                if (transcript.getCourseGradeMap().get(course) != null && transcript.getCourseGradeMap().get(course).getLast() == null)
                    continue;
                if (course.getDay().equals(courseToAdd.getDay()) && course.getHour() == courseToAdd.getHour()) {
                    if (transcript.getCourseGradeMap().get(course) != null
                            && transcript.getCourseGradeMap().get(course).getLast().getLetterGrade().equals("DZ")) {
                        return true;
                    }
                    if (transcript.getCourseGradeMap().get(course) == null
                            && (transcript.getCourseGradeMap().get(courseToAdd) == null || transcript.getCourseGradeMap().get(courseToAdd).getLast().getLetterGrade().equals("DZ"))) {
                        return true;
                    }
                }
            }

            for (LaboratorySection labSection : labSections) {
                if (labSection.getDay().equals(courseToAdd.getDay()) && labSection.getHour() == courseToAdd.getHour()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean maxCoursesReached() {
        //Call the calculateNumberOfCourses() function to limit with the maximum number of courses.
        int numberOfCourses = calculateNumberOfCourses();
        if (numberOfCourses >= getDepartment().getMaxCourseNumber()) {
            ConsoleColours.paintRedMenu();
            logger.warn("Student " + this.getID() + " reached the maximum number of courses.");
            System.out.println("Limit Reached! You can take at most " + this.getDepartment().getMaxCourseNumber() + " courses.");
            return true;
        }
        return false;
    }


    // This method calculates the total number of courses, considering both the drafted courses
    // and the courses in the transcript for which the student has not received a grade yet.
    public int calculateNumberOfCourses() {
        int numberOfCourses = 0;
        numberOfCourses += draft.size();

        for (Course course : transcript.getCourseGradeMap().keySet()) {
            if (transcript.getCourseGradeMap().get(course).getLast() == null) {
                numberOfCourses++;
            }
        }
        return numberOfCourses;
    }

    public void removeCourseFromDraft() {
        // Check if the student has already sent a request
        if (hasRequest) {
            ConsoleColours.paintRedMenu();
            logger.warn("Student " + this.getID() + " can not remove lecture because he/she has a request waiting for " + advisor.getID() + "'s approval.");
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
                System.out.println((i + 1) + ". " + draft.get(i).getCourseCode() + " - " + draft.get(i).getCourseName());
                System.out.println("´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´");
            }
            // Remove input index element -1 from the draft list if the input is valid and in the appropriate range.
            ConsoleColours.paintGreenMenu();
            System.out.print("Choose number between 1 to " + draft.size() + " to remove course: \n");

            int userNumberInput = (new CourseRegistrationSystem()).getInput();

            if (userNumberInput <= draft.size() && userNumberInput >= 1) {
                for (LaboratorySection labSection : draft.get(userNumberInput - 1).getLaboratorySections()) {
                    if (this.labSections.contains(labSection)) {
                        this.labSections.remove(labSection);
                        labSection.setNumberOfStudents(labSection.getNumberOfStudents() - 1);
                        break;
                    }
                }
                logger.info("Student " + this.getID() + " removed " + draft.get(userNumberInput - 1).getCourseCode() + " from draft.");
                draft.remove(userNumberInput - 1);
                removeCourseFromDraft();
            } else if (userNumberInput > draft.size() || userNumberInput < 0) {
                ConsoleColours.paintRedMenu();
                logger.warn("Student " + this.getID() + " entered invalid input.");
                System.out.println("Invalid input, please enter a number");
                removeCourseFromDraft();
            }
        }
    }

    //Checks if the student has a registration request waiting for advisor approval
    //and prints an appropriate message to the console.
    public void showRequestStatus() {
        ConsoleColours.paintYellowMenu();
        if (hasRequest) {
            logger.info("Student " + this.getID() + " has a request waiting for " + advisor.getID() + "'s approval.");
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
        for (Course course : draft) {
            System.out.println(course.getCourseCode() + "-" + course.getCourseName());
        }
        ConsoleColours.resetColour();
        System.out.println();
    }

    private void computeAvailableCoursesToDrop() {
        availableCoursesToDrop.clear();
        Map<Course, List<Grade>> mapGrade = transcript.getCourseGradeMap();
        for (Course course : transcript.getStudentCourses()) {
            if (draft.contains(course)) continue;
            if (mapGrade.get(course).getLast() == null) {
                availableCoursesToDrop.add(course);
            }
        }
    }

    //Checks if the provided username and password match the student's credentials.
    //Overrides the abstract login method in the Person class.
    @Override
    public boolean login(String userName, String password) {
        return getUserName().equals(userName) && getPassword().equals(password);
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

    public byte getSemester() {
        return semester;
    }

    public List<Course> getDraft() {
        return draft;
    }

    public void setDraft(List<Course> draft) {
        this.draft = draft;
    }

    public boolean isHasRequest() {
        return hasRequest;
    }

    public void setHasRequest(boolean hasRequest) {
        this.hasRequest = hasRequest;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public void setTranscript(Transcript transcript) {
        this.transcript = transcript;
    }

    public Course[][] getSchedule() {
        return schedule;
    }

    public List<LaboratorySection> getLabSections() {
        return labSections;
    }
}