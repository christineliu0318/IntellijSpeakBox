package speakbox.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by YingYing on 2016-04-02.
 * Response given by user
 */
public class Response {
    private String responseDate;
    private Double answer;
    private String questionId;


    public Response(String questionId, Double answer, User user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        Calendar cal = Calendar.getInstance();
        this.responseDate = sdf.format(cal.getTime());
        this.questionId = questionId;
        this.answer = answer;

        user.addResponse(this);
    }
}

