package theater.movies.components.movie;

public class HoursMinutes {

    private int hours;
    private int minutes;



    private boolean morning;

    private HoursMinutes HMA;
    private HoursMinutes HMB;

    public boolean isMorning() {
        return morning;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    public HoursMinutes(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public HoursMinutes(HoursMinutes A, HoursMinutes B) {
        this.HMA = A;
        this.HMB = B;
    }

    public int getHours() {
        return hours;
    }
    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public HoursMinutes getHMA() {
        return HMA;
    }

    public void setHMA(HoursMinutes HMA) {
        this.HMA = HMA;
    }

    public HoursMinutes getHMB() {
        return HMB;
    }

    public void setHMB(HoursMinutes HMB) {
        this.HMB = HMB;
    }


}
