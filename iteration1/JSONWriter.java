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


    public void start(Department department) {
        this.department = department;
        writeJson();
    }


    public void writeJson() {
        writeStudents();
        writeRequests();
        writeTranscripts();
    }


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


    public void writeStudents() {
    }
}

