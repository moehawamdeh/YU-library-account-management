package jo.edu.yu.yarmouklibrary.background;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import androidx.legacy.content.WakefulBroadcastReceiver;

public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Launch the specified service when this message is received
        Log.i("LIBRARY_BACKGROUND", "Boot broadcast received");
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Set the alarm here.
            Log.i("LIBRARY_BACKGROUND", "Boot completion received");
            scheduleAlarm(context);
        }
    }
    public void scheduleAlarm(Context context) {
        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(context, AlarmReceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(context, AlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every every half hour from this point onwards
        long firstMillis = SystemClock.elapsedRealtime();
        // alarm is set right away
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstMillis,
                60*1000, pIntent);
        Log.i("LIBRARY_BACKGROUND", "Alarm scheduled");

    }
}