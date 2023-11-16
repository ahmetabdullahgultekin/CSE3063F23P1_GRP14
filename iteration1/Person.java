package iteration1;

/**
 * Represents a person who can be student, lecturer or advisor
 * And contains common features of these classes:
 * ID, name, surname, department, userName and password of the person
 */

public abstract class Person {
    private int ID;
    private String name;
    private String surname;
    private Department department;
    private String userName;
    private String password;

    /**
     * Creates a new person object with the given parameters
     *
     * @param ID       the person's ID
     * @param name     the person's name
     * @param surname  the person's surname
     * @param userName the person's username
     * @param password the person's password
     */
    public Person(int ID, String name, String surname, String userName, String password) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Creates a new person object with the given parameters
     *
     * @param userName the person's username
     * @param password the person's password
     */
    public Person(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }


    /**
     * The method to check whether the userName and password match
     */
    abstract boolean login(String userName, String password);


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getID() {
        return ID;
    }


}
