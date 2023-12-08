package iteration2.Test;
import iteration1.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import iteration1.Course;
import iteration1.Student;
import iteration1.Transcript;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class RegistrationTest {
    private List<Course> courses;
    private Course course1;
    private Course course2;
    private Registration registration;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student(150120000, "name", "surname", "username", "password", (byte) 3);
        course1 = new Course("courseName1", "CSE100", 3, (byte) 3);
        course2 = new Course("courseName2", "CSE101", 4, (byte) 3);
        courses = List.of(course1, course2);
        registration = new Registration(student, courses);
    }

    @Test

    void rejectRequestTest() {
        registration.rejectRequest();
        assertFalse(student.isHasRequest());
        assertEquals(0, student.getDraft().size());
    }
    @Test
    void approveRequestTest() {
        Transcript transcript = new Transcript(student);
        student.setTranscript(transcript);
        registration.approveRequest();

        assertEquals(2, student.getTranscript().getStudentCourses().size());
        assertFalse(student.isHasRequest());
        assertEquals(0, student.getDraft().size());
    }
    
    @Test
    void addRequestTest() {
        Advisor advisor = new Advisor(1234567,"name" ,"surname","username","password");
        registration.addRequest(advisor);
        assertTrue(advisor.getRequests().contains(registration));
        assertTrue(student.isHasRequest());
    }
}


