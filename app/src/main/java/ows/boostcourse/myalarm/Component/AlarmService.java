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
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 */
public class AlarmService extends Service {

    private static final String TAG = AlarmService.class.getSimpleName();
    private static final String POSITION = "position";
    private static final String FLAG = "flag";
    private static final String CHANNEL_ID = "Alarm_ID";
    private static final CharSequence CHANNEL_NAME = "Alarm_Name";
    private static ArrayList<Integer> serviceNum;

    @Override
    public void onCreate() {
        Log.d(TAG,"AlarmService onCreate");
        super.onCreate();
        serviceNum = new ArrayList<Integer>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int position = intent.getIntExtra(POSITION,0);
        if (intent.getBooleanExtra(FLAG, false)) {
            if(!serviceNum.contains(position)){
                serviceNum.add(position);
                settingOnAlarmEvent(position);
            }

        } else {
            serviceNum.remove(Integer.valueOf(position));
            settingOffAlarmEvent(intent.getIntExtra(POSITION, 0));
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"AlarmService onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"AlarmService onBind");
        return null;
    }

    /**
     * Set on notificaion to foreground service.
     * @param position
     */
    public void settingOnAlarmEvent(int position) {
        createNotificationChannel();

        // startForeground 실행
        Notification noti = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .build();
        startForeground(1234, noti);

        // An intent is abstract description of an operation to be performed.
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);

        // This class provides access to the system alarm services.
        // These allow you to schedule your application to be run at some point in the future.
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        // getBroadcast : Retrieve a pendingintent that will perform a broadcast.
        // The returned object can be handed to other application so that they can perform the action you described on your behalf at a later time.
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, position, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Alarm alarm = AlarmDatabase.getInstance(this).get(position);

        Calendar calendar = alarm.getCalendar();
        if (calendar.before(Calendar.getInstance())) {
            alarm.addOneDayCalendar();
            calendar = alarm.getCalendar();
        }

        if (alarmManager != null) {
            // Schedule a repeating alarm.
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                // When We want to alarm system is in low-power idle, you should use setExactAndAllowWhileIdle method.
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
            }
        }
    }

    /**
     * Set off notification to foreground service.
     * @param position
     */
    public void settingOffAlarmEvent(int position) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,position,intent,0);
        alarmManager.cancel(pendingIntent);

        if(serviceNum.size() == 0){
            stopForeground(true);
        }
    }

    /**
     * Create Notification Channel on Android 8.0 and higher.
     */
    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = getBaseContext().getSystemService(NotificationManager.class);

            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_NONE
            );
            serviceChannel.setShowBadge(false);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
