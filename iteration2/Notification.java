package iteration2;

import java.util.ArrayList;

public class Notification {
    private String message;
    private Person recipient;



    public Notification(Person person, String message){
        this.recipient = person;
        this.message = message;
    }

    public void sendNotification(){
        if(recipient instanceof Advisor){
            ((Advisor) recipient).setNotification(this);
        }
        else if(recipient instanceof Student){
            ((Student) recipient).setNotification(this);
        }

    }

    public String getMessage() {
        return message;
    }
}


