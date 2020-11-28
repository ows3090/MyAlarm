package ows.boostcourse.myalarm.Component;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

import ows.boostcourse.myalarm.Interface.MainView;
import ows.boostcourse.myalarm.Interface.Presenter;

/**
 * MainPresenter interact mainView (interface).
 * MainPresenter applied Singleton design pattern.
 */
public class MainPresenter implements Presenter {

    private static MainPresenter instance;
    private static final String TAG = MainPresenter.class.getSimpleName();
    private AlarmDatabase alarmDatabase;
    private Context context;
    private MainView view;
    private AlarmAdapter adapter;

    /**
     * MainPresenter constructor.
     * @param context
     * @param view view that mapped to presenter.
     */
    private MainPresenter(Context context, MainView view) {
        this.context = context;
        this.view = view;
        alarmDatabase = AlarmDatabase.getInstance(context);
        adapter = new AlarmAdapter(context);

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

    /**
     * Called onCreate when MainPresenter create.
     */
    @Override
    public void onCreate() {
        view.onInitView(adapter);
        notifyAlarmEvent();
    }

    /**
     * Called onDestory when MainPresenter destory.
     */
    @Override
    public void onDestory() {
        instance = null;
    }

    /**
     * Set alarm based on time picker.
     * @param hourOfDay
     * @param minute
     */
    public void setAlarm(int hourOfDay, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);

        Alarm alarm = new Alarm(calendar,true);
        if(alarmDatabase.insertDatabase(alarm)){
            Log.d(TAG, alarm.toString() +" insert alarm in Database");
        }
        adapter.addItem(alarm);
        notifyAlarmEvent();
    }

    /**
     * Notify alarm event in database.
     */
    public void notifyAlarmEvent(){

        // An intent is abstract description of an operation to be performed.
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);

        // This class provides access to the system alarm services.
        // These allow you to schedule your application to be run at some point in the future.
        final AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        for(int i = 0;i<alarmDatabase.selectDatabase().size();i++){

            // getBroadcast : Retrieve a pendingintent that will perform a broadcast.
            // The returned object can be handed to other application so that they can perform the action you described on your behalf at a later time.
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,i,alarmIntent,0);

            Alarm alarm = alarmDatabase.selectDatabase().get(i);
            Calendar calendar = alarm.getCalendar();
            if(calendar.before(Calendar.getInstance())){
                alarm.addOneDayCalendar();
                calendar = alarm.getCalendar();
            }

            if(alarmManager != null){
                // Scheduling a repeating alarm.
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY,pendingIntent);

                // When We want to alarm system is in low-power idle, you should use setExactAndAllowWhileIdle method.
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                }
            }
        }
    }

}
