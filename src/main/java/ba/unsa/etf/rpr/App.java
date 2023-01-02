package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.dao.UsersDaoSQLImpl;
import ba.unsa.etf.rpr.domain.Users;

public class App {
    public static void main(String[] args) throws Exception {
     Users m = new Users();
     m.setUsername("admin");
     m.setPassword("admin");
     m.setAdmin(true);

     UsersDaoSQLImpl filmic = new UsersDaoSQLImpl();
     filmic.add(m);

    }
}