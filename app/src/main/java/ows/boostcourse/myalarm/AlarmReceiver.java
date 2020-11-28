package ows.boostcourse.myalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"알림이 울립니다",Toast.LENGTH_LONG).show();
        Log.d("msg","알림");
    }
}
