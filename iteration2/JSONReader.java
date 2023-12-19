package iteration2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The JSONReader class is responsible for reading JSON data and populating the department object with the data.
 * It contains methods for reading the following JSON files:
 * - "courses.json": Contains data about the courses.
 * - "labSections.json": Contains data about the laboratory sections.
 * - "lecturers.json": Contains data about the lecturers.
 * - "students.json": Contains data about the students.
 * - "requests.json": Contains data about the requests.
 * - "advisors.json": Contains data about the advisors.
 * <p>
 * Each of these methods is responsible for reading its respective JSON file and populating the department object with the data.
 */
public class JSONReader {
    private final Map<Course, List<String>> coursePrerequisiteCourseCodesMap = new HashMap<>();
    private final Map<Integer, List<String>> lecturerIDCoursesMap = new HashMap<>();
    private final Map<Integer, Assistant> assistantIDAssistantMap = new HashMap<>();
    private final Map<Integer, List<String>> advisorIDCoursesMap = new HashMap<>();
    private final Map<Integer, Integer> studentIDAdvisorIDMap = new HashMap<>();
    private final Map<Student, Registration> studentRegistrationMap = new HashMap<>();
    private final Map<String, LaboratorySection> sectionCodeSectionMap = new HashMap<>();
    private final Logger logger = LogManager.getLogger(JSONReader.class);
    /**
     * The JSONReader class contains several private fields used throughout the class.
     * <p>
     * - ObjectMapper mapper: An instance of ObjectMapper from the Jackson library, used for converting between Java objects and JSON.
     * - JsonNode jsonNode: A node in the JSON tree, used for parsing JSON data.
     * - Department department: The department object that the JSON data will be populated into.
     * - Map<Course, List<String>> coursePrerequisiteCourseCodesMap: A map that associates each Course object with a list of prerequisite course codes.
     * - Map<Course, List<String>> courseSectionCodesMap: A map that associates each Course object with a list of course section codes.
     * - Map<Integer, List<String>> lecturerIDCoursesMap: A map that associates each lecturer's ID with a list of course codes that they teach.
     * - Map<Integer, List<String>> advisorIDCoursesMap: A map that associates each advisor's ID with a list of course codes that they teach.
     * - Map<Integer, Integer> studentIDAdvisorIDMap: A map that associates each student's ID with their advisor's ID.
     * - Map<Integer, List<String>> studentIDDraftMap: A map that associates each student's ID with a list of draft course codes.
     * - Map<Student, Registration> studentRegistrationMap: A map that associates each Student object with a Registration object.
     */
    private ObjectMapper mapper;
    private JsonNode jsonNode;
    private University university;
    private Department department;


    public void readDepartments(University university) {
        this.university = university;
        mapper = new ObjectMapper();
        try {
            // Parse the JSON file into a Java object.
            jsonNode = mapper.readTree(new File("iteration2/jsons/departments.json"));
        } catch (IOException e) {
            // If the file is not found, print an error message and terminate the program.
            System.out.println("File not found");
            System.exit(0);
        }
        // Get the array of courses from the parsed JSON.
        JsonNode departmentsArray = jsonNode;

        // Iterate over each course in the array.
        for (JsonNode department : departmentsArray) {
            // Retrieve the course name, course code, course credit, and grade level from the JSON.
            String departmentName = department.get("departmentName").asText();

            // Create a new department object with the retrieved details.
            Department department1 = new Department(departmentName);
            logger.info("Department " + departmentName + " created.");
            university.getDepartments().add(department1);
        }
    }


    /**
     * Starts the process of reading JSON data and synchronizing objects in the provided department.
     *
     * @param department the department to start the process for
     */
    public void start(Department department) {
        this.department = department;
        logger.info("Reading JSON data and synchronizing objects for department: {}", department.getDepartmentName());
        readJson();
        syncObjects();
        logger.info("JSON data read and objects synchronized for department: {}", department.getDepartmentName());
    }


