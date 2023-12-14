package iteration2;
/**
 * Represents an assistant who conducts laboratory classes
 * Inherits from the Person class
 */
public class Assistant extends Person{
    /**
     * Creates a new assistant object with the given parameters
     *
     * @param ID       the assistant's ID
     * @param name     the assistant's name
     * @param surname  the assistant's surname
     */
    public Assistant(int ID, String name, String surname) {
        super(ID, name, surname, null,null);
    }

    @Override
    public boolean login(String userName, String password) {
        return false;
    }
}
