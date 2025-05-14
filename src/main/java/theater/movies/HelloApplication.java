package theater.movies;

import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import theater.movies.components.menubar.MenuBar;
import theater.movies.components.movie.Movie;
import theater.movies.components.movie.MovieSerializer;
import theater.movies.components.movie.Ticket;
import theater.movies.components.print.PrintScreen;
import theater.movies.components.seats.Seat;
import theater.movies.components.seats.Seats;
import java.io.*;
import java.util.ArrayList;



public class HelloApplication extends Application implements Serializable{
    private static final long serialVersionUID = 1L;
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

        ComboBox<String> movie = (ComboBox<String>) menuBar.getChildren().get(0);
        DatePicker datePicker = (DatePicker) menuBar.getChildren().get(1);
        ComboBox<String> time = (ComboBox<String>) menuBar.getChildren().get(2);


        seatRows.setStyle("-fx-background-color: #727272;");

        // Seats
        VBox seats = Seats.createSeats();

        ArrayList<String> selectedSeatContainer = new ArrayList<>();

        for (int row = 0; row < seats.getChildren().size(); row++) {
            HBox currentRow = (HBox) seats.getChildren().get(row);
            for (int seat = 0; seat < currentRow.getChildren().size(); seat++) {
                System.out.println(currentRow.getChildren().get(seat));
            }
        }



        actionBar.setStyle("-fx-background-color: #363636;");
        Button printButton = new Button("Print Ticket");
        Button showLastTicket = new Button("Show Last Ticket");
        showLastTicket.setPadding(new Insets(20));
        printButton.setPadding(new Insets(20));
        actionBar.getChildren().add(printButton);
        actionBar.setSpacing(20);
        actionBar.setAlignment(Pos.CENTER);

        printButton.setOnAction(actionEvent -> {


            for (int i = 0; i < seats.getChildren().size(); i++) {
                HBox row = (HBox) seats.getChildren().get(i);
                for (int j = 0; j < row.getChildren().size(); j++) {
                    ToggleButton seat = (ToggleButton) row.getChildren().get(j);
                    if (seat.isSelected()) {
                        Seat newSeat = new Seat(i, j);
                        String seatID = newSeat.nameSeat();
                        if (!selectedSeatContainer.contains(seatID)) {
                            selectedSeatContainer.add(seatID);
                        }
                    }
                }
            }

            if (!(selectedSeatContainer.isEmpty() || movie.getValue() == null || datePicker.getValue() == null || time.getValue() == null)) {
                try {
                    VBox ticketRoot = PrintScreen.getInfoPane(selectedSeatContainer, movie.getValue(), datePicker, time);
                    Stage newStage = new Stage();
                    newStage.setTitle("Ticket for " + MenuBar.getMovie().getName());
                    newStage.setScene(new Scene(ticketRoot, 400, 300));
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
            } else {
                Alert selectionPrompt = new Alert(Alert.AlertType.INFORMATION);
                StringBuilder sb = new StringBuilder();
                sb.append("You are missing: \n");

                if (movie.getValue() == null) {
                    sb.append("A movie selection \n");
                }

                if (datePicker.getValue() == null) {
                    sb.append("A date selection \n");
                }

                if (time.getValue() == null) {
                    sb.append("A time selection \n");
                }

                if (selectedSeatContainer.isEmpty()) {
                    sb.append("A seat selection \n");
                }
                selectionPrompt.setTitle("Please Try Again");
                selectionPrompt.setHeaderText("You are missing some information");
                selectionPrompt.setContentText(sb.toString());
                selectionPrompt.show();
            }
        });

        //basically just a place holder
        //DO NOT MESS WITH OR TURN ON
        //needs to be completely re-designed to work with the current seat system
        Button saveButton = new Button("Place-Holder Save");
        saveButton.setPadding(new Insets(20));
        actionBar.getChildren().add(saveButton);
        actionBar.getChildren().add(showLastTicket);
        actionBar.setPadding(new Insets(50));

        showLastTicket.setOnAction(actionEvent -> {

            try {
                FileInputStream fileIn = new FileInputStream("lastTicket.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                Ticket ticket = (Ticket) in.readObject();
                in.close();
                fileIn.close();

                PrintScreen.showTicket(ticket);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        });

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
        root.setCenter(seats);
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