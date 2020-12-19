package ows.boostcourse.myalarm.Component;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

import ows.boostcourse.myalarm.Interface.MainView;
import ows.boostcourse.myalarm.Interface.Presenter;
import ows.boostcourse.myalarm.Interface.SwitchListener;

/**
 * MainPresenter interact mainView (interface).
 * MainPresenter applied Singleton design pattern.
 */
public class MainPresenter implements Presenter {
    private static final String POSITION = "position";
    private static final String FLAG = "flag";
    private static final String TAG = MainPresenter.class.getSimpleName();
    private static MainPresenter instance;
    private AlarmDatabase alarmDatabase;
    private Context context;
    private MainView view;
    private AlarmAdapter adapter;

    // Switch listener interface is called when switch change
    private SwitchListener switchListener = new SwitchListener() {

        /**
         * Update alarm information when switch changed.
         * @param isChecked switch status
         * @param position  item current position
         */
        @Override
        public void onDetectChangeSwitch(boolean isChecked, int position) {
            Alarm alarm = alarmDatabase.selectDatabase().get(position);
            alarm.setFlag(isChecked);
            alarmDatabase.updateDatabase(alarm,position);

            if(isChecked){
                turnOnAlarmEvent(alarm,position);
            }
            else{
                turnOffAlarmEvent(position);
            }
        }
    };


    /**
     * MainPresenter constructor.
     * @param context
     * @param view view that mapped to presenter.
     */
    private MainPresenter(Context context, MainView view) {
        Log.d(TAG,"Construtor");
        this.context = context;
        this.view = view;
        alarmDatabase = AlarmDatabase.getInstance(context);
        adapter = new AlarmAdapter(switchListener);

        // Update adapter by shardpreferences that have setting alarm information.
        for(int i = 0; i< alarmDatabase.size(); i++){
            adapter.addItem(alarmDatabase.get(i));
            Log.d(TAG, alarmDatabase.get(i).toString());
        }
    }

    /**
     * Get sigleton instance (MainPresenter).
     * @param context
     * @param view view that mapped to presenter.
     * @return
     */
    public static synchronized MainPresenter getInstance(Context context, MainView view){
        Log.d(TAG,"getInstance");
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
        Log.d(TAG,"onCreate");
        view.onInitView(adapter);
        notifyAlarmEvent();
    }

    /**
     * Called onDestory when MainPresenter destory.
     */
    @Override
    public void onDestory() {
        Log.d(TAG,"onDestroy");
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
        turnOnAlarmEvent(alarm,alarmDatabase.size()-1);
    }

    /**
     * Turn on new alarm Event or Change new alarm Event.
     * @param alarm
     * @param position
     */
    public void turnOnAlarmEvent(Alarm alarm, int position){
        Intent intent = new Intent(context,AlarmService.class);
        intent.putExtra(POSITION,position);
        intent.putExtra(FLAG,true);

        // startForegroundService() was introduced in O, just call startService for before O.
        ContextCompat.startForegroundService(context,intent);
    }

    /**
     * Turn off new alarm Event
     * @param position
     */
    public void turnOffAlarmEvent(int position){
        Intent intent = new Intent(context,AlarmService.class);
        intent.putExtra(POSITION,position);
        intent.putExtra(FLAG,false);

        ContextCompat.startForegroundService(context,intent);
    }

    /**
     * Notify alarm event in database.
     */
    public void notifyAlarmEvent(){
        for(int i=0;i<alarmDatabase.size();i++){
            Alarm alarm = alarmDatabase.get(i);

            if(alarm.getFlag()) {
                Intent intent = new Intent(context, AlarmService.class);
                intent.putExtra(POSITION,i);
                intent.putExtra(FLAG,true);
                ContextCompat.startForegroundService(context,intent);
            }
        }
    }

}
