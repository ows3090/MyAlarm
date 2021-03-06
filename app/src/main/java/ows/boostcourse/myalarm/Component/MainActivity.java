package ows.boostcourse.myalarm.Component;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import ows.boostcourse.myalarm.Interface.MainView;
import ows.boostcourse.myalarm.R;

/**
 * This is Alarm App MainActivity.
 */
public class MainActivity extends AppCompatActivity {

    private MainPresenter presenter;
    private RecyclerView recyclerView;
    private TimePicker timePicker;
    private Button setButton;

    /**
     * MainView interface that mapped to MainPresenter.
     */
    private MainView mainView = new MainView() {
        @Override
        public void onInitView(AlarmAdapter adapter) {
            initView(adapter);
        }
    };

    /**
     * Called onCreate() method when MainActivity view create.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = MainPresenter.getInstance(getApplicationContext(),mainView);
        presenter.onCreate();
    }

    /**
     * Called onDestroy() method when MainAcitivity view destory.
     */
    @Override
    protected void onDestroy() {
        Log.d("Main","onDestory");
        super.onDestroy();
        presenter.onDestory();
    }

    /**
     * Initialize view.
     * @param adapter recyclerview adapter.
     */
    public void initView(AlarmAdapter adapter){
        timePicker = findViewById(R.id.activity_main_tp);
        setButton = findViewById(R.id.activity_main_btn);
        recyclerView = findViewById(R.id.activity_main_rv);

        // Setting adatper and layoutManager on recylerview.
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        // Setting Click event listener on setButton.
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.setAlarm(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
            }
        });
    }

}