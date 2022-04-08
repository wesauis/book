package repository;

import database.DB;
import exeptions.ValidationError;
import java.sql.Connection;
import java.sql.ResultSet;
import mixins.Loggable;
import java.sql.SQLException;
import java.util.List;
import model.Book;
import mixins.Repository;

/**
 *
 * @author w2gam
 */
public class BookRepository implements Loggable, Repository<Book> {

    private static BookRepository instance;

    private BookRepository() {
    }

    public static BookRepository instance() {
        if (instance == null) {
            instance = new BookRepository();
        }

        return instance;
    }

    public static void createTable(Connection conn) throws SQLException {
        conn.createStatement().execute(
                "CREATE TABLE IF NOT EXISTS book (\n"
                + "    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n"
                + "    title VARCHAR(512) NOT NULL,\n"
                + "    stars INT CHECK(stars BETWEEN 0 AND 10),\n"
                + "    summary TEXT,\n"
                + "    release_date DATE,\n"
                + "    author VARCHAR(256),\n"
                + "    publisher VARCHAR(256),\n"
                + "    page_count INT CHECK(page_count > 0)\n"
                + ");\n"
        );
    }

    @Override
    public Book parse(ResultSet rs) throws SQLException {
        return new Book()
                .id(rs.getInt("id"))
                .title(rs.getString("title"))
                .stars(rs.getInt("stars"))
                .summary(rs.getString("summary"))
                .release_date(rs.getDate("release_date"))
                .author(rs.getString("author"))
                .publisher(rs.getString("publisher"))
                .page_count(rs.getInt("page_count"));
    }

    public List<Book> all() throws SQLException {
        return createStatement("SELECT * FROM book").query();
    }

    public Book find(int id) throws SQLException {
        return createStatement("SELECT * FROM book WHERE id in {{id}} LIMIT 1")
                .set("id", id)
                .first();
    }

    public synchronized Book create(Book book) throws ValidationError, SQLException {
        book.validate();

        createStatement("INSERT INTO "
                + "book(id, title, stars, summary, release_date, author, publisher, page_count) "
                + "VALUES({{id}}, {{title}}, {{stars}}, {{summary}}, {{release_date}}, {{author}}, {{publisher}}, {{page_count}})")
                .set("id", book.id())
                .set("title", book.title())
                .set("stars", book.stars(), false)
                .set("summary", book.summary())
                .set("release_date", book.release_date())
                .set("author", book.author())
                .set("publisher", book.publisher())
                .set("page_count", book.page_count())
                .update();

        ResultSet rs = createStatement("SELECT last_insert_rowid() as newId").queryRaw();
        rs.next();
        int id = rs.getInt("newId");
        book.id(id);

        return book;
    }

    public synchronized boolean update(Book book) throws ValidationError, SQLException {
        book.validate();

        return createStatement("UPDATE book SET "
                + "title = {{title}}, "
                + "stars = {{stars}}, "
                + "summary = {{summary}}, "
                + "release_date = {{release_date}}, "
                + "author = {{author}}, "
                + "publisher = {{publisher}}, "
                + "page_count = {{page_count}}"
                + "WHERE id = {{id}}")
                .set("id", book.id())
                .set("title", book.title())
                .set("stars", book.stars(), false)
                .set("summary", book.summary())
                .set("release_date", book.release_date())
                .set("author", book.author())
                .set("publisher", book.publisher())
                .set("page_count", book.page_count())
                .update() > 0;
    }

    public synchronized boolean delete(int bookId) throws ValidationError, SQLException {
        return createStatement("DELETE FROM book WHERE id = {{id}}")
                .set("id", bookId)
                .update() > 0;
    }
}
