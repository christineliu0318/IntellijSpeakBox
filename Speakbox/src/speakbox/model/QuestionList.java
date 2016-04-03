package speakbox.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by YingYing on 2016-04-02.
 * List of questions in app
 *
 */
public class QuestionList {
    private Set<Question> questions;

    public QuestionList() {
        questions = new HashSet<>();
    }

    public void addQuestions(Question question) {
        questions.add(question);
    }

    public void removeQuestion (Question question) {
        questions.remove(question);
    }
}
