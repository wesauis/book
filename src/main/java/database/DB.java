package database;

import mixins.Loggable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import repository.BookRepository;

/**
 *
 * @author w2gam
 */
public class DB implements Loggable {

    private static final String URI = "jdbc:sqlite:./app.db";
//    private static final String URI = "jdbc:sqlite::memory:";
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

            connection();
            initDB();
        } catch (ClassNotFoundException ex) {
            logger().severe("Driver not found '" + DRIVER + "'");
            System.exit(1);
        } catch (SQLException ex) {
            logger().log(Level.SEVERE, "Unable to initialize database", ex);
            System.exit(1);
        }
        
        logger().info("DB Initialized Successfully");
    }

    private void initDB() throws SQLException {
        BookRepository.createTable(connection());
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
    
    /**
     * Converte um resultset para uma lista de entidades.
     *
     * @param <E> tipo da entidade
     * @param resultSet resultados
     * @param parser função responsável por converter um registro do DB para uma
     * lista de entidades
     * @return
     * @throws SQLException
     */
    public static <E> List<E> parseResults(ResultSet resultSet, Function<ResultSet, E> parser) throws SQLException {
        List<E> entities = new ArrayList<>();

        while (resultSet.next()) {
            E e = parser.apply(resultSet);
            if (e != null) {
                entities.add(e);
            }
        }

        return entities;
    }

}
