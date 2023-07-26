package ba.unsa.etf.rpr;

import org.apache.commons.cli.*;

import java.io.PrintWriter;
import java.util.ArrayList;

public class App {
    private static final Option addCar = new Option("c", "add-car",false ,"Adding new car to database. Params: name, year, color, power, desc");
    private static final Option addUser = new Option("u", "add-user", false, "Adding new user to database. Params: name, password, adminPrivilege{0,1}");
    private static final Option getReservations = new Option("getR", "get-reservations", false, "Get all reservations from database");
    private static final Option getUsers = new Option("getU", "get-users",false, "Get all users from database");
    private static final Option getCars = new Option("getC", "get-cars", false, "Get all cars from database");


    public static void printFormattedOptions(Options options) {
        HelpFormatter helpFormatter = new HelpFormatter();
        PrintWriter printWriter = new PrintWriter(System.out);
        helpFormatter.printUsage(printWriter, 150, "java -jar quote-maker.jar [option] 'something else if needed' ");
        helpFormatter.printOptions(printWriter, 150, options, 2, 7);
        printWriter.close();
    }


    public static Options addOptions() {
        Options options = new Options();
        options.addOption(addCar);
        options.addOption(addUser);
        options.addOption(getReservations);
        options.addOption(getUsers);
        options.addOption(getCars);
        return options;
    }

    public static void main(String[] args) throws Exception{
        Options options = addOptions();
        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine cl = commandLineParser.parse(options,args);
        if((cl.hasOption(getCars.getOpt())||cl.hasOption(getCars.getLongOpt()))){
            CarManager carManager = new CarManager();
            ArrayList<Car> cars = (ArrayList<Car>) carManager.getAll();
            for(Car x : cars){
                System.out.println(x.toString() + "\n");
            }
        }
        else if((cl.hasOption(getReservations.getOpt())||cl.hasOption(getReservations.getLongOpt()))){
            ReservationManager reservationManager = new ReservationManager();
            ArrayList<Reservation> reservations = (ArrayList<Reservation>) reservationManager.getAll();
            for(Reservation x : reservations){
                System.out.println(x.toString() + "\n");
            }
        }
        else if((cl.hasOption(getUsers.getOpt())||cl.hasOption(getUsers.getLongOpt()))){
            UserManager userManager = new UserManager();
            ArrayList<User> users = (ArrayList<User>) userManager.getAll();
            for(User x : users){
                System.out.println(x.toString() + "\n");
            }
        }
        else if((cl.hasOption(addCar.getOpt())||cl.hasOption((addCar.getLongOpt())))){
            CarManager carManager = new CarManager();
            Car car = new Car();
            car.setName(cl.getArgList().get(0));
            car.setYear(cl.getArgList().get(1));
            car.setColor(cl.getArgList().get(2));
            car.sethP(Integer.parseInt(cl.getArgList().get(3)));
            car.setDescription(cl.getArgList().get(4));
            try {
                carManager.insert(car);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.exit(1);
            }
            System.out.println("Vozilo uspjesno uneseno!");
        }
        else if((cl.hasOption(addUser.getLongOpt())||(cl.hasOption(addUser.getOpt())))){
            UserManager userManager = new UserManager();
            User user = new User();
            user.setName(cl.getArgList().get(0));
            user.setPassword(cl.getArgList().get(1));
            user.setAdmin(Integer.parseInt(cl.getArgList().get(2)));
            if(user.getAdmin()<0||user.getAdmin()>1) throw new Exception("Pogresna privilegija");
            try{
                userManager.insert(user);
            }catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(2);
            }
            System.out.println("Korisnik uspjeno dodan!");
        }
        else{
            printFormattedOptions(options);
            System.exit(-1);
        }


    }
}