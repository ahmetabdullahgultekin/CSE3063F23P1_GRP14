package iteration2;

import java.util.ArrayList;
import java.util.List;

public class Registration {
    /**
     * Represents a student.
     */
    private Student student;
    /**
     * Represents a list of courses.
     * The list is stored as a private member variable of type {@code List<Course>} and can be accessed using appropriate getter and setter methods.
     */
    private List<Course> courses;

    /**
     * Constructs a new Registration object.
     *
     * @param student the student for whom the registration is being created
     * @param courses the list of courses the student is registering for
     */
    public Registration(Student student, List<Course> courses) {
        this.student = student;
        this.courses = courses;
    }

    /**
     * Approves the request for registration.
     * This method updates the student's transcript and sets the status of the request to "approved".
     * It also clears the student's draft and prints a message confirming the approval.
     */
    public void approveRequest() {
        ConsoleColours.paintYellowMenu();
        System.out.println("Request approved.");
        ConsoleColours.resetColour();
        System.out.println();

        for (Course course : courses) {
            //Student will add a course that he has never taken before for the first time
            if (!student.getTranscript().getStudentCourses().contains(course)) {
                ArrayList<Grade> tempGrade = new ArrayList<Grade>();
                tempGrade.add(null);
                student.getTranscript().getCourseGradeMap().put(course, tempGrade);
                student.getTranscript().getStudentCourses().add(course);
                course.setNumberOfStudents(course.getNumberOfStudents() + 1);
            } else {

                List<Grade> myTempList = student.getTranscript().getCourseGradeMap().get(course);

                //If there is only one grade and it is null, student added it to the transcript with
                // 'add' a moment ago, now he will cancel and delete it
                if (myTempList.size() == 1 && myTempList.getFirst() == null) {
                    student.getTranscript().getCourseGradeMap().remove(course);
                    student.getTranscript().getStudentCourses().remove(course);
                    course.setNumberOfStudents(course.getNumberOfStudents() - 1);
                }

                //Student had a grade before, and he had just taken the course. Now, he changed his mind,
                // saying it would be too challenging, and deleted it
                else if (myTempList.size() > 1 && myTempList.getLast() == null) {
                    myTempList.remove(myTempList.getLast());
                    course.setNumberOfStudents(course.getNumberOfStudents() - 1);
                }

                //He has a previous grade, and the final grade is not --, which means
                // he will decide not to take the course again
                else if (myTempList.size() >= 1 && myTempList.getLast() != null) {
                    myTempList.add(null);
                    course.setNumberOfStudents(course.getNumberOfStudents() + 1);
                }
//!!
            }
        }
        student.setHasRequest(false);
        student.getDraft().clear();
        (new Notification(student, "Your request has been approved.")).sendNotification();
    }

    /**
     * Rejects a request by a student.
     * Clears the draft and marks the request as rejected.
     */
    public void rejectRequest() {
        ConsoleColours.paintYellowMenu();
        System.out.println("Request rejected.");
        ConsoleColours.resetColour();
        System.out.println();

        student.setHasRequest(false);
        student.getDraft().clear();

        //!!
        (new Notification(student, "Your request has been rejected.")).sendNotification();
    }

    /**
     * Adds a request to the advisor's list of requests and sets the student's request status to true.
     * Prints a message indicating that the request has been sent to the advisor.
     *
     * @param advisor the advisor to send the request to
     */
    public void addRequest(Advisor advisor) {
        advisor.getRequests().add(this);
        student.setHasRequest(true);
        ConsoleColours.paintYellowMenu();
        System.out.println("Request sent to advisor.");
        ConsoleColours.resetColour();
        System.out.println();
    }

    public Student getStudent() {
        return student;
    }

    public List<Course> getCourses() {
        return courses;
    }
}