    /**
     * This method reads data from various JSON files and populates the department object with the data.
     * It calls separate methods to read the following JSON files:
     * - "courses.json": Contains data about the courses.
     * - "labSections.json": Contains data about the laboratory sections.
     * - "lecturers.json": Contains data about the lecturers.
     * - "students.json": Contains data about the students.
     * - "requests.json": Contains data about the requests.
     * - "advisors.json": Contains data about the advisors.
     * <p>
     * Each of these methods is responsible for reading its respective JSON file and populating the department object with the data.
     */
    public void readJson() {
        readCourses();
        readAssistants();
        readLabSections();
        readLecturers();
        readStudents();
        readRequests();
        readAdvisors();
    }

    /**
     * This method reads courses from a JSON file and adds them to the department's collection.
     * The JSON file should be located at "iteration2/jsons/courses.json".
     * Each course in the JSON file should have the following properties:
     * - "courseName" (String): the name of the course.
     * - "courseCode" (String): the code of the course.
     * - "courseCredit" (int): the credit of the course.
     * - "semester" (int): the grade level of the course.
     * - "preRequisiteCourseCodes" (Array of Strings): the list of prerequisite course codes for the course.
     * <p>
     * After reading and parsing the JSON file, the courses will be added to the department's
     * collection of courses. The course code will be mapped to the course object, and
     * the course will also be added to the department's course list.
     */
    public void readCourses() {
        // Create an ObjectMapper instance for converting between Java objects and JSON.
        mapper = new ObjectMapper();
        try {
            // Parse the JSON file into a Java object.
            jsonNode = mapper.readTree(new File("iteration2/jsons/courses.json"));
        } catch (IOException e) {
            // If the file is not found, print an error message and terminate the program.
            System.out.println("File not found");
            System.exit(0);
        }
        // Get the array of courses from the parsed JSON.
        JsonNode coursesArray = jsonNode;

        // Iterate over each course in the array.
        for (JsonNode course : coursesArray) {
            // Retrieve the course name, course code, course credit, and grade level from the JSON.
            String courseName = course.get("courseName").asText();
            String courseCode = course.get("courseCode").asText();
            String courseType = course.get("courseType").asText();
            int courseCredit = course.get("courseCredit").asInt();
            byte semester = (byte) course.get("semester").asInt();
            int capacity = course.get("capacity").asInt();
            int hour = course.get("hour").asInt();
            String day = course.get("day").asText();

            // Create a new Course object with the retrieved details.
            Course course1 = new Course(courseName, courseCode, courseType, courseCredit, semester, capacity, hour, day);
            logger.info("Course " + courseCode + " created.");

            // Add the new Course object to the department's list of courses.
            department.getCourses().add(course1);

            // Map the course code to the Course object in the department's courseCodeCourseMap.
            department.getCourseCodeCourseMap().put(courseCode, course1);

            // Create a list to store the prerequisite course codes and course section codes.
            List<String> preRequisiteCourseCodes = new ArrayList<>();

            // Get the array of prerequisite course codes and course section codes from the course.
            JsonNode preRequisiteCourseCodesArray = course.get("preRequisiteCourseCodes");
            for (JsonNode preRequisiteCourseCode : preRequisiteCourseCodesArray) {
                preRequisiteCourseCodes.add(preRequisiteCourseCode.asText());
            }
            coursePrerequisiteCourseCodesMap.put(course1, preRequisiteCourseCodes);
        }
    }

