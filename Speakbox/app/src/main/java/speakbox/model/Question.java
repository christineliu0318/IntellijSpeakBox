package speakbox.model;

import java.util.UUID;

/**
 * Created by YingYing on 2016-04-02.
 * Question that the user has to answer
 */
public class Question {
    private String question;
    private String questionId;
    private Response response;

    public Question(String question) {
        this.question = question;
        this.questionId = UUID.randomUUID().toString();
    }

    // should probably be placed in the QuestionDisplayFragment
/*    public void userAnswersQuestion(User user) {
        String answer = "5"; //whatever the user inputs into the text view
        response = new Response(questionId, answer, user);
    }*/

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getQuestionId() {
        return questionId;
    }
}
