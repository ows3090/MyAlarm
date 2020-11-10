package ows.boostcourse.myalarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Alaram ListView의 Adapter Class
 */
public class AlarmAdapter extends BaseAdapter {

    private TextView meridiem;
    private TextView hourOfDay;
    private TextView minute;
    private ArrayList<Alarm> alaramList = new ArrayList<Alarm>();

    public void addItem(Alarm alarm){
        alaramList.add(alarm);
    }

    @Override
    public int getCount() {
        return alaramList.size();
    }

    @Override
    public Alarm getItem(int position) {
        return alaramList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_main_listview,parent,false);
        }

        meridiem = convertView.findViewById(R.id.activity_main_lv_meridiem);
        hourOfDay = convertView.findViewById(R.id.activity_main_lv_hour);
        minute = convertView.findViewById(R.id.activity_main_lv_minute);

        meridiem.setText(alaramList.get(position).getMeridiem());
        hourOfDay.setText(String.format("%02d",alaramList.get(position).getHourOfday()));
        minute.setText(String.format("%02d",alaramList.get(position).getMinute()));

        return convertView;
    }
}
