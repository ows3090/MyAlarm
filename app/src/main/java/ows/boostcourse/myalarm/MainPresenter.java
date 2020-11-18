package ows.boostcourse.myalarm;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

/**
 * MainPresenter interact mainView (interface)
 */
public class MainPresenter implements Presenter{

    private static final String TAG = MainPresenter.class.getSimpleName();
    private Context context;
    private MainView view;
    private Calendar calendar;
    private AlarmAdapter adapter = new AlarmAdapter();

    public MainPresenter(Context context, MainView view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void onCreate() {
        calendar = Calendar.getInstance();
        view.onInitView(adapter);
    }

    /**
     * Get Meridiem "AM" or "PM"
     * @param hourOfDay
     * @return String meridiem
     */
    public String getMeridiem(int hourOfDay){
        String meridiem = "AM";
        if(hourOfDay>12){
            meridiem = "PM";
        }
        return meridiem;
    }

    /**
     * Set alarm based on time picker
     * @param hourOfDay
     * @param minute
     */
    public void setAlarm(int hourOfDay, int minute){

        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);

        String meridiem = getMeridiem(hourOfDay);
        if(getMeridiem(hourOfDay) == "PM"){
            hourOfDay-=12;
        }

        Alarm alarm = new Alarm(meridiem,hourOfDay, minute);
        Log.d(TAG,meridiem+", "+hourOfDay+", "+minute+"분");
        adapter.addItem(alarm);

        alarmNotification(calendar);
    }


    public void alarmNotification(Calendar calendar){

//        PackageManager pm = context.getPackageManager();
//        ComponentName receiver = new ComponentName(context,AppBootReceiver.class);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,alarmIntent,0);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        if(alarmManager != null){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,pendingIntent);

            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
            }
        }

//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP);

    }






}
