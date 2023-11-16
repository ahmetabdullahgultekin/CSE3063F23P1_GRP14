package iteration1;

import java.util.ArrayList;
import java.util.List;

/**
 * The CourseSection class represents a course offered by an educational institution in different sections
 * It extends course class.
 * It contains extra information about the courseSectionCode, day, hour and students of the section.
 */
public class CourseSection extends Course {
    private String courseSectionCode;
    private String day;
    private int hour;
    private List students;

    /**
     * Creates a new CourseSection object with the given parameters
     * Calls the course class's constructor
     *
     * @param course            the section's course
     * @param courseSessionCode the section's code
     * @param day               the section's day
     * @param hour              the section's hour
     */

    public CourseSection(Course course, String courseSessionCode, String day, int hour) {
        super(course.getCourseName(), course.getCourseCode(), course.getCourseCredit(), course.getGradeLevel());
        this.courseSectionCode = courseSessionCode;
        this.day = day;
        this.hour = hour;
        students = new ArrayList<>();
    }

    public String getCourseSectionCode() {
        return courseSectionCode;
    }
}
