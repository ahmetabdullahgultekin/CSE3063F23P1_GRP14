package iteration2.Test;

import iteration1.Advisor;
import iteration1.Person;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PersonTest {

    private Person person;

    @Before
    public void setUp() {
        person = new Advisor(1, "name", "surname", "validUsername", "validPassword");
    }

    @Test
    public void loginShouldReturnTrueWhenCredentialsAreValid() {
        String username = "validUsername";
        String password = "validPassword";
        assertTrue(person.login(username, password));
    }

    @Test
    public void loginShouldReturnFalseWhenCredentialsAreInvalid() {
        String username = "invalidUsername";
        String password = "invalidPassword";
        assertFalse(person.login(username, password));
    }
}