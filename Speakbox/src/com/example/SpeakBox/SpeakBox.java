package com.example.SpeakBox;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;


public class SpeakBox extends Activity {

    private final static String TAG = "MainActivity";
    public final static String PREFS = "PrefsFile";

    private SharedPreferences settings = null;
    private SharedPreferences.Editor editor = null;
    LineChart chart = (LineChart) findViewById(R.id.chart);

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Save time of run:
        settings = getSharedPreferences(PREFS, MODE_PRIVATE);
        editor = settings.edit();

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

    /**
     * This method is called when the submit button is clicked.
     */
    public void submitAnswer(View view) {
        RadioButton radio1 = (RadioButton) findViewById(R.id.radioButton1);
        boolean radio1Pressed = radio1.isPressed();

        RadioButton radio2 = (RadioButton) findViewById(R.id.radioButton2);
        boolean radio2Pressed = radio2.isPressed();

        RadioButton radio3 = (RadioButton) findViewById(R.id.radioButton3);
        boolean radio3Pressed = radio3.isPressed();

        RadioButton radio4 = (RadioButton) findViewById(R.id.radioButton4);
        boolean radio4Pressed = radio4.isPressed();

        RadioButton radio5 = (RadioButton) findViewById(R.id.radioButton5);
        boolean radio5Pressed = radio5.isPressed();

        String xaxis = "Time";
        if (radio1Pressed) {
            //TODO: add it to a list of question answers
        } else if (radio2Pressed) {
            //TODO: add it to a list of question answers
        } else if (radio3Pressed) {
            //TODO: add it to a list of question answers
        } else if (radio4Pressed) {
            //TODO: add it to a list of question answers
        } else {
            //TODO
        }

        LineData data = new LineData(new ArrayList<String>().add(xaxis),);
        chart.setData(data);
        chart.notifyDataSetChanged();
    }

}
