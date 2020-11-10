package ows.boostcourse.myalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public MainPresenter presenter;
    public ListView listView;
    public TimePicker timePicker;
    public Button setButton;
    public MainView mainView = new MainView() {
        @Override
        public void onInitView(AlarmAdapter adapter) {
            initView(adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(mainView);
        presenter.onCreate();

    }

    public void initView(AlarmAdapter adapter){
        timePicker = findViewById(R.id.activity_main_tp);
        setButton = findViewById(R.id.activity_main_btn);
        listView = findViewById(R.id.activity_main_lv);
        listView.setAdapter(adapter);

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.setAlarm(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
            }
        });
    }

}