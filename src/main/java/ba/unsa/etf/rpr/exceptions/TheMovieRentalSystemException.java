package ba.unsa.etf.rpr.exceptions;

public class TheMovieRentalSystemException extends Exception {
    public TheMovieRentalSystemException(String msg, Exception reason){
        super(msg, reason);
    }

    public TheMovieRentalSystemException(String msg){
        super(msg);
    }
}
