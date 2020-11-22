package ows.boostcourse.myalarm;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

/**
 * MainPresenter interact mainView (interface).
 * MainPresenter applied Singleton design pattern.
 */
public class MainPresenter implements Presenter{

    private static MainPresenter instance;
    private static final String TAG = MainPresenter.class.getSimpleName();
    private AlarmDatabase alarmDatabase;
    private Context context;
    private MainView view;
    private Calendar calendar;
    private AlarmAdapter adapter;


    /**
     * MainPresenter constructor.
     * @param context
     * @param view view that mapped to presenter.
     */
    private MainPresenter(Context context, MainView view) {
        this.context = context;
        this.view = view;
        alarmDatabase = new AlarmDatabase(context);
        adapter = new AlarmAdapter();

        /**
         * Update adapter by shardpreferences that have setting alarm information.
         */
        for(int i = 0; i< alarmDatabase.selectDatabase().size(); i++){
            adapter.addItem(alarmDatabase.selectDatabase().get(i));
            Log.d(TAG, alarmDatabase.selectDatabase().get(i).toString());
        }
    }

    /**
     * Get sigleton instance (MainPresenter).
     * @param context
     * @param view view that mapped to presenter.
     * @return
     */
    public static MainPresenter getInstance(Context context, MainView view){
        if(instance == null){
            instance = new MainPresenter(context,view);
        }
        return instance;
    }

    @Override
    public void onCreate() {
        calendar = Calendar.getInstance();
        view.onInitView(adapter);
    }


    /**
     * Get Meridiem (AM or PM).
     * @param hourOfDay
     * @return meridiem.
     */
    public String getMeridiem(int hourOfDay){
        String meridiem = "AM";
        if(hourOfDay>12){
            meridiem = "PM";
        }
        return meridiem;
    }


    /**
     * Set alarm based on time picker.
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
        if(alarmDatabase.insertDatabase(alarm)){
            Log.d(TAG,alarm.toString());
        }
        adapter.addItem(alarm);
        alarmNotification(calendar);
    }


    public void alarmNotification(Calendar calendar){

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

    }



}
