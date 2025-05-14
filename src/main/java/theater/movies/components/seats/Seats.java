package theater.movies.components.seats;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import theater.movies.components.movie.Movie;
import java.util.HashMap;
import java.util.Map;

public class Seats {

    //Variables and Setup
    // establish the static number of rows and seats per theater
    private static int numOfRows = 3;
    private static int seatsPerRow = 8;
    private static ToggleButton[][] seatArray = new ToggleButton[numOfRows][seatsPerRow];

    //Getters
    public static int getSeatsPerRow() {
        return seatsPerRow;
    }
    public static ToggleButton[][] getSeatArray() {
        return seatArray;
    }
    public static int getNumOfRows() {
        return numOfRows;
    }

    //Setters
    //this sets the toggle button state array (global toggle button state array) from an int array gotten from the movies
    private static void setToggleButtonStateArray(int[][] integerArray) {
        for (int row = 0; row < Seats.getNumOfRows(); row++) {
            for (int column = 0; column < Seats.getSeatsPerRow(); column++) {
                if(integerArray[row][column] == 1) {
                    Seats.seatArray[row][column].setSelected(true);
                    Seats.seatArray[row][column].setStyle("-fx-background-color: lightgreen; -fx-text-fill: black;");
                }
                else if (integerArray[row][column] == 0) {
                    Seats.seatArray[row][column].setSelected(false);
                    Seats.seatArray[row][column].setStyle("-fx-background-color: lightgray; -fx-text-fill: black;");
                }
                else { //for the integer value of 2, used when deserializing to show other users bought seats and previously bought seats.
                    Seats.seatArray[row][column].disabledProperty();
                    Seats.seatArray[row][column].setStyle("-fx-background-color: red; -fx-text-fill: black;");
                }
            }
        }
    }



    //Functions
    //this function creates the seat UI element
    public static VBox createSeats() {
        VBox seatRows = new VBox();
        seatRows.setStyle("-fx-background-color: #727272;");

        // create the theater arrangement
        Map<Integer, ToggleButton> seatsInRow = new HashMap<>();
        //
        Map<Integer, Map<Integer, ToggleButton>> rows = new HashMap<>();

        for (int i = 0; i < numOfRows; i++) {
            HBox row = new HBox();
            for (int j = 0; j < seatsPerRow; j++) {
                ToggleButton seat = new ToggleButton();
                seatArray[i][j] = seat;


                seat.setPrefSize(25, 25);
                row.getChildren().addAll(seat);

                seatsInRow.put(j, seat);

                seat.setOnAction(actionEvent -> {
                    if (seat.isSelected()) {
                        seat.setStyle("-fx-background-color: lightgreen;");
                        seat.setSelected(true);
                    } else {
                        seat.setStyle("-fx-background-color: lightgray;");
                        seat.setSelected(false);
                    }
                });
            }

            // row housekeeping
            row.setSpacing(30 + (i * 5));
            row.setPadding(new Insets(20));
            row.setAlignment(Pos.CENTER);

            // vbox housekeeping
            seatRows.getChildren().add(row);
            seatRows.setAlignment(Pos.CENTER);
            rows.put(i, seatsInRow);
        }
        return seatRows;
    }

    //used when changing movies
    //saves the current seats to the current movie
    //updates the screen to display the seat array from the selected movie
    public static void changeMovies(Movie currentMovie, Movie selectedMovie) {
        currentMovie.setToggleButtonStateArray(seatArray);
        setToggleButtonStateArray(selectedMovie.getToggleButtonStateArray());
    }
}
