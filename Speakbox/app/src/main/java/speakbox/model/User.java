package speakbox.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by YingYing on 2016-04-02.
 * User information
 */

public class User {
    private String name;
    private String dateOfBirth;
    private String email;
    private HashMap<String, Object> timeStamp;

    public User(String name, String dateOfBirth, String email, HashMap<String, Object> timeStamp) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.timeStamp = timeStamp;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public HashMap<String, Object> getTimeStamp() {
        return timeStamp;
    }

}
