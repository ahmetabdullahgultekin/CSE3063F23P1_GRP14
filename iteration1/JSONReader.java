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
        this.department = department;
        readJson();
        syncObjects();
    }

    public void readJson() {
        readCourses();
        readCourseSections();
        readLecturers();
        readStudents();
        readRequests();
        readAdvisors();
    }

    public void readCourses() {
        mapper = new ObjectMapper();
        try {
            // Parse the JSON file into a Java object.
            jsonNode = mapper.readTree(new File("iteration1/jsons/courses.json"));
        } catch (IOException e) {
            System.out.println("File not found");
            System.exit(0);
        }
        // Get the students array.
        JsonNode coursesArray = jsonNode;

        for (JsonNode course : coursesArray) {
            String courseName = course.get("courseName").asText();
            String courseCode = course.get("courseCode").asText();
            int courseCredit = course.get("courseCredit").asInt();
            byte gradeLevel = (byte) course.get("gradeLevel").asInt();

            Course course1 = new Course(courseName, courseCode, courseCredit, gradeLevel);
            department.getCourses().add(course1);
            department.getCourseCodeCourseMap().put(courseCode, course1);

            List<String> preRequisiteCourseCodes = new ArrayList<>();
            List<String> courseSectionCodes = new ArrayList<>();

            JsonNode preRequisiteCourseCodesArray = course.get("preRequisiteCourseCodes");
            for (JsonNode preRequisiteCourseCode : preRequisiteCourseCodesArray) {
                preRequisiteCourseCodes.add(preRequisiteCourseCode.asText());
            }
            coursePrerequisiteCourseCodesMap.put(course1, preRequisiteCourseCodes);
            JsonNode courseSectionCodesArray = course.get("courseSectionCodes");
            for (JsonNode courseSectionCode : courseSectionCodesArray) {
                courseSectionCodes.add(courseSectionCode.asText());
            }
            courseSectionCodesMap.put(course1, courseSectionCodes);
        }
    }

    public void readCourseSections() {
        mapper = new ObjectMapper();

        try {
            // Parse the JSON file into a Java object.
            jsonNode = mapper.readTree(new File("iteration1/jsons/courseSections.json"));
        } catch (IOException e) {
            System.out.println("File not found");
            System.exit(0);
        }
        JsonNode courseSectionsArray = jsonNode;

        for (JsonNode courseSection : courseSectionsArray) {
            //Construction part
            String courseSectionCode = courseSection.get("courseSectionCode").asText();
            String courseCode = courseSection.get("courseCode").asText();
            String day = courseSection.get("day").asText();
            int hour = courseSection.get("hour").asInt();
            Course course = department.getCourseCodeCourseMap().get(courseCode);
            CourseSection courseSection1 = new CourseSection(course, courseSectionCode, day, hour);
            department.getCourseSections().add(courseSection1);
            department.getSectionCodeCourseMap().put(courseSectionCode, course);
            department.getSectionCourseMap().put(courseSection1, course);
            //////////////////////////////////////////////
            System.out.printf("courseSection: %s, courseCode: %s, day: %s, hour: %d\n", courseSectionCode, courseCode, day, hour);
        }
    }

    public void readLecturers() {
        mapper = new ObjectMapper();

        try {
            // Parse the JSON file into a Java object.
            jsonNode = mapper.readTree(new File("iteration1/jsons/lecturers.json"));
        } catch (IOException e) {
            System.out.println("File not found");
            System.exit(0);
        }
        JsonNode lecturersArray = jsonNode;

        for (JsonNode lecturer : lecturersArray) {
            //Construction part
            int id = lecturer.get("lecturerID").asInt();
            String name = lecturer.get("name").asText();
            String surname = lecturer.get("surname").asText();

            Lecturer lecturer1 = new Lecturer(id, name, surname);
            department.getLecturers().add(lecturer1);
            lecturer1.setDepartment(department);
            //////////////////////////////////////////////

            List<String> lessonsTaught = new ArrayList<>();

            JsonNode lessonsTaughtArray = lecturer.get("lessonsTaught");
            for (JsonNode lessonTaught : lessonsTaughtArray) {
                lessonsTaught.add(lessonTaught.asText());
            }
            lecturerIDCoursesMap.put(id, lessonsTaught);

            System.out.printf("lecturerID: %d, name: %s, surname: %s\n", id, name, surname);
        }
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
