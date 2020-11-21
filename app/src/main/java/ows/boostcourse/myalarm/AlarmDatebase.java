package ows.boostcourse.myalarm;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AlarmDatebase {

    private static final String FILE_NAME = "AlarmDB";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public AlarmDatebase(Context context){
        preferences = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public boolean insertDatabase(Alarm alarm){
        Gson gson = new Gson();
        String json = gson.toJson(alarm,Alarm.class);
        editor.putString(alarm.toString(),json);
        return editor.commit();
    }

    public boolean deleteDatabase(Alarm alarm){
        editor.remove(alarm.toString());
        return editor.commit();
    }



}
