package speakbox.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.SpeakBox.R;

/**
 * Created by YingYing on 2016-04-03.
 * The message that shows on screen when it is not yet time for user to answer question
 */
public class PendingQuestionFragment extends Fragment {

    TextView thanks;
    TextView username;
    TextView message;

    public static PendingQuestionFragment newInstance() {
        PendingQuestionFragment pqFragment = new PendingQuestionFragment();
        return pqFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pending_question, container, false);
        initializeScreen(rootView);

        thanks.setText("Thanks, ");
        username.setText("Ying Ying"); // the user's name
        message.setText("We'll talk to you later!");

        return rootView;
    }

    private void initializeScreen(View rootView) {
        thanks = (TextView) rootView.findViewById(R.id.helloTextView);
        username = (TextView) rootView.findViewById(R.id.userNameTextView);
        message = (TextView) rootView.findViewById(R.id.displayMessage);
    }

}
