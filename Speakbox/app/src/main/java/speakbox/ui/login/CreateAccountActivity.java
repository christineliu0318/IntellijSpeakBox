package speakbox.ui.login;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.SpeakBox.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import speakbox.model.User;
import speakbox.ui.BaseActivity;
import speakbox.util.Constants;

/**
 * Created by YingYing on 16-04-26.
 */
public class CreateAccountActivity extends BaseActivity {

    private EditText firstNameI, emailI, birthDateI, passwordI;
    private Button register;
    private String firstName, email, birthDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        fb = new Firebase(Constants.FIREBASE_URL);

        initializeScreen();

        register.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }


    public void initializeScreen() {
        firstNameI = (EditText) findViewById(R.id.firstNameInput);
        emailI = (EditText) findViewById(R.id.emailInput);
        birthDateI = (EditText) findViewById(R.id.birthDateInput);
        passwordI = (EditText) findViewById(R.id.passwordInput);
        register = (Button) findViewById(R.id.registerAccountButton);
    }

    public void createAccount() {
        email = emailI.getText().toString();
        String password = passwordI.getText().toString();

        fb.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                String uid = (String) stringObjectMap.get("uid");
                createUserInFirebase(uid);

            }

            @Override
            public void onError(FirebaseError firebaseError) {

            }
        });
    }

    public void createUserInFirebase(String uid) {

        final Firebase userLocation = new Firebase(Constants.FIREBASE_URL+"/users").child(uid);

        userLocation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /* If there is no user, make one */
                if (dataSnapshot.getValue() == null) {
                 /* Set raw version of date to the ServerValue.TIMESTAMP value and save into dateCreatedMap */
                    HashMap<String, Object> timestampJoined = new HashMap<>();
                    timestampJoined.put("timeOfAccountCreation", ServerValue.TIMESTAMP);

                    birthDate = birthDateI.getText().toString();
                    firstName = firstNameI.getText().toString();

                    User newUser = new User(firstName, birthDate, email, timestampJoined);
                    userLocation.setValue(newUser);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // log?
            }
        });



    }

}
