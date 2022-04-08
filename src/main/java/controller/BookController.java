package controller;

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
    public void delete() throws Exception {
        Integer id = Integer.parseInt(request().getParameter("id"));
        BookRepository.instance().delete(id);
        response().sendRedirect("/books");
    }
}
