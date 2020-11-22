package ows.boostcourse.myalarm;


import java.util.Calendar;

/**
 * Alarm Model
 * meridiem : AM, PM
 * hourOfDay : 0~24
 * minute : 0~60
 */
public class Alarm{

    private Calendar calendar;
    private String meridiem;
    private  int hourOfday;
    private int minute;

    /**
     * Alarm constructor.
     * @param calendar
     */
    public Alarm(Calendar calendar){
        this.calendar = calendar;
        updateInfo();
    }


    /**
     * Update alarm information except calendar.
     */
    private void updateInfo(){
        this.meridiem = "AM";
        this.hourOfday = calendar.get(Calendar.HOUR_OF_DAY);
        this.minute = calendar.get(Calendar.MINUTE);

        if(hourOfday>=12){
            this.meridiem = "PM";
            hourOfday-=12;
        }
    }

    @Override
    public String toString() {
        return meridiem+" "+hourOfday+"시 "+minute+"분";
    }

    /**
     * Add One day When calendar has passed the current time.
     */
    public void addOneDayCalendar(){
        calendar.add(Calendar.DATE,1);
        updateInfo();
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public String getMeridiem() {
        return meridiem;
    }

    public int getHourOfday() {
        return hourOfday;
    }

    public int getMinute() {
        return minute;
    }
}
