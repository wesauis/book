package servlet;

import controller.BookController;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author wesley.isotton
 */
@WebServlet(name = "Books", urlPatterns = {"/books/*"})
public class BookServlet extends Servlet<BookController> {
    
    public BookServlet() {
        super(new BookController());
    }
    
}
