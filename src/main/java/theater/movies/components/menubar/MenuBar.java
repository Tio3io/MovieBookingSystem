package theater.movies.components.menubar;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import theater.movies.components.movie.HoursMinutes;
import theater.movies.components.movie.Movie;
import theater.movies.components.seats.Seats;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuBar {

    private static Movie currentMovie;

    public static Movie getMovie() {
        return currentMovie;
    }

    public static void setCurrentMovie(Movie movie) {
        currentMovie = movie;
    }

    public static HBox createMenuBar(ArrayList<Movie> movies) {

        HBox menuBar = new HBox();


        // set the movies and placeholder
        ComboBox<String> movieSelector = new ComboBox<>();
        movieSelector.setPromptText("Select a movie");

        Map<String, Movie> movieMap = new HashMap<>();

        // Load current movies into the selector
        for (Movie movie : movies) {
            movieSelector.getItems().add(movie.getName());
            movieMap.put(movie.getName(), movie);
        }

        // Set the datePicker
        DatePicker movieDate = new DatePicker();
        movieDate.setPromptText("Select a date");

        // Set ip the time picker
        ComboBox<String> timeSelector = new ComboBox<>();
        timeSelector.setPromptText("Select a time");

        // Code that runs when the movieSelector is changed
        movieSelector.setOnAction((e) -> {

            String selectedMovieTitle = movieSelector.getValue();
            Movie selectedMovie = movieMap.get(selectedMovieTitle);
            // If a movie is selected
            if (selectedMovie != null) {
                //if the current movie is not null then the seats are saved and refreshed
                if (currentMovie != null) {
                    Seats.changeMovies(currentMovie, selectedMovie);
                }
                setCurrentMovie(selectedMovie);
                timeSelector.getItems().clear();

                // Set times starting from current time that a customer can choose from
                int currentHours = LocalTime.now().getHour();
                int currentMinutes = 0;
                boolean currentTypeOfTime = LocalTime.now().isAfter(LocalTime.MIDNIGHT);
                for (int i = 0; i < 10; i++) {
                    if (currentHours < 22) {
                        HoursMinutes showTime = selectedMovie.generateAvailableTimes(currentHours, currentMinutes, currentTypeOfTime);
                        String formattedTime = String.format(
                                "%d:%d%s - %d:%d%s",
                                showTime.getHMA().getHours() > 12 ? showTime.getHMA().getHours() - 12 : showTime.getHMA().getHours(),
                                showTime.getHMA().getMinutes(),
                                showTime.getHMA().isMorning() ? "am" : "pm",
                                showTime.getHMB().getHours() > 12 ? showTime.getHMB().getHours() - 12 : showTime.getHMB().getHours(),
                                showTime.getHMB().getMinutes(),
                                showTime.getHMB().isMorning() ? "am" : "pm"
                        );
                        timeSelector.getItems().add(formattedTime);
                        currentHours = showTime.getHMB().getHours();
                        currentMinutes = showTime.getHMB().getMinutes();
                        currentTypeOfTime = showTime.getHMB().isMorning();
                    }
                }
            }
        });




        // Add the movie selector, datePicker, and time selector to the menu bar
        menuBar.getChildren().addAll(movieSelector, movieDate, timeSelector);

        // Organize the menuBar
        menuBar.setSpacing(50);
        menuBar.setAlignment(Pos.CENTER);
        menuBar.setPadding(new Insets(30));
        menuBar.setStyle("-fx-background-color: #DBDBDB;");

        return menuBar;
    }

}
