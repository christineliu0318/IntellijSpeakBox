package speakbox.util;

/**
 * Created by Christine on 3/31/2016.
 */
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import com.example.SpeakBox.R;

public class CheckRecentRun extends Service {

    private final static String TAG = "CheckRecentPlay";
//    private static long MILLISECS_PER_DAY = 86400000;
    private static long MILLISECS_PER_MIN = 60000;
    private static long leftLimit = MILLISECS_PER_MIN*60*3;
    private static long rightLimit = MILLISECS_PER_MIN*60*3;

      private static long delay;   // 3 minutes (for testing)
//    private static long delay = MILLISECS_PER_DAY * 3;   // 3 days

    @Override
    public void onCreate() {
        super.onCreate();

        Log.v(TAG, "Service started");
        SharedPreferences settings = getSharedPreferences(SpeakBox.PREFS, MODE_PRIVATE);

        // Are notifications enabled?
        if (settings.getBoolean("enabled", true)) {
            // Is it time for a notification?
            if (settings.getLong("lastRun", Long.MAX_VALUE) < System.currentTimeMillis() - delay)
                sendNotification();

        } else {
            Log.i(TAG, "Notifications are disabled");
        }

        // Set an alarm for the next time this service should run:
        setAlarm();

        Log.v(TAG, "Service stopped");
        stopSelf();
    }

    public void setAlarm() {

        Intent serviceIntent = new Intent(this, CheckRecentRun.class);
        PendingIntent pi = PendingIntent.getService(this, 131313, serviceIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        delay = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pi);
        Log.v(TAG, "Alarm set");
    }

    public void sendNotification() {

        Intent mainIntent = new Intent(this, SpeakBox.class);
        @SuppressWarnings("deprecation")
        Notification noti = new Notification.Builder(this)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(this, 131314, mainIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT))
                .setContentTitle("How are you feeling?")
                .setContentText("Check in with your SpeakBox and continue to track your mental health!")
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_launcher)
                .setTicker("How are you feeling? Check in with your SpeakBox and .")
                .setWhen(System.currentTimeMillis())
                .getNotification();

        NotificationManager notificationManager
                = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(131315, noti);

        Log.v(TAG, "Notification sent");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
