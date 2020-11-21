package ows.boostcourse.myalarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;

/**
 * This is Alarm App MainActivity
 */
public class MainActivity extends AppCompatActivity {

    private MainPresenter presenter;
    private RecyclerView recyclerView;
    private TimePicker timePicker;
    private Button setButton;
    private MainView mainView = new MainView() {
        @Override
        public void onInitView(AlarmAdapter adapter) {
            initView(adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = MainPresenter.getInstance(getApplicationContext(),mainView);
        presenter.onCreate();

    }

    /**
     * intialize view
     * @param adapter : recyclerview adapter
     */
    public void initView(AlarmAdapter adapter){
        timePicker = findViewById(R.id.activity_main_tp);
        setButton = findViewById(R.id.activity_main_btn);
        recyclerView = findViewById(R.id.activity_main_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.setAlarm(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
            }
        });
    }

}