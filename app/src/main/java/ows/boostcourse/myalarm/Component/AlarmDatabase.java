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

    private static ArrayList<Alarm> list = new ArrayList<Alarm>();
    private static AlarmDatabase instance;
    private static final String FILE_NAME = "AlarmDB";

    // Interface for accessing and modifying preference data returned by Context#getSharedPreferences.
    private SharedPreferences preferences;

    // Interface used for modifying values in SharedPreferences object.
    private SharedPreferences.Editor editor;

    /**
     * AlarmDatabase constructor.
     * @param context
     */
    private AlarmDatabase(Context context){
        preferences = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        editor = preferences.edit();

        Gson gson = new Gson();
        Map<String,String> map = (Map<String,String>)preferences.getAll();

        for(Map.Entry<String,String> entry : map.entrySet()){
            list.add(gson.fromJson(entry.getValue(),Alarm.class));
        }
    }

    /**
     * Get sigleton instance (AlarmDatabase).
     * @param context
     * @return
     */
    public static synchronized AlarmDatabase getInstance(Context context){
        if(instance == null){
            instance = new AlarmDatabase(context);
        }
        return instance;
    }

    /**
     * Get setting alarm count.
     * @return  alarm count.
     */
    public int size(){
        return list.size();
    }

    /**
     * Get alarm object of the position.
     * @param position
     * @return
     */
    public Alarm get(int position){
        return list.get(position);
    }

    /**
     * Insert setting alarm information in database.
     * @param alarm
     * @return if insert success, return true else false.
     */
    public boolean insertDatabase(Alarm alarm){

        // Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String,Class) methods on it.
        Gson gson = new Gson();
        String json = gson.toJson(alarm,Alarm.class);
        editor.putString(alarm.toString(),json);

        list.add(gson.fromJson(json,Alarm.class));
        return editor.commit();
    }

    /**
     * Update object of the same alarm information in database.
     * @param alarm Object to be updated.
     * @return
     */
    public boolean updateDatabase(Alarm alarm,int position){
        Gson gson = new Gson();
        String json = gson.toJson(alarm,Alarm.class);
        editor.putString(alarm.toString(),json);

        list.set(position,alarm);
        return editor.commit();
    }

    /**
     * Get setting alarm informations in database.
     * @return alarm arrayList.
     */
    public ArrayList<Alarm> selectDatabase(){
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
