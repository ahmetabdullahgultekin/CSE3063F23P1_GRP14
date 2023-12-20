package iteration2.Test;

import iteration2.Course;
import iteration2.Grade;
import iteration2.Student;
import iteration2.Transcript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculateCompletedCreditsTest {

    private Transcript transcript;
    private Course course1, course2, course3, course4;
    private Grade grade1, grade2, grade3, grade4;

    @BeforeEach
    void setUp() {
        //Create necessary objects
        Student student = new Student(150120005, "name", "surname", "username", "password", (byte) 3);
        transcript = new Transcript(student);
        course1 = new Course("courseName1", "CSE100", "Mandatory", 3, (byte) 3, 120, 8, "Monday");
        course2 = new Course("courseName2", "CSE101", "Mandatory", 4, (byte) 3, 120, 9, "Monday");
        course3 = new Course("courseName3", "CSE102", "Mandatory", 5, (byte) 3, 120, 10, "Monday");
        course4 = new Course("courseName4", "CSE103", "Mandatory", 7, (byte) 3, 120, 11, "Monday");

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
        Map<Course, List<Grade>> courseGradeMap = new HashMap<>();

        // Add grades for the courses
        courseGradeMap.put(course1, new ArrayList<>());
        courseGradeMap.put(course2, new ArrayList<>());
        courseGradeMap.put(course3, new ArrayList<>());
        courseGradeMap.put(course4, new ArrayList<>());

        courseGradeMap.get(course1).add(grade1);
        courseGradeMap.get(course2).add(grade2);
        courseGradeMap.get(course3).add(grade3);
        courseGradeMap.get(course4).add(grade4);

        // Set the courseGradeMap in the Transcript object
        transcript.setCourseGradeMap(courseGradeMap);

    }

    @Test
    void calculateCompletedCredits() {
        assertEquals(12, transcript.calculateCompletedCredits());
    }
}