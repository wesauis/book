package controller;

import javax.servlet.RequestDispatcher;

/**
 *
 * @author wesley.isotton
 */
public class BookController extends Controller {
    @Override
    public void index() throws Exception {
        set("A", "BAAasasasA");
        render("books/index.jsp");
    }
}
