package ows.boostcourse.myalarm;


public class MainPresenter implements Presenter{

    private MainView view;
    private int hourOfDay;
    private int minute;

    public MainPresenter(MainView view){
        this.view = view;
    }

    public void setAlarm(){
        String meridiem = "AM";
        if(hourOfDay>12){
            meridiem = "PM";
            hourOfDay-=12;
        }
        Alarm alarm = new Alarm(meridiem,hourOfDay,minute);
        view.onAddItemInAlarmList(alarm);
    }

    public void changeTimePicker(int hourOfDay, int minute){
        this.hourOfDay = hourOfDay;
        this.minute = minute;
    }
}
