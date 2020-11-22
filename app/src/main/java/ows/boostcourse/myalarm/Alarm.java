package ows.boostcourse.myalarm;


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

    /**
     * Alarm constructor.
     * @param meridiem
     * @param hourOfday
     * @param minute
     */
    public Alarm(String meridiem, int hourOfday, int minute) {
        this.meridiem = meridiem;
        this.hourOfday = hourOfday;
        this.minute = minute;
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
