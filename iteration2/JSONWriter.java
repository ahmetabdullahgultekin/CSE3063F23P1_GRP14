package iteration2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class JSONWriter {
    private Department department;

    private ObjectMapper objectMapper;

    private JsonNode jsonNode;
    private static final Logger logger = LogManager.getLogger(JSONWriter.class);

    /**
     * Starts the process with the given department and writes the result to a JSON file.
     *
     * @param university the university to be processed
     */
    public void start(University university) {
        for (Department department : university.getDepartments()) {
            this.department = department;
            writeJson();
        }
    }

    /**
     * Writes JSON data for the department,
     * including students, requests, and transcripts.
     * This method is called internally by the start method.
     */
    public void writeJson() {
        logger.info("Writing JSON data for the department");
        writeStudents();
        writeRequests();
        writeTranscripts();
        logger.info("JSON data for the department has been written");
    }

    /**
     * Writes the student transcripts to JSON files.
     * <p>
     * This method iterates over all the students in the department and generates a JSON file for each student's transcript.
     * The method updates the values of "takenCredits", "completedCredits", "cgpa", and "courses" in the JSON file based on the
     * current values of the student's transcript.
     */
    public void writeTranscripts() {
        String filePath;
        for (Student student : department.getStudents()) {
            filePath = "iteration2/jsons/Transcripts/" + student.getID() + ".json";
            try {
                objectMapper = new ObjectMapper();
                jsonNode = objectMapper.readTree(new File(filePath));

                int newTakenCredits = student.getTranscript().getTakenCredits();
                int newCompletedCredits = student.getTranscript().getCompletedCredits();
                double newCgpa = student.getTranscript().getCgpa();
                newCgpa = Math.round(newCgpa * 100.0) / 100.0;

                ((ObjectNode) jsonNode).put("takenCredits", newTakenCredits);
                ((ObjectNode) jsonNode).put("completedCredits", newCompletedCredits);
                ((ObjectNode) jsonNode).put("cgpa", newCgpa);

                ArrayNode newCoursesArray = JsonNodeFactory.instance.arrayNode();

                for (Course course : student.getTranscript().getCourseGradeMap().keySet()) {
                    for (Grade grade : student.getTranscript().getCourseGradeMap().get(course)) {
                    ObjectNode courseNode = JsonNodeFactory.instance.objectNode();
                    courseNode.put("courseCode", course.getCourseCode());
                        if (grade == null) {
                            courseNode.put("letterGrade", (JsonNode) null);
                        } else {
                            courseNode.put("letterGrade", grade.getLetterGrade());
                        }
                        newCoursesArray.add(courseNode);
                    }
                }

                // Replace the existing "courses" array with the new array
                ((ObjectNode) jsonNode).set("courses", newCoursesArray);

                // Write the updated JsonNode back to the file
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), jsonNode);
            } catch (IOException e) {
                System.out.println("File not found");
                System.exit(0);
            }
        }
    }

    /**
     * Writes the student requests to a JSON file.
     */
    public void writeRequests() {
        String filePath = "iteration2/jsons/requests.json";
        try {
            objectMapper = new ObjectMapper();
            ArrayNode jsonArray = objectMapper.createArrayNode();
            for (Student student : department.getStudents()) {
                if (student.isHasRequest()) {
                    ObjectNode newNode = objectMapper.createObjectNode();
                    newNode.put("studentID", student.getID());
                    ArrayNode coursesArray = objectMapper.createArrayNode();
                    for (Course course : student.getDraft()) {
                        coursesArray.add(course.getCourseCode());
                    }
                    newNode.set("courses", coursesArray);
                    jsonArray.add(newNode);
                }
            }
            // Write the entirely new ArrayNode back to the file
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), jsonArray);
        } catch (IOException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }

    /**
     * This method writes student information to a JSON file.
     * The JSON file should be located at "iteration2/jsons/students.json".
     * Each student in the JSON file should have the following properties:
     * - "studentID" (int): the ID of the student.
     * - "hasRequest" (boolean): whether the student has a request.
     * <p>
     * After reading and parsing the JSON file, the method iterates over each student in the JSON file.
     * For each student, it retrieves the student's ID and whether they have a request.
     * It then gets the Student object corresponding to the student's ID from the department's studentIDStudentMap.
     * Finally, it sets the hasRequest flag for the Student object based on the value retrieved from the JSON.
     * <p>
     * If the file is not found, an error message is printed and the program is terminated.
     */
    public void writeStudents() {
        String filePath;
        filePath = "iteration2/jsons/students.json";
        try {
            objectMapper = new ObjectMapper();
            JsonNode jsonArray = objectMapper.readTree(new File(filePath));
            for (JsonNode jsonNode : jsonArray) {
                int studentID = jsonNode.get("studentID").asInt();
                Student student = department.getStudentIDStudentMap().get(studentID);
                String userName = jsonNode.get("userName").asText();
                String password = jsonNode.get("password").asText();
                String departmentName = jsonNode.get("departmentName").asText();
                boolean hasRequest = student.isHasRequest();

                ((ObjectNode) jsonNode).put("hasRequest", hasRequest);
                ((ObjectNode) jsonNode).put("userName", userName);
                ((ObjectNode) jsonNode).put("password", password);
                ((ObjectNode) jsonNode).put("departmentName", departmentName);
            }
            // Write the updated JsonNode back to the file
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), jsonArray);
        } catch (IOException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }
}

