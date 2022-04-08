package controller;

import exeptions.ValidationError;
import helper.Parse;
import model.Book;
import repository.BookRepository;

/**
 *
 * @author wesley.isotton
 */
public class BookController extends Controller {

    @Override
    public void index() throws Exception {
        set("books", BookRepository.instance().all());
        render("book/index.jsp");
    }

    @Override
    public void create() throws Exception {
        create(new Book(), null);
    }
    
    public void create(Book book, String error) throws Exception {
        set("isNew", true);
        set("book", book);
        set("error", error);
        set("endpoint", "books");
        render("book/form.jsp");
    }

    public Book getBook() {
        String id = request().getParameter("id");
        String title = request().getParameter("title");
        String stars = request().getParameter("stars");
        String summary = request().getParameter("summary");
        String release_date = request().getParameter("release_date");
        String author = request().getParameter("author");
        String publisher = request().getParameter("publisher");
        String page_count = request().getParameter("page_count");

        return new Book()
                .id(id)
                .title(Parse.asString(title))
                .stars(Parse.asInt(stars))
                .summary(Parse.asString(summary))
                .release_date(Parse.asDate(release_date))
                .author(Parse.asString(author))
                .publisher(Parse.asString(publisher))
                .page_count(Parse.asInt(page_count));
    }

    @Override
    public void store() throws Exception {
        Book book = getBook();
        try {
            BookRepository.instance().create(book);
        } catch (ValidationError err) {
            create(book, err.getMessage());
        }
        
        out().write(request().getContextPath());
        out().write("books");
        out().flush();
    }

    @Override
    public void delete() throws Exception {
        BookRepository.instance().delete(request().getParameter("id"));
        response().sendRedirect("/books");
    }
}
