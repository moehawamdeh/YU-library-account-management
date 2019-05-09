package jo.edu.yu.yarmouklibrary.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("LIBRARY_BACKGROUND", "Received Alarm");
        Intent i = new Intent(context, SyncService.class);
        i.putExtra("foo", "bar");
        //context.startService(i);
        SyncService.enqueueWork(context,i);
    }
}
