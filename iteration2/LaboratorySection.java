package iteration2;

/**
 * The LaboratorySection class represents a course with a laboratory offered by an educational institution in different departments.
 * It contains extra information about the laboratorySectionCode, capacity, assistant and numberOfStudents,hour,day of the section.
 */
public class LaboratorySection {
    private String laboratorySectionCode;
    private int capacity;
    private Assistant assistant;
    private int numberOfStudents;
    private int hour;
    private String day;


    /**
     * Creates a new LaboratorySection object with the given parameters
     * @param laboratorySectionCode      the section's code
     * @param capacity                   the section's capacity
     * @param hour                       the section's hour
     * @param day                        the section's day
     */

   public LaboratorySection(String laboratorySectionCode, int capacity, int hour, String day) {
        this.laboratorySectionCode = laboratorySectionCode;
        this.capacity = capacity;
        this.hour = hour;
        this.day = day;
    }

    public String getLaboratorySectionCode() {
        return laboratorySectionCode;
    }

    public void setAssistant(Assistant assistant) {
        this.assistant = assistant;
    }

    public String getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

  
}
