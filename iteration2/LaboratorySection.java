package iteration2;

/**
 * The LaboratorySection class represents a course with a laboratory offered by an educational institution in different departments.
 * It contains extra information about the laboratorySectionCode, capacity, assistant and numberOfStudents of the section.
 */
public class LaboratorySection {
    private String laboratorySectionCode;
    private int capacity;
    private Assistant assistant;
    private int numberOfStudents;


    /**
     * Creates a new LaboratorySection object with the given parameters
     * @param laboratorySectionCode      the section's code
     * @param capacity                   the section's capacity
     * @param assistant                  the section's assistant
     */

    public LaboratorySection(String laboratorySectionCode, Assistant assistant, int capacity) {
        this.laboratorySectionCode = laboratorySectionCode;
        this.capacity = capacity;
        this.assistant = assistant;

    }

    public String getLaboratorySectionCode() {
        return laboratorySectionCode;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }
}
