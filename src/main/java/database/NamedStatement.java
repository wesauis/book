package database;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import mixins.Loggable;
import mixins.Repository;

/**
 *
 * @author w2gam
 */
public class NamedStatement<E> implements Loggable {

    private String SQL;
    private Repository<E> repository;

    /**
     *
     * @param SQL sql com placeholders no formato {{{@code name}}}
     */
    public NamedStatement(String SQL, Repository<E> repository) {
        this.SQL = SQL;
        this.repository = repository;
    }

    private NamedStatement<E> replace(String name, String value) {
        SQL = SQL.replaceAll("\\{\\{" + name + "\\}\\}", value);
        return this;
    }

    /**
     * Substitui o placeholder com {@code `field`}
     *
     * @param name
     * @param field
     *
     */
    public NamedStatement<E> setField(String name, String field) {
        return replace(name, '`' + field.replaceAll("`", "").replaceAll("\\\\", "") + '`');
    }

    /**
     * Substitui o placeholder com {@code value.toString()} ou {@code null} se
     * nulo.
     *
     * @param name
     * @param value
     */
    public NamedStatement<E> set(String name, Object value) {
        return replace(name, value == null ? "null" : value.toString());
    }

    /**
     * Substitui o placeholder com {@code 'value'} ou {@code null} se nulo.
     *
     * @param name
     * @param value
     */
    public NamedStatement<E> set(String name, String value) {
        return replace(name, value == null ? "null" : '\'' + value.replaceAll("'", "").replaceAll("\\\\", "") + '\'');
    }

    /**
     * Substitui o placeholder com {@code 'data'} ou {@code null} se nulo.
     *
     * @param name
     * @param date
     */
    public NamedStatement<E> set(String name, Date date) {
        return set(name, date == null ? null : date.toString());
    }

    /**
     * Substitui o placeholder com {@code value} ou {@code null} se valor for 0.
     *
     * @param name
     * @param value
     */
    public NamedStatement<E> set(String name, int value) {
        return set(name, value, true);
    }

    /**
     * Substitui o placeholder com {@code value} ou {@code null} se valor for 0
     * e zeroIsNull for true
     *
     * @param name
     * @param value
     * @param zeroIsNull
     */
    public NamedStatement<E> set(String name, int value, boolean zeroIsNull) {
        return replace(name, zeroIsNull && value == 0 ? "null" : String.valueOf(value));
    }

    public String getSQL() {
        return SQL;
    }
    
    /**
     * Executa a query
     *
     * @return
     * @throws SQLException
     */
    public ResultSet queryRaw() throws SQLException {
        try {
            return DB.instance().connection().createStatement().executeQuery(SQL);
        } catch (SQLException exception) {
            logger().log(Level.WARNING, "Query Error, sql: " + getSQL(), exception);
            throw exception;
        }
    }

    /**
     * Executa a query
     *
     * @return
     * @throws SQLException
     */
    public List<E> query() throws SQLException {
        try {
            return DB.parseResults(queryRaw(), repository::safeParse);
        } catch (SQLException exception) {
            logger().log(Level.WARNING, "Query Error, sql: " + getSQL(), exception);
            throw exception;
        }
    }
    
    /**
     * Executa a query mas retorna apenas o primeiro item
     *
     * @return
     * @throws SQLException
     */
    public E first() throws SQLException {
        List<E> items = query();
        return items.isEmpty() ? null : items.get(0);
    }

    /**
     * Executa a atualização.
     *
     * @return o numero de linhas afetadas
     * @throws SQLException
     */
    public int update() throws SQLException {
        try {
            return DB.instance().connection().createStatement().executeUpdate(SQL);
        } catch (SQLException exception) {
            logger().log(Level.WARNING, "Query Error, sql: " + getSQL(), exception);
            throw exception;
        }
    }

}
