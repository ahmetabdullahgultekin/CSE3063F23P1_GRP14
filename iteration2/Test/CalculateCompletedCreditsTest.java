package iteration2.Test;

import iteration1.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CalculateCompletedCreditsTest {

    private Transcript transcript;
    private Course course1, course2, course3, course4;
    private Grade grade1, grade2, grade3, grade4;

    @BeforeEach
    void setUp() {
        //Create necessary objects
        Student student = new Student(150120005, "name", "surname", "username", "password", (byte) 3);
        transcript = new Transcript(student);
        course1 = new Course("courseName1", "CSE100", 3, (byte) 3);
        course2 = new Course("courseName2", "CSE101", 4, (byte) 3);
        course3 = new Course("courseName3", "CSE102", 5, (byte) 3);
        course4 = new Course("courseName4", "CSE103", 7, (byte) 3);
        grade1 = new Grade("FF");
        grade2 = new Grade("FD");
        grade3 = new Grade("AA");
        grade4 = new Grade("DC");

        transcript.setTakenCredits(19);
        // Add the courses to the Transcript's studentCourses list
        transcript.getStudentCourses().add(course1);
        transcript.getStudentCourses().add(course2);
        transcript.getStudentCourses().add(course3);
        transcript.getStudentCourses().add(course4);

        // Create a sample courseGradeMap
        Map<Course, Grade> courseGradeMap = new HashMap<>();

        // Add grades for the courses
        courseGradeMap.put(course1, grade1);
        courseGradeMap.put(course2, grade2);
        courseGradeMap.put(course3, grade3);
        courseGradeMap.put(course4, grade4);

        // Set the courseGradeMap in the Transcript object
        transcript.setCourseGradeMap(courseGradeMap);

    }
    
    @Test
    void calculateCompletedCredits() {
        assertEquals(12, transcript.calculateCompletedCredits());
    }
}