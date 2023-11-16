package iteration1;

import java.util.List;

public class Registration {
    /**
     * Represents a student.
     */
    private Student student;
    /**
     * Represents a list of courses.
     *
     * The list is stored as a private member variable of type {@code List<Course>} and can be accessed using appropriate getter and setter methods.
     */
    private List<Course> courses;

    /**
     * Constructs a new Registration object.
     *
     * @param student  the student for whom the registration is being created
     * @param courses  the list of courses the student is registering for
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
        System.out.println("\nRequest approved");
        student.getTranscript().getStudentCourses().addAll(courses);
        for (Course course : courses) {
            student.getTranscript().getCourseGradeMap().put(course, null);
        }
        student.setHasRequest(false);
        student.getDraft().clear();
    }

    /**
     * Rejects a request by a student.
     * Clears the draft and marks the request as rejected.
     */
    public void rejectRequest() {
        System.out.println("\nRequest rejected");
        student.setHasRequest(false);
        student.getDraft().clear();
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
        System.out.println("\nRequest sent to advisor");
    }

    public Student getStudent() {
        return student;
    }

    public List<Course> getCourses() {
        return courses;
    }
}