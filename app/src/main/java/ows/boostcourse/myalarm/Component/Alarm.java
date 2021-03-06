package ows.boostcourse.myalarm.Component;


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
    private boolean flag;

    /**
     * Alarm constructor.
     * @param calendar
     */
    public Alarm(Calendar calendar, boolean flag){
        this.calendar = calendar;
        this.flag = flag;
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

    /**
     * Convert Alarm component to String
     * @return
     */
    @Override
    public String toString() {
        return String.format("%s %02d시 %02d분",meridiem,hourOfday,minute);
    }

    /**
     * Add One day When calendar has passed the current time.
     */
    public void addOneDayCalendar(){
        calendar.add(Calendar.DATE,1);
        updateInfo();
    }

    /**
     * Get calendar.
     * @return
     */
    public Calendar getCalendar() { return calendar; }

    /**
     * Get meridiem.
     * @return
     */
    public String getMeridiem() {
        return meridiem;
    }

    /**
     * Get hourofday.
     * @return
     */
    public int getHourOfday() {
        return hourOfday;
    }

    /**
     * Get minute.
     * @return
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Get flag.
     * @return
     */
    public boolean getFlag() { return flag; }

    /**
     * Set flag
     * @param flag
     */
    public void setFlag(boolean flag) { this.flag = flag; }
}
