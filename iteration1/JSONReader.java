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
            String courseSectionCode = courseSection.get("courseSectionCode").asText();
            String courseCode = courseSection.get("courseCode").asText();
            String day = courseSection.get("day").asText();
            int hour = courseSection.get("hour").asInt();
            Course course = department.getCourseCodeCourseMap().get(courseCode);
            CourseSection courseSection1 = new CourseSection(course, courseSectionCode, day, hour);
            department.getCourseSections().add(courseSection1);
            department.getSectionCodeCourseMap().put(courseSectionCode, course);
            department.getSectionCourseMap().put(courseSection1, course);
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
            int id = lecturer.get("lecturerID").asInt();
            String name = lecturer.get("name").asText();
            String surname = lecturer.get("surname").asText();

            Lecturer lecturer1 = new Lecturer(id, name, surname);
            department.getLecturers().add(lecturer1);
            lecturer1.setDepartment(department);

            List<String> lessonsTaught = new ArrayList<>();

            JsonNode lessonsTaughtArray = lecturer.get("lessonsTaught");
            for (JsonNode lessonTaught : lessonsTaughtArray) {
                lessonsTaught.add(lessonTaught.asText());
            }
            lecturerIDCoursesMap.put(id, lessonsTaught);
        }
    }

    public void readStudents() {
        mapper = new ObjectMapper();
        try {
            // Parse the JSON file into a Java object.
            jsonNode = mapper.readTree(new File("iteration1/jsons/students.json"));
        } catch (IOException e) {
            System.out.println("File not found");
            System.exit(0);
        }
        // Get the students array.
        JsonNode studentsArray = jsonNode;

        for (JsonNode student : studentsArray) {
            int id = student.get("studentID").asInt();
            String name = student.get("name").asText();
            String surname = student.get("surname").asText();
            String userName = student.get("userName").asText();
            String password = student.get("password").asText();
            int gradeLevel = student.get("gradeLevel").asInt();
            Student student1 = new Student(id, name, surname, userName, password, (byte) gradeLevel);
            student1.setDepartment(department);
            department.getStudents().add(student1);
            department.getUserNamePersonMap().put(userName, student1);
            department.getStudentIDStudentMap().put(id, student1);

            int advisorID = student.get("advisorID").asInt();
            studentIDAdvisorIDMap.put(id, advisorID);

            List<String> draft = new ArrayList<>();
            JsonNode draftArray = student.get("draft");
            for (JsonNode draftCourse : draftArray) {
                draft.add(draftCourse.asText());
            }
            if (draft.isEmpty()) {
                student1.setHasRequest(false);
            } else {
                student1.setHasRequest(true);
                studentIDDraftMap.put(id, draft);
            }
            readTranscript(student1);
        }
    }

    public void readTranscript(Student student) {
        mapper = new ObjectMapper();

        try {
            // Parse the JSON file into a Java object.
            jsonNode = mapper.readTree(new File("iteration1/jsons/Transcripts/" + student.getID() + ".json"));
        } catch (IOException e) {
            System.out.println("File not found");
            System.exit(0);
        }

        Map<Course, Grade> courseGradeMap = new HashMap<>();
        List<Course> studentCourses = new ArrayList<>();

        // Get the students array.
        JsonNode transcript = jsonNode;
        JsonNode courses = transcript.get("courses");
        for (JsonNode course : courses) {
            String letterGrade = course.get("letterGrade").asText();
            String courseCode = course.get("courseCode").asText();
            Course course1 = department.getCourseCodeCourseMap().get(courseCode);
            studentCourses.add(course1);
            if (letterGrade.equals("null")) {
                courseGradeMap.put(course1, null);
            } else {
                courseGradeMap.put(course1, new Grade(letterGrade));
            }
        }
        Transcript transcript1 = new Transcript(student);
        department.getTranscripts().add(transcript1);
        transcript1.setStudentCourses(studentCourses);
        student.setTranscript(transcript1);
        student.getTranscript().setCourseGradeMap(courseGradeMap);
        transcript1.calculateValues();
    }


    public void readRequests()  {

    }


    public void readAdvisors() {

    }

    public void syncObjects()  {

    }

}
