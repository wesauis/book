package mixins;

import database.DB;
import database.NamedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 *
 * @author w2gam
 */
public interface Repository<E> extends Loggable {

    default Connection conn() {
        return DB.instance().connection();
    }

    default boolean execute(String sql) throws SQLException {
        return conn().createStatement().execute(sql);
    }

    public E parse(ResultSet rs) throws SQLException;

    default E safeParse(ResultSet rs) {
        try {
            return parse(rs);
        } catch (SQLException ex) {
            logger().log(Level.WARNING, "Error parsing result set", ex);
            return null;
        }
    }

    default boolean query(String sql) throws SQLException {
        return conn().createStatement().execute(sql);
    }

    default NamedStatement<E> createStatement(String sql) {
        return new NamedStatement<>(sql, this);
    }
}
