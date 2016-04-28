package speakbox.ui.login;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.SpeakBox.R;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import speakbox.ui.BaseActivity;
import speakbox.util.Constants;


/**
 * Created by YingYing on 16-04-26.
 */
public class LoginActivity extends Activity {

    private Firebase fb;
    private LinearLayout linearLayoutLoginActivity;
    private EditText usernameI, passwordI;
    private TextView showError, register;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initializeScreen();

        fb = new Firebase(Constants.FIREBASE_URL);

        loginButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        register.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public void initializeScreen() {
        linearLayoutLoginActivity = (LinearLayout) findViewById(R.id.loginScreen);
        usernameI = (EditText) findViewById(R.id.usernameInput);
        passwordI = (EditText) findViewById(R.id.passwordInput);
        showError = (TextView) findViewById(R.id.showError);
        loginButton = (Button) findViewById(R.id.loginButton);
        register = (TextView) findViewById(R.id.registerLink);
    }

    public void signIn() {
        String username = usernameI.getText().toString();
        String password = passwordI.getText().toString();

        fb.authWithPassword(username, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                System.out.println("Whhhhyyyyyyyyyy????");
                if (authData != null) {
                /* Go to main activity */
                    Intent intent = new Intent(LoginActivity.this, BaseActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                switch (firebaseError.getCode()) {
                    case FirebaseError.INVALID_EMAIL:
                    case FirebaseError.USER_DOES_NOT_EXIST:
                        usernameI.setError("Incorrect username");
                        break;
                    case FirebaseError.INVALID_PASSWORD:
                        passwordI.setError(firebaseError.getMessage());
                        break;
                    default:
                        showError.setText(firebaseError.toString());
                }

            }
        });

    }

}
