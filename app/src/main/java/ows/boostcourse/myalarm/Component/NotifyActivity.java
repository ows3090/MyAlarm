package ows.boostcourse.myalarm.Component;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TimePicker;

import java.sql.Time;

import ows.boostcourse.myalarm.R;

/**
 * NotifyActivtiy show notification view when the set alarm time is reached.
 */
public class NotifyActivity extends AppCompatActivity {

    private static final String TAG = NotifyActivity.class.getSimpleName();
    private static final String POSITION = "position";
    private static final String FLAG = "flag";
    TimePicker timePicker;
    Button confirmButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        Log.d(TAG,"onCreate");

        timePicker = findViewById(R.id.activity_notify_tp);
        confirmButton = findViewById(R.id.activity_notify_btn);


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O_MR1){
                this.setTurnScreenOn(true);
                this.setShowWhenLocked(true);
            }
            else {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
            }

            KeyguardManager keyguardManager = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
            keyguardManager.requestDismissKeyguard(this,null);
        }else{

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        settingView(getIntent());
    }

    /**
     * Set activity_notify.xml and Register confirmButton click Listener.
     * @param intent
     */
    public void settingView(Intent intent){
        final int position = intent.getIntExtra(POSITION,0);

        Alarm alarm = AlarmDatabase.getInstance(this).get(position);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            timePicker.setHour(alarm.getHourOfday());
            timePicker.setMinute(alarm.getMinute());
        }else{
            timePicker.setCurrentMinute(alarm.getHourOfday());
            timePicker.setCurrentMinute(alarm.getMinute());
        }

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(getApplicationContext(),AlarmService.class);
                serviceIntent.putExtra(POSITION,position);
                serviceIntent.putExtra(FLAG,false);

                ContextCompat.startForegroundService(getApplicationContext(),serviceIntent);
                finish();
            }
        });
    }


}