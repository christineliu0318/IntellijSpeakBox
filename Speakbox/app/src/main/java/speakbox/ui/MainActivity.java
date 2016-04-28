package speakbox.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;



import com.example.SpeakBox.R;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import speakbox.ui.fragments.QuestionDisplayFragment;
import speakbox.ui.login.LoginActivity;

/**
 * Created by YingYing on 16-04-27.
 */
public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fb.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData == null) {
                    //user is not logged in
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        setContentView(R.layout.main);
        addFragment();
    }

    public void addFragment() {
        Fragment fg = QuestionDisplayFragment.newInstance();
        getFragmentManager().beginTransaction().add(R.id.layout, fg).commit();
    }

}
