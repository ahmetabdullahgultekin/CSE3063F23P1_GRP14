package iteration2.Test;

import iteration1.CourseRegistrationSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseRegistrationSystemTest {

    private CourseRegistrationSystem courseRegistrationSystem;

    @BeforeEach
    void setUp() {
        courseRegistrationSystem = new CourseRegistrationSystem();
    }

    @Test
    void getInputReturnsCorrectValueWhenInputIsValid() {
        String input = "1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        int choice = courseRegistrationSystem.getInput();
        assertEquals(1, choice, "The method should return the correct input value when the input is valid.");
    }

    @Test
    void getInputReturnsMinusOneWhenInputIsInvalid() {
        String input = "invalid\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        int choice = courseRegistrationSystem.getInput();
        assertEquals(-1, choice, "The method should return -1 when the input is invalid.");
    }
}