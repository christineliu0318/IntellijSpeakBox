package speakbox.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by YingYing on 2016-04-02.
 * Response given by user
 */
public class Response {
    private String responseDate;
    private String answer;
    private String questionId;
    private String user;


    public Response(String questionId, String answer, String user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        Calendar cal = Calendar.getInstance();
        this.responseDate = sdf.format(cal.getTime());
        this.user = user;
        this.answer = answer;
        this.questionId = questionId;

        //user.addResponse(this);
    }

    public String getAnswer() {
        return answer;
    }

    public String getUser() {
        return user;
    }

    public String getResponseDate() {
        return responseDate;
    }

    public String getQuestionId() {
        return questionId;
    }
}

