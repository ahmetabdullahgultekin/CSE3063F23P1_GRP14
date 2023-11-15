package iteration1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONReader {
    ObjectMapper mapper;
    JsonNode jsonNode;
    Department department;
    Map<Course, List<String>> coursePrerequisiteCourseCodesMap = new HashMap<>();
    Map<Course, List<String>> courseSectionCodesMap = new HashMap<>();
    Map<Integer, List<String>> lecturerIDCoursesMap = new HashMap<>();
    Map<Integer, List<String>> advisorIDCoursesMap = new HashMap<>();
    Map<Integer, Integer> studentIDAdvisorIDMap = new HashMap<>();
    Map<Integer, List<String>> studentIDDraftMap = new HashMap<>();
    Map<Student, Registration> studentRegistrationMap = new HashMap<>();


    public void start(Department department) {

    }

    public void readJson() {
        readCourses();
        readCourseSections();
        readLecturers();
        readStudents();
        readRequests();
        readAdvisors();
    }

    public void readCourses()  {

    }

    public void readCourseSections() {

    }

    public void readLecturers()  {

    }

    public void readStudents() {

    }

    public void readTranscript() {

    }


    public void readRequests()  {

    }


    public void readAdvisors() {

    }

    public void syncObjects()  {

    }

}
