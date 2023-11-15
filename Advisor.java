package iteration1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Advisor extends Lecturer implements IDisplayMenu {
    //All attributes.
    private List<Student> studentsAdvised;
    private List<Registration> requestList;
    private int requestNumber;

    //Implement constructor
    public Advisor(int ID, String name, String surname, String userName, String password) {
        super(ID, name, surname, userName, password);
        studentsAdvised = new ArrayList<>();
        requestList = new ArrayList<>();
    }
    //Shows the list of request
    public void printRequests() {

    }
    //This method is used for replying the request
    public void replyRequests() {

    }
    //This override method is used for print menu.
    @Override
    public void printMenu(String menuType) {
        System.out.println("\nAdvisor Menu");
        System.out.println("Please select from the following options:");
        System.out.println("0. Exit");
        System.out.println("1. List requests");
        System.out.println("2. Log out");
        System.out.print("Enter your choice: ");
    }

    public List<Student> getStudentsAdvised() {
        return studentsAdvised;
    }

    public List<Registration> getRequests() {
        return requestList;
    }

    //This override method is used for username and password part.
    //Overrides the abstract login method in the Person class.
    @Override
    boolean login(String userName, String password) {
        return this.getUserName().equals(userName) && this.getPassword().equals(password);
    }

    public List<Registration> getRequestList() {
        return requestList;
    }
}
