package theater.movies.components.seats;

public class Seat {

    int row;
    int number;

    public Seat(int row, int number) {
        this.row = row;
        this.number = number;
    }

    public String nameSeat() {
        String letter = "";
        switch (row) {
            case 0:
                letter = "A";
                break;
            case 1:
                letter = "B";
                break;
            case 2:
                letter = "C";
                break;
        }

        return String.format("%s%d", letter, number);
    }

}
