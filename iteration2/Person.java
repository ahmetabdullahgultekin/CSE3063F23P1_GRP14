package iteration2;


/**
 * Represents a person who can be lecturer, advisor or student
 * It has common features of these classes:
 * ID, name, surname, department, userName, password
 */
public abstract class Person {
    private int ID;
    private String name;
    private String surname;
    private Department department;
    private String userName;
    private String password;

    /**
     * Creates a person object with given parameters
     *
     * @param ID       the person's ID
     * @param name     the person's name
     * @param surname  the person's surname
     * @param userName the person's userName
     * @param password the person's password
     */
    public Person(int ID, String name, String surname, String userName, String password) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.userName = userName;
        this.password = password;
    }


    public abstract boolean login(String userName, String password);

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
