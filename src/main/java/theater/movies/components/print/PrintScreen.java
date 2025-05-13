package theater.movies.components.print;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import theater.movies.components.movie.Movie;
import theater.movies.components.movie.Ticket;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;

public class PrintScreen {


    public static String generateTheater() {
        int min = 100;
        int max = 200;
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        int randInt = rand.nextInt(26) + 'A';
        sb.append((char)randInt);
        sb.append(rand.nextInt(max - min + 1) + min);
        return sb.toString();
    }

    public static void showTicket(Ticket ticket) {
        GridPane ticketFormat = new GridPane();

        Label name = new Label(ticket.getFullName());
        Label theater = new Label("Theater: " + ticket.getTheater());
        Label movie = new Label(ticket.getMovie());



        Label time = new Label(ticket.getTime());
        Label date = new Label(ticket.getDate());

        VBox timeAndDate = new VBox();
        timeAndDate.getChildren().addAll(time, date);
        timeAndDate.setSpacing(5);
        timeAndDate.setAlignment(Pos.CENTER_LEFT);

        name.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD,15));
        movie.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD,15));
        theater.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 15));
        time.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD,15));
        date.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD,15));

        ticketFormat.add(name, 0,0);
        ticketFormat.add(theater, 1,0);
        ticketFormat.add(movie, 1,1);
        ticketFormat.add(timeAndDate, 0,1);

        ticketFormat.setVgap(100);
        ticketFormat.setHgap(100);
        ticketFormat.setAlignment(Pos.CENTER);




        Stage stage = new Stage();

        String formattedTitle = String.format("%s's movie ticket for %s", ticket.getFullName(), ticket.getMovie());

        stage.setTitle(formattedTitle);
        stage.setScene(new Scene(ticketFormat, 400, 200));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }
    public static VBox getInfoPane(Map<Integer, Integer> seats, ComboBox<Movie> movie, DatePicker date, ComboBox<String> time) {

        VBox total = new VBox();
        GridPane infoPane = new GridPane();

        Label first = new Label("Enter your first name: ");
        Label last = new Label("Enter your last name: ");

        Button print = new Button("Print");

        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();

        print.setPadding(new Insets(20, 30, 20, 30));
        print.setFont(Font.font(20));

        infoPane.add(first, 0, 0);
        infoPane.add(last, 0, 1);
        infoPane.add(firstNameField, 1, 0);
        infoPane.add(lastNameField, 1,1);


        infoPane.setHgap(20);
        infoPane.setVgap(50);
        infoPane.setPadding(new Insets(20));
        infoPane.setAlignment(Pos.CENTER);

        total.getChildren().add(infoPane);
        total.getChildren().add(print);

        total.setSpacing(20);
        total.setAlignment(Pos.CENTER);

        print.setOnAction(actionEvent -> {
            if (!firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty()) {
                Ticket ticket = new Ticket();
                ticket.setFullName(firstNameField.getText().trim(), lastNameField.getText().trim());
                ticket.setDate(date.getValue().format(DateTimeFormatter.ofPattern("MM-dd-yy")));
                ticket.setTheater(generateTheater());
                ticket.setTime(time.getValue());
                String movieSelected = "Movie";
                ticket.setMovie(movieSelected);

                showTicket(ticket);

            }
        });

        return total;
    }
}
