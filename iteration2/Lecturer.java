package iteration2;

import java.util.ArrayList;
import java.util.List;

public class Lecturer extends Person {

    List<Course> lessonsTaught;

    //For creating lecturer with person abstract class.
    public Lecturer(int ID, String name, String surname) {
        super(ID, name, surname, null,null);
        lessonsTaught = new ArrayList<>();
    }

    //For creating advisor with person abstract class.
    public Lecturer(int ID, String name, String surname, String userName, String password) {
        super(ID, name, surname, userName, password);
        lessonsTaught = new ArrayList<>();
    }

    //Get the given courses lecturer gives.
    public List<Course> getLessonsTaught() {
        return lessonsTaught;
    }

    //Advisor login proccess.
    @Override
    public boolean login(String userName, String password) {
        return false;
    }
}
