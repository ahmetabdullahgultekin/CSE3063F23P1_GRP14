package iteration1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class JSONWriter {

    Department department;

    ObjectMapper objectMapper;

    JsonNode jsonNode;

    /**
     * Starts the process with the given department and writes the result to a JSON file.
     *
     * @param department the department to be processed
     */
    public void start(Department department) {
        this.department = department;
        writeJson();
    }

    /**
     * Writes JSON data for the department,
     * including students, requests, and transcripts.
     * This method is called internally by the start method.
     */
    public void writeJson() {
        writeStudents();
        writeRequests();
        writeTranscripts();
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
            filePath = "iteration1/jsons/Transcripts/" + student.getID() + ".json";
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

                for (Course course : student.getTranscript().getStudentCourses()) {
                    ObjectNode courseNode = JsonNodeFactory.instance.objectNode();
                    courseNode.put("courseCode", course.getCourseCode());
                    if (student.getTranscript().getCourseGradeMap().get(course) == null) {
                        courseNode.put("letterGrade", (JsonNode) null);
                    } else {
                        courseNode.put("letterGrade", student.getTranscript().getCourseGradeMap().get(course).getLetterGrade());
                    }
                    newCoursesArray.add(courseNode);
                }

                // Replace the existing "courses" array with the new array
                ((ObjectNode) jsonNode).set("courses", newCoursesArray);

                // Write the updated JsonNode back to the file
                objectMapper.writeValue(new File(filePath), jsonNode);

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
        String filePath = "iteration1/jsons/requests.json";
        try {
            objectMapper = new ObjectMapper();
            ArrayNode jsonArray = objectMapper.createArrayNode();
            for (Student student : department.getStudents()) {
                if (!student.getDraft().isEmpty()) {
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
            objectMapper.writeValue(new File(filePath), jsonArray);
        } catch (IOException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }

    /**
     * Writes student information to a JSON file.
     */
    public void writeStudents() {
        String filePath;
        filePath = "iteration1/jsons/students.json";
        try {
            objectMapper = new ObjectMapper();
            JsonNode jsonArray = objectMapper.readTree(new File(filePath));
            for (JsonNode jsonNode : jsonArray) {
                int studentID = jsonNode.get("studentID").asInt();
                Student student = department.getStudentIDStudentMap().get(studentID);
                boolean hasRequest = jsonNode.get("hasRequest").asBoolean();
                student.setHasRequest(hasRequest);
            }
        } catch (IOException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }
}

