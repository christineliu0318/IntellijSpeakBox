package speakbox.util;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.SpeakBox.R;
import com.firebase.client.Firebase;

import speakbox.ui.QuestionDisplayFragment;


/**
 * Main Activity
 */

public class SpeakBox extends Activity {

    private final static String TAG = "MainActivity";
    public final static String PREFS = "PrefsFile";

    private SharedPreferences settings = null;
    private SharedPreferences.Editor editor = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //FRAGMENT CODE
        Configuration config = getResources().getConfiguration();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        QuestionDisplayFragment qdFragment = new QuestionDisplayFragment();
        fragmentTransaction.replace(android.R.id.content, qdFragment);

        // Save time of run:
        settings = getSharedPreferences(PREFS, MODE_PRIVATE);
        editor = settings.edit();

        //FIREBASE CODE
        Firebase.setAndroidContext(this);

        // First time running app?
        if (!settings.contains("lastRun"))
            enableNotification(null);
        else
            recordRunTime();

        Log.v(TAG, "Starting CheckRecentRun service...");
        startService(new Intent(this,  CheckRecentRun.class));
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
