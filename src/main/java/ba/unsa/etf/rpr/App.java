package ba.unsa.etf.rpr;


import ba.unsa.etf.rpr.business.MoviesManager;
import ba.unsa.etf.rpr.business.RentsManager;
import ba.unsa.etf.rpr.business.UsersManager;
import ba.unsa.etf.rpr.domain.Movies;
import ba.unsa.etf.rpr.domain.Rents;
import ba.unsa.etf.rpr.domain.Users;
import ba.unsa.etf.rpr.exceptions.TheMovieRentalSystemException;
import org.apache.commons.cli.*;
import java.sql.Date;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.*;

/**
 * @author Aida Zametica
 * CLI (Command Line Interface) implementation in following class
 */
public class App {
    /**
     * Defining final variables to describe all code having options
     */
    private static final Option addUser = new Option("aU", "add-user", false, "Adding new user to TheMovieRentalSystem database");
    private static final Option addMovie = new Option("aM", "add-movie", false, "Adding new movie to TheMovieRentalSystem database");
    private static final Option getUsers = new Option("getU", "get-users", false, "Printing all users from TheMovieRentalSystem database");
    private static final Option getMovies = new Option("getM", "get-movies", false, "Printing all movies from TheMovieRentalSystem database");
    private static final Option addRent = new Option("aR", "add-rent", false, "Adding new rent to TheMovieRentalSystem database");
    private static final Option getRents = new Option("getR", "get-rents", false, "Printing all rents from TheMovieRentalSystem database");

    /**
     * @param options
     */
    public static void printFormattedOptions(Options options) {
        HelpFormatter helpFormatter = new HelpFormatter();
        PrintWriter printWriter = new PrintWriter(System.out);
        helpFormatter.printUsage(printWriter, 150, "java -jar TheRentalMovieSystem.jar [option] 'something else if needed' ");
        helpFormatter.printOptions(printWriter, 150, options, 2, 7);
        printWriter.close();
    }

    public static Options addOptions() {
        Options options = new Options();
        options.addOption(addUser);
        options.addOption(addMovie);
        options.addOption(getUsers);
        options.addOption(getMovies);
        options.addOption(addRent);
        options.addOption(getRents);
        return options;
    }


    public static void main(String[] args) throws TheMovieRentalSystemException, ParseException {
        Options options = addOptions();

        CommandLineParser commandLineParser = new DefaultParser();

        CommandLine cl = commandLineParser.parse(options, args);

        if (cl.hasOption(addUser.getOpt())) {
            String username = cl.getArgList().get(0);
            String password = cl.getArgList().get(1);
            boolean admin = Boolean.parseBoolean(cl.getArgList().get(2));
            UsersManager usersManager = new UsersManager();
            Users user = new Users();
            user.setPassword(password);
            user.setUsername(username);
            user.setAdmin(admin);
            usersManager.add(user);
            System.out.println("You successfully added a new user to the database!");
        } else if (cl.hasOption(getUsers.getOpt())) {
            UsersManager usersManager = new UsersManager();
            List<Users> usersList = usersManager.getAll();
            usersList.forEach(user -> System.out.println(user.getId() + ". " + user.getUsername() + " (" + user.getPassword() + ")" + " (" + user.isAdmin() + ")"));
        } else if (cl.hasOption(addMovie.getOpt())) {
            String movieName = cl.getArgList().get(0);
            int duration = Integer.parseInt(cl.getArgList().get(1));
            String genre = cl.getArgList().get(2);
            double ratings = Double.parseDouble(cl.getArgList().get(3));
            double price = Double.parseDouble(cl.getArgList().get(4));
            String language = cl.getArgList().get(5);
            int releaseYear = Integer.parseInt(cl.getArgList().get(6));
            MoviesManager moviesManager = new MoviesManager();
            Movies movie = new Movies();
            movie.setMovie_name(movieName);
            movie.setDuration(duration);
            movie.setGenre(genre);
            movie.setRatings(ratings);
            movie.setPrice(price);
            movie.setLanguage(language);
            movie.setRelease_year(releaseYear);
            moviesManager.add(movie);
            System.out.println("You successfully added a new movie to the database!");
        } else if (cl.hasOption(getMovies.getOpt())) {
            MoviesManager moviesManager = new MoviesManager();
            List<Movies> moviesList = moviesManager.getAll();
            moviesList.forEach(movie -> System.out.println(movie.getId() + ". " + movie.getMovie_name() + " (" + movie.getDuration() + ")")); //trb dodati jos
        } else if (cl.hasOption(addRent.getOpt())) {
            int userId = Integer.parseInt(cl.getArgList().get(0));
            int movieId = Integer.parseInt(cl.getArgList().get(1));
            UsersManager usersManager = new UsersManager();
            MoviesManager moviesManager = new MoviesManager();
            Users user = usersManager.getById(userId);
            Movies movie = moviesManager.getById(movieId);
            RentsManager rentsManager = new RentsManager();
            Rents rent = new Rents();
            rent.setUser(user);
            rent.setMovie(movie);
            rent.setRent_date(Date.valueOf(LocalDate.now()));
            rentsManager.add(rent);
            System.out.println("You successfully added a new rent to the database!");
        } else if (cl.hasOption(getRents.getOpt())) {
            RentsManager rentsManager = new RentsManager();
            List<Rents> rentsList = rentsManager.getAll();
            rentsList.forEach(rent -> System.out.println("Rent ID: " + rent.getId() +
                    ", User: " + rent.getUser().getUsername() +
                    ", Movie: " + rent.getMovie().getMovie_name() +
                    ", Rent Date: " + rent.getRent_date()));
        } else {
            printFormattedOptions(options);
            System.exit(-1);
        }
    }
}