    /**
     * This method reads assistants from a JSON file and adds them to the department's collection.
     * The JSON file should be located at "iteration2/jsons/assistants.json".
     * Each assistant in the JSON file should have the following properties:
     * - "assistantID" (int): the ID of the assistant.
     * - "name" (String): the name of the assistant.
     * - "surname" (String): the surname of the assistant.
     * <p>
     * After reading and parsing the JSON file, the assistants will be added to the department's
     * collection of assistants. The assistant's ID will be mapped to the assistant object, and
     * the assistant will also be added to the department's assistant list.
     */
    public void readAssistants() {
        // Create an ObjectMapper instance for converting between Java objects and JSON.
        mapper = new ObjectMapper();
        try {
            // Parse the JSON file into a Java object.
            jsonNode = mapper.readTree(new File("iteration2/jsons/assistants.json"));
        } catch (IOException e) {
            // If the file is not found, print an error message and terminate the program.
            System.out.println("File not found");
            System.exit(0);
        }
        // Get the array of lecturers from the parsed JSON.
        JsonNode assistantsArray = jsonNode;

        // Iterate over each lecturer in the array.
        for (JsonNode lecturer : assistantsArray) {
            // Retrieve the lecturer's ID, name, and surname from the JSON.
            int id = lecturer.get("assistantID").asInt();
            String name = lecturer.get("name").asText();
            String surname = lecturer.get("surname").asText();

            // Create a new Lecturer object with the retrieved details.
            Person assistant1 = new Assistant(id, name, surname);
            logger.info("Assistant created with ID: {}", id);

            // Add the new Lecturer object to the department's list of lecturers.
            department.getAssistants().add((Assistant) assistant1);
            assistantIDAssistantMap.put(id, (Assistant) assistant1);
            // Set the department for the lecturer.
            assistant1.setDepartment(department);
        }
    }

    /**
     * This method reads course sections from a JSON file and adds them to the department's collection.
     * The JSON file should be located at "iteration2/jsons/labSections.json".
     * Each course section in the JSON file should have the following properties:
     * - "courseSectionCode" (String): the code of the course section.
     * - "courseCode" (String): the code of the course.
     * - "day" (String): the day of the course section.
     * - "hour" (int): the hour of the course section.
     * - "capacity" (int): the capacity of the course section.
     * - "lecturerID" (int): the ID of the lecturer for the course section.
     * <p>
     * After reading and parsing the JSON file, the course sections will be added to the department's
     * collection of course sections. The course section code will be mapped to the course object, and
     * the course section will also be added to the department's course section list.
     */
    public void readLabSections() {
        // Create an ObjectMapper instance for converting between Java objects and JSON.
        mapper = new ObjectMapper();
        try {
            // Parse the JSON file into a Java object.
            jsonNode = mapper.readTree(new File("iteration2/jsons/labSections.json"));
        } catch (IOException e) {
            // If the file is not found, print an error message and terminate the program.
            System.out.println("File not found");
            System.exit(0);
        }
        JsonNode labSectionsArray = jsonNode;

        for (JsonNode labSection : labSectionsArray) {
            String labSectionCode = labSection.get("labSectionCode").asText();
            String courseCode = labSection.get("courseCode").asText();
            String day = labSection.get("day").asText();
            int hour = labSection.get("hour").asInt();
            int capacity = labSection.get("capacity").asInt();
            int assistantID = labSection.get("assistantID").asInt();
            Course course = department.getCourseCodeCourseMap().get(courseCode);

            LaboratorySection labSection1 = new LaboratorySection(labSectionCode, capacity, hour, day);
            logger.info("Lab section created with code: {}", labSectionCode);

            labSection1.setAssistant(assistantIDAssistantMap.get(assistantID));
            sectionCodeSectionMap.put(labSectionCode, labSection1);
            department.getLaboratorySections().add(labSection1);
            department.getSectionCodeCourseMap().put(labSectionCode, course);
            department.getLabSectionCourseMap().put(labSection1, course);
        }
    }


