package speakbox.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.SeekBar;
import android.widget.TextView;
import com.example.SpeakBox.R;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import speakbox.model.Response;

import speakbox.util.Constants;

/**
 * Created by YingYing on 2016-04-03.
 * Displays the question, images and response method
 */
public class QuestionDisplayFragment extends Fragment {

    private TextView userName;
    private SeekBar seekBar;
    private TextView seekBarValue;
    private Button submitResponse;
    private String userFirstName;


    public static QuestionDisplayFragment newInstance() {
        QuestionDisplayFragment qdFragment = new QuestionDisplayFragment();
        return qdFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.display_question, container, false);
        getUserFirstName();
        initializeScreen(rootView);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBarValue.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //auto-generated by IntelliJ
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //auto-generated by IntelliJ
            }
        });

        submitResponse.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitResponse();
                popUp();

            }
        });

        return rootView;
    }

    private void initializeScreen(View rootView) {
        userName = (TextView) rootView.findViewById(R.id.userNameTextView);
        seekBar = (SeekBar) rootView.findViewById(R.id.rateSeekBar);
        seekBarValue = (TextView) rootView.findViewById(R.id.seekBarValueTextView);
        submitResponse = (Button) rootView.findViewById(R.id.submitButton);
    }

    private void submitResponse() {
        Firebase ref = new Firebase(Constants.FIREBASE_URL).child("responses");
        String userEnteredName = userName.getText().toString();
        String response = seekBarValue.getText().toString();

        Map<String, Object> responses = new HashMap<>();

        Response currentResponse = new Response("testId", response);
        responses.put("responseObj", currentResponse);
        responses.put("user", userEnteredName);


        ref.push().setValue(responses);
    }

    private void popUp() {
        //TODO: create the pop up window alerting user that their response has been recorded
    }


    private void getUserFirstName() {

        Firebase fb = new Firebase(Constants.FIREBASE_URL);

        if (fb.getAuth() != null) {

            AuthData ad = fb.getAuth();
            String uid = ad.getUid();

            Firebase userLocation = new Firebase(Constants.FIREBASE_URL + "/users/" + uid + "/name");

            userLocation.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        userFirstName = dataSnapshot.getValue().toString();
                        userName.setText(userFirstName);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("didn't work");
                }
            });
        }
    }


}
