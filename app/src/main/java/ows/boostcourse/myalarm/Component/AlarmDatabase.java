package ows.boostcourse.myalarm.Component;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

/**
 * AlaramDatabae is database that have setting alarm information.
 * This is used SharedPreference and applied Singleton design pattern.
 */
public class AlarmDatabase {

    private static AlarmDatabase instance;
    private static final String FILE_NAME = "AlarmDB";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    /**
     * AlarmDatabase constructor.
     * @param context
     */
    private AlarmDatabase(Context context){
        preferences = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * Get sigleton instance (AlarmDatabase).
     * @param context
     * @return
     */
    public static AlarmDatabase getInstance(Context context){
        if(instance == null){
            instance = new AlarmDatabase(context);
        }
        return instance;
    }

    /**
     * Insert setting alarm information in database.
     * @param alarm
     * @return if insert success, return true else false.
     */
    public boolean insertDatabase(Alarm alarm){
        Gson gson = new Gson();
        String json = gson.toJson(alarm,Alarm.class);
        editor.putString(alarm.toString(),json);
        return editor.commit();
    }

    /**
     * Update object of the same alarm information in database.
     * @param alarm Object to be updated.
     * @return
     */
    public boolean updateDatabase(Alarm alarm){
        Gson gson = new Gson();
        String json = gson.toJson(alarm,Alarm.class);
        editor.putString(alarm.toString(),json);
        return editor.commit();
    }

    /**
     * Get setting alarm informations in database.
     * @return alarm arrayList.
     */
    public ArrayList<Alarm> selectDatabase(){
        ArrayList<Alarm> list = new ArrayList<Alarm>();
        Map<String,String> map = (Map<String, String>) preferences.getAll();

        Gson gson = new Gson();
        for(Map.Entry<String,String> entry : map.entrySet()){
            list.add(gson.fromJson(entry.getValue(),Alarm.class));
        }
        return list;
    }

    /**
     * Delete setting alarm information in database.
     * @param alarm
     * @return if delete success, return true else false.
     */
    public boolean deleteDatabase(Alarm alarm){
        editor.remove(alarm.toString());
        return editor.commit();
    }


}
