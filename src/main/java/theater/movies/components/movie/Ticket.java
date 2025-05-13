package theater.movies.components.movie;

public class Ticket {

    private String theater;
    private String fullName;
    private String time;
    private String date;
    private String movie;


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


    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }



}
