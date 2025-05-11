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
import theater.movies.components.seats.Seats;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws IOException {

        String path = "src/main/java/theater/movies/components/movie/movies.txt"; // input the path of the movies directory

        ArrayList<Movie> movies = MovieSerializer.loadMovies(path);

        // Create UI elements
        HBox actionBar = new HBox();
        VBox seatRows = new VBox();
        BorderPane root = new BorderPane();

        // set up the menu bar behind scenes
        HBox menuBar = MenuBar.createMenuBar(movies);
        seatRows.setStyle("-fx-background-color: #727272;");

        // create the theater arrangement

        Map<Integer, ToggleButton> seatsInRow = new HashMap<>();
        //
        Map<Integer, Map<Integer, ToggleButton>> rows = new HashMap<>();



        actionBar.setStyle("-fx-background-color: #363636;");
        Button printButton = new Button("Print Ticket");
        printButton.setPadding(new Insets(20));
        actionBar.getChildren().add(printButton);
        actionBar.setAlignment(Pos.CENTER);
        actionBar.setPadding(new Insets(50));

        printButton.setOnAction(actionEvent -> {
            try {
                System.out.println();
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
        });

        //basically just a place holder
        //DO NOT MESS WITH OR TURN ON
        //needs to be completely re-designed to work with the current seat system
        Button saveButton = new Button("Place-Holder Save");
        saveButton.setPadding(new Insets(20));
        actionBar.getChildren().add(saveButton);
        actionBar.setPadding(new Insets(50));

        saveButton.setOnAction(save -> {

            try {
                FileOutputStream fileOut = new FileOutputStream("PlaceHolderName.ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(seatRows);
                out.close();
                fileOut.close();

                //DEBUG TEXT
                System.out.println("Everything went through and is A OK");

            } catch (IOException e) {
                System.out.println("Error occurred during save process: " + e);
            }
        });

        // Set up the sections of the BorderPane
        root.setTop(menuBar);
        root.setCenter(Seats.createSeats());
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