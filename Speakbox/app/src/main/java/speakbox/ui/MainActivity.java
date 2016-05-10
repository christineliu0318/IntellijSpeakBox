package speakbox.ui;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.FragmentTabHost;
import android.view.View;

import com.example.SpeakBox.R;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import speakbox.ui.fragments.ChartFragment;
import speakbox.ui.fragments.ProfileFragment;
import speakbox.ui.fragments.QuestionDisplayFragment;
import speakbox.ui.login.LoginActivity;

/**
 * Created by YingYing on 16-04-27.
 */
public class MainActivity extends BaseActivity {
    private FragmentTabHost mTabHost;

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
        setContentView(R.layout.maintest);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("question").setIndicator("Question"), QuestionDisplayFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("chart").setIndicator("Chart"), ChartFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("profile").setIndicator("Profile"), ProfileFragment.class, null);
    }

    public void logout(View v) {
        fb.unauth();
    }

}
