package theater.movies.components.movie;

import java.io.Serializable;
import java.util.ArrayList;


public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;
    private String theater;
    private String fullName;
    private String time;
    private String date;
    private String movie;
    private ArrayList<String> seats;





    public String getTheater() {
        return theater;
    }

    public void setTheater(String theater) {
        this.theater = theater;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String first, String last) {
        this.fullName = first + " " + last;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public void setSeats(ArrayList<String> seats) {
        this.seats = seats;
    }

    public String getSeats() {
        StringBuilder sb = new StringBuilder();
        int count = 0;

        for (String seat : this.seats) {
            if (count == (this.seats.size() - 1)) {
                sb.append(seat);
            } else {
                sb.append(seat).append(", ");
            }
            count++;
        }

        return sb.toString();
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }



}
