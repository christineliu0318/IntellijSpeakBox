package speakbox.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.SpeakBox.R;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import speakbox.ui.MainActivity;
import speakbox.util.Constants;

/**
 * Created by YingYing on 2016-04-03.
 * The message that shows on screen when it is not yet time for user to answer question
 */
public class PendingQuestionFragment extends Fragment {

    private TextView thanks;
    private TextView username;
    private TextView message;
    private String userFirstName;

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

        getUserFirstName();

        thanks.setText("Thanks, ");
        message.setText("We'll talk to you later!");

        return rootView;
    }

    public void initializeScreen(View rootView) {
        thanks = (TextView) rootView.findViewById(R.id.helloTextView);
        username = (TextView) rootView.findViewById(R.id.userNameTextView);
        message = (TextView) rootView.findViewById(R.id.displayMessage);
    }

    private void getUserFirstName() {

        Firebase fb = new Firebase(Constants.FIREBASE_URL);
        AuthData ad = fb.getAuth();
        String uid = ad.getUid();

        Firebase userLocation = new Firebase(Constants.FIREBASE_URL + "/users/" + uid + "/name");

        userLocation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userFirstName = dataSnapshot.getValue().toString();
                username.setText(userFirstName);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("didn't work");
            }
        });
    }

}
