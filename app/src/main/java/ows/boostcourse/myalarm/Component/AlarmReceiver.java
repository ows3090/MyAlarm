package ows.boostcourse.myalarm.Component;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import ows.boostcourse.myalarm.R;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = AlarmReceiver.class.getSimpleName();
    private static final String NOTIFICATION_TITLE = "Alarm App";
    private static final String NOTIFICATION_TEXT = "This is alarm app";
    private static final String CHANNEL_ID = "Alarm_ID";
    private static final CharSequence CHANNEL_NAME = "Alarm_Name";
    private static final String CHANNEL_DESCRIPTION = "This is alarm channel";
    private static final int NOTIFICATION_ID = 1;

    /**
     * This method is called when the BroadcastReceiver is receiving an Intent broadcast.
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "start alarm ring");

        /**
         * An intent is abstract description of an operation to be performed.
         * setAction() : Set the general action to be performed.
         * Intent.ACTION_MAIN : Start as a main entry point, does not expect to receive data.
         *
         * addCategory() : Add a new category to the intent.
         * Categories provide additional detail about the action the intent performs.
         * Intent.CATEGORY_LAUNCHER : Should be displayed in the top-level launcher.
         *
         * setFlags() : Set special flags controlling how this intent is handled.
         * If set, this activity will become the start of a new task on this history stack.
         */
        Intent notificationIntent = new Intent(context,MainActivity.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // PendintIntent.FLAG_UPDATE_CURRENT
        // Flag indicating that if the described PendingIntent already exists, then keep it but replace its extra data with what is in this new intent.
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        // Help for accessing features in Notification.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(NOTIFICATION_TEXT)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        createNotificationChannel(context,builder);
    }

    /**
     * This method is creating notification channel.
     * Before you can deliver the notification on Android 8.0 and higher, you must register your app's notification channel
     * with the system by passing an instance of NotificationChannel.
     * @param context
     * @param builder
     */
    public void createNotificationChannel(Context context,NotificationCompat.Builder builder){

        // Class to notify the user to events that happen.
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            builder.setSmallIcon(android.R.drawable.ic_dialog_alert);

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(CHANNEL_DESCRIPTION);

            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(NOTIFICATION_ID,builder.build());
        }
        else{
            builder.setSmallIcon(R.mipmap.ic_launcher);
            notificationManager.notify(NOTIFICATION_ID,builder.build());
        }

    }
}
