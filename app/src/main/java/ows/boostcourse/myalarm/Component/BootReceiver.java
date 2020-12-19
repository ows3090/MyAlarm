package ows.boostcourse.myalarm.Component;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

/**
 * This is BootReceiver that broadcast when application boot up or reboot.
 */
public class BootReceiver extends BroadcastReceiver {

    private static final String POSITION = "position";
    private static final String FLAG = "flag";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            for(int i=0;i<AlarmDatabase.getInstance(context).size();i++){
                Alarm alarm = AlarmDatabase.getInstance(context).get(i);

                if(alarm.getFlag()){
                    Intent serviceIntent = new Intent(context, AlarmService.class);
                    serviceIntent.putExtra(POSITION,i);
                    serviceIntent.putExtra(FLAG,true);

                    ContextCompat.startForegroundService(context,serviceIntent);
                }
            }
        }
    }
}
