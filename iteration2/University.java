package iteration2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class University {
    private String name;
    private List<Department> departments; //it holds departments list.
    private Map<String, Person> userNamePersonMap;  //it holds username and password of users.

    public University(String name) {
        this.name = name;
        departments = new ArrayList<>();
        userNamePersonMap = new HashMap<>();
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public Map<String, Person> getUserNamePersonMap() {
        return userNamePersonMap;
    }
}
