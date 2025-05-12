package theater.movies.components.movie;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import theater.movies.components.seats.Seats;

import java.io.Serializable;

public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private int durationInMinutes;
    private int[][] toggleButtonStateArray;

    Movie (String name, int WatchHours, int WatchMinutes) {
        this.name = name;
        this.durationInMinutes = (WatchHours * 60) + WatchMinutes;

        this.toggleButtonStateArray = new int[Seats.getNumOfRows()][Seats.getSeatsPerRow()];
        for (int row = 0; row < Seats.getNumOfRows(); row++) {
            for (int seat = 0; seat < Seats.getSeatsPerRow(); seat++) {
                this.toggleButtonStateArray[row][seat] = 0;
            }
        }
    }

    public String getName() {
        return name;
    }

    public int[][] getToggleButtonStateArray() {
        return this.toggleButtonStateArray;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setTimeInMinutes(int timeInMinutes) {
        this.durationInMinutes = timeInMinutes;
    }

    public void setToggleButtonStateArray(ToggleButton[][] seatArray) {
        for (int row = 0; row < Seats.getNumOfRows(); row++) {
            for (int seat = 0; seat < Seats.getSeatsPerRow(); seat++) {
                if(seatArray[row][seat].isSelected()) {
                    this.toggleButtonStateArray[row][seat] = 1;
                }
                else {
                    this.toggleButtonStateArray[row][seat] = 0;
                }
            }
        }
    }

    public static HoursMinutes decToTime(double decimal) {
        HoursMinutes finalTime = new HoursMinutes(0, 0);

        // split the decimal into the calculable parts
        String[] splitTime = Double.toString(decimal).split("\\.");
        int hours = Integer.parseInt(splitTime[0]);
        // calculate minutes
        double minutesDec = decimal - hours;
        double minutes = minutesDec * 60;

        // check if the text displayed should be "AM" or "PM"
        finalTime.setMorning(hours <= 12);

        // set the final time of the movie end
        finalTime.setHours(hours);
        finalTime.setMinutes((int)minutes);


        return finalTime ;
    }

    public HoursMinutes generateAvailableTimes(int startHour, int startMinutes, boolean typeOfTime) {
        // Turn the start time into minutes and calulate the end time as a decimal
        int startTimeInMinutes = (startHour * 60) + startMinutes;
        int endTimeInMinutes = startTimeInMinutes + this.getDurationInMinutes();
        double endDecimal = (double) endTimeInMinutes / 60;
        // turn the decimal into a time
        HoursMinutes endTime = decToTime(endDecimal);

        // add a 0 to the end of single digit times
        if (endTime.getMinutes() < 10) {
            endTime.setMinutes(endTime.getMinutes() * 10);
        }

        // round up any lingering minutes
        if (endTime.getMinutes() > 60) {
            endTime.setHours(endTime.getHours() + 1);
            endTime.setMinutes(endTime.getMinutes() - 60);
        }
        // finalize the entire duration of the movie
        HoursMinutes finalDuration = new HoursMinutes(new HoursMinutes(startHour, startMinutes), endTime);
        // set the text to display
        if (typeOfTime) {
            finalDuration.getHMA().setMorning(true);
        }
        return finalDuration;

    }




}
