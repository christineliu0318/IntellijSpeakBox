package speakbox.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.SpeakBox.R;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import speakbox.ui.login.LoginActivity;
import speakbox.util.CheckRecentRun;
import speakbox.util.Constants;


/**
 * Main Activity
 */

public class BaseActivity extends Activity {

    private final static String TAG = "MainActivity";
    public final static String PREFS = "PrefsFile";

    private SharedPreferences settings = null;
    private SharedPreferences.Editor editor = null;

    private Firebase fb;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FIREBASE CODE
        Firebase.setAndroidContext(this);

        fb = new Firebase(Constants.FIREBASE_URL);

        fb.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    //user is logged in
                    Intent intent = new Intent(BaseActivity.this, MainActivity.class);

                } else {
                    //user is not logged in
                    Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        // Save time of run:
        settings = getSharedPreferences(PREFS, MODE_PRIVATE);
        editor = settings.edit();

        // First time running app?
        if (!settings.contains("lastRun"))
            enableNotification(null);
        else
            recordRunTime();

        Log.v(TAG, "Starting CheckRecentRun service...");
        startService(new Intent(this, CheckRecentRun.class));
    }


    public void addFragment() {
        Fragment fg = QuestionDisplayFragment.newInstance();
        getFragmentManager().beginTransaction().add(R.id.layout, fg).commit();
    }

    public void recordRunTime() {
        editor.putLong("lastRun", System.currentTimeMillis());
        editor.commit();
    }

    public void enableNotification(View v) {
        editor.putLong("lastRun", System.currentTimeMillis());
        editor.putBoolean("enabled", true);
        editor.commit();
        Log.v(TAG, "Notifications enabled");
    }

//    public void disableNotification(View v) {
//        editor.putBoolean("enabled", false);
//        editor.commit();
//        Log.v(TAG, "Notifications disabled");
//    }

}
