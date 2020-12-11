package ows.boostcourse.myalarm.Component;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class AlarmService extends Service {

    private static final String CHANNEL_ID = "Alarm_ID";
    private static final CharSequence CHANNEL_NAME = "Alarm_Name";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //createNotificationChannel();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationManager manager = getBaseContext().getSystemService(NotificationManager.class);

            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            manager.createNotificationChannel(serviceChannel);
        }

        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent pI = PendingIntent.getActivity(this,1,notificationIntent,0);

        Notification noti = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("alarm service")
                .setContentText("This is alarm service")
                .setSmallIcon(android.R.drawable.btn_radio)
                .setContentIntent(pI)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();

        startForeground(1234,noti);

        // An intent is abstract description of an operation to be performed.
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);

        // This class provides access to the system alarm services.
        // These allow you to schedule your application to be run at some point in the future.
        AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);


        int position = AlarmDatabase.getInstance(this).size()-1;
        Alarm alarm = AlarmDatabase.getInstance(this).get(position);
        // getBroadcast : Retrieve a pendingintent that will perform a broadcast.
        // The returned object can be handed to other application so that they can perform the action you described on your behalf at a later time.
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,position,alarmIntent,0);

        Calendar calendar = alarm.getCalendar();
        if (calendar.before(Calendar.getInstance())) {
            alarm.addOneDayCalendar();
            calendar = alarm.getCalendar();
        }

        if (alarmManager != null) {
            // Scheduling a repeating alarm.
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);

            // When We want to alarm system is in low-power idle, you should use setExactAndAllowWhileIdle method.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void createNotificationChannel(){


    }
}
