package iteration1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Department {
    private String departmentName; //it holds department name.
    private List<Advisor> advisors; //it holds advisor list.
    private List<Transcript> transcripts; //it holds transcript list.
    private List<Course> courses; //it holds course list.
    private List<Student> students; //it holds student list.
    private List<CourseSection> courseSections; //it holds courseSection list.
    private List<Lecturer> lecturers; //it holds lecturer list.
    private Map<String, Person> userNamePersonMap;  //it holds username and password of users.
    private Map<String, Course> courseCodeCourseMap; //it holds course code and course.
    private Map<String, Course> sectionCodeCourseMap; //it holds course code and course.
    private Map<CourseSection, Course> sectionCourseMap; //it holds course section and course.
    private Map<Integer, Student> studentIDStudentMap; //it holds student id and student.
    private Map<Integer, Advisor> advisorIDAdvisorMap; //it holds advisor id and advisor.
    private byte maxCourseNumber = 5;



    //Creating department objects.
    public Department(String departmentName) {
        this.departmentName = departmentName;
        advisors = new ArrayList<>();
        transcripts = new ArrayList<>();
        courses = new ArrayList<>();
        students = new ArrayList<>();
        courseSections = new ArrayList<>();
        lecturers = new ArrayList<>();
        userNamePersonMap = new HashMap<>();
        courseCodeCourseMap = new HashMap<>();
        studentIDStudentMap = new HashMap<>();
        advisorIDAdvisorMap = new HashMap<>();
        sectionCodeCourseMap = new HashMap<>();
        sectionCourseMap = new HashMap<>();
    }

    //Return the department name.
    public String getDepartmentName() {
        return departmentName;
    }
    //Return the course list.
    public List<Course> getCourses() {
        return courses;
    }
    //Return the student list.
    public List<Student> getStudents() {
        return students;
    }
    //Return the advisor list.
    public List<Advisor> getAdvisors() {
        return advisors;
    }
    //Return the transcript list.
    public List<Transcript> getTranscripts() {
        return transcripts;
    }
    //Return the username map.
    public Map<String, Person> getUserNamePersonMap() {
        return userNamePersonMap;
    }
    //Return the course code map.
    public Map<String, Course> getCourseCodeCourseMap() {
        return courseCodeCourseMap;
    }
    //Return the course section list.
    public List<CourseSection> getCourseSections() {
        return courseSections;
    }
    //Return the lecturer list.
    public List<Lecturer> getLecturers() {
        return lecturers;
    }
    //Return student id map.
    public Map<Integer, Student> getStudentIDStudentMap() {
        return studentIDStudentMap;
    }
    //Return the advisor id map.
    public Map<Integer, Advisor> getAdvisorIDAdvisorMap() {
        return advisorIDAdvisorMap;
    }
    //Return the max course number.
    public byte getMaxCourseNumber() {
        return maxCourseNumber;
    }
    //Return the section code map.
    public Map<String, Course> getSectionCodeCourseMap() {
        return sectionCodeCourseMap;
    }
    //Return the section course map.
    public Map<CourseSection, Course> getSectionCourseMap() {
        return sectionCourseMap;
    }

}