    /**
     * This method reads lecturers from a JSON file and adds them to the department's collection.
     * The JSON file should be located at "iteration2/jsons/lecturers.json".
     * Each lecturer in the JSON file should have the following properties:
     * - "lecturerID" (int): the ID of the lecturer.
     * - "name" (String): the name of the lecturer.
     * - "surname" (String): the surname of the lecturer.
     * - "lessonsTaught" (Array of Strings): the list of lessons taught by the lecturer.
     * <p>
     * After reading and parsing the JSON file, the lecturers will be added to the department's
     * collection of lecturers. The lecturer's ID will be mapped to the lecturer object, and
     * the lecturer will also be added to the department's lecturer list.
     */
    public void readLecturers() {
        // Create an ObjectMapper instance for converting between Java objects and JSON.
        mapper = new ObjectMapper();
        try {
            // Parse the JSON file into a Java object.
            jsonNode = mapper.readTree(new File("iteration2/jsons/lecturers.json"));
        } catch (IOException e) {
            // If the file is not found, print an error message and terminate the program.
            System.out.println("File not found");
            System.exit(0);
        }
        // Get the array of lecturers from the parsed JSON.
        JsonNode lecturersArray = jsonNode;

        // Iterate over each lecturer in the array.
        for (JsonNode lecturer : lecturersArray) {
            // Retrieve the lecturer's ID, name, and surname from the JSON.
            int id = lecturer.get("lecturerID").asInt();
            String name = lecturer.get("name").asText();
            String surname = lecturer.get("surname").asText();

            // Create a new Lecturer object with the retrieved details.
            Person lecturer1 = new Lecturer(id, name, surname);
            logger.info("Lecturer created with ID: {}", id);

            // Add the new Lecturer object to the department's list of lecturers.
            department.getLecturers().add((Lecturer) lecturer1);

            // Set the department for the lecturer.
            lecturer1.setDepartment(department);

            // Create a list to store the lessons taught by the lecturer.
            List<String> lessonsTaught = new ArrayList<>();

            // Get the array of lessons taught from the lecturer.
            JsonNode lessonsTaughtArray = lecturer.get("lessonsTaught");
            // Iterate over each lesson taught in the array.
            for (JsonNode lessonTaught : lessonsTaughtArray) {
                // Add the lesson to the lessons taught list.
                lessonsTaught.add(lessonTaught.asText());
            }
            // Map the lecturer's ID to the lessons taught in the lecturerIDCoursesMap.
            lecturerIDCoursesMap.put(id, lessonsTaught);
        }
    }

    /**
     * This method reads students from a JSON file and adds them to the department's collection.
     * The JSON file should be located at "iteration2/jsons/students.json".
     * Each student in the JSON file should have the following properties:
     * - "studentID" (int): the ID of the student.
     * - "name" (String): the name of the student.
     * - "surname" (String): the surname of the student.
     * - "userName" (String): the username of the student.
     * - "password" (String): the password of the student.
     * - "semester" (int): the grade level of the student.
     * - "advisorID" (int): the ID of the student's advisor.
     * - "transcript" (Array of Objects): the list of courses in the student's transcript.
     * <p>
     * After reading and parsing the JSON file, the students will be added to the department's
     * collection of students. The student's ID will be mapped to the student object, and
     * the student will also be added to the department's student list.
     */
    public void readStudents() {
        // Create an ObjectMapper instance for converting between Java objects and JSON.
        mapper = new ObjectMapper();
        try {
            // Parse the JSON file into a Java object.
            jsonNode = mapper.readTree(new File("iteration2/jsons/students.json"));
        } catch (IOException e) {
            // If the file is not found, print an error message and terminate the program.
            System.out.println("File not found");
            System.exit(0);
        }
        // Get the array of students from the parsed JSON.
        JsonNode studentsArray = jsonNode;

        // Iterate over each student in the array.
        for (JsonNode student : studentsArray) {
            // Retrieve the student's ID, name, surname, username, password, and grade level from the JSON.
            int id = student.get("studentID").asInt();
            String name = student.get("name").asText();
            String surname = student.get("surname").asText();
            String userName = student.get("userName").asText();
            String password = student.get("password").asText();
            int semester = student.get("semester").asInt();

            // Create a new Student object with the retrieved details.
            Person student1 = new Student(id, name, surname, userName, password, (byte) semester);
            logger.info("Student created with ID: {}", id);
            student1.setDepartment(department);


            JsonNode labSectionsArray = student.get("labSections");
            for (JsonNode labSectionCode : labSectionsArray) {
                ((Student) student1).getLabSections().add(sectionCodeSectionMap.get(labSectionCode.asText()));
            }

            // Add the new Student object to the department's list of students.
            department.getStudents().add((Student) student1);

            // Map the student's username to the Student object in the university's userNamePersonMap.
            university.getUserNamePersonMap().put(userName, student1);

            // Map the student's ID to the Student object in the department's studentIDStudentMap.
            department.getStudentIDStudentMap().put(id, (Student) student1);

            // Retrieve the advisor ID for the student from the JSON.
            int advisorID = student.get("advisorID").asInt();

            // Map the student's ID to the advisor ID in the studentIDAdvisorIDMap.
            studentIDAdvisorIDMap.put(id, advisorID);

            // Read the transcript for the student.
            readTranscript((Student) student1);
        }
    }

