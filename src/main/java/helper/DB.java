package helper;

import helper.Loggable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author w2gam
 */
public class DB implements Loggable {

    private static final String URI = "jdbc:sqlite:db.sqlite";
    private static final String DRIVER = "org.sqlite.JDBC";

    private static DB instance;

    /**
     *
     * @return the DB instance
     */
    public static DB instance() {
        if (instance == null) {
            instance = new DB();
        }

        return instance;
    }

    private Connection connection;

    private DB() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            logger().severe("Driver not found '" + DRIVER + "'");
            System.exit(1);
        }
    }
    
    /**
     *
     * @return an open connection
     */
    public Connection connection() {
        try {
            if (connection == null || connection.isClosed()) {
                logger().finest("Connecting to database...");

                try {
                    connection = DriverManager.getConnection(URI);
                } catch (SQLException ex) {
                    logger().severe("Unable to connect to '" + URI + "'");
                    System.exit(1);
                }
            }
        } catch (SQLException ex) {
            logger().severe("Unable to determinate connection status");
            System.exit(1);
        }

        return connection;
    }

}
