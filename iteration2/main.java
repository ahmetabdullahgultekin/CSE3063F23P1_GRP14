package iteration2;

public class main {
    public static void main(String[] args) {
        System.setProperty("log4j.configurationFile", "log4j2.xml"); //to set the log4j2 configuration file
        CourseRegistrationSystem courseRegistrationSystem = new CourseRegistrationSystem();
        courseRegistrationSystem.start();
    }
}