    /**
     * This method reads a student's transcript from a JSON file and sets it for the student.
     * The JSON file should be located at "iteration2/jsons/Transcripts/{studentID}.json".
     * Each course in the JSON file should have the following properties:
     * - "courseCode" (String): the code of the course.
     * - "letterGrade" (String): the letter grade of the student in the course.
     * <p>
     * After reading and parsing the JSON file, the transcript will be set for the student.
     * The course grade map will also be set for the student's transcript.
     *
     * @param student the student to read the transcript for
     */
    public void readTranscript(Student student) {
        // Create an ObjectMapper instance for converting between Java objects and JSON.
        mapper = new ObjectMapper();
        CourseRegistrationSystem system = new CourseRegistrationSystem();
        try {
            // Parse the JSON file into a Java object.
            jsonNode = mapper.readTree(new File("iteration2/jsons/Transcripts/" + student.getID() + ".json"));
        } catch (IOException e) {
            // If the file is not found, print an error message and terminate the program.
            System.out.println("File not found");
            System.exit(0);
        }

        // Create a map to store the course and grade pairs.
        Map<Course, List<Grade>> courseGradeMap = new HashMap<>();
        // Create a list to store the courses of the student.
        List<Course> studentCourses = new ArrayList<>();

        // Get the transcript from the parsed JSON.
        JsonNode transcript = jsonNode;
        // Get the courses from the transcript.
        JsonNode courses = transcript.get("courses");
        // Iterate over each course in the transcript.
        for (JsonNode course : courses) {
            // Retrieve the letter grade and course code from the JSON.
            String letterGrade = course.get("letterGrade").asText();
            String courseCode = course.get("courseCode").asText();
            // Get the Course object corresponding to the course code.
            Course course1 = department.getCourseCodeCourseMap().get(courseCode);
            // Add the course to the student's courses.

            if (!studentCourses.contains(course1)) {
                studentCourses.add(course1);
                List<Grade> grades = new ArrayList<>();

                if (letterGrade.equals("null")) {
                    system.addToSchedule(course1, student);
                    course1.setNumberOfStudents(course1.getNumberOfStudents() + 1);
                    grades.add(null);
                    courseGradeMap.put(course1, grades);
                } else {
                    grades.add(new Grade(letterGrade));
                    courseGradeMap.put(course1, grades);
                }
            } else {
                if (letterGrade.equals("null")) {
                    system.addToSchedule(course1, student);
                    course1.setNumberOfStudents(course1.getNumberOfStudents() + 1);
                    courseGradeMap.get(course1).add(null);
                } else {
                    courseGradeMap.get(course1).add(new Grade(letterGrade));
                }
            }


        }
        // Create a new Transcript object for the student.
        Transcript transcript1 = new Transcript(student);
        // Add the transcript to the department's transcripts.
        department.getTranscripts().add(transcript1);
        // Set the student's courses in the transcript.
        transcript1.setStudentCourses(studentCourses);
        // Set the transcript for the student.
        student.setTranscript(transcript1);
        // Set the course grade map in the student's transcript.
        student.getTranscript().setCourseGradeMap(courseGradeMap);
        // Calculate the values for the transcript.
        transcript1.calculateValues();
        logger.info("Transcript created for student with ID: {}", student.getID());
    }

