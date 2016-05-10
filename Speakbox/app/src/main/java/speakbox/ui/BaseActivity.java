package speakbox.ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.firebase.client.Firebase;

import speakbox.util.CheckRecentRun;
import speakbox.util.Constants;


/**
 * BaseActivity - All other activites to extend from this
 */

public class BaseActivity extends AppCompatActivity {

    private final static String TAG = "BaseActivity";
    public final static String PREFS = "PrefsFile";

    private SharedPreferences settings = null;
    private SharedPreferences.Editor editor = null;

    protected Firebase fb;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FIREBASE CODE
        Firebase.setAndroidContext(this);

        fb = new Firebase(Constants.FIREBASE_URL);

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
