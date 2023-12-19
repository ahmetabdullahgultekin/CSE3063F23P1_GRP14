package iteration2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Notification {
    private final Logger logger = LogManager.getLogger(Notification.class);
    private String message;
    private Person recipient;


    public Notification(Person person, String message) {
        this.recipient = person;
        this.message = message;
    }

    public void sendNotification() {
        if (recipient instanceof Advisor) {
            ((Advisor) recipient).setNotification(this);
        } else if (recipient instanceof Student) {
            logger.info("Notification sent to student " + recipient.getID() + " that their request has been rejected.");
            ((Student) recipient).setNotification(this);
        }

    }

    public String getMessage() {
        return message;
    }
}