    /**
     * This method reads requests from a JSON file and sets them for the students.
     * The JSON file should be located at "iteration2/jsons/requests.json".
     * Each request in the JSON file should have the following properties:
     * - "studentID" (int): the ID of the student.
     * - "courses" (Array of Strings): the list of course codes in the student's request.
     * <p>
     * After reading and parsing the JSON file, the request will be set for the student.
     * The draft courses will also be set for the student.
     */
    public void readRequests() {
        // Create an ObjectMapper instance for converting between Java objects and JSON.
        mapper = new ObjectMapper();

        try {
            // Parse the JSON file into a Java object.
            jsonNode = mapper.readTree(new File("iteration2/jsons/requests.json"));
        } catch (IOException e) {
            // If the file is not found, print an error message and terminate the program.
            System.out.println("File not found");
            System.exit(0);
        }
        // Get the array of requests from the parsed JSON.
        JsonNode requestsArray = jsonNode;
        // Iterate over each request in the array.
        for (JsonNode request : requestsArray) {
            // Create a list to store the draft courses for the student.
            List<Course> draftCourses = new ArrayList<>();
            // Retrieve the student's ID from the JSON.
            int studentID = request.get("studentID").asInt();
            // Get the array of course codes from the request.
            JsonNode courseCodesArray = request.get("courses");
            // Iterate over each course code in the array.
            for (JsonNode courseCode : courseCodesArray) {
                // Get the Course object corresponding to the course code.
                Course course1 = department.getCourseCodeCourseMap().get(courseCode.asText());
                // Add the course to the draft courses.
                draftCourses.add(course1);
            }
            // Get the Student object corresponding to the student ID.
            Student student = department.getStudentIDStudentMap().get(studentID);
            // Set the draft courses for the student.
            student.setDraft(draftCourses);
            // Set the student's hasRequest flag to true.
            student.setHasRequest(true);
            // Map the student to the Registration object in the studentRegistrationMap.
            studentRegistrationMap.put(student, new Registration(student, draftCourses));
        }
    }

