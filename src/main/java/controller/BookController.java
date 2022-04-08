package controller;

import javax.servlet.RequestDispatcher;

/**
 *
 * @author wesley.isotton
 */
public class BookController extends Controller {
    @Override
    public void index() throws Exception {
        request().setAttribute("A", "B");
        RequestDispatcher rd = dispatcher("META-INF/pages/books/index.jsp");
        rd.forward(request(), response());
    }
}
