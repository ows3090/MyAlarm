package ows.boostcourse.myalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("msg","hihi");
//        Intent serviceIntent = new Intent(context,MyService.class);
//        context.startService(serviceIntent);
    }
}
