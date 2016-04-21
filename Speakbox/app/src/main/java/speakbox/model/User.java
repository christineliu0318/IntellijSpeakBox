package speakbox.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by YingYing on 2016-04-02.
 * User information
 */

public class User {
    private String name;
    private String gender;
    private Date dateOfBirth;
    private String email;
    private List<Response> responses;

    public User(String name, String gender, Date dateOfBirth, String email) {
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.responses = new ArrayList<>();
    }

    public void addResponse(Response response) {
        responses.add(response);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
