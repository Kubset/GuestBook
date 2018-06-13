package GuestBook.messageDAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider {

    private Connection connection;

    public ConnectionProvider() {
        this.connection = connect();
    }

    public Connection getConnection() {
        return connection;
    }

    private Connection connect() {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver"); // force classloader to load the driver
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/kuba", "postgres", "xc90"); // set user and password
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return c;
    }
}
