package ows.boostcourse.myalarm;


import java.util.Calendar;

/**
 * MainView와 통신하는 Presenter
 */
public class MainPresenter implements Presenter{

    private MainView view;
    private int hourOfDay;
    private int minute;
    private Calendar calendar;
    private AlarmAdapter adapter = new AlarmAdapter();

    public MainPresenter(MainView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        calendar = Calendar.getInstance();
        hourOfDay = calendar.getTime().getHours();
        minute = calendar.getTime().getMinutes();
        view.onInitView(adapter);
    }

    public String getMeridiem(){
        String meridiem = "AM";
        if(hourOfDay>12){
            meridiem = "PM";
            hourOfDay-=12;
        }
        return meridiem;
    }

    public void setAlarm(int hourOfDay, int minute){
        this.hourOfDay = hourOfDay;
        this.minute = minute;

        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);

        Alarm alarm = new Alarm(getMeridiem(),this.hourOfDay,this.minute);
        adapter.addItem(alarm);
        adapter.notifyDataSetChanged();
    }

}
