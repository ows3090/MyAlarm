package ows.boostcourse.myalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public MainPresenter presenter;
    public AlarmAdapter alarmAdapter = new AlarmAdapter();
    public ListView listView;
    public MainView view = new MainView() {
        @Override
        public void onAddItemInAlarmList(Alarm alarm) {
            alarmAdapter.addItem(alarm);
            alarmAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(view);

        TimePicker timePicker = findViewById(R.id.activity_main_tp);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                presenter.changeTimePicker(hourOfDay, minute);
            }
        });

        findViewById(R.id.activity_main_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.setAlarm();
            }
        });

        listView = findViewById(R.id.activity_main_lv);
        listView.setAdapter(alarmAdapter);
    }

    public class AlarmAdapter extends BaseAdapter{

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
            hourOfDay.setText(String.valueOf(alaramList.get(position).getHourOfday()));
            minute.setText(String.valueOf(alaramList.get(position).getMinute()));

            return convertView;
        }
    }


}