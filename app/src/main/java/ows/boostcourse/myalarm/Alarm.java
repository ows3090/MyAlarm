package ows.boostcourse.myalarm;

import androidx.annotation.NonNull;

/**
 * Alarm Model
 * meridiem : AM, PM
 * hourOfDay : 0~24
 * minute : 0~60
 */
public class Alarm{

    private String meridiem;
    private  int hourOfday;
    private int minute;

    public Alarm(String meridiem, int hourOfday, int minute) {
        this.meridiem = meridiem;
        this.hourOfday = hourOfday;
        this.minute = minute;
    }

    public String toString(){
        return String.format("%s %d시 %d분",meridiem,hourOfday,minute);
    }

    public String getMeridiem() {
        return meridiem;
    }

    public void setMeridiem(String meridiem) {
        this.meridiem = meridiem;
    }

    public int getHourOfday() { return hourOfday; }

    public void setHourOfday(int hourOfday) {
        this.hourOfday = hourOfday;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

}
