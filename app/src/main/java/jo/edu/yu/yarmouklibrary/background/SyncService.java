package jo.edu.yu.yarmouklibrary.background;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import jo.edu.yu.yarmouklibrary.R;
import jo.edu.yu.yarmouklibrary.model.data.LibraryRepository;
import jo.edu.yu.yarmouklibrary.model.data.UserCache;
import jo.edu.yu.yarmouklibrary.model.User;
import jo.edu.yu.yarmouklibrary.view.MainActivity;

public class SyncService extends JobIntentService {
    /**
     * Unique job ID for this service.
     */
    static final int JOB_ID = 1000;
    private static final String CHANNEL_ID ="testchannel";

    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, SyncService.class, JOB_ID, work);
    }
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.i("LIBRARY_BACKGROUND", "Service running");
        User user=UserCache.getUser(this);
        user=new User("2012121212");
        user.setName("asadasdas");
        user.setBooksCount(3);
        user.setLateBooksCount(1);
        if(user!=null)
        {
            if(!user.getID().isEmpty()){
                LibraryRepository libraryRepository=new LibraryRepository();
                libraryRepository.syncData(this,user.getID());
                Log.i("LIBRARY_BACKGROUND",user.getID()+" "+user.getName()+" "+user.getBooksCount()+""+user.getLateBooksCount());
                createNotification("wut wut wut",this);
//                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                        .setSmallIcon(R.drawable.ic_refresh)
//                        .setContentTitle(user.getID())
//                        .setContentText(user.getID()+" "+user.getName()+" "+user.getBooksCount()+""+user.getLateBooksCount())
//                        //.setStyle(NotificationCompat.BigTextStyle()bigText("Much longer text that cannot fit one line..."))
//                        .setAutoCancel(true)
//
//                        //.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                        .setPriority(NotificationCompat.PRIORITY_LOW);
//                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//
//            // notificationId is a unique int for each notification that you must define
//                notificationManager.notify(new Random().nextInt(), mBuilder.build());
            }
        }
    }

    private NotificationManager notifManager;
    public void createNotification(String aMessage, Context context) {
        final int NOTIFY_ID = 0; // ID of notification
        String id = "testchannel";  // default_channel_id
        String title = "title test";// Default Channel
        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;
        if (notifManager == null) {
            notifManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder)   // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        }
        else {
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder)   // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            //setPRiortiyu

        }
        Notification notification = builder.build();
        notifManager.notify(new Random().nextInt(), notification);
    }

}
