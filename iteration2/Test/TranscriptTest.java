package iteration2.Test;

import iteration2.Course;
import iteration2.Grade;
import iteration2.Student;
import iteration2.Transcript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TranscriptTest {

    private Transcript transcript;
    private Course course1;
    private Course course2;
    private Course course3;

    @BeforeEach
    void setUp() {
        Student student = new Student(150120000, "name", "surname", "username", "password", (byte) 3);
        transcript = new Transcript(student);

        course1 = new Course("courseName1", "CSE100", "Mandatory", 3, (byte) 3, 120, 8, "Monday");
        course2 = new Course("courseName2", "CSE101", "Mandatory", 4, (byte) 3, 120, 9, "Monday");
        course3 = new Course("courseName3", "CSE102", "Mandatory", 5, (byte) 3, 120, 10, "Monday");
    }

    @Test
    void calculateCgpaWithNoCourses() {
        assertEquals(0, transcript.calculateCgpa(), "CGPA should be 0 when no courses are taken.");
    }

    @Test
    void calculateCgpaWithCoursesNoGrades() {
        transcript.getStudentCourses().add(course1);
        transcript.getStudentCourses().add(course2);
        transcript.getStudentCourses().add(course3);
        transcript.getCourseGradeMap().put(course1, new ArrayList<>());
        transcript.getCourseGradeMap().put(course2, new ArrayList<>());
        transcript.getCourseGradeMap().put(course3, new ArrayList<>());
        transcript.getCourseGradeMap().get(course1).add(null);
        transcript.getCourseGradeMap().get(course2).add(null);
        transcript.getCourseGradeMap().get(course3).add(null);
        assertEquals(0, transcript.calculateCgpa(), "CGPA should be 0 when no grades are assigned.");
    }

    @Test
    void calculateCgpaWithCoursesAndGrades() {
        transcript.getStudentCourses().add(course1);
        transcript.getStudentCourses().add(course2);
        transcript.getStudentCourses().add(course3);
        transcript.getCourseGradeMap().put(course1, new ArrayList<>());
        transcript.getCourseGradeMap().put(course2, new ArrayList<>());
        transcript.getCourseGradeMap().put(course3, new ArrayList<>());
        transcript.getCourseGradeMap().get(course1).add(new Grade("AA"));
        transcript.getCourseGradeMap().get(course2).add(new Grade("FD"));
        transcript.getCourseGradeMap().get(course3).add(new Grade("BA"));
        double expectedCgpa = ((4.0 * 3) + (0.5 * 4) + (3.5 * 5)) / 12;
        assertEquals(expectedCgpa, transcript.calculateCgpa(), "CGPA should be calculated correctly based on the grades and credits of the courses.");
    }
}