    /**
     * This method reads advisors from a JSON file and adds them to the department's collection.
     * The JSON file should be located at "iteration2/jsons/advisors.json".
     * Each advisor in the JSON file should have the following properties:
     * - "advisorID" (int): the ID of the advisor.
     * - "name" (String): the name of the advisor.
     * - "surname" (String): the surname of the advisor.
     * - "userName" (String): the username of the advisor.
     * - "password" (String): the password of the advisor.
     * - "lessonsTaught" (Array of Strings): the list of lessons taught by the advisor.
     * <p>
     * After reading and parsing the JSON file, the advisors will be added to the department's
     * collection of advisors. The advisor's ID will be mapped to the advisor object, and
     * the advisor will also be added to the department's advisor list.
     */
    public void readAdvisors() {
        // Create an ObjectMapper instance for converting between Java objects and JSON.
        mapper = new ObjectMapper();
        try {
            // Parse the JSON file into a Java object.
            jsonNode = mapper.readTree(new File("iteration2/jsons/advisors.json"));
        } catch (IOException e) {
            // If the file is not found, print an error message and terminate the program.
            System.out.println("File not found");
            System.exit(0);
        }

        // Get the array of advisors from the parsed JSON.
        JsonNode advisorsArray = jsonNode;
        // Iterate over each advisor in the array.
        for (JsonNode advisor : advisorsArray) {
            // Retrieve the advisor's ID, name, surname, username, and password from the JSON.
            int id = advisor.get("advisorID").asInt();
            String name = advisor.get("name").asText();
            String surname = advisor.get("surname").asText();
            String userName = advisor.get("userName").asText();
            String password = advisor.get("password").asText();

            // Create a new Advisor object with the retrieved details.
            Person advisor1 = new Advisor(id, name, surname, userName, password);
            logger.info("Advisor created with ID: {}", id);

            // Add the new Advisor object to the department's list of advisors.
            department.getAdvisors().add((Advisor) advisor1);

            // Set the department for the advisor.
            advisor1.setDepartment(department);

            // Map the advisor's ID to the Advisor object in the department's advisorIDAdvisorMap.
            department.getAdvisorIDAdvisorMap().put(id, (Advisor) advisor1);

            // Map the advisor's username to the Advisor object in the university's userNamePersonMap.
            university.getUserNamePersonMap().put(userName, advisor1);

            // Create a list to store the lessons taught by the advisor.
            List<String> lessonsTaught = new ArrayList<>();

            // Get the array of lessons taught from the advisor.
            JsonNode lessonsTaughtArray = advisor.get("lessonsTaught");
            // Iterate over each lesson taught in the array.
            for (JsonNode lessonTaught : lessonsTaughtArray) {
                // Add the lesson to the lessons taught list.
                lessonsTaught.add(lessonTaught.asText());
            }
            // Map the advisor's ID to the lessons taught in the advisorIDCoursesMap.
            advisorIDCoursesMap.put(id, lessonsTaught);
        }
    }

    /**
     * This method synchronizes the objects in the department.
     * It ensures that all necessary information is properly linked and associated.
     * <p>
     * For example, it ensures that each course has its prerequisite courses and course sections,
     * each lecturer has their courses, each student has their advisor and registration,
     * and each advisor has their courses.
     */
    public void syncObjects() {
        // Sync for courses
        // For each course in the department, add its prerequisite courses and course sections
        for (Course course : department.getCourses()) {
            for (String courseCode : coursePrerequisiteCourseCodesMap.get(course)) {
                course.getPreRequisiteCourses().add(department.getCourseCodeCourseMap().get(courseCode));
            }
        }

        // Sync for lab sections
        for (LaboratorySection labSection : department.getLaboratorySections()) {
            Course course = department.getLabSectionCourseMap().get(labSection);
            course.getLaboratorySections().add(labSection);
        }

        // Sync for lecturers
        // For each lecturer in the department, add the courses they teach and set them as the lecturer for each course
        for (Lecturer lecturer : department.getLecturers()) {
            for (String courseCode : lecturerIDCoursesMap.get(lecturer.getID())) {
                lecturer.getLessonsTaught().add(department.getCourseCodeCourseMap().get(courseCode));
            }
            for (Course course : lecturer.getLessonsTaught()) {
                course.setLecturer(lecturer);
            }
        }

        // Sync for advisors
        // For each student in the department, add them to their advisor's list of advised students and add their registration to the advisor's request list
        // For each advisor in the department, add the courses they teach
        for (Student student : department.getStudents()) {
            Advisor advisor = department.getAdvisorIDAdvisorMap().get(studentIDAdvisorIDMap.get(student.getID()));
            student.setAdvisor(advisor);
            advisor.getStudentsAdvised().add(student);
            if (studentRegistrationMap.get(student) != null)
                advisor.getRequestList().add(studentRegistrationMap.get(student));
        }
        for (Advisor advisor : department.getAdvisors()) {
            for (String courseCode : advisorIDCoursesMap.get(advisor.getID())) {
                advisor.getLessonsTaught().add(department.getCourseCodeCourseMap().get(courseCode));
            }
        }
    }
}
