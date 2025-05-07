package theater.movies.components.movie;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieSerializer {


    public static ArrayList<Movie> loadMovies(String path) {

        ArrayList<Movie> movies = new ArrayList<>();

        try {
            File movieFile = new File(path);
            Scanner decoder = new Scanner(movieFile);
            // Check every line for the movie info
            while (decoder.hasNextLine()) {
                String information = decoder.nextLine();

                try {
                    // Split up the movie info
                    String[] splitInfo = information.split(",");
                    // Create and add the movie info to the array of movies
                    Movie movie = new Movie(splitInfo[0], Integer.parseInt(splitInfo[1]), Integer.parseInt(splitInfo[2]));
                    movies.add(movie);
                } catch (Exception e) {
                    System.out.println("Error: " + e);
                }
            }
            decoder.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not locate the movie file. Make sure the loader contains the path of the movies file.");
        }
        return movies;
    }

}
