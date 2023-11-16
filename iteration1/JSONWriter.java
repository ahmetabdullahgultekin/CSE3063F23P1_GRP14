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

    }

    public void writeRequests() {

    }


    public void writeStudents() {
    }
}

