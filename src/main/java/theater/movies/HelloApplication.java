package theater.movies;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import theater.movies.components.menubar.MenuBar;
import theater.movies.components.movie.Movie;
import theater.movies.components.movie.MovieSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws IOException {

        String path = "src/main/java/theater/movies/components/movie/movies.txt"; // imput the path of the movies directory

        ArrayList<Movie> movies = MovieSerializer.loadMovies(path);

        // Create UI elements
        HBox actionBar = new HBox();
        VBox seatRows = new VBox();
        BorderPane root = new BorderPane();

        // establish the static number of rows and seats per theater
        int numOfRows = 3;
        int seatsPerRow = 8;

        // set up the menu bar behind scenes

        HBox menuBar = MenuBar.createMenuBar(movies);
        seatRows.setStyle("-fx-background-color: #727272;");

        // create the theater arrangement

        Map<Integer, ToggleButton> seatsInRow = new HashMap<Integer, ToggleButton>();
        //
        Map<Integer, Map<Integer, ToggleButton>> rows = new HashMap<>();

        for (int i = 0; i < numOfRows; i++) {
            HBox row = new HBox();
            for (int j = 0; j < seatsPerRow; j++) {
                ToggleButton seat = new ToggleButton();

                seat.setPrefSize(25, 25);
                row.getChildren().addAll(seat);

                seatsInRow.put(j, seat);

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


        actionBar.setStyle("-fx-background-color: #363636;");
        Button printButton = new Button("Print Ticket");
        printButton.setPadding(new Insets(20));
        actionBar.getChildren().add(printButton);
        actionBar.setAlignment(Pos.CENTER);
        actionBar.setPadding(new Insets(50));
        printButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    AnchorPane ticketRoot = new AnchorPane();
                    Stage newStage = new Stage();
                    newStage.setTitle("Ticket for " + MenuBar.getMovie().getName());
                    newStage.setScene(new Scene(ticketRoot, 300, 300));
                    newStage.initModality(Modality.APPLICATION_MODAL);
                    newStage.show();
                } catch (NullPointerException n) {
                    Alert selectMovie = new Alert(Alert.AlertType.NONE);
                    selectMovie.setTitle("No movie selected!");
                    selectMovie.setHeaderText("Please select a movie to print a ticket.");
                    selectMovie.getButtonTypes().add(ButtonType.CLOSE);
                    selectMovie.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

        // Set up the sections of the BorderPane
        root.setTop(menuBar);
        root.setCenter(seatRows);
        root.setBottom(actionBar);

        // Set up the scene
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Hello!");
        stage.setMinWidth(650);
        stage.setMinHeight(450);
        stage.setScene(scene);
        stage.show();
    }


}