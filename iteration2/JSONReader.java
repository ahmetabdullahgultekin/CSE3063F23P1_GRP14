package iteration2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The JSONReader class is responsible for reading data from various JSON files and populating the department object with the data.
 * It contains several methods for reading different types of data, such as courses, course sections, lecturers, students, requests, and advisors.
 * Each of these methods reads data from a specific JSON file and populates the department object with the data.
 * The class also contains a method for synchronizing the objects in the department, ensuring that all necessary information is properly linked and associated.
 */
public class JSONReader {
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

    private Map<Course, List<String>> coursePrerequisiteCourseCodesMap = new HashMap<>();
    private Map<Course, List<String>> labSectionCodesMap = new HashMap<>();
    private Map<Integer, List<String>> lecturerIDCoursesMap = new HashMap<>();
    private Map<Integer, List<String>> assistantIDCoursesMap = new HashMap<>();
    private Map<Integer, List<String>> advisorIDCoursesMap = new HashMap<>();
    private Map<Integer, Integer> studentIDAdvisorIDMap = new HashMap<>();
    private Map<Integer, List<String>> studentIDDraftMap = new HashMap<>();
    private Map<Student, Registration> studentRegistrationMap = new HashMap<>();


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
        readJson();
        syncObjects();
    }


    /**
     * This method reads data from various JSON files and populates the department object with the data.
     * It calls separate methods to read the following JSON files:
     * - "courses.json": Contains data about the courses.
     * - "courseSections.json": Contains data about the course sections.
     * - "lecturers.json": Contains data about the lecturers.
     * - "students.json": Contains data about the students.
     * - "requests.json": Contains data about the requests.
     * - "advisors.json": Contains data about the advisors.
     * <p>
     * Each of these methods is responsible for reading its respective JSON file and populating the department object with the data.
     */
    public void readJson() {
        readCourses();
        readLabSections();
        readAssistants();
        readLecturers();
        readStudents();
        readRequests();
        readAdvisors();
    }

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

            // Add the new Lecturer object to the department's list of lecturers.
            department.getAssistants().add((Assistant) assistant1);

            // Set the department for the lecturer.
            assistant1.setDepartment(department);
        }
    }

    /**
     * This method reads the course data from a JSON file and populates the department object with the course information.
     * The JSON file should be located at "iteration2/jsons/courses.json".
     * Each course in the JSON file should have the following properties:
     * - "courseName" (String): the name of the course.
     * - "courseCode" (String): the code of the course.
     * - "courseCredit" (int): the credit value of the course.
     * - "semester" (int): the grade level of the course.
     * - "preRequisiteCourseCodes" (Array of Strings): the list of prerequisite course codes for the course.
     * - "courseSectionCodes" (Array of Strings): the list of course section codes for the course.
     * <p>
     * After reading and parsing the JSON file, the method creates a new Course object for each course.
     * It then adds the Course object to the department's courses collection and maps the course code to the Course object.
     * It also maps the prerequisite course codes and course section codes to the Course object.
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
            int courseCredit = course.get("courseCredit").asInt();
            byte semester = (byte) course.get("semester").asInt();
            int capacity = course.get("capacity").asInt();
            int hour = course.get("hour").asInt();
            String day = course.get("day").asText();

            // Create a new Course object with the retrieved details.
            Course course1 = new Course(courseName, courseCode, courseCredit, semester, capacity, hour, day);

            // Add the new Course object to the department's list of courses.
            department.getCourses().add(course1);

            // Map the course code to the Course object in the department's courseCodeCourseMap.
            department.getCourseCodeCourseMap().put(courseCode, course1);

            // Create a list to store the prerequisite course codes and course section codes.
            List<String> preRequisiteCourseCodes = new ArrayList<>();
            List<String> labSections = new ArrayList<>(); //TODO: sync lab sections

            // Get the array of prerequisite course codes and course section codes from the course.
            JsonNode preRequisiteCourseCodesArray = course.get("preRequisiteCourseCodes");
            for (JsonNode preRequisiteCourseCode : preRequisiteCourseCodesArray) {
                preRequisiteCourseCodes.add(preRequisiteCourseCode.asText());
            }
            coursePrerequisiteCourseCodesMap.put(course1, preRequisiteCourseCodes);
        }
    }

    /**
     * This method reads course sections from a JSON file and adds them to the department's collection.
     * The JSON file should be located at "iteration2/jsons/courseSections.json".
     * Each course section in the JSON file should have the following properties:
     * - "courseSectionCode" (String): the code of the course section.
     * - "courseCode" (String): the code of the course that the section belongs to.
     * - "day" (String): the day of the week that the section meets.
     * - "hour" (int): the hour of the day that the section starts.
     * <p>
     * After reading and parsing the JSON file, the course sections will be added to the department's
     * collection of course sections. The course section code will be mapped to the course object, and
     * the course section will also be added to the course's section list.
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

            Course course = department.getCourseCodeCourseMap().get(courseCode);

            LaboratorySection labSection1 = new LaboratorySection(labSectionCode, capacity, hour, day);

            department.getLaboratorySections().add(labSection1);
            department.getSectionCodeCourseMap().put(labSectionCode, course);
            department.getLabSectionCourseMap().put(labSection1, course);
        }
    }


    /**
     * This method reads the lecturer data from a JSON file and populates the department object with the lecturer information.
     * The JSON file should be located at "iteration2/jsons/lecturers.json".
     * Each lecturer in the JSON file should have the following properties:
     * - "lecturerID" (int): the ID of the lecturer.
     * - "name" (String): the name of the lecturer.
     * - "surname" (String): the surname of the lecturer.
     * - "lessonsTaught" (Array of Strings): the list of lessons taught by the lecturer.
     * <p>
     * After reading and parsing the JSON file, the method creates a new Lecturer object for each lecturer.
     * It then adds the Lecturer object to the department's lecturers collection and sets the department for the lecturer.
     * It also maps the lecturer's ID to the lessons taught by the lecturer.
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
     * Reads the student data from a JSON file and populates the department object
     * with the student information.
     * The JSON file should be located at "iteration2/jsons/students.json".
     * Each student in the JSON file should have the following properties:
     * - "studentID" (int): the ID of the student.
     * - "name" (String): the name of the student.
     * - "surname" (String): the surname of the student.
     * - "userName" (String): the username of the student.
     * - "password" (String): the password of the student.
     * - "semester" (int): the grade level of the student.
     * - "advisorID" (int): the ID of the advisor for the student.
     * - "draft" (Array of Strings): the list of draft courses for the student.
     * <p>
     * After reading and parsing the JSON file, the students will be added to the department's
     * collection of students. The student's username will be mapped to the student object,
     * and the student's ID will be mapped to the advisorID.
     * The draft courses of each student will be added to the student's draft list,
     * and if the draft list is empty, the student's hasRequest flag will be set to false.
     * The transcript of each student will also be read.
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
            student1.setDepartment(department);

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
     * This method reads the transcript for a given student from a JSON file.
     * The JSON file should be located at "iteration2/jsons/Transcripts/{studentID}.json".
     * Each course in the transcript should have the following properties:
     * - "letterGrade" (String): the letter grade of the course.
     * - "courseCode" (String): the code of the course.
     * <p>
     * After reading and parsing the JSON file, the method creates a new Transcript object for the student.
     * It then sets the student's courses and course grades in the transcript.
     * Finally, it calculates the values for the transcript.
     *
     * @param student the student whose transcript is being read
     */
    public void readTranscript(Student student) {
        // Create an ObjectMapper instance for converting between Java objects and JSON.
        mapper = new ObjectMapper();
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

//                    addToSchedule(course1, student); TODO : add to schedule
                    course1.setNumberOfStudents(course1.getNumberOfStudents() + 1);
                    grades.add(null);
                    courseGradeMap.put(course1, grades);
                } else {
                    grades.add(new Grade(letterGrade));
                    courseGradeMap.put(course1, grades);
                }
            } else {
                if (letterGrade.equals("null")) {
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
    }

    /**
     * This method reads the requests data from a JSON file.
     * The JSON file should be located at "iteration2/jsons/requests.json".
     * Each request in the JSON file should have the following properties:
     * - "studentID" (int): the ID of the student making the request.
     * - "courses" (Array of Strings): the list of course codes that the student is requesting.
     * <p>
     * After reading and parsing the JSON file, the method creates a new Registration object for each request.
     * It then sets the draft courses for the student and sets the student's hasRequest flag to true.
     * Finally, it maps the student to the Registration object in the studentRegistrationMap.
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
     * This method reads the advisors data from a JSON file.
     * The JSON file should be located at "iteration2/jsons/advisors.json".
     * Each advisor in the JSON file should have the following properties:
     * - "advisorID" (int): the ID of the advisor.
     * - "name" (String): the name of the advisor.
     * - "surname" (String): the surname of the advisor.
     * - "userName" (String): the username of the advisor.
     * - "password" (String): the password of the advisor.
     * - "lessonsTaught" (Array of Strings): the list of lessons taught by the advisor.
     * <p>
     * After reading and parsing the JSON file, the method creates a new Advisor object for each advisor.
     * It then adds the Advisor object to the department's advisors collection and maps the advisor's ID to the Advisor object.
     * It also maps the advisor's username to the Advisor object and sets the lessons taught by the advisor.
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
     * It updates the relationships between courses, lecturers, students, and advisors,
     * ensuring that all necessary information is properly linked and associated.
     */
    public void syncObjects() {
        // Sync for courses
        // For each course in the department, add its prerequisite courses and course sections
        for (Course course : department.getCourses()) {
            for (String courseCode : coursePrerequisiteCourseCodesMap.get(course)) {
                course.getPreRequisiteCourses().add(department.getCourseCodeCourseMap().get(courseCode));
            }

            //TODO: sync lab sections
//            for (String labSectionCode : labSectionCodesMap.get(course)) {
//                course.getLaboratorySections().add(department.getLabSectionCourseMap().get(labSectionCode));
//            }
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

        // Sync for students
        // For each student in the department, set their advisor and add their draft courses
        for (Student student : department.getStudents()) {
            student.setAdvisor(department.getAdvisorIDAdvisorMap().get(studentIDAdvisorIDMap.get(student.getID())));
            if (studentIDDraftMap.get(student.getID()) != null) {
                for (String courseCode : studentIDDraftMap.get(student.getID())) {
                    student.getDraft().add(department.getCourseCodeCourseMap().get(courseCode));
                }
            }
        }

        // Sync for advisors
        // For each student in the department, add them to their advisor's list of advised students and add their registration to the advisor's request list
        // For each advisor in the department, add the courses they teach
        for (Student student : department.getStudents()) {
            Advisor advisor = department.getAdvisorIDAdvisorMap().get(studentIDAdvisorIDMap.get(student.getID()));
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
