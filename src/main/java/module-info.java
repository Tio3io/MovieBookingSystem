module theater.movies {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens theater.movies to javafx.fxml;
    exports theater.movies;
}