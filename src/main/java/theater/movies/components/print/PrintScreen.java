package theater.movies.components.print;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PrintScreen {

    public static GridPane ticketInfo() {
        // Base for print info
        GridPane root = new GridPane();

        // Controls
        TextField nameText = new TextField("Enter name here");


        return root;
    }

}
