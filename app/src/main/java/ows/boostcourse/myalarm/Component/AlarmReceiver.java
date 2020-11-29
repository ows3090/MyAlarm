package ows.boostcourse.myalarm.Component;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import ows.boostcourse.myalarm.R;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "channel_id";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("msg", "알림");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
            builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
            builder.setContentTitle("Alarm App");
            builder.setContentText("This is alarm app");
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);


            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"ows", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("This is alarm app");

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(1,builder.build());
        }
        else{
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
            builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
            builder.setContentTitle("Alarm App");
            builder.setContentText("This is alarm app");
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            builder.build();
        }



    }
}
