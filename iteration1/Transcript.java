package iteration1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Transcript {
    private List<Course> studentCourses;
    private double cgpa;
    private byte gradeLevel;
    private int takenCredits;
    private int completedCredits;
    private Student student;
    private Map<Course, Grade> courseGradeMap;


    /**
     * Constructs a new Transcript object for the given student.
     *
     * @param student the Student object for which the Transcript is being created
     */
    public Transcript(Student student) {
        this.studentCourses = new ArrayList<>();
        this.student = student;
        this.gradeLevel = student.getGradeLevel();
        this.cgpa = calculateCgpa();
        this.takenCredits = calculateTakenCredits();
        this.completedCredits = calculateCompletedCredits();
        this.courseGradeMap = new HashMap<>();
    }

    /**
     * Calculates the student's GPA, taken credits, and completed credits,
     * and assigns these values to the corresponding fields in the class.
     */
    public void calculateValues() {
        this.cgpa = calculateCgpa();
        this.takenCredits = calculateTakenCredits();
        this.completedCredits = calculateCompletedCredits();
    }

    /**
     * Calculates the CGPA (Cumulative Grade Point Average) for the student.
     *
     * @return The calculated CGPA.
     */
    public double calculateCgpa() {
        if (studentCourses.isEmpty()) {
            return 0;
        }
        double totalGrade = 0;
        int totalCredits = 0;
        for (Course course : studentCourses) {
            if (courseGradeMap.get(course) != null) {
                totalGrade += courseGradeMap.get(course).getGradeOver4() * course.getCourseCredit();
                totalCredits += course.getCourseCredit();
            }
        }
        cgpa = totalGrade / totalCredits;
        return cgpa;
    }

    /**
     * Calculates the total number of credits taken by the student.
     *
     * @return The total number of credits taken.
     */
    public int calculateTakenCredits() {
        int totalCredits = 0;
        for (Course course : studentCourses) {
            totalCredits += course.getCourseCredit();
        }
        takenCredits = totalCredits;
        return totalCredits;
    }

    /**
     * Calculates the completed credits of a student based on their course grades and credits.
     *
     * @return The total completed credits.
     */
    public int calculateCompletedCredits() {
        completedCredits = 0;
        for (Course course : studentCourses) {
            if (courseGradeMap.get(course) == null
                    || courseGradeMap.get(course).getLetterGrade().equals("FF")
                    || courseGradeMap.get(course).getLetterGrade().equals("FD")) {
            } else {
                completedCredits += course.getCourseCredit();
            }
        }
        return completedCredits;
    }


    /**
     * Generates a JSON transcript for the student and saves it to a file.
     * The transcript includes information such as student name, ID, GPA, completed and taken credits,
     * current semester, department, and a list of courses taken by the student with their respective
     * course ID, credits, grade, and name.
     */
    public void showTranscript() {
        System.out.println("\nTranscript for " + student.getName() + " " + student.getSurname() + ":");
        System.out.println("Student ID: " + student.getID());
        System.out.printf("CGPA: %.2f\n", cgpa);
        System.out.println("Completed Credits: " + calculateCompletedCredits());
        System.out.println("Taken Credits: " + calculateTakenCredits());
        System.out.println("Grade Level: " + gradeLevel);
        System.out.println("Department: " + student.getDepartmentName());
        System.out.println("Courses:");

        System.out.format("%-15s%-38s%-20s%-15s\n", "Course Code", "Course Name", "Course Credit", "Grade");
        System.out.println("-------------------------------------------------------------------------------");
        for (Course course : studentCourses) {
            if (courseGradeMap.get(course) == null) {
                System.out.format("%-15s%-45s%-15s%-15s\n", course.getCourseCode(), course.getCourseName(), course.getCourseCredit(), "--");
            } else {
                System.out.format("%-15s%-45s%-15s%-15s\n", course.getCourseCode(), course.getCourseName(), course.getCourseCredit(), courseGradeMap.get(course).getLetterGrade());
            }
        }
    }

    /**
     * Checks the status of each course taken by a student and classifies them into successful courses,
     * failed courses, and ongoing courses.
     */
    public void courseStatusCheck() {
        List<Course> successfulCourses = new ArrayList<>();
        List<Course> failedCourses = new ArrayList<>();
        List<Course> ongoingCourses = new ArrayList<>();
        for (Course course : studentCourses) {
            if (courseGradeMap.get(course) == null) {
                ongoingCourses.add(course);
            } else if (courseGradeMap.get(course).getLetterGrade().equals("FF")
                    || courseGradeMap.get(course).getLetterGrade().equals("FD")) {
                failedCourses.add(course);
            } else {
                successfulCourses.add(course);
            }
        }
        System.out.println("\nSuccessful Courses:");
        for (Course course : successfulCourses) {
            System.out.println(course.getCourseCode() + " " + course.getCourseName());
        }
        System.out.println("Failed Courses:");
        for (Course course : failedCourses) {
            System.out.println(course.getCourseCode() + " " + course.getCourseName());
        }
        System.out.println("Ongoing Courses:");
        for (Course course : ongoingCourses) {
            System.out.println(course.getCourseCode() + " " + course.getCourseName());
        }
    }

    public List<Course> getStudentCourses() {
        return studentCourses;
    }

    public void setTakenCredits(int takenCredits) {
        this.takenCredits = takenCredits;
    }

    public void setCourseGradeMap(Map<Course, Grade> courseGradeMap) {
        this.courseGradeMap = courseGradeMap;
    }

    public void setStudentCourses(List<Course> studentCourses) {
        this.studentCourses = studentCourses;
    }

    public int getTakenCredits() {
        return takenCredits;
    }

    public int getCompletedCredits() {
        return completedCredits;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Map<Course, Grade> getCourseGradeMap() {
        return courseGradeMap;
    }
}
