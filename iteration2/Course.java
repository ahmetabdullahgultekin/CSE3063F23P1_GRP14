package iteration2;


import java.util.ArrayList;
import java.util.List;

/**
 * The Course class represents a course offered by an educational institution.
 * It contains information about the name, code, credit, semester, sessions, and prerequisites of the course.
 */
public class Course {
    private String courseName;
    private String courseCode;
    private String courseType;
    private int courseCredit;
    private byte semester;
    private Lecturer lecturer;
    private List<LaboratorySection> laboratorySections;
    private List<Course> preRequisiteCourses;

    private int numberOfStudents;
    private int capacity;
    private int hour;
    private String day;

    /**
     * Creates a new course object with the given parameters
     *
     * @param courseName   the course's name
     * @param courseCode   the course's code
     * @param courseCredit the course's credit
     * @param semester     the course's level
     */


    public Course(String courseName, String courseCode, String courseType, int courseCredit, byte semester, int capacity, int hour, String day) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.courseType = courseType;
        this.capacity = capacity;
        this.courseCredit = courseCredit;
        this.semester = semester;
        this.hour = hour;
        this.day = day;
        laboratorySections = new ArrayList<>();
        preRequisiteCourses = new ArrayList<>();
    }

    public void addPreRequisiteCourse(Course course) {
        preRequisiteCourses.add(course);
    }

    public String getCourseName() {
        return courseName;
    }

    public List getCourseSections() {
        return laboratorySections;
    }

    public List<Course> getPreRequisiteCourses() {
        return preRequisiteCourses;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public int getCourseCredit() {
        return courseCredit;
    }

    public byte semester() {
        return semester;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public List<LaboratorySection> getLaboratorySections() {
        return laboratorySections;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public String getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }
}
