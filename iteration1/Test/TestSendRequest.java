package iteration1.Test;

import iteration1.Advisor;
import iteration1.Course;
import iteration1.Registration;
import iteration1.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class TestSendRequest {

    private Student student1;
    private Student student2;
    private Student student3;
    Advisor advisor1 = mock(Advisor.class);


    //Set up for the test part
    @BeforeEach
    public void setUp(){
        student1 = new Student(150121073, "name", "surname", "username", "password", (byte) 3);
        student2 = new Student(150121091, "name", "surname", "username", "password", (byte) 3);
        student3 = new Student(150120045, "name", "surname", "username", "password", (byte) 3);
        Course course1 = new Course("courseName1", "CSE100", 3, (byte) 3);
        advisor1 = new Advisor(130116002, "name", "surname", "username", "password");
        List<Course> draft1 = new ArrayList<>();
        List<Course> draft2 = new ArrayList<>();
        List<Course> draft3 = new ArrayList<>();
        student1.setDraft(draft1);
        student2.setDraft(draft2);
        student3.setDraft(draft3);
        student3.setAdvisor(advisor1);
        draft3.add(course1);
        Registration registration1 = new Registration(student3, draft3);
        registration1.addRequest(advisor1);
        student1.setHasRequest(true);
        student2.setHasRequest(false);
        student3.setHasRequest(false);
    }

    //Test for the students which has a request.
    @Test
    public void testSendRequestWithHasRequest(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String expected = "\nYou already have a request waiting for approval";
        student1.sendRequest();
        Assertions.assertEquals(expected.trim(),outContent.toString().trim());
        System.setOut(System.out);
    }

    //Test for the student which has an empty draft.
    @Test
    public void testSendRequestWithDraftIsEmpty(){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        String expected = "\nYour draft is empty!";
        student2.sendRequest();
        Assertions.assertEquals(expected.trim(),outputStream.toString().trim());
        System.setOut(System.out);
    }


    //Test for the student which has valid request.
    @Test
    public void testSendRequestWithValidRequest(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String expected = "\nRequest sent to advisor";
        student3.sendRequest();
        Assertions.assertEquals(expected.trim(),outContent.toString().trim());
        System.setOut(System.out);
    }
}
